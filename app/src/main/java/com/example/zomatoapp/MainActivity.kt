package com.example.zomatoapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatoapp.Adapter.MainAdapter
import com.example.zomatoapp.Fragments.DashboardFragment
import com.example.zomatoapp.Fragments.NotfactonFragment
import com.example.zomatoapp.Fragments.OrderFragment
import com.example.zomatoapp.Fragments.ProfileFragment
import com.example.zomatoapp.Modals.MainDataModal
import com.example.zomatoapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        replaceFragment(DashboardFragment())

        val botomBa=findViewById<SmoothBottomBar>(R.id.bottomBar)
        botomBa.onItemSelected= { index ->
            when (index) {
                0 -> {
                    replaceFragment(DashboardFragment())
                    true
                }

                1 -> {
                    replaceFragment(NotfactonFragment())
                    true

                }

                2 -> {
                    replaceFragment(OrderFragment())
                    true

                }

                3 -> {
                    replaceFragment(ProfileFragment())
                    true
                }

            }

        }


        }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFram, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}