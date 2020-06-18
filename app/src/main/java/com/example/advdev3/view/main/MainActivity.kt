package com.example.advdev3.view.main
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advdev3.R
import com.example.advdev3.model.data.DataModel
import com.example.advdev3.model.data.SearchResult
import com.example.advdev3.utils.network.isOnline
import com.example.advdev3.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.IllegalStateException

class MainActivity : BaseActivity<DataModel,MainInteractor>() {

    override lateinit var model: MainViewModel

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val fabClickListener: View.OnClickListener = View.OnClickListener {
        val searchDialogFragment = SearchDialogFragment.newInstance()

        searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
        searchDialogFragment.show(supportFragmentManager,BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }
    private val onListItemClickListener: MainAdapter.OnListItemClickListener = object :MainAdapter.OnListItemClickListener{
        override fun onItemClick(data: SearchResult) {
            Toast.makeText(this@MainActivity, data.text,Toast.LENGTH_SHORT).show()
        }
    }

    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener = object : SearchDialogFragment.OnSearchClickListener{
        override fun onClick(searchWord: String) {
            isNetworkAvailable = isOnline(applicationContext)
            if (isNetworkAvailable){
                model.getData(searchWord,isNetworkAvailable)
            } else{
                showNoInternetConnectionDialog()
            }
        }
    }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         initViewModel()
         initViews()
    }
    override fun renderData(dataModel: DataModel) {
     when (dataModel) {
         is DataModel.Success ->{
             showViewWorking()
             val searchResult = dataModel.data
             if (searchResult.isNullOrEmpty()) {
                 showAlertDialog(
                     getString(R.string.dialog_title_sorry),
                     getString(R.string.empty_server_response_on_success)
                 )
             } else {
                 adapter.setData(searchResult)
             }
         }
         is DataModel.Loading -> {
             showViewWorking()
             if (dataModel.progress !=null){
                 progress_bar_horizontal.visibility = VISIBLE
                 progress_bar_round.visibility = GONE
                 progress_bar_horizontal.progress = dataModel.progress
             } else{
                 progress_bar_horizontal.visibility = GONE
                 progress_bar_round.visibility = VISIBLE
             }
         }
         is DataModel.Error->{
             showViewWorking()
             showAlertDialog(getString(R.string.error_stub), dataModel.error.message)
         }
     }
    }

    private fun initViewModel(){
        if (main_activity_recyclerview.adapter != null){
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel:MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@MainActivity, Observer<DataModel> {renderData(it)})
    }

    private fun initViews() {
        search_fab.setOnClickListener(fabClickListener)
        main_activity_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        main_activity_recyclerview.adapter = adapter
    }

    private fun showViewWorking() {
        loading_frame_layout.visibility = GONE
    }

    private fun showViewLoading() {
        loading_frame_layout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
    }

}
