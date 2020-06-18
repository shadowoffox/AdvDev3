package com.example.advdev3.model.repository

import com.example.advdev3.model.data.SearchResult
import com.example.advdev3.model.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<SearchResult>>):Repository<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> {
        return dataSource.getData(word)
    }
}