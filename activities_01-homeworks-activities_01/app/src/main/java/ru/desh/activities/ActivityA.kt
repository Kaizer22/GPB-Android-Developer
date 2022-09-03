package ru.desh.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button


class ActivityA : AppCompatActivity() {
    companion object {
        private val TAG = "ActivityA"
    }

    private val onClickStartB = View.OnClickListener {
        val i = Intent(this, ActivityB::class.java)
        startActivity(i)
    }

    // Для отслеживания текущих задач и стека использовал
    // adb shell dumpsys activity activities | grep ru.desh.activities | grep Hist
    // adb shell dumpsys activity activities | grep ru.desh.activities | grep Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        findViewById<Button>(R.id.button_open_b).setOnClickListener(onClickStartB)
        Log.d(TAG, "onCreate $this")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "onNewIntent $this")
    }

}