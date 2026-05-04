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
import com.deepeye.musicpro.databinding.BottomsheetV4aM3Binding
import com.deepeye.musicpro.dsp.v4a.V4AEffect
import com.deepeye.musicpro.dsp.v4a.V4AEngineState
import com.deepeye.musicpro.dsp.v4a.V4APreset
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File

class V4ABottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomsheetV4aM3Binding? = null
    private val binding get() = _binding!!
    private val viewModel: V4AViewModel by viewModels()
    private var rendering = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomsheetV4aM3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.v4aMasterSwitch.setOnCheckedChangeListener { _, checked -> 
            if (!rendering) viewModel.setMasterEnabled(checked) 
        }
        
        binding.tubeWarmthSlider.addOnChangeListener { _, value, _ -> 
            if (!rendering) viewModel.setTubeWarmth(value) 
        }
        
        binding.fetThresholdSlider.addOnChangeListener { _, value, _ -> 
            if (!rendering) viewModel.setFET(threshold = value) 
        }
        
        binding.savePresetButton.setOnClickListener { viewModel.saveCurrentPreset() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.engineState.collect(::renderState) }
                launch { viewModel.presets.collect(::populatePresetChips) }
                launch { viewModel.irsFiles.collect(::populateConvolverChips) }
            }
        }
    }

    private fun populatePresetChips(presets: List<V4APreset>) {
        binding.presetChipGroup.removeAllViews()
        presets.forEach { preset ->
            binding.presetChipGroup.addView(Chip(android.view.ContextThemeWrapper(requireContext(), com.google.android.material.R.style.Widget_Material3_Chip_Filter)).apply {
                text = preset.name
                tag = preset.id
                isCheckable = true
                setOnClickListener { viewModel.applyPreset(preset) }
            })
        }
    }

    private fun populateConvolverChips(files: List<File>) {
        binding.convolverChipGroup.removeAllViews()
        files.forEach { file ->
            binding.convolverChipGroup.addView(Chip(android.view.ContextThemeWrapper(requireContext(), com.google.android.material.R.style.Widget_Material3_Chip_Filter)).apply {
                text = file.nameWithoutExtension.replace('_', ' ')
                tag = file.name
                isCheckable = true
                setOnClickListener { viewModel.selectConvolver(file) }
            })
        }
    }

    private fun renderState(state: V4AEngineState) {
        rendering = true
        binding.v4aMasterSwitch.isChecked = state.effects.masterEnabled
        binding.tubeWarmthSlider.value = state.effects.tubeWarmth.coerceIn(binding.tubeWarmthSlider.valueFrom, binding.tubeWarmthSlider.valueTo)
        binding.fetThresholdSlider.value = state.effects.fetThreshold.coerceIn(binding.fetThresholdSlider.valueFrom, binding.fetThresholdSlider.valueTo)
        
        checkChip(binding.presetChipGroup, state.currentPresetName)
        checkChip(binding.convolverChipGroup, state.effects.convolverIR)
        rendering = false
    }

    private fun checkChip(group: ChipGroup, selected: String?) {
        for (index in 0 until group.childCount) {
            val chip = group.getChildAt(index) as? Chip ?: continue
            chip.isChecked = chip.tag == selected || chip.text == selected
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private companion object {
        const val TAG = "V4ABottomSheet"
    }
}

