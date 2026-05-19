package com.example.habittracker

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AddHabitActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        val habitInput = findViewById<EditText>(R.id.habitInput)
        val categoryGroup = findViewById<RadioGroup>(R.id.categoryGroup)
        val completedCheckBox = findViewById<CheckBox>(R.id.completedCheckBox)
        val saveHabitBtn = findViewById<Button>(R.id.saveHabitBtn)
        val savedHabitText = findViewById<TextView>(R.id.savedHabitText)

        sharedPreferences = getSharedPreferences("HabitTrackerData", MODE_PRIVATE)

        val savedHabit = sharedPreferences.getString("habit", "")
        val savedCategory = sharedPreferences.getString("category", "")
        val completed = sharedPreferences.getBoolean("completed", false)

        habitInput.setText(savedHabit)
        completedCheckBox.isChecked = completed

        when (savedCategory) {
            "Health" -> categoryGroup.check(R.id.healthRadio)
            "Study" -> categoryGroup.check(R.id.studyRadio)
            "Lifestyle" -> categoryGroup.check(R.id.lifestyleRadio)
        }

        updateSavedText(savedHabit ?: "", savedCategory ?: "", completed, savedHabitText)

        saveHabitBtn.setOnClickListener {
            val habit = habitInput.text.toString().trim()

            val selectedId = categoryGroup.checkedRadioButtonId
            val category = if (selectedId != -1) {
                findViewById<RadioButton>(selectedId).text.toString()
            } else {
                "General"
            }

            val isCompleted = completedCheckBox.isChecked
            val oldCompleted = sharedPreferences.getBoolean("completed", false)
            var streak = sharedPreferences.getInt("streak", 0)

            if (isCompleted && !oldCompleted) {
                streak += 1
            }

            if (!isCompleted) {
                streak = 0
            }

            sharedPreferences.edit()
                .putString("habit", habit)
                .putString("category", category)
                .putBoolean("completed", isCompleted)
                .putInt("streak", streak)
                .apply()

            updateSavedText(habit, category, isCompleted, savedHabitText)
        }
    }

    private fun updateSavedText(
        habit: String,
        category: String,
        completed: Boolean,
        textView: TextView
    ) {
        if (habit.isEmpty()) {
            textView.text = "No habit saved yet."
        } else {
            val status = if (completed) "Completed today ✅" else "Not completed yet"
            textView.text = "Saved Habit: $habit\nCategory: $category\nStatus: $status"
        }
    }
}