package com.afrakhteh.noteapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager.LayoutParams.*
import com.afrakhteh.noteapp.R

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
