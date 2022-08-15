package com.example.ticktock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ticktock.databinding.ActivityMainBinding
import com.example.ticktock.utils.DateUtils
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        draw()
        time()
    }

    private fun time() {
        CoroutineScope(Dispatchers.Main).launch {
            val calendar = Calendar.getInstance()
            binding.tvMainTime.text =
                "${DateUtils.getIn2Format(calendar.get(Calendar.HOUR_OF_DAY))} ${
                    DateUtils.getIn2Format(
                        calendar.get(Calendar.MINUTE)
                    )
                } ${
                    DateUtils.getIn2Format(calendar.get(Calendar.SECOND))
                }"
            withContext(Dispatchers.Default) {
                delay(1000)
                time()
            }
        }
    }

    private fun draw() {
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                binding.timeClockLayout.cdTimeClockSecs.invalidate()
                binding.timeClockLayout.cdTimeClockMinutes.invalidate()
                binding.timeClockLayout.cdTimeClockHours.invalidate()
                delay(1000L)
            }
        }
    }
}