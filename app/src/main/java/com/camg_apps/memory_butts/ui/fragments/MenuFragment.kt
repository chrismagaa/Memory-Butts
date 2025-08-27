package com.camg_apps.memory_butts.ui.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.camg_apps.memory_butts.*
import com.camg_apps.memory_butts.databinding.FragmentMenuBinding
import com.camg_apps.memory_butts.models.CardModel
import com.camg_apps.memory_butts.ui.CardsShowActivity
import com.camg_apps.memory_butts.ui.RankingActivity
import com.camg_apps.memory_butts.utils.PreferencesKey
import com.camg_apps.memory_butts.utils.PreferencesProvider
import com.camg_apps.memory_butts.utils.RankingModel
import com.google.gson.Gson

import java.io.File

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)



        binding.rgDifficulty.setOnCheckedChangeListener { radioGroup, i ->
            reproduceSound(R.raw.click)
        }

        binding.ivLogo.setOnClickListener {
           //goToCardsShowActivity
            val intent = Intent(requireContext(), CardsShowActivity::class.java)
            startActivity(intent)
        }

        binding.tvRanking.setOnClickListener {
            reproduceSound(R.raw.click)
           //go To Ranking Activity
           val intent = Intent(requireContext(), RankingActivity::class.java)
              startActivity(intent)
        }

        binding.tvPlay.setOnClickListener {
            reproduceSound(R.raw.click)

            val difficulty = when{
                binding.rbEasy.isChecked -> MemoryGameViewModel.AMOUNT_PAIRS_EASY
                binding.rbMedium.isChecked -> MemoryGameViewModel.AMOUNT_PAIRS_MEDIUM
                binding.rbDifficult.isChecked -> MemoryGameViewModel.AMOUNT_PAIRS_DIFFICULT
                else -> MemoryGameViewModel.AMOUNT_PAIRS_EASY
            }

            var dificultadNombre = when{
                binding.rbEasy.isChecked -> PreferencesKey.RANKING_EASY
                binding.rbMedium.isChecked -> PreferencesKey.RANKING_MEDIUM
                binding.rbDifficult.isChecked -> PreferencesKey.RANKING_DIFFICULT
                else -> PreferencesKey.RANKING_EASY
            }

         //   val showPreview = binding.switchPreview.isChecked
           // val autoHide = binding.switchAutoHide.isChecked


            val bundle = bundleOf(MemoryGameFragment.ARG_AMOUNT_PAIRS to difficulty)

            findNavController().navigate(R.id.memoryGameFragment, bundle)


        }

        return binding.root
    }

    private fun reproduceSound(sonido: Int = R.raw.click) {
        val mediaPlayer = MediaPlayer.create(requireContext(), sonido)
        mediaPlayer.start()
    }


}