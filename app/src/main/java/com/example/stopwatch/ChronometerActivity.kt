package com.example.stopwatch

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

class ChronometerActivity : AppCompatActivity() {


    private lateinit var chronometer: Chronometer
    private var savedState: Bundle? = null

    lateinit var btnStart: Button
    lateinit var btnRestore: Button
    lateinit var btnResume: Button

    var elapsedMillis: Long = 0
    var lastStartTime: Long = 0
    var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chronometer)
        title = "Cronômetro"

        chronometer = findViewById(R.id.chronometer)
        btnStart = findViewById<Button>(R.id.btn_start)
        btnResume = findViewById<Button>(R.id.btn_resume)
        btnRestore = findViewById<Button>(R.id.btn_restore)

        savedState = savedInstanceState // inicializar a variável savedState

        startButton()

        btnStart.setOnClickListener {
            if (btnStart.text == "Iniciar") startChronometer(chronometer, btnStart)
            else stopChronometer(chronometer, btnStart)
        }

        btnRestore.setOnClickListener{
            lastStartTime = 0L
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
        isRunning = true
    }

    fun stopChronometer(chronometerActivity: Chronometer, btnStart: Button) {
        elapsedMillis = SystemClock.elapsedRealtime() - lastStartTime
        chronometerActivity.stop()
        btnStart.visibility = View.GONE
        findViewById<Button>(R.id.btn_restore).visibility = View.VISIBLE
        findViewById<Button>(R.id.btn_resume).visibility = View.VISIBLE
    }

    fun startButton() {
        btnStart.visibility = View.VISIBLE
        btnRestore.visibility = View.GONE
        btnResume.visibility = View.GONE

        val params = findViewById<Button>(R.id.btn_start).layoutParams as ConstraintLayout.LayoutParams
        params.startToEnd = R.id.btn_restore
        params.endToStart = R.id.btn_resume
        findViewById<Button>(R.id.btn_start).layoutParams = params
    }

}