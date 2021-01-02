package com.afrakhteh.noteapp.ui


import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.afrakhteh.noteapp.R
import com.afrakhteh.noteapp.data.DBManager
import com.afrakhteh.noteapp.util.CalendarTool
import kotlinx.android.synthetic.main.fragment_adding_new_note.*
import kotlinx.android.synthetic.main.notetemplate.*

/**
 * A simple [Fragment] subclass.
 */
class AddingNewNoteFragment : Fragment() {
    var mainFragment = MainFragment()
    var dbManager: DBManager? = null
    var bundle = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adding_new_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageButton_addingfrag_back.setOnClickListener {
            view.findNavController().navigate(R.id.mainFragment)
        }

        try {
             bundle = arguments?.getInt("ID")!!
            if (bundle != 0) {
                textView_addingfrag_appname.setText(getString(R.string.editTitleText))
                val title = arguments?.getString("titleedit")
                val des = arguments?.getString("desedit")
                editText_addingfrag_getTitle.setText(title)
                editText_addingfrag_getdescription.setText(des)

                textView_cardtemplate_showtitle.text = title
                textView_cardtemplate_showdescription.text = des
            }
        } catch (e: Exception) { }

        button_addingfrag_addnewnote.setOnClickListener {
            textView_addingfrag_appname.setText(getString(R.string.addingnewnoteText))
            dbManager = DBManager(this.activity!!)

            val values = ContentValues()
            values.put("TITLE", editText_addingfrag_getTitle.text.toString())
            values.put("DESCRIPTION", editText_addingfrag_getdescription.text.toString())

            val calender = CalendarTool()
            val currentDate = calender.gregorianDate
            values.put("DATE",currentDate)
            if (bundle == 0) {
                val id = dbManager!!.Insert(values)
                if (id > 0) {
                    Toast.makeText(context, getString(R.string.adingnotetoast), Toast.LENGTH_LONG)
                        .show()
                    view.findNavController().navigate(R.id.mainFragment)
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.notadingnotetoast),
                        Toast.LENGTH_LONG
                    ).show()
                }
                try {
                    mainFragment.getDataRecyclerShow("%")
                }catch (e:Exception){
                    e.message.toString()
                }
            }
            else {
                val selectionArg = arrayOf(bundle.toString())
                dbManager?.Update(values, "ID=?", selectionArg)
                view.findNavController().navigate(R.id.mainFragment)
            }

        }
    }
}

