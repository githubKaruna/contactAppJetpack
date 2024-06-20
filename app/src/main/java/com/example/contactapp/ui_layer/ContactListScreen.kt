package com.example.contactapp.ui_layer

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.contactapp.R
import com.example.contactapp.data.database.Contact
import com.example.contactapp.navigation.Routes

@Composable
fun ContactListScreen(viewModel: ContactViewModel,
                      onEvent: () -> Unit,context: Context,navHostController: NavHostController) {

    val state by viewModel.state.collectAsState()


    Scaffold(
        topBar = {
            AddContactTopAppBar(onbackClicked = { navHostController.navigateUp() }, title = "Contacts",viewModel)

        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(Routes.AddNewContact.route)
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)

            }
        }
        ,
        floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            if (state.contacts.isEmpty()) {
                // Show empty state message if no contacts found
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                    ,
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Image(
                            painter = painterResource(id = R.drawable.nocontacts),
                            contentDescription = "Description of the image",
                            modifier = Modifier
                                .size(100.dp) // Set the image size
                                .padding(8.dp) // Add padding around the image
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text("No Contacts Found")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    items(state.contacts) {

                        ContactListItem(contact = it,state,navHostController,viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun ContactListItem(contact: Contact,state: Contactstate,navHostController: NavHostController,viewModel: ContactViewModel) {
   var context: Context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier

            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
        shape = RoundedCornerShape(8.dp), // Maintain rounded corners
        // Set background color
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .clickable {
                    state.id.value = contact.id
                    state.name.value = contact.name
                    state.email.value = contact.email
                    state.number.value = contact.number
                    state.dateOfCreation.value = contact.dateOfCreation
                    navHostController.navigate(Routes.AddNewContact.route)
                }
        ) {
            // Display contact image (if available)
            var bitmaop: Bitmap? = null
            if (contact.image != null)
                bitmaop = BitmapFactory.decodeByteArray(contact.image, 0, contact.image.size)
            if (bitmaop != null) {
                Image(
                    bitmap = bitmaop.asImageBitmap(), // Replace with your logic for contact image
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp) // Set the image size
                        .padding(8.dp)
                        .clip(CircleShape)// Clip the image into a circle

                        .border(
                            shape = CircleShape,
                            border = BorderStroke(.5.dp, Color.Transparent)
                        )

                        .background(color = Color.Transparent, shape = CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.user), // Replace with your logic for contact image
                    contentDescription = "Contact Image",
                    modifier = Modifier
                        .size(70.dp) // Set the image size
                        .padding(8.dp)
                        .clip(CircleShape)// Clip the image into a circle

                        .border(
                            shape = CircleShape,
                            border = BorderStroke(.5.dp, Color.Transparent)
                        )

                        .background(color = Color.Transparent, shape = CircleShape)
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
            Column {
                Text(text = contact.name, style = MaterialTheme.typography.bodyLarge,fontWeight = FontWeight.Bold)
                Text(text = contact.number, style = MaterialTheme.typography.bodyMedium)
                Text(text = contact.email, style = MaterialTheme.typography.bodyMedium)

            }
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            )
            Column {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        state.id.value = contact.id
                        state.name.value = contact.name
                        state.email.value = contact.email
                        state.number.value = contact.number
                        state.dateOfCreation.value = contact.dateOfCreation
                        viewModel.deleteContact()
                    },
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.padding(10.dp))

                Icon(
                    imageVector = Icons.Outlined.Call,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.number}"))
                        context.startActivity(intent)
                    },
                    tint = Color(0xFF00bbf0)
                )
            }

        }
        }

}
