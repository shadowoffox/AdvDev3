package com.example.advdev3.view.main

import com.example.advdev3.model.data.DataModel
import com.example.advdev3.model.data.SearchResult
import com.example.advdev3.model.repository.Repository
import com.example.advdev3.viewmodel.Interactor

class MainInteractor(
    private val repositoryRemote: Repository<List<SearchResult>>,
    private val repositoryLocal: Repository<List<SearchResult>>
): Interactor<DataModel> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): DataModel {
        return DataModel.Success(
            if (fromRemoteSource){repositoryRemote}
        else{repositoryLocal}.getData(word)
        )
    }
}