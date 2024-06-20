package com.example.contactapp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactapp.ui_layer.AddEditScreen
import com.example.contactapp.ui_layer.ContactListScreen
import com.example.contactapp.ui_layer.ContactViewModel
import com.example.contactapp.ui_layer.Contactstate

@Composable
fun NavHostController(modifier: Modifier=Modifier,viewModel: ContactViewModel,state: Contactstate,navHostController: NavHostController,context: Context) {
    NavHost(navController = navHostController, startDestination = Routes.ContactListScreen.route)
    {
        composable(Routes.HomeScreen.route)
        {

        }
        composable(Routes.AddNewContact.route){
  AddEditScreen(state = state, navHostController =navHostController , onEvent = { viewModel.saveContact()
      navHostController.navigateUp() }, context = context,viewModel=viewModel)

    }
        composable(Routes.ContactListScreen.route){

            ContactListScreen(
                viewModel = viewModel,
                onEvent = {
                            navHostController.navigateUp()},
                context = context,
                navHostController =navHostController
            )
        }
}
}