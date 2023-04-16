package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.dataBase.dataBaseManager.NotesRealmManager
import com.example.myapplication.dataBase.dataBaseManager.TagsRealmManager
import com.example.myapplication.dataBase.models.NotesModel
import com.example.myapplication.dataBase.models.TagsModel
import com.example.myapplication.databinding.ActivityNewTagsBinding

class NewTagsActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewTagsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewTagsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnTagSave.setOnClickListener {
            val tagsManager = TagsRealmManager()
            val tag: TagsModel = TagsModel(System.currentTimeMillis().toString(), binding.tagText.text.toString())

            tagsManager.addTag(tag)
            finish()
        }
        binding.btnTagCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}