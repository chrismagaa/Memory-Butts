package com.camg_apps.memory_butts.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.camg_apps.memory_butts.MemoryGameViewModel
import com.camg_apps.memory_butts.R
import com.camg_apps.memory_butts.databinding.FragmentMemoryGameHardBinding
import kotlin.properties.Delegates


class MemoryGameFragment : Fragment() {

    companion object {
        const val ARG_AMOUNT_PAIRS = "arg_amount_pairs"
        const val ARG_SHOW_PREVIEW = "arg_show_preview"
        const val ARG_AUTO_HIDE = "arg_auto_hide"
    }

    private lateinit var imageViewList: List<ImageView>
    private lateinit var cardViewList: List<CardView>

    private val vmMemoryGame: MemoryGameViewModel by viewModels()

    private var amounPairs by Delegates.notNull<Int>()
    private var showPreview by Delegates.notNull<Boolean>()
    private var autoHide by Delegates.notNull<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amounPairs = it.getInt(ARG_AMOUNT_PAIRS)
            showPreview = it.getBoolean(ARG_SHOW_PREVIEW)
            autoHide = it.getBoolean(ARG_AUTO_HIDE)
        }

    }

    private var _binding: FragmentMemoryGameHardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemoryGameHardBinding.inflate(inflater, container, false)
        setViewLists()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vmMemoryGame.iMoves.observe(viewLifecycleOwner) { iMoves ->
            iMoves?.let { binding.tvMoves.text = "$it Moves" }
        }

        vmMemoryGame.lTime.observe(viewLifecycleOwner) { lTime ->
            lTime?.let {
                binding.tvTime.text = "$it s"
                if (lTime == 0L) {
                    //Pierde
                    binding.tvVeredicto.text = getString(R.string.txt_you_lost)
                    binding.tvVeredicto.visibility = View.VISIBLE
                    binding.tvBackToMenu.visibility = View.VISIBLE
                    binding.tvBackToMenu.setOnClickListener { findNavController().popBackStack() }
                }
            }
        }

        vmMemoryGame.newGame(imageViewList, cardViewList, amounPairs, showPreview, autoHide)
        vmMemoryGame.playGame(requireContext())
        {
            //Gana
            binding.tvVeredicto.text = getString(R.string.txt_you_win)
            binding.tvVeredicto.visibility = View.VISIBLE
            binding.tvBackToMenu.visibility = View.VISIBLE
            binding.tvBackToMenu.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setViewLists() {
        imageViewList = listOf(
            binding.ivCard1, binding.ivCard2, binding.ivCard3, binding.ivCard4,
            binding.ivCard6, binding.ivCard7, binding.ivCard8, binding.ivCard9,
            binding.ivCard11, binding.ivCard12, binding.ivCard13, binding.ivCard14,
            binding.ivCard16, binding.ivCard17, binding.ivCard18, binding.ivCard19,
            binding.ivCard21, binding.ivCard22, binding.ivCard23, binding.ivCard24,
            binding.ivCard26, binding.ivCard27, binding.ivCard28, binding.ivCard29,
            binding.ivCard5, binding.ivCard10, binding.ivCard15, binding.ivCard20,
            binding.ivCard25, binding.ivCard30
        )

        cardViewList = listOf(
            binding.cv1, binding.cv2, binding.cv3, binding.cv4,
            binding.cv6, binding.cv7, binding.cv8, binding.cv9,
            binding.cv11, binding.cv12, binding.cv13, binding.cv14,
            binding.cv16, binding.cv17, binding.cv18, binding.cv19,
            binding.cv21, binding.cv22, binding.cv23, binding.cv24,
            binding.cv26, binding.cv27, binding.cv28, binding.cv29,
            binding.cv5, binding.cv10, binding.cv15, binding.cv20,
            binding.cv25, binding.cv30
        )
    }




}