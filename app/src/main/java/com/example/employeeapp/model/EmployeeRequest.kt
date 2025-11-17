package com.example.employeeapp.model

import com.google.gson.annotations.SerializedName

data class EmployeeRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("salary")
    val salary: String,

    @SerializedName("age")
    val age: String
)