package pl.edu.uwr.appnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import pl.edu.uwr.appnote.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        val loginText: TextView = findViewById(R.id.textViw_login)
        loginText.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val registerButoon: Button = findViewById(R.id.button)

        registerButoon.setOnClickListener{

            performSignUp()
        }

    }
    private fun performSignUp() {
        val email = findViewById<EditText>(R.id.login2)
        val password = findViewById<EditText>(R.id.pass2)

        if(email.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(baseContext, "Fill all fields.",
                Toast.LENGTH_SHORT).show()
            return
        }
        val inputEmail = email.text.toString()
        val inputPass = password.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail,inputPass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, NotesActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(baseContext, "Authentication success.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(baseContext, "Error",
                    Toast.LENGTH_SHORT).show()
            }
    }
}