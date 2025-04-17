package com.dac.diabetes

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

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
        val btnPredict = findViewById<Button>(R.id.btnPredict)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        // Gán giá trị spinner
        val genders = listOf("Male", "Female", "Other")
        val smokingHistories = listOf("never", "former", "current", "not current", "ever", "No Info")

        spGender.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genders)
        spSmokingHistory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, smokingHistories)

        // Xử lý khi nhấn nút dự đoán
        btnPredict.setOnClickListener {
            val age = etAge.text.toString().toIntOrNull() ?: 0
            val gender = spGender.selectedItem.toString()
            val bmi = etBMI.text.toString().toDoubleOrNull() ?: 0.0
            val hba1c = etHbA1c.text.toString().toDoubleOrNull() ?: 0.0
            val glucose = etBloodGlucose.text.toString().toDoubleOrNull() ?: 0.0
            val smoking = spSmokingHistory.selectedItem.toString()

            if (age == 0 || bmi == 0.0 || hba1c == 0.0 || glucose == 0.0) {
                tvResult.text = "Vui lòng nhập đầy đủ thông tin!"
                return@setOnClickListener
            }

            // Fake kết quả test
            val fakePrediction = 1
            tvResult.text = if (fakePrediction == 1) "Nguy cơ tiểu đường cao!" else "Ít nguy cơ tiểu đường."
        }
    }
}
