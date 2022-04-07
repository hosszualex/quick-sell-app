package com.example.quicksellapp.screens.home

import android.content.Context
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
import com.example.quicksellapp.extensions.put
import com.example.quicksellapp.screens.quicksell.QuickSellFragment

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

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
        binding.executePendingBindings()
        //TODO develop language feature
        val items = listOf("English", "Romanian")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_language, items)
        (binding.tilLanguageMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.btnQuickSell.setOnClickListener {
            activity?.addFragmentOnTopWithAnimationLeftToRight(QuickSellFragment(), Constants.QUICK_SELL_SCREEN_TAG)
        }
        binding.tvLanguage.setOnItemClickListener { _, _, _, _ ->
            val languageText = binding.tvLanguage.text.toString()
            val sharedPreferences =
                requireContext().getSharedPreferences("name", Context.MODE_PRIVATE)
            if (languageText == "English") {
                sharedPreferences.put("LANG", "en")
            } else {
                sharedPreferences.put("LANG", "ro")
            }
            requireActivity().recreate()
        }
        return binding.root
    }
}