package com.example.quicksellapp.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.quicksellapp.R

val FragmentActivity.supportFragmentManagerTransaction: FragmentTransaction
    get() {
        return this.supportFragmentManager.beginTransaction()
    }

fun FragmentActivity.addFragmentOnTop(fragment: Fragment, tag: String) {
    supportFragmentManagerTransaction
        .add(R.id.fragmentContainer, fragment, tag)
        .addToBackStack(tag)
        .setReorderingAllowed(true)
        .commit()
}

fun FragmentActivity.addFragmentOnTopWithAnimationLeftToRight(fragment: Fragment, tag: String) {
    supportFragmentManagerTransaction
        .setCustomAnimations(
            R.anim.left_to_right,
            R.anim.left_to_right_end,
            R.anim.right_to_left,
            R.anim.right_to_left_end
        )
        .add(R.id.fragmentContainer, fragment, tag)
        .addToBackStack(tag)
        .setReorderingAllowed(true)
        .commit()
}

fun FragmentActivity.lastFragment(): Fragment? {
    return if (supportFragmentManager.fragments.isEmpty()) {
        null
    } else {
        supportFragmentManager.fragments.last()
    }
}
