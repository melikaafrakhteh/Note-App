package com.afrakhteh.noteapp


import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.afrakhteh.noteapp.data.DBManager
import kotlinx.android.synthetic.main.fragment_adding_new_note.*
import kotlinx.android.synthetic.main.notetemplate.*

/**
 * A simple [Fragment] subclass.
 */
class AddingNewNoteFragment : Fragment() {
    var dbManager: DBManager? = null
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
        var iD = 0
        try {
            var title = arguments?.getString("titleedit")
            var des = arguments?.getString("desedit")
            if (iD != 0) {
                textView_cardtemplate_showtitle.setText(title)
                textView_cardtemplate_showdescription.setText(des)
            }
        } catch (e: Exception) {
        }
        button_addingfrag_addnewnote.setOnClickListener {
            dbManager = DBManager(this.activity!!)
            var values = ContentValues()
            values.put("Title", textView_cardtemplate_showtitle.text.toString())
            values.put("Des", textView_cardtemplate_showdescription.text.toString())

            if (iD == 0) {
                val id = dbManager?.Insert(values)
                if (id!! > 0) {
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
                val main = MainFragment()
                main.getDataRecyclerShow("%")
            } else {
                var selectionArg = arrayOf(iD.toString())
                val updateId = dbManager?.Update(values, "ID=?", selectionArg)
                view.findNavController().navigate(R.id.mainFragment)
            }

        }
    }
}

