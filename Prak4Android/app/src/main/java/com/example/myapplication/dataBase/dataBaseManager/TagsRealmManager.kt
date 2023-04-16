package com.example.myapplication.dataBase.dataBaseManager

import com.example.myapplication.dataBase.models.NotesModel
import com.example.myapplication.dataBase.models.TagsModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults

class TagsRealmManager {
    private val config: RealmConfiguration = RealmConfiguration.Builder().name("Tags.realm")
        .deleteRealmIfMigrationNeeded().schemaVersion(1).build()

    fun addTag(tag: TagsModel) {
        val realm = Realm.getInstance(config)
        realm.beginTransaction()
        tag.let { realm.insertOrUpdate(it) }
        realm.commitTransaction()
        realm.close()
    }


    val getAllTags: RealmResults<TagsModel>?
        get() = Realm.getInstance(config).where(TagsModel::class.java).findAll()

    fun getTag(name: String): TagsModel? {
        return Realm.getInstance(config).where(TagsModel::class.java).equalTo("name", name).findFirst()
    }

    fun getTagById(id: String): TagsModel? {
        return Realm.getInstance(config).where(TagsModel::class.java).equalTo("id", id).findFirst()
    }

    fun deleteTag(id: String) {
        val realm = Realm.getInstance(config)
        val itemRealmObject: TagsModel? = realm.where(TagsModel::class.java).equalTo("id", id).findFirst()
        if(itemRealmObject != null) {
            realm.beginTransaction()
            itemRealmObject.deleteFromRealm()
            realm.commitTransaction()
            realm.close()
        }
    }
}