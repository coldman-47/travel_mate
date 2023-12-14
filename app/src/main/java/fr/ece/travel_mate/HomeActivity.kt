package fr.ece.travel_mate

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import fr.ece.travel_mate.databinding.ActivityHomeBinding
import fr.ece.travel_mate.databinding.FragmentHotelBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        findViewById<ImageButton>(R.id.logoutBtn).setOnClickListener {
            val sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
            sharedPreferences.edit().clear().commit()
            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        val hotelBtn = findViewById<Button>(R.id.hotelBtn)
        val destBtn = findViewById<Button>(R.id.destinationBtn)
        hotelBtn.setOnClickListener({
            setContentView(FragmentHotelBinding.inflate(layoutInflater).root)
        })

//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        return;
    }
}