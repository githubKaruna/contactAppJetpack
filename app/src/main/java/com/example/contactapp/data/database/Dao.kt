package com.example.contactapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Upsert
  suspend fun upsertContact(contact: Contact)

  @Delete
  suspend fun deleteConatct(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY `user name` ASC")
    fun getContactsSortName(): Flow<List<Contact>>

    @Query("SELECT * FROM contact_table ORDER BY dateOfCreation ASC")
    fun getContactsSortDate(): Flow<List<Contact>>
}