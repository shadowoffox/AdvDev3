package com.example.advdev3.model.datasource

import com.example.advdev3.model.data.SearchResult

class RoomDataBaseImplementation: DataSource<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> {
        TODO("Not yet implemented")
    }


}