package com.sanislo.soft.photospot.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.sanislo.soft.photospot.R
import com.sanislo.soft.photospot.databinding.ActivityMainBinding
import com.sanislo.soft.photospot.presentation.auth.AuthActivity

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            navigateToAuth()
        }
    }

    fun navigateToAuth() {
        val intent = Intent(this, AuthActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}