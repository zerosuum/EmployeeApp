package com.example.employeeapp.network

import retrofit2.http.GET
import retrofit2.Call
import com.example.employeeapp.model.EmployeeResponse

interface ApiService {
    @GET("employees")
    fun getAllEmployees(): Call<EmployeeResponse>
}