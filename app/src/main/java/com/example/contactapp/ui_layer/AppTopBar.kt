package com.example.contactapp.ui_layer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactTopAppBar(onbackClicked: () -> Unit,title:String,viewModel: ContactViewModel) {
   val sortIconVisibility: MutableState<Boolean> = remember { mutableStateOf(false) }
    val sortBy: MutableState<String> = remember { mutableStateOf("Name") }

    if (title=="Contacts") {
        sortIconVisibility.value = true
        if (sortBy.value == "name")
            sortBy.value = "Name"
        else
            sortBy.value = "Date of Creation"
    }
        else
        sortIconVisibility.value = false

    TopAppBar(
        modifier = Modifier.padding(top = 20.dp),


        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,

                color = Color.White, // Use contentColorFor for text color based on background
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)

            )
        },
        navigationIcon = {
            if(title!="Contacts") {
                IconButton(onClick = onbackClicked) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
            }
        },

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF00bbf0) // Set the background color here
        )
        , actions = {
            if(sortIconVisibility.value){
            IconButton(onClick = { viewModel.changesorting() }) {
                Icon(
                    imageVector = Icons.Rounded.Sort,
                    contentDescription = sortBy.value,
                    tint = Color.White,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            }
        }
    )
}
