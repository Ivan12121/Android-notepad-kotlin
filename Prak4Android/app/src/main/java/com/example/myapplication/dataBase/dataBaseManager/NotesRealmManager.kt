package com.example.myapplication.dataBase.dataBaseManager

import com.example.myapplication.dataBase.models.NotesModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

class NotesRealmManager {
    private val config: RealmConfiguration = RealmConfiguration.Builder().name("Note.realm")
        .deleteRealmIfMigrationNeeded().schemaVersion(1).build()

    fun addNote(note: NotesModel) {
        val realm = Realm.getInstance(config)
        realm.beginTransaction()
        note.let { realm.insertOrUpdate(it) }
        realm.commitTransaction()
        realm.close()
    }

    val getAllNotes: RealmResults<NotesModel>?
        get() = Realm.getInstance(config).where(NotesModel::class.java).findAll()

    fun getNote(id: String): NotesModel? {
        return Realm.getInstance(config).where(NotesModel::class.java).equalTo("id", id).findFirst()
    }

    fun deleteNote(id: String) {
        val realm = Realm.getInstance(config)
        val itemRealmObject: NotesModel? = realm.where(NotesModel::class.java).equalTo("id", id).findFirst()
        if(itemRealmObject != null) {
            realm.beginTransaction()
            itemRealmObject.deleteFromRealm()
            realm.commitTransaction()
            realm.close()
        }
    }

}