package pl.edu.uwr.appnote

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class Adapter(options : FirestoreRecyclerOptions<Note>) : FirestoreRecyclerAdapter<Note, Adapter.AdpaterHolder>(
    options
){
    class AdpaterHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val noteText: TextView = itemView.findViewById(R.id.notetitle)
        val contentText: TextView = itemView.findViewById(R.id.notecontent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdpaterHolder {
        return AdpaterHolder(LayoutInflater.from(parent.context).inflate(R.layout.notes_layout,parent,false))
    }

    override fun onBindViewHolder(holder: AdpaterHolder, position: Int, model: Note) {
        holder.noteText.text = model.title
        holder.contentText.text = model.content


        holder.itemView.setOnClickListener { v ->
                        val intent = Intent(v.context, EditNotes::class.java)
                        intent.putExtra("title", model.title)
                        intent.putExtra("content", model.content)
                        intent.putExtra("priority", model.priority)
                        intent.putExtra("noteId", model.did)
                        v.context.startActivity(intent)

                    }
    }

}