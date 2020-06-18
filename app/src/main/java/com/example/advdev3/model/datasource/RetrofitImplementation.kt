package com.example.advdev3.model.datasource

import com.example.advdev3.model.data.SearchResult
import com.example.advdev3.model.data.api.ApiService
import com.example.advdev3.model.data.api.BaseInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImplementation: DataSource<List<SearchResult>> {
    override suspend fun getData(word: String): List<SearchResult> {
        return getService(BaseInterceptor.interceptor).searchAsync(word).await()
    }

    private fun getService(interceptor: Interceptor):ApiService{
        return createRetrofit(interceptor).create(ApiService::class.java)
    }

    private fun createRetrofit(interceptor: Interceptor) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient(interceptor))
            .build()
    }

    private fun createOkHttpClient(interceptor: Interceptor):OkHttpClient{
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }
    companion object {
        private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"
    }

}