package com.ynemreuslu.wordwhiz.screen.worddetails

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.ynemreuslu.wordwhiz.R
import com.ynemreuslu.wordwhiz.databinding.FragmentWordDetailsBinding
import com.ynemreuslu.wordwhiz.data.model.remote.WordData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordDetailsFragment : Fragment() {

    private var _binding: FragmentWordDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WordDetailsViewModel by viewModels()
    private val args: WordDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordDetailsBinding.inflate(inflater, container, false)
        viewModel.fetchWordDetails(args.wordsArg.english)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        setupListeners()
        hideBottomNavigation()
    }

    private fun bindData() {
        binding.tvEnglishWord.text = args.wordsArg.english
        binding.tvTurkishWord.text = args.wordsArg.turkish

        // Observe loading state and word details


        viewModel.wordDetails.observe(viewLifecycleOwner) { wordDefinitions ->
            updateWordExample(wordDefinitions)
            updatePronunciationText(wordDefinitions)
            setupAudio(wordDefinitions.firstOrNull()?.phonetics?.getOrNull(0)?.audio)
            updateSynonyms(wordDefinitions)

        }
    }

    private fun updateWordExample(wordDefinitions: List<WordData>) {
        val example = wordDefinitions.firstOrNull()
            ?.meanings?.firstOrNull()?.definitions?.firstOrNull()?.example ?: "No example found"
        binding.tvExample.text = example
    }

    private fun updatePronunciationText(wordDefinitions: List<WordData>) {
        val pronunciation = wordDefinitions.firstOrNull()
            ?.phonetics?.firstOrNull()?.text ?: "Not found"
        binding.tvPronunciation.text = pronunciation
    }

    private fun updateSynonyms(wordDefinitions: List<WordData>) {
        val synonymsList = wordDefinitions.firstOrNull()
            ?.meanings?.firstOrNull()?.synonyms

        binding.tvSynonyms.text = if (synonymsList.isNullOrEmpty()) {
            "No synonyms found"
        } else {
            synonymsList.joinToString(", ")
        }
    }


    private fun setupAudio(audioUri: String?) {
        if (audioUri.isNullOrEmpty()) {
            binding.btnPlayPronunciation.isEnabled = false
            return
        }

        binding.btnPlayPronunciation.setOnClickListener {
            playAudio(audioUri)
        }
    }

    private fun playAudio(audioUri: String) {
        try {
            val mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(requireContext(), audioUri.toUri())
                prepare()
                start()
            }
        } catch (e: Exception) {
            Log.e("WordDetailsFragment", "Error playing audio", e)
        }
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnLearned.setOnClickListener {
            markWordAsLearned()
            findNavController().popBackStack()
        }
    }

    private fun markWordAsLearned() {
        val word = args.wordsArg
        if (viewModel.isWordInLearnedList(word)) {
            showSnackbar("Word already in learned list")
        } else {
            viewModel.addWordToLearnedList(word)
            showSnackbar("Word added to learned list")
            findNavController().popBackStack()
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun hideBottomNavigation() {
        (requireActivity() as? AppCompatActivity)?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility =
            View.GONE
    }

    private fun showBottomNavigation() {
        (requireActivity() as? AppCompatActivity)?.findViewById<BottomNavigationView>(R.id.bottomNav)?.visibility =
            View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        // Restore bottom navigation when fragment is destroyed
        showBottomNavigation()
    }
}