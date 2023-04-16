package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.dataBase.dataBaseManager.NotesRealmManager
import com.example.myapplication.dataBase.dataBaseManager.TagsRealmManager
import com.example.myapplication.dataBase.models.NotesModel
import com.example.myapplication.dataBase.models.TagsModel
import com.example.myapplication.databinding.ActivityNewNotesBinding
import io.realm.RealmList
import java.text.SimpleDateFormat
import java.util.*

class NewNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewNotesBinding
    private var tagsResult = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewNotesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSaveNote.setOnClickListener {
            val noteManager = NotesRealmManager()
            val dateFormat = SimpleDateFormat("d MMM yyyy")
            val date = dateFormat.format(Date())
            val tagsAll = RealmList<TagsModel>()
            tagsResult.forEach {
                val tag = TagsRealmManager().getTag(it)
                tagsAll.add(tag)
            }
            val note: NotesModel = NotesModel(System.currentTimeMillis().toString(), date.toString(), binding.textHeadNote.text.toString(), binding.textMainNote.text.toString(), tagsAll)

            noteManager.addNote(note)
            finish()
        }
        binding.btnCancelNote.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnAddTagInNote.setOnClickListener {
            if(binding.inputTextTagNote.text.isEmpty()) {
                Toast.makeText(this, "Заполните тег", Toast.LENGTH_SHORT).show()
            }
            else {
                val tag = TagsRealmManager().getTag(binding.inputTextTagNote.text.toString())
                if(tag!=null) {
                    tagsResult.add(tag.name)
                    Toast.makeText(this, "тег добавлен", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(this, "такого тега нет", Toast.LENGTH_SHORT).show()
            }
        }
    }
}