package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.myapplication.dataBase.dataBaseManager.NotesRealmManager
import com.example.myapplication.dataBase.dataBaseManager.TagsRealmManager
import com.example.myapplication.dataBase.models.NotesModel
import com.example.myapplication.dataBase.models.TagsModel
import com.example.myapplication.databinding.ActivityEditNoteBinding
import io.realm.RealmList
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.util.*

class EditNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditNoteBinding
    var notes: NotesModel? = null
    private var tagsResult = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val extras: Bundle? = getIntent().getExtras()
        val id = extras?.getString("id_note").toString()


        notes = NotesRealmManager().getNote(id)
        if(notes!=null) {
            binding.inputHeadEditNote.setText(notes!!.head)
            binding.inputTextEditNote.setText(notes!!.body)
        }

        binding.btnSaveEditNote.setOnClickListener {
            val noteManager = NotesRealmManager()
            val dateFormat = SimpleDateFormat("d MMM yyyy")
            val date = dateFormat.format(Date())
            val note = NotesModel()
            note.id = id
            note.head = binding.inputHeadEditNote.text.toString()
            note.body = binding.inputTextEditNote.text.toString()
            note.date = date
            Toast.makeText(this, tagsResult.size.toString(), Toast.LENGTH_SHORT).show()
            val tagsAll = RealmList<TagsModel>()
            tagsResult.forEach {
                val tag = TagsRealmManager().getTag(it)
                tagsAll.add(tag)
            }
            note.tags?.let { it1 -> tagsAll.addAll(it1) }
            note.tags = tagsAll

            noteManager.addNote(note)
            finish()
        }

        binding.btnAddTag.setOnClickListener {
            if(binding.inputTagName.text.isEmpty()) {
                Toast.makeText(this, "Заполните тег", Toast.LENGTH_SHORT).show()
            }
            else {
                val tag = TagsRealmManager().getTag(binding.inputTagName.text.toString())
                if(tag!=null) {
                    tagsResult.add(tag.name)
                    Toast.makeText(this, "тег добавлен", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(this, "такого тега нет", Toast.LENGTH_SHORT).show()
            }
        }

    }
}