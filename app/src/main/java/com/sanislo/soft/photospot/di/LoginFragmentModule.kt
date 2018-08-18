package com.sanislo.soft.photospot.di

import com.sanislo.soft.photospot.presentation.auth.login.LoginViewModel
import dagger.Module
import dagger.Provides

@Module
class LoginFragmentModule {
    @Provides
    fun provideViewModel(): LoginViewModel {
        return LoginViewModel()
    }
}
