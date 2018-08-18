package com.sanislo.soft.photospot.presentation.base

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    fun makeSnack(view: View, text: CharSequence = "", duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(view, text, duration).show()
    }
}