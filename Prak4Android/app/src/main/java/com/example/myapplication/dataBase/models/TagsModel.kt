package com.example.myapplication.dataBase.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class TagsModel(@PrimaryKey var id: String = "", var name: String = "", var notes: RealmList<NotesModel>? = null): RealmObject() {

}