package com.afrakhteh.noteapp.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.afrakhteh.noteapp.MainFragment
import com.afrakhteh.noteapp.R
import com.afrakhteh.noteapp.data.DBManager
import com.afrakhteh.noteapp.model.Note
import java.util.*
import kotlin.collections.ArrayList


class Adapter : RecyclerView.Adapter<Adapter.ViewHolder> {
    var listNote = ArrayList<Note>()
    var context: Context? = null

    constructor(context: Context, listNote: ArrayList<Note>) {
        this.context = context
        this.listNote = listNote
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context!!).inflate(R.layout.notetemplate, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dbManager = DBManager(context!!)
        var note = listNote[position]
        holder.titleText.text = note.title.toString().trim()
        holder.descriptionText.text = note.description.toString().trim()
        holder.dateText.text= Calendar.getInstance().get(Calendar.DATE).toString()

        holder.deletebtn.setOnClickListener {
            //delete items
            var selectionArg = arrayOf(note.id.toString())
            dbManager.Delete("ID=?",selectionArg)
            notifyDataSetChanged()
            var main =MainFragment()
            main.getDataRecyclerShow("%")
        }

        holder.editbtn.setOnClickListener {
            val view:View?=null
            var titleedt = note.title.toString().trim()
            var descriptionedt = note.description.toString().trim()
            var bundle = Bundle()
            bundle.putString("titleedit",titleedt)
            bundle.putString("desedit",descriptionedt)

            view?.findNavController()?.navigate(R.id.addingNewNoteFragment,bundle)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.textView_cardtemplate_showtitle)
        val descriptionText: TextView =
            itemView.findViewById(R.id.textView_cardtemplate_showdescription)
        val deletebtn: ImageButton = itemView.findViewById(R.id.imagebtn_cardtemplate_delete)
        val editbtn: ImageButton = itemView.findViewById(R.id.imagebtn_cardtemplate_edit)
        val dateText: TextView = itemView.findViewById(R.id.textView_cardtemplate_showdate)



    }


}