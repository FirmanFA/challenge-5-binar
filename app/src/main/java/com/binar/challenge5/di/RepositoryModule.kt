package com.binar.challenge5.di

import com.binar.challenge5.repository.AuthRepository
import com.binar.challenge5.repository.DetailRepository
import com.binar.challenge5.repository.FavoriteRepository
import com.binar.challenge5.repository.HomeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::AuthRepository)
    singleOf(::HomeRepository)
    singleOf(::FavoriteRepository)
    singleOf(::DetailRepository)
}