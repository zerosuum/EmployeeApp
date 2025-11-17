package com.example.employeeapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.employeeapp.databinding.ActivityDetailEmployeeBinding
import com.example.employeeapp.model.EmployeeDetailResponse
import com.example.employeeapp.model.EmployeeRequest
import com.example.employeeapp.model.StatusResponse
import com.example.employeeapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEmployeeBinding
    private val client = ApiClient.getInstance()
    private var employeeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEmployeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeId = intent.getIntExtra("EXTRA_ID", -1)
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

        // tombol update
        binding.btnUpdate.setOnClickListener {
            updateEmployee()
        }

        // tombol delete
        binding.btnDelete.setOnClickListener {
            deleteEmployee()
        }
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
                    // sekarang txtName / txtSalary / txtAge adalah EditText
                    txtName.setText(employee.employeeName)
                    txtSalary.setText(employee.employeeSalary.toString())
                    txtAge.setText(employee.employeeAge.toString())
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

    private fun updateEmployee() {
        val name = binding.txtName.text.toString().trim()
        val salary = binding.txtSalary.text.toString().trim()
        val age = binding.txtAge.text.toString().trim()

        if (name.isEmpty() || salary.isEmpty() || age.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val body = EmployeeRequest(
            name = name,
            salary = salary,
            age = age
        )

        val call = client.updateEmployee(employeeId, body)
        call.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@DetailEmployeeActivity,
                        "HTTP ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                val msg = response.body()?.message ?: "Berhasil update employee"
                Toast.makeText(
                    this@DetailEmployeeActivity,
                    msg,
                    Toast.LENGTH_SHORT
                ).show()
                finish() // kembali ke list
            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                Toast.makeText(
                    this@DetailEmployeeActivity,
                    "Gagal: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun deleteEmployee() {
        val call = client.deleteEmployee(employeeId)
        call.enqueue(object : Callback<StatusResponse> {
            override fun onResponse(
                call: Call<StatusResponse>,
                response: Response<StatusResponse>
            ) {
                if (!response.isSuccessful) {
                    Toast.makeText(
                        this@DetailEmployeeActivity,
                        "HTTP ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                val msg = response.body()?.message ?: "Berhasil menghapus employee"
                Toast.makeText(
                    this@DetailEmployeeActivity,
                    msg,
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }

            override fun onFailure(call: Call<StatusResponse>, t: Throwable) {
                Toast.makeText(
                    this@DetailEmployeeActivity,
                    "Gagal: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}