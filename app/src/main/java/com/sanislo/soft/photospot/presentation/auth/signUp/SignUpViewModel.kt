package com.sanislo.soft.photospot.presentation.auth.signUp

import android.net.Uri
import android.util.Log.d
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sanislo.soft.photospot.domain.model.User
import com.sanislo.soft.photospot.global.SingleLiveEvent

class SignUpViewModel : ViewModel() {
    private val mFirebaseAuth = FirebaseAuth.getInstance()
    val mSignUpCompleteEvent: SingleLiveEvent<Void> = SingleLiveEvent()
    val mErrorSingleLiveEVent: SingleLiveEvent<Throwable> = SingleLiveEvent()
    val mIsSignUpInProgress = ObservableField<Boolean>(false)
    val mAvatar = MutableLiveData<Uri>()

    public fun signUp(email: String, password: String, name: String) {
        mIsSignUpInProgress.set(true)
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    d(TAG, "success $authResult")
                    val user = User(authResult.user.uid, name, null)
                    if (mAvatar.value == null) saveUser(user) else uploadAvatar(user, mAvatar.value!!)
                }
                .addOnFailureListener {
                    d(TAG, "failure $it")
                    mErrorSingleLiveEVent.value = it
                }
                .addOnCanceledListener {
                    d(TAG, "canceled")
                    mIsSignUpInProgress.set(false)
                }
    }

    fun saveUser(user: User) {
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(user.uid)
                .set(user)
                .addOnSuccessListener {
                    mSignUpCompleteEvent.call()
                }
                .addOnFailureListener {
                    d(TAG, "failure $it")
                    mFirebaseAuth.signOut()
                    mErrorSingleLiveEVent.value = it
                }
                .addOnCompleteListener {
                    mIsSignUpInProgress.set(false)
                }
    }

    fun uploadAvatar(user: User, avatar: Uri) {
        FirebaseStorage.getInstance().reference
                .child("users")
                .child(user.uid)
                .child("avatar")
                .putFile(avatar)
                .addOnSuccessListener {
                    saveUser(user.copy(avatar = it.uploadSessionUri.toString()))
                }
                .addOnFailureListener {
                    mErrorSingleLiveEVent.value = it
                    mIsSignUpInProgress.set(false)
                }
    }

    fun avatarPicked(uri: Uri?) {
        mAvatar.value = uri
    }

    companion object {
        val TAG = SignUpViewModel::class.java.simpleName
    }
}
