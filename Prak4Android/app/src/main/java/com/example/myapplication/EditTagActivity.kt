package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.dataBase.dataBaseManager.NotesRealmManager
import com.example.myapplication.dataBase.dataBaseManager.TagsRealmManager
import com.example.myapplication.dataBase.models.NotesModel
import com.example.myapplication.dataBase.models.TagsModel
import com.example.myapplication.databinding.ActivityEditTagBinding
import io.realm.RealmList
import java.text.SimpleDateFormat
import java.util.*

class EditTagActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditTagBinding
    var tags: TagsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEditTagBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val extras: Bundle? = getIntent().getExtras()
        val id = extras?.getString("id_tag").toString()

        tags = TagsRealmManager().getTagById(id)
        if(tags!=null) {
            binding.inputEditTextTag.setText(tags!!.name)
        }

        binding.btnSaveEditTag.setOnClickListener {
            val tagManager = TagsRealmManager()
            val tag = TagsModel()
            tag.id = id
            tag.name = binding.inputEditTextTag.text.toString()

            tagManager.addTag(tag)
            finish()
        }
    }
}