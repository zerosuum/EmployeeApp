package com.example.employeeapp.network

import com.example.employeeapp.model.EmployeeDetailResponse
import retrofit2.http.GET
import retrofit2.Call
import com.example.employeeapp.model.EmployeeResponse
import retrofit2.http.Path

interface ApiService {
    @GET("employees")
    fun getAllEmployees(): Call<EmployeeResponse>

    @GET("employee/{id}")
    fun getEmployeeDetail(
    @Path("id") id: Int
    ): Call<EmployeeDetailResponse>
}