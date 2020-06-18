package com.example.advdev3.model.repository

interface Repository<T> {
    suspend fun getData(word:String):T
}