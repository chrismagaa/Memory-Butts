package com.camg_apps.memory_butts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.camg_apps.memory_butts.*
import com.camg_apps.memory_butts.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        binding.tvPlay.setOnClickListener {
            val difficulty = when{
                binding.rbEasy.isChecked -> MemoryGameViewModel.AMOUNT_PAIRS_EASY
                binding.rbMedium.isChecked -> MemoryGameViewModel.AMOUNT_PAIRS_MEDIUM
                binding.rbDifficult.isChecked -> MemoryGameViewModel.AMOUNT_PAIRS_DIFFICULT
                else -> MemoryGameViewModel.AMOUNT_PAIRS_EASY
            }

            val showPreview = binding.switchPreview.isChecked
            val autoHide = binding.switchAutoHide.isChecked


            val bundle = bundleOf(MemoryGameFragment.ARG_AMOUNT_PAIRS to difficulty,
                                        MemoryGameFragment.ARG_SHOW_PREVIEW to showPreview,
                                        MemoryGameFragment.ARG_AUTO_HIDE to autoHide)
            findNavController().navigate(R.id.memoryGameFragment, bundle)


        }

        return binding.root
    }






}