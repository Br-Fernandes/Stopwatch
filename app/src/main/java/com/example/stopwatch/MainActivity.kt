package com.example.stopwatch

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


        val chronometer = findViewById<Chronometer>(R.id.chronometer)
        val startBtn = findViewById<Button>(R.id.btn_start)
        startBtn?.setOnClickListener(object : View.OnClickListener {

            var isWorking = false

            override fun onClick(v: View) {
                if (!isWorking) {
                    chronometer.start()
                    isWorking = true
                } else {
                    chronometer.stop()
                    isWorking = false
                }

                startBtn.setText(if (isWorking) R.string.start else R.string.stop)

                Toast.makeText(this@MainActivity, getString(
                    if (isWorking)
                        R.string.working
                    else
                        R.string.stopped),
                    Toast.LENGTH_SHORT).show()
            }
        })


    }

    /*fun startStopwatch() {
        var stopwatch = findViewById<TextView>(R.id.tv_numbers).text
        var stopwatchNumbers = stopwatch.split(":")

        var hourStopwatch = stopwatchNumbers.get(0)
        var minStopwatch = stopwatchNumbers.get(1)
        var secStopwatch = stopwatchNumbers.get(2)

        var timer = Timer()

        val task = object : TimerTask() {
            override fun run() {

            }
        }

        timer.scheduleAtFixedRate(task, 0, 1000)
    }*/

}