package com.example.stopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
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
            startButton()
            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.stop()
            btnStart.setBackgroundColor(Color.BLUE)
            btnStart.setText(R.string.btn_start)
        }

        btnResume.setOnClickListener{
            startButton()
            startChronometer(chronometer, btnStart)
        }
    }

    var elapsedMillis: Long = 0
    var lastStartTime: Long = 0

    fun startChronometer(chronometer: Chronometer, btnStart: Button) {
        if (lastStartTime == 0L) {
            lastStartTime = SystemClock.elapsedRealtime()
        } else {
            lastStartTime = SystemClock.elapsedRealtime() - elapsedMillis
        }
        chronometer.base = lastStartTime
        chronometer.start()
        btnStart.setText(R.string.btn_stop)
        btnStart.setBackgroundColor(Color.RED)
    }

    fun stopChronometer(chronometer: Chronometer, btnStart: Button) {
        elapsedMillis = SystemClock.elapsedRealtime() - lastStartTime
        chronometer.stop()
        btnStart.visibility = View.GONE
        findViewById<Button>(R.id.btn_restore).visibility = View.VISIBLE
        findViewById<Button>(R.id.btn_resume).visibility = View.VISIBLE
    }

    fun startButton() {
        findViewById<Button>(R.id.btn_start).visibility = View.VISIBLE
        findViewById<Button>(R.id.btn_restore).visibility = View.GONE
        findViewById<Button>(R.id.btn_resume).visibility = View.GONE

        val params = findViewById<Button>(R.id.btn_start).layoutParams as ConstraintLayout.LayoutParams
        params.startToEnd = R.id.btn_restore
        params.endToStart = R.id.btn_resume
        findViewById<Button>(R.id.btn_start).layoutParams = params
    }

}