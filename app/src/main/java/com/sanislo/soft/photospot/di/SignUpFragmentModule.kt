package com.sanislo.soft.photospot.di

import com.sanislo.soft.photospot.presentation.auth.signUp.SignUpViewModel
import dagger.Module
import dagger.Provides

@Module
class SignUpFragmentModule {
    @Provides
    fun provideViewModel(): SignUpViewModel {
        return SignUpViewModel()
    }
}
