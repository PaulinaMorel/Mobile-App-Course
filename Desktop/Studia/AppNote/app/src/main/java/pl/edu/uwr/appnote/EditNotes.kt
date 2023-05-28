package pl.edu.uwr.appnote
import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class EditNotes : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    var firebaseFirestore: FirebaseFirestore? = null
    var firebaseUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(pl.edu.uwr.appnote.R.layout.activity_edit_notes)
        var medittitleofnote: EditText = findViewById(pl.edu.uwr.appnote.R.id.edittie)
        var meditcontentofnote: EditText = findViewById(pl.edu.uwr.appnote.R.id.editcontentofnote)
        var msaveeditnote: FloatingActionButton = findViewById(pl.edu.uwr.appnote.R.id.saveeditnote)
        var trash: ImageView = findViewById(pl.edu.uwr.appnote.R.id.trash)

        val check1: CheckBox = findViewById(pl.edu.uwr.appnote.R.id.buttonp12)
        val check2: CheckBox = findViewById(pl.edu.uwr.appnote.R.id.buttonp22)
        val check3: CheckBox = findViewById(pl.edu.uwr.appnote.R.id.buttonp32)
        val checkboxes = listOf(check1, check2, check3)
        var selectedPriority = 0

        if (check1.isChecked) {
            selectedPriority = 1
        } else if (check2.isChecked) {
            selectedPriority = 2
        } else if (check3.isChecked) {
            selectedPriority = 3
        }
        val checkedCount = checkboxes.count { it.isChecked }
        val data = intent
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        msaveeditnote.setOnClickListener(View.OnClickListener {
            val newtitle = medittitleofnote.getText().toString()
            val newcontent = meditcontentofnote.getText().toString()
            if (newtitle.isEmpty() || newcontent.isEmpty()) {
                Toast.makeText(applicationContext, "Something is empty", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if(checkedCount != 1) {
                Toast.makeText(this, "Mark just one priority", Toast.LENGTH_SHORT)
                    .show()
            }else {
                val documentReference = firebaseFirestore!!.collection("notes").document(
                    firebaseUser!!.uid
                ).collection("myNotes").document(data.getStringExtra("noteId").toString())
                val nott: Note = Note(
                    title = newtitle,
                    content = newcontent,
                    priority = selectedPriority,
                    did = documentReference.id
                )
                documentReference.set(nott).addOnSuccessListener {
                    Toast.makeText(applicationContext, "Note is updated", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@EditNotes, NotesActivity::class.java))
                }.addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Failed To update",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
        val notetitle = data.getStringExtra("title")
        val notecontent = data.getStringExtra("content")
        meditcontentofnote.setText(notecontent)
        medittitleofnote.setText(notetitle)

        trash.setOnClickListener { v->
            val documentReference =
                firebaseFirestore!!.collection("notes").document(
                    firebaseUser!!.uid
                ).collection("myNotes").document(data.getStringExtra("noteId").toString())
            documentReference.delete().addOnSuccessListener {
                Toast.makeText(
                    v.context,
                    "This note is deleted",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this@EditNotes, NotesActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(
                    v.context,
                    "Failed To Delete",
                    Toast.LENGTH_SHORT
                ).show()

        }
    }




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}