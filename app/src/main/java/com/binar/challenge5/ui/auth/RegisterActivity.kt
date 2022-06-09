package com.binar.challenge5.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.binar.challenge5.R
import com.binar.challenge5.data.local.model.User
import com.binar.challenge5.ui.ui.theme.Challenge5Theme
import com.binar.challenge5.ui.ui.theme.TmdbBlue
import com.binar.challenge5.ui.ui.theme.TmdbLightBlue
import com.binar.challenge5.utils.AESEncryption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = get<AuthViewModel>()

            Challenge5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = TmdbBlue
                ) {

                    Column {
                        HeaderRegister()
                        IputRegisterForm { user ->
                            //intent
                            lifecycleScope.launch(Dispatchers.IO) {
                                viewModel.register(user)
                                runOnUiThread {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Register Sukses",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    finish()
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderRegister() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_logotmdb),
            contentDescription = "login logo",
            Modifier.height(100.dp),
            contentScale = ContentScale.None
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "SignUp",
            fontSize = 30.sp,
            fontWeight = FontWeight(800),
            color = Color.White
        )
    }
}

@Composable
fun IputRegisterForm(onCLickRegister: (User) -> Unit) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        val email = remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            placeholder = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                unfocusedBorderColor = TmdbLightBlue,
                focusedBorderColor = TmdbLightBlue,
                placeholderColor = Color.Gray,
                cursorColor = TmdbBlue,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        val nama = remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = nama.value,
            onValueChange = { nama.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = { Text(text = "Nama") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                unfocusedBorderColor = TmdbLightBlue,
                focusedBorderColor = TmdbLightBlue,
                placeholderColor = Color.Gray,
                cursorColor = TmdbBlue,
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        var password by rememberSaveable { mutableStateOf("") }
        var passwordHidden by rememberSaveable { mutableStateOf(true) }
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                unfocusedBorderColor = TmdbLightBlue,
                focusedBorderColor = TmdbLightBlue,
                placeholderColor = Color.Gray,
                cursorColor = TmdbBlue,
            ),
            trailingIcon = {
                IconButton(onClick = {
                    passwordHidden = !passwordHidden
                }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    // Please provide localized description for accessibility services
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(
                        imageVector = visibilityIcon,
                        contentDescription = description
                    )
                }
            }

        )

        Spacer(modifier = Modifier.height(8.dp))

        var passwordVerif by rememberSaveable { mutableStateOf("") }
        var passwordVerifHidden by rememberSaveable { mutableStateOf(true) }
        OutlinedTextField(
            value = passwordVerif,
            onValueChange = { passwordVerif = it },
            placeholder = { Text(text = "passwordVerif") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation =
            if (passwordVerifHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                unfocusedBorderColor = TmdbLightBlue,
                focusedBorderColor = TmdbLightBlue,
                placeholderColor = Color.Gray,
                cursorColor = TmdbBlue,
            ),
            trailingIcon = {
                IconButton(onClick = {
                    passwordVerifHidden = !passwordVerifHidden
                }) {
                    val visibilityIcon =
                        if (passwordVerifHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    // Please provide localized description for accessibility services
                    val description = if (passwordVerifHidden) "Show password" else "Hide password"
                    Icon(
                        imageVector = visibilityIcon,
                        contentDescription = description
                    )
                }
            }

        )

        val user = User(
            id = null,
            name = nama.value,
            email = email.value,
            password = AESEncryption.encrypt(password).toString()
        )

        Button(
            onClick = { onCLickRegister(user) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = TmdbLightBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Register")
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun RegisterPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = TmdbBlue
    ) {
        Column {
            HeaderRegister()
            IputRegisterForm { }
//            ActionItem({}, {})
        }
    }
}