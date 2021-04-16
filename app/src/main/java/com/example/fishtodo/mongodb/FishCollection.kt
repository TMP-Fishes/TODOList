package com.example.fishtodo.mongodb

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId
import java.util.*


open class FishCollection : RealmObject() {
    @PrimaryKey
    private var _id: ObjectId? = null
    open var date: Date? = null
    open var greeting: String? = null
    open var partition: String? = null
    open var tasks: RealmList<FishCollection_tasks>? = null
}

@RealmClass(embedded = true)
open class FishCollection_tasks : RealmObject() {
    // Standard getters & setters
    var description: String? = null
    var name: String? = null
    var rate: Int? = null

    override fun toString(): String {
        return ("Task: $name \n" +
                "Rate: $rate \n" +
                "Description: $description \n\n")
    }
}
