package com.example.basket4all.data.local

import android.app.Application

class B4AllApplication : Application() {
    // Con el uso de lazy la base de datos solo se creará cuando se necesite
    val database by lazy { AppDatabase.getDatabase(this) }
}