package com.example.quicksellapp

import android.os.Build
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.quicksellapp.databinding.ActivityMainBinding
import com.example.quicksellapp.extensions.addFragmentOnTop
import com.example.quicksellapp.extensions.get
import com.example.quicksellapp.extensions.lastFragment
import com.example.quicksellapp.screens.home.HomeFragment
import java.util.*

import android.os.Build.VERSION_CODES.N
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAppLanguage()
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        this.addFragmentOnTop(HomeFragment(), Constants.HOME_SCREEN_TAG)
        setSupportActionBar(binding.toolbar)
    }

    private fun setupAppLanguage() {
        val sharedPreferences =
            getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        val config = resources.configuration
        val lang = sharedPreferences.get(Constants.PREFERENCES_LANGUAGE_KEY, Constants.LOCALE_EN)
        val locale = Locale(lang)
        Locale.setDefault(locale)
        config.setLocale(locale)

        if (Build.VERSION.SDK_INT >= N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
//        menu.findItem(R.id.menu_logout).isVisible = viewModel.isLogoutVisible().value == true
//        menu.findItem(R.id.menu_search).isVisible = viewModel.isSearchVisible().value == true
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
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