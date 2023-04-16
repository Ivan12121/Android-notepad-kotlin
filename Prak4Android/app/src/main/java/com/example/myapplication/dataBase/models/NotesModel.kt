package com.example.myapplication.dataBase.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.PrimaryKey

open class NotesModel (@PrimaryKey var id: String ="", var date: String = "", var head: String = "", var body: String = "", var tags: RealmList<TagsModel>? = null): RealmObject() {


}