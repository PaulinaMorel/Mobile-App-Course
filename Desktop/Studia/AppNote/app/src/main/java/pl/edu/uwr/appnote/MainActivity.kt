package pl.edu.uwr.appnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var switch2: Switch = findViewById(R.id.switch12)
        auth = Firebase.auth
        val registerText: TextView = findViewById(R.id.textViw_register)


        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                println("AAAAAAAAAAAAAAAAAAAAAA")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else{
                println("BBBBBBBBBBBBBBBBBBBBBBB")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        registerText.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        val loginButton: Button = findViewById(R.id.button2)

        loginButton.setOnClickListener {
            performLogin()
        }
    }
    private fun performLogin(){
        val emaillog: EditText = findViewById(R.id.login)
        val passwordlog: EditText = findViewById(R.id.pass)

//        if(emaillog.text.isEmpty() || passwordlog.text.isEmpty()){
//            Toast.makeText(baseContext, "Fill all fields.",
//                Toast.LENGTH_SHORT).show()
//            return
//        }
        val inputEmaillog = "1234@gmail.com"//emaillog.text.toString()
        val inputPasslog = "1234567"//passwordlog.text.toString()

        auth.signInWithEmailAndPassword(inputEmaillog,inputPasslog)
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