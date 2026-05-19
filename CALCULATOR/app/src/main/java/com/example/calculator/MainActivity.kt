package com.example.calculator

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sleepInput: EditText
    private lateinit var waterInput: EditText
    private lateinit var stressInput: EditText
    private lateinit var resultText: TextView
    private lateinit var calculateBtn: Button
    private lateinit var infoBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sleepInput = findViewById(R.id.sleepInput)
        waterInput = findViewById(R.id.waterInput)
        stressInput = findViewById(R.id.stressInput)
        resultText = findViewById(R.id.resultText)
        calculateBtn = findViewById(R.id.calculateBtn)
        infoBtn = findViewById(R.id.infoBtn)

        calculateBtn.setOnClickListener {

            val sleep = sleepInput.text.toString().toIntOrNull() ?: 0
            val water = waterInput.text.toString().toIntOrNull() ?: 0
            val stress = stressInput.text.toString().toIntOrNull() ?: 0

            val mood = when {
                sleep >= 8 && water >= 6 && stress <= 3 -> "Happy 😊"
                sleep < 5 && stress >= 7 -> "Tired 😴"
                stress >= 8 -> "Stressed 😓"
                else -> "Energetic ⚡"
            }

            resultText.text = "Your Mood: $mood"
        }

        infoBtn.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.popup_layout)
            dialog.show()
        }
    }
}