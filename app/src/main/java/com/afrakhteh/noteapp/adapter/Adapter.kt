package com.afrakhteh.noteapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.noteapp.R
import com.afrakhteh.noteapp.data.DBManager
import com.afrakhteh.noteapp.model.Note


class Adapter : RecyclerView.Adapter<Adapter.ViewHolder> {
    //for buttons
   // private val listener:(Note) ->Unit

    var listNote = ArrayList<Note>()
    var context: Context? = null

    constructor(context: Context, listNote: ArrayList<Note>) {
        this.context = context
        this.listNote = listNote
      //  this.listener = listener

    }

    var itemClick: ((String) -> Unit)? = null
    var onItemLongPress: ((Note) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context!!).inflate(R.layout.notetemplate, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var note = listNote[position]
        holder.bind(note)
        val dbManager = DBManager(context!!)

        //update / edit note
        holder.editbtn.setOnClickListener {
            val titleedt = note.title.toString().trim()
            val descriptionedt = note.description.toString().trim()

            var bundle =Bundle()
            bundle.putInt("ID", note.id!!)
            bundle.putString("titleedit",titleedt)
            bundle.putString("desedit" , descriptionedt)
            it?.findNavController()?.navigate(R.id.addingNewNoteFragment,bundle)
        }
        //delete one note
        holder.deletebtn.setOnClickListener {
            val alertdialogbuild = AlertDialog.Builder(context)
            val views = LayoutInflater.from(context).inflate(R.layout.deletealert, null)
            alertdialogbuild.setView(views)
            val yesbtn = views.findViewById<Button>(R.id.button_alert_deleting)
            val nobtn = views.findViewById<Button>(R.id.button_alert_notdelete)
            val dialog = alertdialogbuild.create()
            dialog.show()
            yesbtn.setOnClickListener {
                var selectionArg = arrayOf(note.id.toString())
                dbManager.Delete("ID=?", selectionArg)
                listNote.remove(note)
                notifyItemRemoved(position)
                dialog.dismiss()
            }
            nobtn.setOnClickListener {
                dialog.dismiss()
            }
        }
        //share one note
        holder.sharebtn.setOnClickListener {
            val sharing = note.description
            var i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT, sharing)
            context!!.startActivity(i)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){


        val titleText: TextView = itemView.findViewById(R.id.textView_cardtemplate_showtitle)
        val descriptionText: TextView =
            itemView.findViewById(R.id.textView_cardtemplate_showdescription)
        val deletebtn: ImageButton = itemView.findViewById(R.id.imagebtn_cardtemplate_delete)
        val editbtn: ImageButton = itemView.findViewById(R.id.imagebtn_cardtemplate_edit)
        val dateText: TextView = itemView.findViewById(R.id.textView_cardtemplate_showdate)
        val sharebtn: ImageButton = itemView.findViewById(R.id.imagebtn_cardtemplate_share)



        fun bind(note: Note) {
            titleText.text = note.title.toString().trim()
            descriptionText.text = note.description.toString().trim()
            dateText.text = note.date
        }


    }



}




