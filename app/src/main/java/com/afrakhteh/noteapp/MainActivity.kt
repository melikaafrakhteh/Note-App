package com.afrakhteh.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            FLAG_FULLSCREEN
                       , FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)
    }
}
