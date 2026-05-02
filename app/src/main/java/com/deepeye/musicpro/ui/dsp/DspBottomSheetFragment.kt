package com.deepeye.musicpro.ui.dsp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.deepeye.musicpro.databinding.ScreenDspSheetBinding
import com.deepeye.musicpro.dsp.DSPPreset
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class DspBottomSheetFragment : BottomSheetDialogFragment() {
    private var binding: ScreenDspSheetBinding? = null
    private val viewModel: DspViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ScreenDspSheetBinding.inflate(inflater, container, false)
        return binding!!.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        populatePresetChips()
        lifecycleScope.launch {
            viewModel.currentPreset.collect { preset ->
                binding?.dspTitle?.text = preset.name
                binding?.dspSummary?.text = "Limiter ${preset.limiterCeilingDb} dB • Bass ${(preset.bassBoost * 100).toInt()}%"
                checkPresetChip(preset)
            }
        }
        binding?.dspMasterToggle?.setOnCheckedChangeListener { _, isChecked -> viewModel.setEnabled(isChecked) }
        binding?.dspReset?.setOnClickListener { viewModel.applyPreset(DSPPreset.Flat) }
    }

    private fun populatePresetChips() {
        val chipGroup = binding?.presetChipGroup ?: return
        chipGroup.removeAllViews()
        DSPPreset.BuiltIns.forEach { preset ->
            val chip = Chip(requireContext()).apply {
                text = preset.name
                tag = preset.id
                isCheckable = true
                isClickable = true
                setOnClickListener { viewModel.applyPreset(preset) }
            }
            chipGroup.addView(chip)
        }
    }

    private fun checkPresetChip(preset: DSPPreset) {
        val chipGroup = binding?.presetChipGroup ?: return
        for (index in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(index) as? Chip ?: continue
            chip.isChecked = chip.tag == preset.id
        }
    }

    override fun onDestroyView() { binding = null; super.onDestroyView() }
}
