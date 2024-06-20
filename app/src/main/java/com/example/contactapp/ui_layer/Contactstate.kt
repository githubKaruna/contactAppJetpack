package com.example.contactapp.ui_layer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.contactapp.data.database.Contact

data class Contactstate (
    val contacts : List<Contact> = emptyList(),
    val id : MutableState<Int> = mutableStateOf(0),

    val name : MutableState<String> = mutableStateOf(""),
    val number : MutableState<String> = mutableStateOf(""),
    val email : MutableState<String> = mutableStateOf(""),
    val dateOfCreation : MutableState<Long> = mutableStateOf(0),
    val image : MutableState<ByteArray?> = mutableStateOf(null)
)