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
import com.google.firebase.auth.FirebaseAuth // Import FirebaseAuth
import com.google.firebase.auth.FirebaseUser // Import FirebaseUser

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    // Declare FirebaseAuth and FirebaseUser
    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser // Get the current user

        // Check if user is logged in and navigate accordingly
//        checkLoginStatus() WILL REMOVE TEMPORARILY

        // Rest of your existing setup (only if the user is logged in and stays in MainActivity)
        // If you're navigating away immediately, you might move this.
        initCategory()
        initTopBoosters()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up click listener for the profile button (using your binding)
        // Assuming your layout has a view with the ID 'profileTxt' for the profile button
        binding.profileNav.setOnClickListener {
            // Replace Login::class.java with your actual ProfileActivity::class.java
            val intent = Intent(applicationContext, com.example.user.Login::class.java)
            startActivity(intent)
            finish()
        }

        binding.seeMoreGames.setOnClickListener {
            val intent = Intent(this, com.example.gamelist.GamesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkLoginStatus() {
        if (user == null) {
            // If user is not logged in, navigate to LoginActivity
            val intent = Intent(applicationContext, com.example.user.Login::class.java)
            startActivity(intent)
            finish() // Finish MainActivity so the user can't go back to it from Login
        } else {
            // If user is logged in, you can optionally navigate to a dashboard activity
            // or proceed with displaying content in MainActivity.
            // Based on your Java code, you might navigate here.
            // If MainActivity IS your dashboard, you can remove this else block.
            // Example if you had a separate DashboardActivity:
            // val intent = Intent(applicationContext, DashboardActivity::class.java)
            // startActivity(intent)
            // finish() // Finish MainActivity

            // If MainActivity is the dashboard, you just continue in onCreate
            // and the existing initCategory() and initTopBoosters() will run.
            Log.d("MainActivity", "User is logged in: ${user?.email}")
            // You might update UI elements here to show user details
            // binding.userDetails.text = "Logged in as: ${user?.email}" // Assuming userDetails TextView
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