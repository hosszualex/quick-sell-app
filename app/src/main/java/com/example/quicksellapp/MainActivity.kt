package com.example.quicksellapp

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.quicksellapp.databinding.ActivityMainBinding
import com.example.quicksellapp.extensions.addFragmentOnTop
import com.example.quicksellapp.extensions.get
import com.example.quicksellapp.extensions.lastFragment
import com.example.quicksellapp.screens.home.HomeFragment
import java.util.*
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setupToolbar()
        setupNavigationDrawer()
        this.addFragmentOnTop(HomeFragment(), Constants.HOME_SCREEN_TAG)
    }

    override fun attachBaseContext(newBase: Context?) {
        val sharedPreferences =
            newBase?.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        val lang = sharedPreferences?.get(Constants.PREFERENCES_LANGUAGE_KEY, Constants.LOCALE_EN)
        super.attachBaseContext(ContextWrapper(newBase?.setAppLocale(lang ?: Constants.LOCALE_EN)))
    }

    private fun Context.setAppLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return createConfigurationContext(config)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportFragmentManager.addOnBackStackChangedListener {
            if (lastFragment()?.tag != Constants.HOME_SCREEN_TAG) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    private fun setupNavigationDrawer() {
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.title == getString(R.string.navigation_item_contacts_page)) {
                onContactItemClicked()
            }
            binding.drawerLayout.close()
            false
        }
    }

    private fun onContactItemClicked() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.contact_alert_title))
        builder.setPositiveButton(resources.getString(android.R.string.ok)) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (closeNavDrawerIfOpen()) return
        val lastFragment = this.lastFragment()
        if (lastFragment is HomeFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun closeNavDrawerIfOpen(): Boolean {
        if (binding.drawerLayout.isOpen) {
            binding.drawerLayout.close()
            return true
        }
        return false
    }
}