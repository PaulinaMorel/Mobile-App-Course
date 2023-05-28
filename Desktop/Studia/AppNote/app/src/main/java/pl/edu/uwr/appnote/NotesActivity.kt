package pl.edu.uwr.appnote

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color.green
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.appnote.firebasemodel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xwpf.usermodel.XWPFParagraph
import org.apache.poi.xwpf.usermodel.XWPFRun
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*



class NotesActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null
    private lateinit var adapter: Adapter
    var firebaseUser: FirebaseUser? = null
    var firebaseFirestore: FirebaseFirestore? = null



    private lateinit var rV: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        val fb: FloatingActionButton = findViewById(R.id.createnotefab)
        var log: ImageView = findViewById(R.id.logout)
        var switch: Switch = findViewById(R.id.switch1)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        firebaseFirestore = FirebaseFirestore.getInstance()
        supportActionBar!!.title = "All Notes"
        fb.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@NotesActivity,CreateNote::class.java
                )
            )
        })
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        } else{
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
        }
        log.setOnClickListener{
            firebaseAuth!!.signOut()
            finish()
            startActivity(Intent(this@NotesActivity, MainActivity::class.java))
        }

        val query = firebaseFirestore!!.collection("notes").document(
            firebaseUser!!.uid
        ).collection("myNotes").orderBy("title", Query.Direction.ASCENDING)
        val allusernotes: FirestoreRecyclerOptions<Note> =
            FirestoreRecyclerOptions.Builder<Note>().setQuery(
                query,
                Note::class.java
            ).build()
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), PackageManager.PERMISSION_GRANTED
        )

        rV = findViewById(R.id.recyclerview)

        rV.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        }
        adapter = pl.edu.uwr.appnote.Adapter(allusernotes)
        rV.adapter = adapter


    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notetitle: TextView
        val notecontent: TextView
        var mnote: LinearLayout

        init {
            setContentView(R.layout.notes_layout)

            notetitle = itemView.findViewById(R.id.notetitle)
            notecontent = itemView.findViewById(R.id.notecontent)
            mnote = itemView.findViewById(R.id.note)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }



    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) {
            adapter!!.stopListening()
        }
    }

}
