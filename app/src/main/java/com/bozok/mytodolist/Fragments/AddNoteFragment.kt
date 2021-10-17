package com.bozok.mytodolist.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.bozok.mytodolist.R
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.lang.Exception

class AddNoteFragment : Fragment() {
    var timeColor:String="null"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        btnToday.setOnClickListener {
            timeColor="#ED0303"

            Toast.makeText(it.context,"Time is Today",Toast.LENGTH_LONG).show()
        }
        btnWeek.setOnClickListener {
            timeColor="#F66B00"

            Toast.makeText(it.context,"Time is This Week",Toast.LENGTH_LONG).show()
        }
        btnMonth.setOnClickListener {
            timeColor="#CCC707"

            Toast.makeText(it.context,"Time is This Month",Toast.LENGTH_LONG).show()
        }

        btnSave.setOnClickListener {

            val noteName=editNote.text.toString()
            val saveTimeColor= timeColor

           try {
            context?.let {
                val database=it.openOrCreateDatabase("Notes",Context.MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS notes(id INTEGER PRIMARY KEY,note VARCHAR,color VARCHAR)")

                val sqlString="INSERT INTO notes (note,color) VALUES(?,?)"
                val statement=database.compileStatement(sqlString)
                statement.bindString(1,noteName)
                statement.bindString(2,saveTimeColor)

                statement.execute()

            }

           }catch (e:Exception){
               e.printStackTrace()
           }

            val action=AddNoteFragmentDirections.actionAddNoteFragmentToMainFragment()
            Navigation.findNavController(view).navigate(action)

        }

        super.onViewCreated(view, savedInstanceState)
    }

}