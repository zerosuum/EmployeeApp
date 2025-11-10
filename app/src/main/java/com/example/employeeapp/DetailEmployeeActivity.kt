package com.example.employeeapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.employeeapp.databinding.ActivityDetailEmployeeBinding
import com.example.employeeapp.model.EmployeeDetailResponse
import com.example.employeeapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEmployeeBinding
    private val client = ApiClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val employeeId = intent.getIntExtra("EXTRA_ID", -1)
        if (employeeId == -1) {
            Toast.makeText(
                this,
                "ID tidak valid",
                Toast.LENGTH_SHORT
            ).show()
            finish()
            return
        }

        getEmployeeDetails(employeeId)
    }

    private fun getEmployeeDetails(id: Int) {
        val call = client.getEmployeeDetail(id)

        call.enqueue(object : Callback<EmployeeDetailResponse> {
            override fun onResponse(
                call: Call<EmployeeDetailResponse>,
                response: Response<EmployeeDetailResponse>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@DetailEmployeeActivity,
                        "HTTP ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                val employee = response.body()?.data
                if (employee == null) {
                    Toast.makeText(
                        this@DetailEmployeeActivity,
                        "Data tidak ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                with(binding) {
                    txtName.text = employee.employeeName
                    txtSalary.text = "Salary: ${employee.employeeSalary}"
                    txtAge.text = "Age: ${employee.employeeAge}"
                }
            }

            override fun onFailure(call: Call<EmployeeDetailResponse>, t: Throwable) {
                Toast.makeText(
                    this@DetailEmployeeActivity,
                    "Gagal: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
