package com.example.advdev3.model.datasource

interface DataSource<T> {

    suspend fun getData(word: String): T
}