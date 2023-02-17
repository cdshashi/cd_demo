package com.countrydelight

import com.countrydelight.entity.EmployeeDataItem
import com.countrydelight.network.EmployeeApi


// this is the main class to interact with your shared module code
class EmployeeSDK() {
    private val api = EmployeeApi()

    @Throws(Exception::class)
    suspend fun getEmployees(): List<EmployeeDataItem> {
        return  api.getAllEmployee().data
    }
}