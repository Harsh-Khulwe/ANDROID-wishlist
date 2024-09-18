package com.example.wishlistapp

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wishlistapp.data.DummyWish
import com.example.wishlistapp.data.Wish
import com.example.wishlistapp.navigation.Screen
import kotlinx.coroutines.launch
import androidx.compose.material3.Text as Text

@Composable
fun AddDetailView(
    id: Long,
    navController: NavController,
    viewModel: WishViewModel
) {
//    val context = LocalContext.current
    var snackMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    if(id != 0L) {
        val wish = viewModel.getAWishById(id)
            .collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.wish
        viewModel.wishDescriptionState = wish.value.wishDescription
    }
    else {
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        topBar = {
            AppBarView(title = if (id == 0L) "Add new wish" else "Update wish") {
//                Toast.makeText(context, "Back Button Clicked", Toast.LENGTH_LONG).show()
                navController.navigateUp()
            }
        },
        scaffoldState = scaffoldState

    ) { it ->
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(
                label = "Title", 
                value = viewModel.wishTitleState,
                onValueChanged = {
                    viewModel.onTitleChanged(it) 
                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            WishTextField(
                label = "Description",
                value = viewModel.wishDescriptionState,
                onValueChanged = {
                    viewModel.onDescriptionChanged(it)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                if(viewModel.wishTitleState.isNotEmpty()
                    && viewModel.wishDescriptionState.isNotEmpty()) {
                    if(id == 0L) {
                        viewModel.addAWish(
                            Wish(
                                wish = viewModel.wishTitleState.trim(),
                                wishDescription = viewModel.wishDescriptionState.trim()
                            )
                        )
                        snackMessage = "Your wish has been added"
                    }
                    else {
                        viewModel.updateWish(
                            Wish(
                                id = id,
                                wish = viewModel.wishTitleState.trim(),
                                wishDescription = viewModel.wishDescriptionState.trim()
                            )
                        )
                        snackMessage = "Wish has been updated"
                    }
                }
                else snackMessage = "Please enter both fields"

                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage)

                    if(viewModel.wishTitleState.isNotEmpty()
                        && viewModel.wishDescriptionState.isNotEmpty())
                            navController.navigateUp()
                }

            }) {
                Text(
                    text = if (id == 0L) "ADD" else "UPDATE",
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChanged(it) },
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier
            .fillMaxWidth().padding(horizontal = 12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        )
    )
}