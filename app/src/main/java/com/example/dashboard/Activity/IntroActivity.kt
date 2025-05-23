package com.example.dashboard.Activity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.dashboard.Activity.BaseActivity
import com.example.dashboard.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroBinding
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            startBtn.setOnClickListener {
                startActivity(Intent(this@IntroActivity, com.example.user.Login::class.java))
            }
        }
    }
}