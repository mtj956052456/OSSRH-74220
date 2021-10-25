package com.example.mtjapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mtj.common.util.Lg

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Lg.lgd("发布maven成功")
    }
}