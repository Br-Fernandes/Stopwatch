package com.example.stopwatch

import android.annotation.SuppressLint

import android.graphics.Color

import android.media.MediaPlayer

import android.os.Bundle
import android.os.CountDownTimer

import android.view.View

import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import java.util.concurrent.TimeUnit

class TimerActivity : AppCompatActivity() {

    var timeRemaining: Long = 0

    private lateinit var countDownTimer: CountDownTimer
    lateinit var txtCountDown: TextView
    lateinit var minutePicker: NumberPicker
    lateinit var secondPicker: NumberPicker
    lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer)
        title = "Temporizador"

        btnStart = findViewById(R.id.btn_start_timer)
        txtCountDown = findViewById(R.id.txt_countdown)

        configTimer()

        btnStart.setOnClickListener {
            if (btnStart.text == "Iniciar") startCountDownTimer()
            else if (btnStart.text == "Retomar") resumeCountDownTimer()
            else pauseCountDownTimer()
        }
    }

    fun startCountDownTimer() {
        btnStart.setText(R.string.btn_stop)
        btnStart.setBackgroundColor(Color.RED)

        minutePicker.visibility = View.GONE
        secondPicker.visibility = View.GONE
        txtCountDown.visibility = View.VISIBLE
        val time = (minutePicker.value * 60000) + (secondPicker.value * 1000)

        var minutesLeft = minutePicker.value.toString().toLong()
        var secondsLeft = secondPicker.value.toString().toLong()

        countDownTimer = object : CountDownTimer(time.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                secondsLeft--

                if (secondsLeft < 0) {
                    minutesLeft--
                    secondsLeft = 59
                }

                val timeString = String.format("%02d:%02d", minutesLeft, secondsLeft)

                txtCountDown.text = timeString
                timeRemaining = convertToMilli(timeString)
            }

            override fun onFinish() {
                txtCountDown.text = "00:00"
                var mediaPlayer: MediaPlayer? = MediaPlayer.create(this@TimerActivity, R.raw.alarme)
                mediaPlayer?.start()

                resetConfig()
            }
        }.start()
    }


    fun pauseCountDownTimer() {
        btnStart.setText(R.string.btn_resume)
        btnStart.setBackgroundColor(Color.BLUE)

        countDownTimer.cancel()
    }

    fun resumeCountDownTimer() {
        btnStart.setText(R.string.btn_stop)
        btnStart.setBackgroundColor(Color.RED)

        var str = formatTime(timeRemaining)
        var minutesLeft = str[0].toLong()
        var secondsLeft = str[1].toLong()

        countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                secondsLeft--

                if (secondsLeft < 0) {
                    minutesLeft--
                    secondsLeft = 59
                }

                val timeString = String.format("%02d:%02d", minutesLeft, secondsLeft)

                txtCountDown.text = timeString
                timeRemaining = convertToMilli(timeString)
            }

            override fun onFinish() {
                txtCountDown.text = "00:00"
                var mediaPlayer: MediaPlayer? = MediaPlayer.create(this@TimerActivity, R.raw.music)
                mediaPlayer?.start()

                resetConfig()
            }
        }.start()
    }

    fun configTimer() {
        minutePicker = findViewById(R.id.minutesPicker)
        minutePicker.minValue = 0
        minutePicker.maxValue = 59

        secondPicker = findViewById(R.id.secondsPicker)
        secondPicker.minValue = 0
        secondPicker.maxValue = 59
    }

    fun resetConfig() {
        btnStart.setText(R.string.btn_start)
        btnStart.setBackgroundColor(Color.BLUE)

        txtCountDown.visibility = View.GONE
        minutePicker.visibility = View.VISIBLE
        secondPicker.visibility = View.VISIBLE
    }

    fun convertToMilli(str: String): Long {
        var numbers = str.split(":")
        return (numbers[0].toLong() * 60000) + (numbers[1].toLong() * 1000)
    }

    fun formatTime(time: Long): Array<String> {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(time)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(time) % 60
        val formattedMinutes = String.format("%02d", minutes)
        val formattedSeconds = String.format("%02d", seconds)
        return arrayOf(formattedMinutes, formattedSeconds)
    }

}