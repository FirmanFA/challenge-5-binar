package com.binar.challenge5.di

import com.binar.challenge5.ui.auth.AuthViewModel
import com.binar.challenge5.ui.detail.DetailViewModel
import com.binar.challenge5.ui.favorite.FavoriteViewModel
import com.binar.challenge5.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::AuthViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::HomeViewModel)
}