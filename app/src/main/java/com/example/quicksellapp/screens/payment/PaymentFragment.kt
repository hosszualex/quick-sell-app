package com.example.quicksellapp.screens.payment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.quicksellapp.Constants
import com.example.quicksellapp.R
import com.example.quicksellapp.databinding.FragmentPaymentBinding
import com.example.quicksellapp.extensions.popBackStackUntilFragmentInclusive

class PaymentFragment: Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private lateinit var viewModel: PaymentViewModel
    private var totalCost: Float = 0f

    private val onHasPayed = Observer<Boolean> { hasPayed ->
        if (hasPayed) {
            createSuccessDialog().show()
        } else {
            activity?.popBackStackUntilFragmentInclusive(Constants.QUICK_SELL_SCREEN_TAG)
        }
    }

    private fun createSuccessDialog(): SweetAlertDialog {
        return SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(resources.getString(R.string.success_payment_title))
            .setContentText(resources.getString(R.string.success_payment_message))
            .setConfirmClickListener { dialog ->
                activity?.popBackStackUntilFragmentInclusive(Constants.QUICK_SELL_SCREEN_TAG)
                dialog.dismiss()
            }
    }

    fun newInstance(data: Float): PaymentFragment {
        val fragment = PaymentFragment()
        val args = Bundle()
        args.putFloat(Constants.PAYMENT_AMOUNT_KEY, data)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        totalCost = arguments?.getFloat(Constants.PAYMENT_AMOUNT_KEY) ?: 0f
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = initializeScreen(inflater)
        connectViewModel()
        return rootView
    }

    private fun initializeScreen(inflater: LayoutInflater): View {
        binding = FragmentPaymentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.totalCost = String.format("%.2f", totalCost)
        binding.executePendingBindings()
        return binding.root
    }

    private fun connectViewModel() {
        viewModel.onHasPayed.observe(viewLifecycleOwner, onHasPayed)
    }
}