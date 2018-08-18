package com.sanislo.soft.photospot.global

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("avatar")
    fun setImageUrl(view: ImageView, image: Uri) {
        Glide.with(view.context).load(image).into(view)
    }
}