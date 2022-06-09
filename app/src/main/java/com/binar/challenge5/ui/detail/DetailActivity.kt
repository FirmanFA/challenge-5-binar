package com.binar.challenge5.ui.detail

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.binar.challenge5.R
import com.binar.challenge5.data.api.Resource
import com.binar.challenge5.data.api.Status
import com.binar.challenge5.data.api.model.DetailMovieResponse
import com.binar.challenge5.data.api.model.MovieResponse
import com.binar.challenge5.data.api.model.Result
import com.binar.challenge5.ui.home.DefaultMovieList
import com.binar.challenge5.ui.ui.theme.Challenge5Theme
import com.binar.challenge5.ui.ui.theme.TmdbBlue
import com.binar.challenge5.ui.ui.theme.TmdbLightBlue
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class DetailActivity : ComponentActivity() {

    //    private val viewModel = get<DetailViewModel>()
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movieId = intent.getIntExtra("movie_id", 0)

        setContent {

            val detailMovieResponse = viewModel.detailMovie.observeAsState(
                Resource(
                    Status.LOADING,
                    null,
                    ""
                )
            )
            val similarMovieResponse = viewModel.similarMovies.observeAsState(
                Resource(
                    Status.LOADING,
                    null,
                    ""
                )
            )

            Challenge5Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = TmdbBlue
                ) {

                    var similarMoviesState by rememberSaveable {
                        mutableStateOf(listOf<Result>())
                    }
                    similarMoviesState = similarMovieResponse.value.data?.results ?: listOf()

                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {

                        HeaderDetail()

                        Spacer(modifier = Modifier.height(16.dp))

                        DetailMovieInfo(movieData = detailMovieResponse.value.data)


                        val genreString = detailMovieResponse.value.data?.genres?.joinToString {
                            it.name
                        } ?: ""

                        MovieTitle(
                            title = detailMovieResponse.value.data?.title ?: "",
                            genre = genreString
                        )

                        MovieOverview(overview = detailMovieResponse.value.data?.overview ?: "")

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Similar Movies",
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 24.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight(800),
                            color = TmdbLightBlue
                        )

                        DefaultMovieList(movieList = similarMoviesState)

                    }

                }
            }
        }

        viewModel.getDetailMovie(movieId)
        viewModel.getSimilarMovies(movieId)
    }

}

@Composable
fun HeaderDetail() {

    Column(modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(48.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_logotmdb),
            contentDescription = "logo",
            Modifier
                .padding(start = 24.dp)
                .height(24.dp),
            contentScale = ContentScale.None,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Detail Movie",
            modifier = Modifier.padding(start = 24.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight(800),
            color = TmdbLightBlue
        )

    }

}


@Composable
fun DetailMovieInfo(movieData: DetailMovieResponse?) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .padding(
                    start = 24.dp
                )
                .size(
                    width = 220.dp,
                    height = 310.dp
                ),
            shape = RoundedCornerShape(20.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500${movieData?.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = "Discover Poster",
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Status",
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color.White,
                style = TextStyle(textAlign = TextAlign.Center)
            )
            Text(
                text = movieData?.status ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color.White,
                style = TextStyle(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "User Score",
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color.White,
                style = TextStyle(textAlign = TextAlign.Center)
            )
            Text(
                text = "${movieData?.voteAverage ?: (0 * 10)}",
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color.White,
                style = TextStyle(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Release Data",
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color.White,
                style = TextStyle(textAlign = TextAlign.Center)
            )
            Text(
                text = "${movieData?.releaseDate}",
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color.White,
                style = TextStyle(textAlign = TextAlign.Center)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Language",
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = Color.White,
                style = TextStyle(textAlign = TextAlign.Center)
            )
            Text(
                text = "${movieData?.originalLanguage}",
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = Color.White,
                style = TextStyle(textAlign = TextAlign.Center)
            )
        }


    }
}

@Composable
fun MovieTitle(title: String, genre: String) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            modifier = Modifier.padding(start = 24.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight(700),
            color = Color.White
        )
        Text(
            text = genre,
            modifier = Modifier.padding(start = 24.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight(400),
            color = Color.White,
            fontStyle = FontStyle.Italic
        )
    }
}

@Composable
fun MovieOverview(overview: String) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Overview",
            modifier = Modifier.padding(horizontal = 24.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight(700),
            color = TmdbLightBlue
        )
        Text(
            text = overview,
            modifier = Modifier.padding(horizontal = 24.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight(400),
            color = Color.White,
            style = TextStyle(
                textAlign = TextAlign.Justify
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    Challenge5Theme {
    }
}