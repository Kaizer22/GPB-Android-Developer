package ru.desh.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class ActivityC : AppCompatActivity() {
    companion object {
        private val TAG = "ActivityC"
    }

    private val onClickStartA = View.OnClickListener {
        val i = Intent(this, ActivityA::class.java)
        startActivity(i)
    }
    private val onClickStartD = View.OnClickListener {
        val i = Intent(this, ActivityD::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
    }

    private val onClickCloseC = View.OnClickListener { finish() }

    private val onClickCloseStack = View.OnClickListener {
        val i = Intent(this, ActivityA::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        findViewById<Button>(R.id.button_open_a).setOnClickListener(onClickStartA)
        findViewById<Button>(R.id.button_open_d).setOnClickListener(onClickStartD)
        findViewById<Button>(R.id.button_close_c).setOnClickListener(onClickCloseC)
        findViewById<Button>(R.id.button_close_stack).setOnClickListener(onClickCloseStack)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart $this")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause $this")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy $this")
    }

}