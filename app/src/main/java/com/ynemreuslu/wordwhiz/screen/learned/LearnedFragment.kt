package com.ynemreuslu.wordwhiz.screen.learned


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ynemreuslu.wordwhiz.databinding.FragmentLearnedBinding
import com.ynemreuslu.wordwhiz.data.model.Words
import com.ynemreuslu.wordwhiz.screen.learned.adapter.LearnedWordAdapter
import com.ynemreuslu.wordwhiz.util.showCustomAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LearnedFragment : Fragment() {
    private var _binding: FragmentLearnedBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: LearnedWordAdapter
    private val viewModel: LearnedWordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLearnedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = LearnedWordAdapter(emptyList()) {
            showDeleteConfirmationDialog(it)
        }
        binding.rvLeanerd.adapter = adapter
        binding.rvLeanerd.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getLearnedWords()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.learnedWords.collect { words ->
                    adapter.updateWords(words)
                }
            }
        }

        if (viewModel.isLearningListEmpty() || viewModel.learnedWords.value.isEmpty()) {
            binding.imageView.visibility = View.VISIBLE
            binding.textView.visibility = View.VISIBLE

        }
        else {
            binding.imageView.visibility = View.GONE
            binding.textView.visibility = View.GONE
        }

    }

    private fun showDeleteConfirmationDialog(word: Words) {
        showCustomAlertDialog(
            title = "Delete Word",
            message = "Are you sure you want to delete ${word.english} from the learned list?",
            positiveButtonText = "OK",
            onPositiveAction = { deleteWordFromLearnedList(word) },
            negativeButtonText = "Cancel"
        )
    }

    private fun deleteWordFromLearnedList(word: Words) {
        viewModel.deleteWord(word)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

