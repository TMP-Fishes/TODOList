package com.example.fishtodo.mongodb

import android.content.Context
import android.util.Log
import com.example.fishtodo.viewmodel.HomeViewModel
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults
import io.realm.kotlin.where
import io.realm.mongodb.*
import io.realm.mongodb.sync.SyncConfiguration
import java.util.*

class DBManager(context: Context) {
    private val app: App

    init {
        Realm.init(context)
        app = App(AppConfiguration.Builder(APP_ID).build())
        login()
    }

    private fun login() {
        app.loginAsync(Credentials.anonymous()) {
            if (it.isSuccess) {
                Log.d("Onboard", "Succeeded to login with user: ${app.currentUser()}")
            } else {
                Log.e("Onboard", "Failed to login with error: ${it.error}")
            }
        }
    }

    fun readLatest(): FishCollection? {
        return app.currentUser()?.let {
            val config = SyncConfiguration.Builder(app.currentUser(), PARTITION_KEY)
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build()
            return  Realm.getInstance(config)
                .where<FishCollection>().sort("date")
                .findFirst() // TODO: get today's latest;
        }
    }

    private fun getDayBegin(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        return calendar.time
    }

    private fun getDayEnd(): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        return calendar.time
    }

    companion object {
        const val APP_ID = "fishtodo-qmgrg"
        const val PARTITION_KEY = "FishTODO"
    }
}