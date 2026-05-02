package com.deepeye.musicpro.ui.v4a

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.deepeye.musicpro.databinding.ItemV4aEffectToggleBinding
import com.deepeye.musicpro.databinding.ScreenV4aSheetBinding
import com.deepeye.musicpro.dsp.v4a.V4AEffect
import com.deepeye.musicpro.dsp.v4a.V4AEngineState
import com.deepeye.musicpro.dsp.v4a.V4APreset
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File

class V4ABottomSheetFragment : BottomSheetDialogFragment() {
    private var binding: ScreenV4aSheetBinding? = null
    private val viewModel: V4AViewModel by viewModels()
    private val effectSwitches = mutableMapOf<V4AEffect, SwitchMaterial>()
    private var rendering = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ScreenV4aSheetBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        populateEffectToggles()
        configureSliders()
        binding?.v4aMasterToggle?.setOnCheckedChangeListener { _, checked -> if (!rendering) viewModel.setMasterEnabled(checked) }
        binding?.fetAttackSlider?.addOnChangeListener { _, value, _ -> if (!rendering) pushFetFromSliders(attack = value) }
        binding?.fetReleaseSlider?.addOnChangeListener { _, value, _ -> if (!rendering) pushFetFromSliders(release = value) }
        binding?.fetThresholdSlider?.addOnChangeListener { _, value, _ -> if (!rendering) pushFetFromSliders(threshold = value) }
        binding?.fetKneeSlider?.addOnChangeListener { _, value, _ -> if (!rendering) pushFetFromSliders(knee = value) }
        binding?.tubeWarmthSlider?.addOnChangeListener { _, value, _ -> if (!rendering) viewModel.setTubeWarmth(value) }
        binding?.savePresetButton?.setOnClickListener { viewModel.saveCurrentPreset() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.engineState.collect(::renderState) }
                launch { viewModel.presets.collect(::populatePresetChips) }
                launch { viewModel.irsFiles.collect(::populateConvolverChips) }
                launch { viewModel.ddcFiles.collect(::populateDdcChips) }
                launch { animateSpectrum() }
            }
        }
    }

    private fun configureSliders() {
        binding?.fetAttackSlider?.apply { valueFrom = 1f; valueTo = 80f; value = 6f }
        binding?.fetReleaseSlider?.apply { valueFrom = 20f; valueTo = 600f; value = 120f }
        binding?.fetThresholdSlider?.apply { valueFrom = -36f; valueTo = 0f; value = -10f }
        binding?.fetKneeSlider?.apply { valueFrom = 0f; valueTo = 18f; value = 6f }
        binding?.tubeWarmthSlider?.apply { valueFrom = 0f; valueTo = 1f; value = 0.25f }
    }

    private fun populateEffectToggles() {
        val container = binding?.effectToggleContainer ?: return
        container.removeAllViews()
        effectSwitches.clear()
        V4AEffect.values().forEach { effect ->
            val item = ItemV4aEffectToggleBinding.inflate(layoutInflater, container, false)
            item.effectTitle.text = effect.label
            item.effectSubtitle.text = when (effect) {
                V4AEffect.EQ -> "10-band gain shaping before V4A dynamics"
                V4AEffect.CONVOLVER -> "IRS room, surround, and bass impulses"
                V4AEffect.DDC -> "Headphone correction curves"
                V4AEffect.FET -> "Fast analog-style compression"
                V4AEffect.TUBE -> "Even-harmonic warmth and saturation"
                V4AEffect.REVERB -> "Room tail for depth and concert presets"
            }
            item.effectSwitch.setOnCheckedChangeListener { _, checked -> if (!rendering) viewModel.setEffectEnabled(effect, checked) }
            effectSwitches[effect] = item.effectSwitch
            container.addView(item.root)
        }
    }

    private fun populatePresetChips(presets: List<V4APreset>) {
        val chipGroup = binding?.presetChipGroup ?: return
        chipGroup.removeAllViews()
        presets.forEach { preset ->
            chipGroup.addView(Chip(requireContext()).apply {
                text = preset.name
                tag = preset.id
                isCheckable = true
                isClickable = true
                setOnClickListener {
                    Log.d(TAG, "presetChipTap id=${preset.id} name=${preset.name}")
                    viewModel.applyPreset(preset)
                }
            })
        }
        renderState(viewModel.engineState.value)
    }

    private fun populateConvolverChips(files: List<File>) {
        val chipGroup = binding?.convolverChipGroup ?: return
        chipGroup.removeAllViews()
        files.forEach { file ->
            chipGroup.addView(Chip(requireContext()).apply {
                text = file.nameWithoutExtension.replace('_', ' ')
                tag = file.name
                isCheckable = true
                isClickable = true
                setOnClickListener { viewModel.selectConvolver(file) }
            })
        }
        renderState(viewModel.engineState.value)
    }

    private fun populateDdcChips(files: List<File>) {
        val chipGroup = binding?.ddcChipGroup ?: return
        chipGroup.removeAllViews()
        files.forEach { file ->
            chipGroup.addView(Chip(requireContext()).apply {
                text = file.nameWithoutExtension.replace('_', ' ')
                tag = file.name
                isCheckable = true
                isClickable = true
                setOnClickListener { viewModel.selectDDC(file) }
            })
        }
        renderState(viewModel.engineState.value)
    }

    private fun renderState(state: V4AEngineState) {
        val currentBinding = binding ?: return
        rendering = true
        currentBinding.v4aMasterToggle.isChecked = state.effects.masterEnabled
        currentBinding.modeIndicator.bind(state)
        currentBinding.currentPresetTitle.text = state.currentPresetName
        currentBinding.v4aModeSummary.text = state.message
        effectSwitches.forEach { (effect, switch) -> switch.isChecked = state.effects.isEnabled(effect) }
        currentBinding.fetAttackSlider.value = state.effects.fetAttack.coerceIn(currentBinding.fetAttackSlider.valueFrom, currentBinding.fetAttackSlider.valueTo)
        currentBinding.fetReleaseSlider.value = state.effects.fetRelease.coerceIn(currentBinding.fetReleaseSlider.valueFrom, currentBinding.fetReleaseSlider.valueTo)
        currentBinding.fetThresholdSlider.value = state.effects.fetThreshold.coerceIn(currentBinding.fetThresholdSlider.valueFrom, currentBinding.fetThresholdSlider.valueTo)
        currentBinding.fetKneeSlider.value = state.effects.fetKnee.coerceIn(currentBinding.fetKneeSlider.valueFrom, currentBinding.fetKneeSlider.valueTo)
        currentBinding.tubeWarmthSlider.value = state.effects.tubeWarmth.coerceIn(currentBinding.tubeWarmthSlider.valueFrom, currentBinding.tubeWarmthSlider.valueTo)
        checkChip(currentBinding.presetChipGroup, state.currentPresetName)
        checkChip(currentBinding.convolverChipGroup, state.effects.convolverIR)
        checkChip(currentBinding.ddcChipGroup, state.effects.ddcProfile)
        rendering = false
    }

    private fun checkChip(group: ChipGroup, selected: String?) {
        for (index in 0 until group.childCount) {
            val chip = group.getChildAt(index) as? Chip ?: continue
            chip.isChecked = chip.tag == selected || chip.text == selected
        }
    }

    private fun pushFetFromSliders(
        attack: Float? = null,
        release: Float? = null,
        threshold: Float? = null,
        knee: Float? = null
    ) {
        val currentBinding = binding ?: return
        viewModel.setFET(
            attack ?: currentBinding.fetAttackSlider.value,
            release ?: currentBinding.fetReleaseSlider.value,
            threshold ?: currentBinding.fetThresholdSlider.value,
            knee ?: currentBinding.fetKneeSlider.value
        )
    }

    private suspend fun animateSpectrum() {
        val bins = FloatArray(32)
        while (currentCoroutineContext().isActive) {
            viewModel.getSpectrumMagnitudes(bins)
            binding?.spectrumVisualizer?.setMagnitudes(bins)
            delay(SPECTRUM_FRAME_MS)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private companion object {
        const val SPECTRUM_FRAME_MS = 16L
        const val TAG = "V4ABottomSheet"
    }
}
