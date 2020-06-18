package com.example.advdev3.di

import com.example.advdev3.model.data.SearchResult
import com.example.advdev3.model.datasource.RetrofitImplementation
import com.example.advdev3.model.datasource.RoomDataBaseImplementation
import com.example.advdev3.model.repository.Repository
import com.example.advdev3.model.repository.RepositoryImplementation
import com.example.advdev3.view.main.MainInteractor
import com.example.advdev3.view.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module{

    single<Repository<List<SearchResult>>>(named(NAME_REMOTE)) { RepositoryImplementation(RetrofitImplementation()) }
    single<Repository<List<SearchResult>>>(named(NAME_LOCAL)) {RepositoryImplementation( RoomDataBaseImplementation()
    ) }
}

val mainScreen = module {
    factory { MainInteractor(get(named(NAME_REMOTE)),get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}