package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.myapplication.adapter.NoteAdapter
import com.example.myapplication.adapter.TagAdapter
import com.example.myapplication.dataBase.dataBaseManager.NotesRealmManager
import com.example.myapplication.dataBase.dataBaseManager.TagsRealmManager
import com.example.myapplication.dataBase.models.NotesModel
import com.example.myapplication.dataBase.models.TagsModel
import com.example.myapplication.databinding.FragmentTagsBinding
import io.realm.RealmResults

class TagsFragment : Fragment() {
    lateinit var binding: FragmentTagsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTagsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNewTag.setOnClickListener {
            val intent = Intent(context, NewTagsActivity::class.java)
            startActivity(intent)
        }

        initData()
    }

    private fun initData() {
        binding.recycleBodyTag.layoutManager = LinearLayoutManager(context)
        val tagList : RealmResults<TagsModel>? = TagsRealmManager().getAllTags
        val tagAdapter = TagAdapter(context, tagList, object : TagAdapter.OnCallBack{
            override fun onCallBack(view: View, tag: TagsModel?) {
                if (tag != null) {
                    showMenu(view, tag)
                }
            }

            override fun onSelected(category: TagsModel) {

            }
        })
        tagList?.addChangeListener {
                t, _ -> tagAdapter.setList(t)
        }
        binding.recycleBodyTag.adapter = tagAdapter

    }

    private fun showMenu(view: View, tag: TagsModel) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_edit_tag, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.action_edit_tag -> {
                    startActivity(Intent(context, EditTagActivity::class.java).putExtra("id_tag", tag.id))
                }
                R.id.action_delete_tag -> {
                    tag.id.let { TagsRealmManager().deleteTag(it) }
                }
            }
            true
        }
        popupMenu.show()
    }

}