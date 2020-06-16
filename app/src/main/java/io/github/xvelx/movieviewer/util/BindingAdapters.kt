package io.github.xvelx.movieviewer.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import io.github.xvelx.movieviewer.R

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView).apply {
        (if (imageUrl.startsWith("http", true))
            load(imageUrl).placeholder(R.drawable.place_holder_poster)
        else
            load(R.drawable.place_holder_poster)).into(imageView)

    }
}