package com.madhumarga.app

import android.app.Application
import com.madhumarga.app.data.AppDatabase
import com.madhumarga.app.data.HiveRepository

class MadhuMargaApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.get(this) }
    val repository: HiveRepository by lazy {
        HiveRepository(
            database.hiveDao(),
            database.inspectionDao(),
            database.harvestDao()
        )
    }
}
