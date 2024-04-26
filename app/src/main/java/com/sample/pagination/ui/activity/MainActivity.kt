package com.sample.pagination.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.pagination.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate layout and set content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}