package com.sanislo.soft.photospot.di

import com.sanislo.soft.photospot.presentation.auth.signUp.SignUpFragment
import com.sanislo.soft.photospot.presentation.auth.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [SignUpFragmentModule::class])
    abstract fun contributeSignUpFragment(): SignUpFragment
}
