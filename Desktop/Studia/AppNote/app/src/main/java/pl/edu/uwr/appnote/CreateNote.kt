package pl.edu.uwr.appnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class CreateNote : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    var firebaseFirestore = FirebaseFirestore.getInstance()
    var firebaseUser = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)


        val mcreatetitleofnote: EditText = findViewById(R.id.createtitleofnote)
        val mcreatecontentofnote: EditText = findViewById(R.id.createcontentofnote)
        val msavenote: FloatingActionButton = findViewById(R.id.savenote)

        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        msavenote.setOnClickListener(View.OnClickListener {
            val title = mcreatetitleofnote.getText().toString().trim { it <= ' ' }
            val content = mcreatecontentofnote.getText().toString().trim { it <= ' ' }
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(applicationContext, "Both field are Require", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val documentReference = firebaseFirestore!!.collection("notes").document(
                    firebaseUser!!.uid
                ).collection("myNotes").document()
                val nott:Note = Note(
                title =  title,
                content = content,
                did = documentReference.id)
                documentReference.set(nott).addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Note Created Succesffuly",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@CreateNote, NotesActivity::class.java)) //
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Failed To Create Note",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

}