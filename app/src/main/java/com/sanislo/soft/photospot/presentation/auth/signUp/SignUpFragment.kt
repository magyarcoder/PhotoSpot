package com.sanislo.soft.photospot.presentation.auth.signUp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.sanislo.soft.photospot.R
import com.sanislo.soft.photospot.databinding.FragmentSignUpBinding
import com.sanislo.soft.photospot.presentation.MainActivity
import com.sanislo.soft.photospot.presentation.base.BaseFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SignUpFragment : BaseFragment() {
    @Inject
    lateinit var mFactory: ViewModelProvider.Factory

    lateinit var mBinding: FragmentSignUpBinding
    lateinit var mViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        obtainViewModel()
    }

    private fun obtainViewModel() {
        mViewModel = ViewModelProviders.of(
                this,
                mFactory)
                .get(SignUpViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_sign_up,
                container,
                false
        )
        mBinding.viewModel = mViewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSignUpSuccess()
        observeError()
        mBinding.ivAvatar.setOnClickListener {
            pickImage()
        }
        mViewModel.mAvatar.observe(this, Observer {
            Glide.with(context!!)
                    .load(it)
                    .into(mBinding.ivAvatar)
        })
    }

    fun pickImage() {
        val pickerIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val chooserIntent = Intent.createChooser(pickerIntent,
                getString(R.string.picture_chooser_title))
        //chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toTypedArray())
        startActivityForResult(chooserIntent, RC_PICK_IMAGE)
    }

    fun observeSignUpSuccess() {
        mViewModel.mSignUpCompleteEvent.observe(this, Observer {
            startActivity(Intent(context, MainActivity::class.java))
        })
    }

    fun observeError() {
        mViewModel.mErrorSingleLiveEVent.observe(this, Observer {
            makeSnack(mBinding.root, it?.localizedMessage ?: "")
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val uri = data.data
                    mViewModel.avatarPicked(uri)
                }
            }
        }
    }

    companion object {
        val TAG = SignUpFragment::class.java.simpleName
        val RC_PICK_IMAGE = 101
    }
}
