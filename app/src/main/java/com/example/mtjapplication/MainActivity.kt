package com.example.mtjapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mtj.common.util.Lg.lgd

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lgd("发布maven成功")
    }
}