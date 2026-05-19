package com.example.habittracker

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val reminderSwitch = findViewById<Switch>(R.id.reminderSwitch)
        val saveNameBtn = findViewById<Button>(R.id.saveNameBtn)
        val nameText = findViewById<TextView>(R.id.nameText)

        sharedPreferences = getSharedPreferences("HabitTrackerData", MODE_PRIVATE)

        val savedName = sharedPreferences.getString("username", "")
        val reminder = sharedPreferences.getBoolean("reminder", false)

        nameInput.setText(savedName)
        reminderSwitch.isChecked = reminder

        updatePreferenceText(savedName ?: "", reminder, nameText)

        saveNameBtn.setOnClickListener {
            val username = nameInput.text.toString().trim()
            val reminderOn = reminderSwitch.isChecked

            sharedPreferences.edit()
                .putString("username", username)
                .putBoolean("reminder", reminderOn)
                .apply()

            updatePreferenceText(username, reminderOn, nameText)
        }
    }

    private fun updatePreferenceText(username: String, reminder: Boolean, textView: TextView) {
        if (username.isEmpty()) {
            textView.text = "No preferences saved yet."
        } else {
            val reminderText = if (reminder) "Reminder Preference: ON" else "Reminder Preference: OFF"
            textView.text = "Saved Name: $username\n$reminderText"
        }
    }
}