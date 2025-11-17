package com.example.employeeapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.employeeapp.databinding.ActivityCreateEmployeeBinding
import com.example.employeeapp.model.EmployeeRequest
import com.example.employeeapp.model.StatusResponse
import com.example.employeeapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEmployeeBinding
    private val client = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            createEmployee()
        }
    }

    private fun createEmployee() {
        val name = binding.edtName.text.toString().trim()
        val salary = binding.edtSalary.text.toString().trim()
        val age = binding.edtAge.text.toString().trim()

        if (name.isEmpty() || salary.isEmpty() || age.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val body = EmployeeRequest(
            name = name,
            salary = salary,
            age = age
        )

        val call = client.createEmployee(body)
        call.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@CreateEmployeeActivity,
                        "HTTP ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                val msg = response.body()?.message ?: "Berhasil membuat employee"
                Toast.makeText(
                    this@CreateEmployeeActivity,
                    msg,
                    Toast.LENGTH_SHORT
                ).show()
                // kembali ke MainActivity
                finish()
            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                Toast.makeText(
                    this@CreateEmployeeActivity,
                    "Gagal: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}