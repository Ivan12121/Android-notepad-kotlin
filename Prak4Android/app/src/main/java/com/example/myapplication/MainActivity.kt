package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.NotesFragment
import com.example.myapplication.ui.TagsFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.body_layout, NotesFragment()).commit()

        binding.btnNotes.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.body_layout, NotesFragment()).commit()

        }
        binding.btnTags.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.body_layout, TagsFragment()).commit()
        }

    }
}