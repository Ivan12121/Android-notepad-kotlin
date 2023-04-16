package com.example.myapplication.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.dataBase.models.NotesModel
import io.realm.RealmResults

class NoteAdapter(private  val context: Context?, private var list: RealmResults<NotesModel>?, private val onCallback: OnCallBack): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    fun setList(newList: RealmResults<NotesModel>) {
        this.list = newList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = list?.get(position)?.date ?: ""
        holder.name.text = list?.get(position)?.head ?: ""
        holder.text.text = list?.get(position)?.body ?: ""
        var tagsString = ""
        list?.get(position)?.tags?.forEach {
            tagsString += "${it.name} "
        }
        holder.tags.text = tagsString
        holder.btnMore.setOnClickListener{onCallback.onCallBack(holder.btnMore, list?.get(position))}
        holder.itemView.setOnClickListener {
            list?.get(position)?.let { it1 ->onCallback.onSelected(it1) }
        }
    }

    interface OnCallBack {
        fun onCallBack(view: View, note: NotesModel?)
        fun onSelected(category: NotesModel)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.date_note)
        var name: TextView = itemView.findViewById(R.id.name_note)
        var text: TextView = itemView.findViewById(R.id.text_note)
        var tags: TextView = itemView.findViewById(R.id.tags_note)
        var btnMore: ImageButton = itemView.findViewById(R.id.btn_update_or_delete_note)
    }
}