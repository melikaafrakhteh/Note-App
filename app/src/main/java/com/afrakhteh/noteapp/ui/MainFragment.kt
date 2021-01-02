package com.afrakhteh.noteapp.ui


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afrakhteh.noteapp.R
import com.afrakhteh.noteapp.adapter.Adapter
import com.afrakhteh.noteapp.data.DBManager
import com.afrakhteh.noteapp.model.Note
import kotlinx.android.synthetic.main.fragment_main.*


/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    var dbManager: DBManager? = null
    lateinit var myNotesAdapter: Adapter
    lateinit var  listNotes:ArrayList<Note>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        dbManager = DBManager(this.activity!!)
        if (dbManager!!.Count() == 0) {
            view.findNavController().navigate(R.id.action_mainFragment_to_emptyNoteFragment)
        } else {
            getDataRecyclerShow("%")

            btnAdd()
        }

    }

    private fun btnAdd() {
        btn_mainfragment_addnewnote.setOnClickListener {
            view?.findNavController()?.navigate(R.id.addingNewNoteFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)
       val sv = menu?.findItem(R.id.search).actionView as SearchView
       val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        }

        sv.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getDataRecyclerShow("%"+ newText +"%")
                if (newText?.length == 0){
                    view?.findNavController()?.navigate(R.id.mainFragment)
                    myNotesAdapter.notifyDataSetChanged()

                }
                return true
            }
        })
    }



    fun getDataRecyclerShow(title: String) {
        dbManager = DBManager(activity!!)
        listNotes = ArrayList<Note>()
        val projections = arrayOf("ID", "TITLE", "DESCRIPTION", "DATE")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager!!.Query(projections, "TITLE like ?", selectionArgs, "TITLE")
        listNotes.clear()
        if (cursor.moveToFirst()) {

            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Title = cursor.getString(cursor.getColumnIndex("TITLE"))
                val Description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"))
                val Date = cursor.getString(cursor.getColumnIndex("DATE"))

                listNotes.add(Note(ID, Title, Description, Date))

            } while (cursor.moveToNext())
        }

        myNotesAdapter = Adapter(activity!!, listNotes)

        val layoutManager = LinearLayoutManager(context)


        recy_mainfragment_recyshowitems.layoutManager = layoutManager
        recy_mainfragment_recyshowitems.itemAnimator = DefaultItemAnimator()
        recy_mainfragment_recyshowitems.adapter = myNotesAdapter


    }



}



