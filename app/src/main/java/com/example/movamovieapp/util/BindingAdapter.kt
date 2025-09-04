package com.example.movamovieapp.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movamovieapp.R

@BindingAdapter("load_url")
fun loadImage(imageView: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
            imageView.setImageResource(R.drawable.placeholder)
    } else {
        imageView.loadImageUrl(url)
    }
}

@BindingAdapter("rating")
fun formatVote(textView: TextView, vote: Number?) {
    vote?.let {
        textView.text = String.format("%.1f", it.toDouble())
    } ?: run { textView.text = "N/A" }
}


@BindingAdapter("load_url_youtube")
fun loadImageYoutube(imageView: ImageView, key: String?) {
    if (key.isNullOrEmpty()) {
        imageView.setImageResource(R.drawable.placeholder)
    } else {
        imageView.loadImageYoutube(key)
    }
}
