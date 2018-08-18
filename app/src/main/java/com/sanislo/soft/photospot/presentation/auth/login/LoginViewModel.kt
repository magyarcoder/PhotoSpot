package com.sanislo.soft.photospot.presentation.auth.login

import android.text.TextUtils
import android.util.Log.d
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.sanislo.soft.photospot.global.SingleLiveEvent

class LoginViewModel(): ViewModel() {
    val mLoginSuccessEvent = SingleLiveEvent<AuthResult>()
    val mLoginFailureEvent = SingleLiveEvent<Throwable>()
    val mAuthorizing = ObservableField<Boolean>(false)
    val mNavigateToSignUp = SingleLiveEvent<Void>()

    fun login(email: String, password: String) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) return
        mAuthorizing.set(true)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    d(TAG, "success $it")
                    d(TAG, "success ${it.user}")
                    d(TAG, "success ${it.additionalUserInfo.providerId}")
                    mLoginSuccessEvent.value = it
                }
                .addOnFailureListener {
                    d(TAG, "failure $it")
                    mLoginFailureEvent.value = it
                }
                .addOnCompleteListener {
                    mAuthorizing.set(false)
                }
                .addOnCanceledListener {
                    mAuthorizing.set(false)
                }
    }

    fun navigateToSignUpScreen() {
        mNavigateToSignUp.call()
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        val TAG = LoginViewModel::class.java.simpleName
    }
}