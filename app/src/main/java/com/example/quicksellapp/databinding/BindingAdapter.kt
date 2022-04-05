package com.example.quicksellapp.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.quicksellapp.R
import com.squareup.picasso.Picasso

@BindingAdapter("android:srcUrl")
fun ImageView.setImageResourceFromUrl(url: String) {
    if (url.isEmpty()) {
        Picasso.get().load(R.drawable.loading_image).placeholder(R.drawable.loading_image).fit().into(this)
    } else {
        Picasso.get().load(url).placeholder(R.drawable.loading_image).fit().into(this)
    }
}

