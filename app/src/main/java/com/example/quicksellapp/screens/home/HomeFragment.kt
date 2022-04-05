package com.example.quicksellapp.screens.home

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.example.quicksellapp.Constants
import com.example.quicksellapp.R
import com.example.quicksellapp.databinding.FragmentHomeBinding
import com.example.quicksellapp.extensions.addFragmentOnTopWithAnimationLeftToRight
import com.example.quicksellapp.screens.quicksell.QuickSellFragment

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initializeScreen(inflater)
    }

    private fun initializeScreen(inflater: LayoutInflater): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        //TODO
        val items = listOf("English", "Romanian")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_language, items)
        (binding.tilLanguageMenu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.btnQuickSell.setOnClickListener {
            activity?.addFragmentOnTopWithAnimationLeftToRight(QuickSellFragment(), Constants.QUICK_SELL_SCREEN_TAG)
        }
        return binding.root
    }
}