package com.example.advdev3.view.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.advdev3.R
import com.example.advdev3.model.data.DataModel
import com.example.advdev3.utils.network.isOnline
import com.example.advdev3.utils.ui.AlertDialogFragment
import com.example.advdev3.viewmodel.BaseViewModel
import com.example.advdev3.viewmodel.Interactor

abstract class BaseActivity<T: DataModel, I: Interactor<T>>: AppCompatActivity() {
    abstract val model: BaseViewModel<T>
    protected var isNetworkAvailable : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState:PersistableBundle?) {
        super.onCreate(savedInstanceState,persistentState)
        isNetworkAvailable = isOnline(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(applicationContext)
        if (!isNetworkAvailable && isDialogNull()){

        }
    }

    protected fun showNoInternetConnectionDialog(){
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?){
        AlertDialogFragment.newInstance(title, message).show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull():Boolean{
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    abstract fun renderData(dataModel: T)
    companion object {
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    }

}