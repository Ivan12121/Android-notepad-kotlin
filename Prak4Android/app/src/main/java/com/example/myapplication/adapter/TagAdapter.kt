package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.dataBase.models.NotesModel
import com.example.myapplication.dataBase.models.TagsModel
import io.realm.RealmResults

class TagAdapter(private  val context: Context?, private var list: RealmResults<TagsModel>?, private val onCallback: TagAdapter.OnCallBack): RecyclerView.Adapter<TagAdapter.ViewHolder>() {
    interface OnCallBack {
        fun onCallBack(view: View, category: TagsModel?)
        fun onSelected(category: TagsModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tags, parent, false)
        )
    }

    fun setList(newList: RealmResults<TagsModel>) {
        this.list = newList
    }

    override fun onBindViewHolder(holder: TagAdapter.ViewHolder, position: Int) {
        holder.name.text = list?.get(position)?.name ?: ""
        holder.btnMore.setOnClickListener{onCallback.onCallBack(holder.btnMore, list?.get(position))}
        holder.itemView.setOnClickListener {
            list?.get(position)?.let { it1 ->onCallback.onSelected(it1) }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name_tag)
        var btnMore: ImageButton = itemView.findViewById(R.id.btn_update_or_delete_tag)
    }
}