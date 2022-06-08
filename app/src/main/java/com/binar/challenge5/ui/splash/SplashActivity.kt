package com.binar.challenge5.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.binar.challenge5.R
import com.binar.challenge5.ui.auth.AuthViewModel
import com.binar.challenge5.ui.auth.LoginActivity
import com.binar.challenge5.ui.home.HomeActivity
import com.binar.challenge5.ui.ui.theme.Challenge5Theme
import com.binar.challenge5.ui.ui.theme.TmdbBlue
import org.koin.android.ext.android.get

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    val viewModel = get<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val emailPreferences by viewModel.emailPreference().observeAsState("")

            Challenge5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = TmdbBlue
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_logotmdb),
                        contentDescription = "login logo",
                        modifier = Modifier.size(width = 300.dp, height = 200.dp),
                        contentScale = ContentScale.None,
                        alignment = Alignment.Center
                    )

                    Handler(Looper.getMainLooper()).postDelayed({

                        if (emailPreferences == ""){
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }


                    }, 2000)


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    Challenge5Theme {
        Greeting("Android")
    }
}