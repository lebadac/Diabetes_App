package com.dac.diabetes

data class DiabetesRequest(
    val age: Int,
    val hypertension: Int,
    val heart_disease: Int,
    val bmi: Double,
    val HbA1c_level: Double,
    val blood_glucose_level: Double,
    val gender: String,
    val smoking_history: String
)
