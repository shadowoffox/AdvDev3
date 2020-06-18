package com.example.advdev3.view.main

import androidx.lifecycle.LiveData
import com.example.advdev3.model.data.DataModel
import com.example.advdev3.utils.parseSearchResults
import com.example.advdev3.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val interactor: MainInteractor): BaseViewModel<DataModel>() {
    private val liveDataForViewToObserve : LiveData<DataModel> = mutableLiveData

    fun subscribe():LiveData<DataModel>{
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        mutableLiveData.value = DataModel.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word,isOnline) }
    }

    private suspend fun startInteractor(word: String,isOnline: Boolean)= withContext(Dispatchers.IO){
        mutableLiveData.postValue(parseSearchResults(interactor.getData(word,isOnline)))
    }

    override fun handleError(error: Throwable) {
        mutableLiveData.postValue(DataModel.Error(error))
    }

    override fun onCleared() {
        mutableLiveData.value = DataModel.Success(null)
        super.onCleared()
    }


}