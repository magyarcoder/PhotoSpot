package com.sanislo.soft.photospot.presentation.base

import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View

open class BaseFragment : Fragment() {

    fun makeSnack(view: View, text: CharSequence = "", duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(view, text, duration).show()
    }
}