package com.example.dashboard.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dashboard.Adapter.CategoryAdapter
import com.example.dashboard.Adapter.TopBoosterAdapter
import com.example.dashboard.R
import com.example.dashboard.ViewModel.MainViewModel
import com.example.dashboard.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initCategory()

        initTopBoosters()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun initTopBoosters() {
        binding.apply {
            progressBarTopBooster.visibility=View.VISIBLE
            viewModel.boosters.observe(this@MainActivity, Observer {
                recyclerViewTopBooster.layoutManager=LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                recyclerViewTopBooster.adapter= TopBoosterAdapter(it)
                progressBarTopBooster.visibility= View.GONE
            })
            viewModel.loadBoosters()

            boosterListTxt.setOnClickListener {
                startActivity(Intent(this@MainActivity, TopBoostersActivity::class.java))
            }
        }
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
            viewModel.category.observe(this@MainActivity, Observer{
                Log.d("CategoryObserver", "List size: ${it.size}")
                binding.viewCategory.layoutManager = LinearLayoutManager(this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,false)
                binding.viewCategory.adapter= CategoryAdapter(it)
                binding.progressBarCategory.visibility= View.GONE
            })
        viewModel.loadCategory()
    }

}