package com.example.quicksellapp.screens.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.quicksellapp.Constants
import com.example.quicksellapp.R
import com.example.quicksellapp.databinding.FragmentHomeBinding
import com.example.quicksellapp.extensions.addFragmentOnTopWithAnimationLeftToRight
import com.example.quicksellapp.extensions.popBackStackUntilFragmentInclusive
import com.example.quicksellapp.extensions.put
import com.example.quicksellapp.screens.quicksell.QuickSellFragment

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val currentLanguage: String by lazy {
        if (sharedPreferences.getString(Constants.PREFERENCES_LANGUAGE_KEY, Constants.LOCALE_EN) == Constants.LOCALE_EN) {
            englishLanguage
        } else {
            romanianLanguage
        }
    }
    private val englishLanguage: String by lazy { resources.getString(R.string.language_english) }
    private val romanianLanguage: String by lazy { resources.getString(R.string.language_romanian) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //TODO ask for token image
        return initializeScreen(inflater)
    }
    private fun initializeScreen(inflater: LayoutInflater): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.currentLanguage = currentLanguage
        binding.executePendingBindings()
        initializeDropDownLanguage()
        return binding.root
    }

    private fun initializeDropDownLanguage() {
        val items = listOf(englishLanguage, romanianLanguage)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_language, items)
        (binding.tilLanguageMenu.editText as AutoCompleteTextView).setAdapter(adapter)
        binding.btnQuickSell.setOnClickListener { activity?.addFragmentOnTopWithAnimationLeftToRight(QuickSellFragment(), Constants.QUICK_SELL_SCREEN_TAG) }

        binding.tvLanguage.setOnItemClickListener { _, _, _, _ ->
            onLanguageChanged()
        }
    }

    private fun onLanguageChanged() {
        val languageText = binding.tvLanguage.text.toString()
        if (languageText == englishLanguage) {
            sharedPreferences.put(Constants.PREFERENCES_LANGUAGE_KEY, Constants.LOCALE_EN)
        } else {
            sharedPreferences.put(Constants.PREFERENCES_LANGUAGE_KEY, Constants.LOCALE_RO)
        }
        requireActivity().popBackStackUntilFragmentInclusive(Constants.HOME_SCREEN_TAG)
        requireActivity().recreate()
    }
}