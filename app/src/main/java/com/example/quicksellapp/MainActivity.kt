package com.example.quicksellapp

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.quicksellapp.databinding.ActivityMainBinding
import com.example.quicksellapp.extensions.addFragmentOnTop
import com.example.quicksellapp.extensions.get
import com.example.quicksellapp.extensions.lastFragment
import com.example.quicksellapp.screens.home.HomeFragment
import java.util.*

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
        val config = resources.configuration
        val lang = sharedPreferences.get("LANG", "en") // your language code
        val locale = Locale(lang)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale)
        else
            config.locale = locale

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)


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