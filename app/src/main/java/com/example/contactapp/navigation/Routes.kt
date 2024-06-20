package com.example.contactapp.navigation

sealed class Routes(val route: String) {
    object HomeScreen : Routes("HomeScreen")

    object  AddNewContact : Routes("AddNewContact")
    object  ContactListScreen : Routes("ContatctListScreen")



}