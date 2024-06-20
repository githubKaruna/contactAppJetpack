package com.example.contactapp.ui_layer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.contactapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    state : Contactstate,
    navHostController: NavHostController,
    onEvent: () -> Unit,
    context: Context,
    viewModel: ContactViewModel

)
{
    var bitmaop :Bitmap? = null

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            uri: Uri?->
        if(uri!=null) {
            val inputStream = context.contentResolver.openInputStream(uri)
            Log.d("TAG", uri.toString())
            val byte = inputStream?.readBytes()
            if(byte!=null)
              state.image.value = byte
        }
    }

    Scaffold(
        topBar = {
            AddContactTopAppBar(onbackClicked = { navHostController.navigateUp() },"Add Conatct", viewModel = viewModel)

        },

        ) { innerPadding ->
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

                if(state.image.value!=null) {
                    bitmaop = BitmapFactory.decodeByteArray(
                        state.image.value,
                        0,
                        state.image.value!!.size
                    )

                    Image(
                        bitmap = bitmaop!!.asImageBitmap(),
                        contentScale = ContentScale.FillBounds,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp) // Set the image size
                            .padding(8.dp)
                            .clip(CircleShape)// Clip the image into a circle

                            .border(shape = CircleShape, border = BorderStroke(.5.dp, Color.Transparent))
                            .clickable { launcher.launch("image/*") }// Add padding around the image
                            .background(color = Color.Transparent, shape = CircleShape)

                    )
                }else
                {
                    Image(
                        painter = painterResource(id = R.drawable.adduser),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp) // Set the image size
                            .padding(8.dp)
                            .clickable { launcher.launch("image/*") }// Add padding around the image

                    )
                }
                Text(text = "Add photo", textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = state.name.value,
                    onValueChange = { state.name.value = it },
                    placeholder = { Text(text = "Name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Leading icon"
                        )
                    },
                    shape = RoundedCornerShape(23.dp)
                )
                Spacer(modifier = Modifier.padding(10.dp))


                OutlinedTextField(
                    value = state.number.value,
                    onValueChange = { state.number.value = it },
                    placeholder = { Text(text = "Enter your mobile number") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "phone icon"
                        )
                    },
                    shape = RoundedCornerShape(23.dp)
                )
                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = state.email.value,
                    onValueChange = { state.email.value = it },
                    placeholder = { Text(text = "Enter your email") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "email icon"
                        )
                    },
                    shape = RoundedCornerShape(23.dp)
                )
                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedButton(
                    onClick = { onEvent.invoke()
                                navHostController.navigateUp()},
                    modifier = Modifier
                        .padding(15.dp)
                        .background(color = Color(0xFF00bbf0), shape = RoundedCornerShape(23.dp)),
                    border = null,

                ) {
                    Text(text = "Save Contact", color = Color.White, modifier = Modifier.padding(horizontal = 20.dp))
                }
            }
        }
    }
}

