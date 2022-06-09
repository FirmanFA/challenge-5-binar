package com.binar.challenge5.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillNode
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalAutofill
import androidx.compose.ui.platform.LocalAutofillTree
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.binar.challenge5.ui.ui.theme.Challenge5Theme
import com.binar.challenge5.R
import com.binar.challenge5.ui.home.HomeActivity
import com.binar.challenge5.ui.ui.theme.TmdbBlue
import com.binar.challenge5.ui.ui.theme.TmdbLightBlue
import com.binar.challenge5.utils.AESEncryption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.androidx.compose.get

class LoginActivity : ComponentActivity() {
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
                        HeaderLogin()
                        InputForm(onClickLogin = { email, password ->
                            //intent
                            val encryptedPassword = AESEncryption.encrypt(password).toString()
                            lifecycleScope.launch(Dispatchers.IO) {
                                val login = viewModel.login(email, encryptedPassword)
                                runOnUiThread {
                                    if (login!=null){

                                        lifecycleScope.launch(Dispatchers.IO){
                                            viewModel.setEmailPreference(email)
                                            viewModel.setNamaPreference(login.name)

                                            runBlocking(Dispatchers.Main) {
                                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                                startActivity(intent)
                                                finish()
                                            }
                                        }


                                    }
                                }
                            }

                        }, onClickRegister = {
                           val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                           startActivity(intent)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderLogin() {
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
            text = "Signin",
            fontSize = 30.sp,
            fontWeight = FontWeight(800),
            color = Color.White
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputForm(onClickLogin: (String, String) -> Unit, onClickRegister: () -> Unit) {

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
            onValueChange = {email.value = it },
            keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Email),
            placeholder = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth().autofill(
                autofillTypes = listOf(AutofillType.EmailAddress),
                onFill = {email.value = it}),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                unfocusedBorderColor = TmdbLightBlue,
                focusedBorderColor = TmdbLightBlue,
                placeholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        var password by rememberSaveable { mutableStateOf("") }
        var passwordHidden by rememberSaveable { mutableStateOf(true) }
        OutlinedTextField(
            value = password,
            onValueChange = {password = it },
            placeholder = { Text(text = "Password") },
            modifier = Modifier.fillMaxWidth().autofill(
                autofillTypes = listOf(AutofillType.Password),
                onFill = {email.value = it}),
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White,
                unfocusedBorderColor = TmdbLightBlue,
                focusedBorderColor = TmdbLightBlue,
                placeholderColor = Color.Gray
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
        Button(
            onClick = { onClickLogin(email.value, password) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = TmdbLightBlue,
                contentColor = Color.White
            )
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Belum Punya Akun?",
            modifier = Modifier.clickable(
                onClick = onClickRegister,
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ),
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White)
        )
    }
}

@Composable
fun ActionItem(onClickLogin:() -> Unit, onClickRegister:() -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.autofill(
    autofillTypes: List<AutofillType>,
    onFill: ((String) -> Unit),
) = composed {
    val autofill = LocalAutofill.current
    val autofillNode = AutofillNode(onFill = onFill, autofillTypes = autofillTypes)
    LocalAutofillTree.current += autofillNode

    this.onGloballyPositioned {
        autofillNode.boundingBox = it.boundsInWindow()
    }.onFocusChanged { focusState ->
        autofill?.run {
            if (focusState.isFocused) {
                requestAutofillForNode(autofillNode)
            } else {
                cancelAutofillForNode(autofillNode)
            }
        }
    }
}

@Preview(showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = TmdbBlue
    ) {
        Column {
            HeaderLogin()
            InputForm({_,_->}) {}
//            ActionItem({}, {})
        }
    }
}