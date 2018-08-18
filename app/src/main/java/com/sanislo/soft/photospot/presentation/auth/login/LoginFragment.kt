package com.sanislo.soft.photospot.presentation.auth.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanislo.soft.photospot.R
import com.sanislo.soft.photospot.databinding.FragmentLoginBinding
import com.sanislo.soft.photospot.presentation.MainActivity
import com.sanislo.soft.photospot.presentation.auth.AuthActivity
import com.sanislo.soft.photospot.presentation.auth.signUp.SignUpFragment
import com.sanislo.soft.photospot.presentation.base.BaseFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginFragment : BaseFragment() {
    @Inject
    lateinit var mFactory: ViewModelProvider.Factory

    lateinit var mBinding: FragmentLoginBinding
    lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        obtainViewModel()
    }

    private fun obtainViewModel() {
        mViewModel = ViewModelProviders.of(
                this,
                mFactory)
                .get(LoginViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false
        )
        mBinding.viewModel = mViewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoginSuccess()
        observeLoginFailure()
        observeNavigateToSignUp()
    }

    fun observeNavigateToSignUp() {
        mViewModel.mNavigateToSignUp.observe(this, Observer {
            activity?.supportFragmentManager!!
                    .beginTransaction()
                    .replace(R.id.fl_fragment_container, SignUpFragment())
                    .addToBackStack(null)
                    .commit()
        })
    }

    fun observeLoginFailure() {
        mViewModel.mLoginFailureEvent.observe(this, Observer {
            makeSnack(mBinding.root, it?.message ?: "")
        })
    }

    fun observeLoginSuccess() {
        mViewModel.mLoginSuccessEvent.observe(this, Observer {
            navigateToMain()
        })
    }

    fun navigateToMain() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding.unbind()
    }
}