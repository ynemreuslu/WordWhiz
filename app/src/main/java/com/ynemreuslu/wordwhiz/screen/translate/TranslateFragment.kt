package com.ynemreuslu.wordwhiz.screen.translate

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ynemreuslu.wordwhiz.databinding.FragmentTranslateBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TranslateFragment : Fragment() {

    private var _binding: FragmentTranslateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TranslateViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTranslateBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_dropdown_item, viewModel.availableLanguages
        )
        binding.sourceLangSelector.adapter = adapter
        binding.targetLangSelector.adapter = adapter
        binding.sourceLangSelector.setSelection(adapter.getPosition(TranslateViewModel.Language("tr")))
        binding.targetLangSelector.setSelection(adapter.getPosition(TranslateViewModel.Language("en")))

        binding.sourceLangSelector.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    setProgressText(binding.targetText)
                    viewModel.sourceLang.value = adapter.getItem(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.targetText.text = ""
                }
            }
        binding.targetLangSelector.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    setProgressText(binding.targetText)
                    viewModel.targetLang.value = adapter.getItem(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    binding.targetText.text = ""
                }
            }

        binding.buttonSwitchLang.setOnClickListener {
            val targetText = binding.targetText.text.toString()
            setProgressText(binding.targetText)
            val sourceLangPosition = binding.sourceLangSelector.selectedItemPosition
            binding.sourceLangSelector.setSelection(binding.targetLangSelector.selectedItemPosition)
            binding.targetLangSelector.setSelection(sourceLangPosition)

            // Also update srcTextView with targetText
            binding.sourceText.setText(targetText)
            viewModel.sourceText.value = targetText
        }

        // Set up toggle buttons to delete or download remote models locally.
        binding.buttonSyncSource.setOnCheckedChangeListener { _, isChecked ->
            val language = adapter.getItem(binding.sourceLangSelector.selectedItemPosition)
            if (isChecked) {
                viewModel.downloadLanguage(language!!)
            } else {
                viewModel.deleteLanguage(language!!)
            }
        }
        binding.buttonSyncTarget.setOnCheckedChangeListener { _, isChecked ->
            val language = adapter.getItem(binding.targetLangSelector.selectedItemPosition)
            if (isChecked) {
                viewModel.downloadLanguage(language!!)
            } else {
                viewModel.deleteLanguage(language!!)
            }
        }

        // Translate input text as it is typed
        binding.sourceText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                setProgressText(binding.targetText)
                viewModel.sourceText.postValue(s.toString())
            }
        })

        viewModel.translatedText.observe(viewLifecycleOwner) { resultOrError ->
            if (resultOrError.error != null) {
                binding.sourceText.error = resultOrError.error!!.localizedMessage
            } else {
                binding.targetText.text = resultOrError.result
            }
        }

        // Update sync toggle button states based on downloaded models list.
        viewModel.availableModels.observe(viewLifecycleOwner) { translateRemoteModels ->
            val output = getString(
                com.ynemreuslu.wordwhiz.R.string.downloaded_models_label,
                translateRemoteModels
            )
            binding.downloadedModels.text = output

            binding.buttonSyncSource.isChecked = !viewModel.requiresModelDownload(
                adapter.getItem(binding.sourceLangSelector.selectedItemPosition)!!,
                translateRemoteModels
            )
            binding.buttonSyncTarget.isChecked = !viewModel.requiresModelDownload(
                adapter.getItem(binding.targetLangSelector.selectedItemPosition)!!,
                translateRemoteModels
            )
        }
    }

    private fun setProgressText(tv: TextView) {
        tv.text = getString(com.ynemreuslu.wordwhiz.R.string.translate_progress)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
