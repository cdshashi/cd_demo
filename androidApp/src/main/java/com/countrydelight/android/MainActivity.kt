package com.countrydelight.android

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.countrydelight.EmployeeSDK
import com.countrydelight.android.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val mainScope = MainScope()

    // Initialising native SqlDriver
    private val sdk = EmployeeSDK()
    private val adapter = EmployeeAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.swlRefresh.setOnRefreshListener { displayLaunches(true) }
        mBinding.rvList.adapter = adapter
        displayLaunches(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun displayLaunches(needReload: Boolean) {
        if (!needReload)
            mBinding.progressBar.isVisible = true
        mainScope.launch {
            kotlin.runCatching {
                // calling shared module code feature which provides employees data
                sdk.getEmployees()
            }.onSuccess {
                // handle success
                adapter.launches = it
                adapter.notifyDataSetChanged()
            }.onFailure {
                // handle exceptions
                Log.e("Mango",it.localizedMessage)
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
            }
            mBinding.progressBar.isVisible = false
            mBinding.swlRefresh.isRefreshing = false
        }
    }
}