package com.example.employeeapp.network

import com.example.employeeapp.model.EmployeeDetailResponse
import retrofit2.http.GET
import retrofit2.Call
import com.example.employeeapp.model.EmployeeResponse
import retrofit2.http.Path
import com.example.employeeapp.model.EmployeeRequest
import com.example.employeeapp.model.StatusResponse
import retrofit2.http.POST
import retrofit2.http.PATCH
import retrofit2.http.DELETE
import retrofit2.http.Body

interface ApiService {
    @GET("employees")
    fun getAllEmployees(): Call<EmployeeResponse>

    @GET("employee/{id}")
    fun getEmployeeDetail(
    @Path("id") id: Int
    ): Call<EmployeeDetailResponse>

    // create (HTTP POST /create)
    @POST("create")
    fun createEmployee(
        @Body body: EmployeeRequest
    ): Call<StatusResponse>

    // update (HTTP PATCH /update/{id})
    @PATCH("update/{id}")
    fun updateEmployee(
        @Path("id") id: Int,
        @Body body: EmployeeRequest
    ): Call<StatusResponse>

    // DELETE (HTTP DELETE /delete/{id})
    @DELETE("delete/{id}")
    fun deleteEmployee(
        @Path("id") id: Int
    ): Call<StatusResponse>
}