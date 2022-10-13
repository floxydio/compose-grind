package com.example.belajarcompose

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.belajarcompose.ui.theme.BelajarcomposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BelajarcomposeTheme {
                AppBarTitle()
            }
        }
    }
}

@Composable
fun AppBarTitle(
) {
   Scaffold(
       topBar = {
           TopAppBar(
               elevation = 10.dp, title = {
                   Text("Main AppBar")
               })
       }
   ) {
    MainScreen()

   }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var valueInput by remember {
        mutableStateOf("")
    }
    var context = LocalContext.current
    Surface(

        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 15.dp)
        ) {
            EditInput(value = valueInput, onValueChange = {
                valueInput = it
            }, label = "Input Name" )
            Spacer(modifier = Modifier.height(20.dp))
            Button(modifier = Modifier.fillMaxWidth(),onClick = {
                if (valueInput.isEmpty()) {
                 showToast(context, value ="Input is Empty")
                } else {
                    saveData(context, value = valueInput, key = "input")
                }

            }) {
                Text("Save Me")

            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(modifier = Modifier.fillMaxWidth(),onClick = {
                getData(context,key = "input")

            }) {
                Text("Get Me")

            }

        }
    }
}

@Composable
fun EditInput(modifier: Modifier = Modifier, value: String, onValueChange: (String) -> Unit, label: String) {
    TextField(value = value, label = {
        Text(label, modifier = Modifier.fillMaxWidth())
    }, onValueChange = onValueChange)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BelajarcomposeTheme {
        AppBarTitle()
    }
}

private fun saveData(context: Context, value: String, key: String) {
    val userDetails: SharedPreferences = context.getApplicationContext().getSharedPreferences("dataText", MODE_PRIVATE)
    val edit = userDetails.edit()
    edit.putString(key, value.toString().trim())
    edit.apply()
}

private fun getData(context: Context, key: String) {
    val userDetails: SharedPreferences = context.getApplicationContext().getSharedPreferences("dataText", MODE_PRIVATE)
    val data = userDetails.getString(key,"DEFAULT")
    if (data != null) {
        showToast(context, data)
    }

}


private fun showToast(context: Context,value: String) {
    Toast.makeText(context, value.trim(),Toast.LENGTH_LONG).show()
}