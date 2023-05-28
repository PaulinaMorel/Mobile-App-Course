package pl.edu.uwr.appnote
import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class NotesDetails : AppCompatActivity() {

    var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(pl.edu.uwr.appnote.R.layout.activity_notes_details)
        var mtitleofnotedetail = findViewById<TextView>(pl.edu.uwr.appnote.R.id.titleofnotedetail)
        var mcontentofnotedetail = findViewById<TextView>(pl.edu.uwr.appnote.R.id.contentofnotedetail)
        var mgotoeditnote = findViewById<FloatingActionButton>(pl.edu.uwr.appnote.R.id.gotoeditnote)
        val data = intent
        mgotoeditnote.setOnClickListener(View.OnClickListener { v ->
            val intent = Intent(v.context, EditNotes::class.java)
            intent.putExtra("title", data.getStringExtra("title"))
            intent.putExtra("content", data.getStringExtra("content"))
            intent.putExtra("noteId", data.getStringExtra("noteId"))
            v.context.startActivity(intent)
        })
        mcontentofnotedetail.setText(data.getStringExtra("content"))
        mtitleofnotedetail.setText(data.getStringExtra("title"))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}