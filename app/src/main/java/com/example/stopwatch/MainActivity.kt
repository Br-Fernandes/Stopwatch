package com.example.stopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton()

        val chronometer = findViewById<Chronometer>(R.id.chronometer)
        val btnStart = findViewById<Button>(R.id.btn_start)
        val btnRestore = findViewById<Button>(R.id.btn_restore)
        val btnResume = findViewById<Button>(R.id.btn_resume)
        btnStart.setOnClickListener {
            if (btnStart.text == "Start") startChronometer(chronometer, btnStart)
            else stopChronometer(chronometer, btnStart)
        }

        btnRestore.setOnClickListener{
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.stop()
        }

        btnResume.setOnClickListener{
            chronometer.start()
        }

    }

    fun startChronometer(chronometer: Chronometer, btnStart: Button) {
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
        btnStart.setText(R.string.btn_stop)
        btnStart.setBackgroundColor(Color.RED)
    }

    fun stopChronometer(chronometer: Chronometer, btnStart: Button) {
        chronometer.stop()
        btnStart.visibility = View.GONE
        findViewById<Button>(R.id.btn_restore).visibility = View.VISIBLE
        findViewById<Button>(R.id.btn_resume).visibility = View.VISIBLE
    }

    fun startButton() {
        findViewById<Button>(R.id.btn_restore).visibility = View.GONE
        findViewById<Button>(R.id.btn_resume).visibility = View.GONE
    }

}