package com.afrakhteh.noteapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.afrakhteh.noteapp.adapter.Adapter
import com.afrakhteh.noteapp.data.DBManager
import com.afrakhteh.noteapp.model.Note
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    var dbManager: DBManager? = null
    val note: Note? = null
    var adapter: Adapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbManager = DBManager(this.activity!!)

        if (dbManager!!.count() == 0) {
            view.findNavController().navigate(R.id.action_mainFragment_to_emptyNoteFragment)
        }
        getDataRecyclerShow("%")
    }

    fun getDataRecyclerShow(title: String) {

        var listNote = ArrayList<Note>()
        val projection = arrayOf( "ID", "TITLE", "DESCRIPTION", "DATE" )
        val selectionArg = arrayOf(title)
        val cursor = dbManager?.Query(projection, "TITLE like ? ", selectionArg, "TITLE")
        listNote.clear()
        if (cursor!!.moveToFirst()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title = cursor.getString(cursor.getColumnIndex("TITLE"))
                val des = cursor.getString(cursor.getColumnIndex("DESCRIPTION"))
                val date = cursor.getString(cursor.getColumnIndex("DATE"))

                listNote.add(Note(ID, Title, des, date))

            } while (cursor!!.moveToNext())
        }
        adapter = Adapter(context!!, listNote)
        recy_mainfragment_recyshowitems.adapter = adapter
    }
}
