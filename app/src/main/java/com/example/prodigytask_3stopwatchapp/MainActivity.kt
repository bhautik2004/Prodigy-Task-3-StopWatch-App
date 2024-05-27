package com.example.prodigytask_3stopwatchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.prodigytask_3stopwatchapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isRunning = false
    private var timeSeconds = 0

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            timeSeconds++
            val hours = timeSeconds / 3600
            val minutes = (timeSeconds % 3600) / 60
            val seconds = timeSeconds % 60
            val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            binding.timertext.text = time

            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startbtn.setOnClickListener {
            startTime()
        }
        binding.stopbtn.setOnClickListener {
            stopTimer()
        }
        binding.resetbtn.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTime() {
        if (!isRunning) {
            handler.postDelayed(runnable, 1000)
            isRunning = true
            binding.startbtn.isEnabled = false
            binding.stopbtn.isEnabled = true
            binding.resetbtn.isEnabled = true
            updateButtonColors()
        }
    }

    private fun stopTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable)
            isRunning = false
            binding.startbtn.text = "Resume"
            binding.startbtn.isEnabled = true
            binding.stopbtn.isEnabled = false
            binding.resetbtn.isEnabled = true
            updateButtonColors()
        }
    }

    private fun resetTimer() {
        if (!isRunning) {
            timeSeconds = 0
            binding.timertext.text = "00:00:00"
            binding.startbtn.text = "Start"
            binding.startbtn.isEnabled = true
            binding.stopbtn.isEnabled = false
            binding.resetbtn.isEnabled = false
            updateButtonColors()
        }
    }

    private fun updateButtonColors() {
        val enabledColor = ContextCompat.getColor(this, R.color.navyblue)
        val disabledColor = ContextCompat.getColor(this, R.color.gray) // Assume you have a gray color for disabled state

        binding.startbtn.setBackgroundColor(if (binding.startbtn.isEnabled) enabledColor else disabledColor)
        binding.stopbtn.setBackgroundColor(if (binding.stopbtn.isEnabled) enabledColor else disabledColor)
        binding.resetbtn.setBackgroundColor(if (binding.resetbtn.isEnabled) enabledColor else disabledColor)
    }
}
