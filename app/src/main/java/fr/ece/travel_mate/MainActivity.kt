package fr.ece.travel_mate

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import fr.ece.travel_mate.databinding.LoginBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: LoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginBinding.inflate(layoutInflater)
        val sharedPreferences = getSharedPreferences("login", MODE_PRIVATE)
        Log.d(TAG, "boom "+sharedPreferences.all["user"].toString())
        if (sharedPreferences.all["user"] != null) {
            loadHomePage()
        } else {
            setContentView(binding.root)
            // Initialize Firebase Auth
            auth = Firebase.auth
            val btn = findViewById<Button>(R.id.btnInscription)
            val registerBtn = findViewById<Button>(R.id.registerLink)
            btn.setOnClickListener(View.OnClickListener {
                val login = findViewById<AppCompatEditText>(R.id.loginEmail).text
                val password = findViewById<EditText>(R.id.loginPwd).text
                auth.signInWithEmailAndPassword(login.toString(), password.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            sharedPreferences.edit()
                                .putString("user", task.result.user?.email.toString()).commit()
//                        setContentView(HomeBinding.inflate(layoutInflater).root)
                            loadHomePage()

                        } else {
                            Toast.makeText(
                                baseContext,
                                "Connexion refusée.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            })
            registerBtn.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@MainActivity, InscriptionActivity::class.java)
                startActivity(intent)
                /*setContentView(RegisterBinding.inflate(layoutInflater).root)
                val loginLink: Button = findViewById(R.id.loginLink)
                loginLink.setOnClickListener(View.OnClickListener {
                    setContentView(binding.root)
                })
                */
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private fun loadHomePage() {
        val intent = Intent(this@MainActivity, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}