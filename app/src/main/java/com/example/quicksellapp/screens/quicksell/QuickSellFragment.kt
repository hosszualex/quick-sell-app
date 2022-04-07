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
import android.text.InputType
import androidx.appcompat.app.AlertDialog
import android.widget.EditText
import com.example.quicksellapp.Constants
import com.example.quicksellapp.R
import com.example.quicksellapp.extensions.addFragmentOnTopWithAnimationLeftToRight
import com.example.quicksellapp.screens.payment.PaymentFragment

class QuickSellFragment : Fragment(), ProductsAdapter.IOnProductClickListener {
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
        Toast.makeText(
            requireContext(),
            "Error Message: " + onError.errorMessage + "\nError Code: " + onError.errorCode,
            Toast.LENGTH_LONG
        ).show()
    }

    private val onNavigate = Observer<Float> { totalPayment ->
        if (totalPayment != 0f) {
            activity?.addFragmentOnTopWithAnimationLeftToRight(PaymentFragment().newInstance(totalPayment), Constants.PAYMENT_SCREEN_TAG)
        } else {
            createWarningDialog(resources.getString(R.string.warning_dialog_no_product_title)).show()
        }
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
        binding.viewModel = viewModel
        binding.executePendingBindings()
        return binding.root
    }

    private fun connectViewModel() {
        viewModel.onGetProducts.observe(viewLifecycleOwner, onGetProducts)
        viewModel.onError.observe(viewLifecycleOwner, onError)
        viewModel.onNavigate.observe(viewLifecycleOwner, onNavigate)
    }

    override fun onProductClicked(product: Product) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.amount_dialog_title))
        val input = EditText(requireContext())
        input.inputType =  InputType.TYPE_CLASS_NUMBER
        builder.setView(input)
        builder.setPositiveButton(getString(android.R.string.ok)) { _, _ ->
            handlePositiveButtonCase(input, product)
        }
        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun handlePositiveButtonCase(
        input: EditText,
        product: Product
    ) {
        val amount = if (input.editableText.toString().isBlank()) { -1 } else { input.editableText.toString().toInt() }
        if (amount != -1) {
            viewModel.updateProductAmount(product, amount)
            adapter.updateItemStatus(product.id, amount)
        } else {
            createWarningDialog(getString(R.string.warning_dialog_amount_bigger_than_zero_title)).show()
        }
    }

    private fun createWarningDialog(title: String): AlertDialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setPositiveButton(resources.getString(android.R.string.ok)) { dialog, _ ->
            dialog.cancel()
        }
        return builder.create()
    }
}