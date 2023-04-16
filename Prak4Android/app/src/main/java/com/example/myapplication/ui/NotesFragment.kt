package com.example.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.EditNoteActivity
import com.example.myapplication.NewNotesActivity
import com.example.myapplication.R
import com.example.myapplication.adapter.NoteAdapter
import com.example.myapplication.dataBase.dataBaseManager.NotesRealmManager
import com.example.myapplication.dataBase.models.NotesModel
import com.example.myapplication.databinding.FragmentNotesBinding
import io.realm.RealmResults

class NotesFragment : Fragment() {
    lateinit var binding: FragmentNotesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNewNote.setOnClickListener {
            val intent = Intent(context, NewNotesActivity::class.java)
            startActivity(intent)
        }

        initData()
    }

    private fun initData() {
        binding.recycleBodyNote.layoutManager = LinearLayoutManager(context)
        val noteList : RealmResults<NotesModel>? = NotesRealmManager().getAllNotes
        val noteAdapter = NoteAdapter(context, noteList, object : NoteAdapter.OnCallBack{
            override fun onCallBack(view: View, note: NotesModel?) {
                showMenu(view, note)
            }

            override fun onSelected(category: NotesModel) {

            }
        })
        noteList?.addChangeListener {
            t, _ -> noteAdapter.setList(t)

        }
        binding.recycleBodyNote.adapter = noteAdapter

    }

    private fun showMenu(view: View, note: NotesModel?) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.menuInflater.inflate(R.menu.menu_edit_note, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.action_edit_note -> {
                    if(note!=null) {
                        startActivity(Intent(context, EditNoteActivity::class.java).putExtra("id_note", note.id))
                    }
                }
                R.id.action_delete_note -> {
                    note?.id?.let { NotesRealmManager().deleteNote(it) }
                }
            }
            true
        }
        popupMenu.show()
    }


}