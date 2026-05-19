package com.example.habittracker

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var welcomeText: TextView
    private lateinit var motivationText: TextView
    private lateinit var dashboardText: TextView
    private lateinit var streakText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        welcomeText = findViewById(R.id.welcomeText)
        motivationText = findViewById(R.id.motivationText)
        dashboardText = findViewById(R.id.dashboardText)
        streakText = findViewById(R.id.streakText)

        val addHabitBtn = findViewById<Button>(R.id.addHabitBtn)
        val settingsBtn = findViewById<Button>(R.id.settingsBtn)
        val infoBtn = findViewById<Button>(R.id.infoBtn)

        sharedPreferences = getSharedPreferences("HabitTrackerData", MODE_PRIVATE)

        updateHomeScreen()

        addHabitBtn.setOnClickListener {
            startActivity(Intent(this, AddHabitActivity::class.java))
        }

        settingsBtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        infoBtn.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.popup_layout)
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        updateHomeScreen()
    }

    private fun updateHomeScreen() {
        val username = sharedPreferences.getString("username", "")
        val habit = sharedPreferences.getString("habit", "")
        val category = sharedPreferences.getString("category", "")
        val completed = sharedPreferences.getBoolean("completed", false)
        val streak = sharedPreferences.getInt("streak", 0)

        welcomeText.text = if (username.isNullOrEmpty()) {
            "Daily Habit Tracker"
        } else {
            "Welcome, $username"
        }

        motivationText.text = if (completed) {
            "Great job. You completed your habit today!"
        } else {
            "Small habits create big changes."
        }

        dashboardText.text = if (habit.isNullOrEmpty()) {
            "No habit added yet.\nTap Add / Edit Habit to begin."
        } else {
            val status = if (completed) "Completed today ✅" else "Not completed yet"
            "Habit: $habit\nCategory: $category\nStatus: $status"
        }

        streakText.text = "Current Streak: $streak days"
    }
}