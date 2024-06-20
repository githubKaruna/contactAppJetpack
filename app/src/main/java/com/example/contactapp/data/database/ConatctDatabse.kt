package com.example.contactapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 2, exportSchema = true)
abstract class ConatctDatabse : RoomDatabase() {
   abstract val dao : Dao

}