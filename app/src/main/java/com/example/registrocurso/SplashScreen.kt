package com.example.registrocurso

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }



        val timerThread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(4000) // 3 segundos = 3000 milisegundos
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    val i = Intent("com.example.registrocurso.MAIN")
                    startActivity(i)
                    finish()
                    super.run()
                }
            }
        }
        timerThread.start()


    }

    override fun onPause() {
        // TODO Auto-generated method stub
        super.onPause()
        finish()
    }
}