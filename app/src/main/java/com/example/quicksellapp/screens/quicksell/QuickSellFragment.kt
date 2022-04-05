package com.example.quicksellapp.screens.quicksell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quicksellapp.databinding.FragmentQuickSellBinding
import com.example.quicksellapp.model.ErrorResponse
import com.example.quicksellapp.model.Product

class QuickSellFragment: Fragment(), ProductsAdapter.IOnProductClickListener {
    private lateinit var binding: FragmentQuickSellBinding
    private lateinit var viewModel: QuickSellViewModel
    private lateinit var adapter: ProductsAdapter

    private val onGetProducts = Observer<List<Product>> { products ->
        if (!this::adapter.isInitialized) {
            initializeAdapter()
        }
        adapter.setDataSource(products)
    }

    private fun initializeAdapter() {
        adapter = ProductsAdapter(this)
        binding.rvPosts.adapter = adapter
    }


    private val onError = Observer<ErrorResponse> { onError ->
        Toast.makeText(requireContext(), "Error Message: " + onError.errorMessage + "\nError Code: " + onError.errorCode, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuickSellViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = initializeScreen(inflater)
        viewModel.retrieveProducts()
        connectViewModel()
        return rootView
    }

    private fun initializeScreen(inflater: LayoutInflater): View {
        binding = FragmentQuickSellBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        return binding.root
    }

    private fun connectViewModel() {
        viewModel.onGetProducts.observe(viewLifecycleOwner, onGetProducts)
        viewModel.onError.observe(viewLifecycleOwner, onError)
    }

    override fun onProductClicked(order: Product) {
        //TODO("Not yet implemented")
    }
}