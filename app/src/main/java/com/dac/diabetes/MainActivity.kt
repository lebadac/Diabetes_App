package com.dac.diabetes

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.Color

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khai báo view
        val etAge = findViewById<EditText>(R.id.etAge)
        val etBMI = findViewById<EditText>(R.id.etBMI)
        val etHbA1c = findViewById<EditText>(R.id.etHbA1c)
        val etBloodGlucose = findViewById<EditText>(R.id.etBloodGlucose)
        val spGender = findViewById<Spinner>(R.id.spGender)
        val spSmokingHistory = findViewById<Spinner>(R.id.spSmokingHistory)
        val spHypertension = findViewById<Spinner>(R.id.spHypertension)
        val spHeartDisease = findViewById<Spinner>(R.id.spHeartDisease)
        val btnPredict = findViewById<Button>(R.id.btnPredict)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        // Gán giá trị cho các spinner
        val yesNo = listOf("No", "Yes")
        val genders = listOf("Male", "Female", "Other")
        val smokingHistories = listOf("never", "former", "current", "not current", "ever", "No Info")

        spHypertension.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, yesNo)
        spHeartDisease.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, yesNo)
        spGender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders)
        spSmokingHistory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, smokingHistories)

        // Xử lý khi nhấn nút dự đoán
        btnPredict.setOnClickListener {
            val request = DiabetesRequest(
                age = etAge.text.toString().toIntOrNull() ?: 0,
                hypertension = if (spHypertension.selectedItem.toString() == "Yes") 1 else 0,
                heart_disease = if (spHeartDisease.selectedItem.toString() == "Yes") 1 else 0,
                bmi = etBMI.text.toString().toDoubleOrNull() ?: 0.0,
                HbA1c_level = etHbA1c.text.toString().toDoubleOrNull() ?: 0.0,
                blood_glucose_level = etBloodGlucose.text.toString().toDoubleOrNull() ?: 0.0,
                gender = spGender.selectedItem.toString(),
                smoking_history = spSmokingHistory.selectedItem.toString()
            )

            RetrofitClient.apiService.predictDiabetes(request).enqueue(object : Callback<DiabetesResponse> {
                override fun onResponse(call: Call<DiabetesResponse>, response: Response<DiabetesResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()?.prediction
                        tvResult.text = "🧠 Kết quả dự đoán: $result"

                        // Update text color based on the result
                        if (result.equals("Diabetes", ignoreCase = true)) {
                            tvResult.setTextColor(Color.parseColor("#F07474")) // Red color for Diabetes
                        } else {
                            tvResult.setTextColor(Color.parseColor("#000000")) // Green color for Not Diabetes
                        }
                    } else {
                        tvResult.text = "❌ Lỗi phản hồi: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<DiabetesResponse>, t: Throwable) {
                    tvResult.text = "⚠️ Lỗi kết nối: ${t.message}"
                }
            })
        }
    }
}
