package com.example.contactapp.ui_layer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactapp.data.database.ConatctDatabse
import com.example.contactapp.data.database.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(var databse: ConatctDatabse):ViewModel()
{
    private  val isSortedByName = MutableStateFlow(false)
    private  val contact =isSortedByName.flatMapLatest {
        if (it) {
            databse.dao.getContactsSortName()
        } else {
            databse.dao.getContactsSortDate()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

val _state = MutableStateFlow(Contactstate())
    val state = combine(_state,contact,isSortedByName){
        _state,contacts,isSortedByName ->
        _state.copy(contacts=contacts)
    }.stateIn(viewModelScope,SharingStarted.WhileSubscribed(5000), Contactstate())

    fun saveContact()
    { val contact  = Contact(name = state.value.name.value,
       id = state.value.id.value,
        number = state.value.number.value,
        email = state.value.email.value,
        dateOfCreation = System.currentTimeMillis(),
        isActive = true
        ,image = state.value.image.value
    )
viewModelScope.launch {
    databse.dao.upsertContact(contact)
}

        state.value.id.value=0
        state.value.name.value=""
        state.value.number.value=""
        state.value.email.value=""
        state.value.dateOfCreation.value=0
        state.value.image.value=null

    }
    fun deleteContact()
    {
        val contact = Contact(
            id = state.value.id.value,
            name = state.value.name.value,
            number = state.value.number.value,
            email = state.value.email.value,
            dateOfCreation = state.value.dateOfCreation.value,
            image = state.value.image.value,
            isActive = true
        )
        viewModelScope.launch {

            databse.dao.deleteConatct(contact)
        }
        state.value.id.value=0
        state.value.name.value=""
        state.value.number.value=""
        state.value.email.value=""
        state.value.dateOfCreation.value=0
        state.value.image.value=null


    }

    fun changesorting()
    {
        isSortedByName.value=!isSortedByName.value
    }

}

