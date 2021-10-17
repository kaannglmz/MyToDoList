package com.bozok.mytodolist.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bozok.mytodolist.Fragments.MainFragmentDirections
import com.bozok.mytodolist.R
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*

class NoteRecyclerAdapter(val noteList:ArrayList<String>,val idList:ArrayList<Int>,val colorList:ArrayList<String>):RecyclerView.Adapter<NoteRecyclerAdapter.NoteHolder>() {

    class NoteHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder { // layout bağlama işlemlerini yaptığımız kısım.
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row,parent,false)
        return NoteHolder(view)

    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.itemView.recylerTextView.text=noteList[position]
        try {
            val color=colorList[position]
            holder.itemView.recylerTextView.setBackgroundColor(Color.parseColor(color))
        }catch (e:Exception){
            e.printStackTrace()
        }

        holder.itemView.setOnClickListener {
            val alert=AlertDialog.Builder(it.context)
            alert.setTitle("Delete")
            alert.setMessage("Do you want delete?")
            alert.setPositiveButton("Yes"){dialogInterface:DialogInterface,i:Int->

                try {

                    val database=holder.itemView.context.openOrCreateDatabase("Notes",Context.MODE_PRIVATE,null)
                    database.execSQL("DELETE FROM notes WHERE id = ?",arrayOf<String>(java.lang.String.valueOf(idList[position]))
                    )



                }catch (e:Exception){
                    e.printStackTrace()
                }
                val action=MainFragmentDirections.actionMainFragmentSelf()
                Navigation.findNavController(it).navigate(action)

            }
            alert.setNegativeButton("No") {dialogInterface: DialogInterface, i: Int ->

                Toast.makeText(it.context,"Not Deleted",Toast.LENGTH_LONG).show() }
            alert.show()

        }


    }



    override fun getItemCount(): Int { // recycler'da ne kadar sütün olacağını belirlediğimiz kısım.
        return noteList.size
    }

}