package com.bozok.mytodolist.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bozok.mytodolist.R
import com.bozok.mytodolist.adapter.NoteRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.Exception
import java.text.DateFormat
import java.util.*

class MainFragment : Fragment() {

    var noteList=ArrayList<String>()
    var idList=ArrayList<Int>()
    var colorList=ArrayList<String>()
    var time=DateFormat.getTimeInstance()
    var date=DateFormat.getDateInstance()

    private lateinit var noteAdapter:NoteRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textDate.text=date.format(Date())
        textTime.text=time.format(Date())


        noteAdapter= NoteRecyclerAdapter(noteList,idList,colorList)
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.adapter=noteAdapter


        getDataSql()

       btnAddNote.setOnClickListener {
           addNote(it)
       }

        super.onViewCreated(view, savedInstanceState)
    }


    fun addNote(view:View){
        val action=MainFragmentDirections.actionMainFragmentToAddNoteFragment()
        Navigation.findNavController(view).navigate(action)

    }

    fun getDataSql(){

        //Collections.sort(colorList)

        try {

            context?.let {
                val database=it.openOrCreateDatabase("Notes",Context.MODE_PRIVATE,null)

                val cursor=database.rawQuery("SELECT*FROM notes",null)
                val noteIndex=cursor.getColumnIndex("note")
                val idIndex=cursor.getColumnIndex("id")
                val colorIndex=cursor.getColumnIndex("color")
                val colorIdIndex=cursor.getColumnIndex("colorId")

                noteList.clear()
                idList.clear()
                colorList.clear()

                while (cursor.moveToNext()){

                    noteList.add(cursor.getString(noteIndex))
                    idList.add(cursor.getInt(idIndex))
                    colorList.add(cursor.getString(colorIndex))


                }

                noteAdapter.notifyDataSetChanged() // verilerin değiştiğini bildiriyoruz ki güncelleme olsun.
                cursor.close()

            }


        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}