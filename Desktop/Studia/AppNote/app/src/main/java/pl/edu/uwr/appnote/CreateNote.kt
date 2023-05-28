package pl.edu.uwr.appnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
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

        val spinner = findViewById<Spinner>(R.id.spinner)
        val priority = arrayOf("Low", "Medium", "High")
        val arrayAdap = ArrayAdapter(this@CreateNote, android.R.layout.simple_spinner_dropdown_item,priority)
        spinner.adapter = arrayAdap

        val check1: CheckBox = findViewById(R.id.buttonp1)
        val check2: CheckBox = findViewById(R.id.buttonp2)
        val check3: CheckBox = findViewById(R.id.buttonp3)
        val checkboxes = listOf(check1, check2, check3)
        val mcreatetitleofnote: EditText = findViewById(R.id.createtitleofnote)
        val mcreatecontentofnote: EditText = findViewById(R.id.createcontentofnote)
        val msavenote: FloatingActionButton = findViewById(R.id.savenote)

        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        var selectedPriority = 0



        msavenote.setOnClickListener(View.OnClickListener {
            if (check1.isChecked) {
                selectedPriority = 1
            } else if (check2.isChecked) {
                selectedPriority = 2
            } else if (check3.isChecked) {
                selectedPriority = 3
            }
            val checkedCount = checkboxes.count { it.isChecked }
            val title = mcreatetitleofnote.getText().toString().trim { it <= ' ' }
            val content = mcreatecontentofnote.getText().toString().trim { it <= ' ' }
            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(applicationContext, "Both field are Require", Toast.LENGTH_SHORT)
                    .show()
            } else if(checkedCount != 1) {
                Toast.makeText(this, "Mark just one priority", Toast.LENGTH_SHORT)
                    .show()
            }else {
                val documentReference = firebaseFirestore!!.collection("notes").document(
                    firebaseUser!!.uid
                ).collection("myNotes").document()
                val nott:Note = Note(
                title =  title,
                content = content,
                priority = selectedPriority,
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