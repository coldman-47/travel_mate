package fr.ece.travel_mate

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import fr.ece.travel_mate.databinding.ActivityInscriptionBinding

class InscriptionActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityInscriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        val btn = findViewById<Button>(R.id.registerBttn)
        btn.setOnClickListener {
            val email = findViewById<EditText>(R.id.registerEmail).text
            val password = findViewById<EditText>(R.id.registerPwd).text
            val confpass = findViewById<EditText>(R.id.registerConfPwd).text

            Log.d(TAG,"boom " + email.toString())
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