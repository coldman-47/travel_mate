package fr.ece.travel_mate

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.ui.AppBarConfiguration
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import fr.ece.travel_mate.databinding.ActivityInscriptionBinding
import fr.ece.travel_mate.databinding.LoginBinding
import fr.ece.travel_mate.databinding.RegisterBinding
import fr.ece.travel_mate.databinding.RegisterEndBinding

class InscriptionActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityInscriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_inscription)
        auth = Firebase.auth

        val btn = findViewById<Button>(R.id.loginBtn)
        val suivant = findViewById<Button>(R.id.nextBtn)

        suivant.setOnClickListener(View.OnClickListener {
            setContentView(RegisterEndBinding.inflate(layoutInflater).root)

            btn.setOnClickListener(View.OnClickListener {
                setContentView(binding.root)
            })
        })


        setContentView(ActivityInscriptionBinding.inflate(layoutInflater).root)
        btn.setOnClickListener {
            val email = findViewById<EditText>(R.id.Emailtxt).text
            val password = findViewById<EditText>(R.id.Pwdtxt).text
            val confpass = findViewById<EditText>(R.id.ConfPwdtxt).text

            if (email.isNotEmpty() && password.isNotEmpty() && confpass.isNotEmpty()) {
                if (password.toString() == confpass.toString()) {
                    auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                //setContentView(LoginBinding.inflate(layoutInflater).root)
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)

                            } else {
                                Log.d(TAG, "Boom : " + task.exception?.message.toString())
                                Toast.makeText(
                                    baseContext,
                                    "Connexion refusée.",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Champs obligatoire", Toast.LENGTH_SHORT).show()
            }
        }
    }
}