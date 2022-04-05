package com.example.quicksellapp

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.quicksellapp.extensions.addFragmentOnTop
import com.example.quicksellapp.extensions.lastFragment
import com.example.quicksellapp.databinding.ActivityMainBinding
import com.example.quicksellapp.screens.home.HomeFragment

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        this.addFragmentOnTop(HomeFragment(), Constants.HOME_SCREEN_TAG)
    }

    override fun onBackPressed() {
        val lastFragment = this.lastFragment()
        if (lastFragment is HomeFragment) {
            finish()
        }else {
            super.onBackPressed()
        }
    }
}