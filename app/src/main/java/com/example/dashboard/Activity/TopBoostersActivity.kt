package com.example.dashboard.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboard.Activity.MainActivity
import com.example.dashboard.Adapter.TopBoosterAdapter
import com.example.dashboard.Adapter.TopBoosterAdapter2
import com.example.dashboard.R
import com.example.dashboard.ViewModel.MainViewModel
import com.example.dashboard.databinding.ActivityTopBoostersBinding
import com.example.dashboard.databinding.ViewholderTopBoosterBinding

class TopBoostersActivity : BaseActivity() {
    private lateinit var binding: ActivityTopBoostersBinding
    private val viewModel= MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTopBoostersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTopBoosters()

    }

    private fun initTopBoosters() {
        binding.apply {
            progressBarTopBooster.visibility=View.VISIBLE
            viewModel.boosters.observe(this@TopBoostersActivity, Observer {
                viewTopBoosterList.layoutManager=
                    LinearLayoutManager(this@TopBoostersActivity, LinearLayoutManager.VERTICAL, false)
                viewTopBoosterList.adapter= TopBoosterAdapter2(it)
                progressBarTopBooster.visibility= View.GONE
            })
            viewModel.loadBoosters()

            backBtn.setOnClickListener { finish() }
        }
    }
}