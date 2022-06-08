package com.binar.challenge5.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.binar.challenge5.R
import com.binar.challenge5.data.api.Resource
import com.binar.challenge5.data.api.Status
import com.binar.challenge5.data.api.model.MovieResponse
import com.binar.challenge5.data.api.model.Result
import com.binar.challenge5.ui.detail.DetailActivity
import com.binar.challenge5.ui.ui.theme.Challenge5Theme
import com.binar.challenge5.ui.ui.theme.TmdbBlue
import com.binar.challenge5.ui.ui.theme.TmdbLightBlue
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerScope
import org.koin.android.ext.android.get
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder

class HomeActivity : ComponentActivity() {

    private val viewModel = get<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val discoverMovies by viewModel.discoverMovies.observeAsState(
                Resource(Status.LOADING,
                    null,
                    "")
            )

            val airingMovies by viewModel.airingMovies.observeAsState(
                Resource(Status.LOADING,
                    null,
                    "")
            )

            val upcomingMovies by viewModel.upcomingMovies.observeAsState(
                Resource(Status.LOADING,
                    null,
                    "")
            )

            val topRatedMovies by viewModel.topRatedMovies.observeAsState(
                Resource(Status.LOADING,
                    null,
                    "")
            )

            val name by viewModel.namaPreference.observeAsState("")

            Challenge5Theme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = TmdbBlue
                ) {
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                        HeaderHome(name = name, favoriteClick = {
                            Toast.makeText(this@HomeActivity, "Coming Soon", Toast.LENGTH_SHORT)
                                .show()
                        }, profileClick =  {
                            Toast.makeText(this@HomeActivity, "Coming Soon", Toast.LENGTH_SHORT)
                                .show()
                        })

                        var discoverMoviesState by rememberSaveable {
                            mutableStateOf(listOf<Result>())
                        }
                        discoverMoviesState = discoverMovies.data?.results ?: listOf()

                        var airingMoviesState by rememberSaveable {
                            mutableStateOf(listOf<Result>())
                        }
                        airingMoviesState = airingMovies.data?.results ?: listOf()

                        var upcomingMoviesState by rememberSaveable {
                            mutableStateOf(listOf<Result>())
                        }
                        upcomingMoviesState = upcomingMovies.data?.results ?: listOf()

                        var topRatedMoviesState by rememberSaveable {
                            mutableStateOf(listOf<Result>())
                        }
                        topRatedMoviesState = topRatedMovies.data?.results ?: listOf()

                        DiscoverMoviesCarousels(
                            movieList = discoverMoviesState,
                            onItemClick = {movieId->
                                //go to detail page
                                val intent = Intent(this@HomeActivity,
                                    DetailActivity::class.java
                                )
                                intent.putExtra("movie_id", movieId)
                                startActivity(intent)
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Airing Movies",
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 24.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight(800),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        DefaultMovieList(
                            movieList = airingMoviesState,
                            onItemClick = {movieId->
                                //go to detail page
                                val intent = Intent(this@HomeActivity,
                                    DetailActivity::class.java
                                )
                                intent.putExtra("movie_id", movieId)
                                startActivity(intent)
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Upcoming Movies",
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 24.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight(800),
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        DefaultMovieList(
                            movieList = upcomingMoviesState,
                            onItemClick = {movieId->
                                //go to detail page
                                val intent = Intent(this@HomeActivity,
                                    DetailActivity::class.java
                                )
                                intent.putExtra("movie_id", movieId)
                                startActivity(intent)
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Top Rated Movies",
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 24.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight(800),
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        DefaultMovieList(
                            movieList = topRatedMoviesState,
                            onItemClick = {movieId->
                                //go to detail page
                                val intent = Intent(this@HomeActivity,
                                    DetailActivity::class.java
                                )
                                intent.putExtra("movie_id", movieId)
                                startActivity(intent)
                            }
                        )

                    }

                }
            }
        }

        viewModel.getDiscoverMovies()
        viewModel.getAiringMovies()
        viewModel.getUpcomingMovies()
        viewModel.getTopRatedMovies()

    }
}

@Composable
fun HeaderHome(name: String = "", favoriteClick: () -> Unit, profileClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(48.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ic_logotmdb),
                contentDescription = "logo",
                Modifier
                    .padding(start = 24.dp)
                    .height(24.dp),
                contentScale = ContentScale.None,
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = favoriteClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = profileClick,
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Favorite",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome, $name",
            modifier = Modifier.padding(start = 24.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight(800),
            color = TmdbLightBlue
        )

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DiscoverMoviesCarousels(movieList: List<Result>, onItemClick: (Int) -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Discover Movies",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 24.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight(800),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPager(
            count = movieList.size,
            modifier = Modifier.height(300.dp)
        ) {
            DiscoverMOvieItem(
                pagerScope = this,
                currentMovie = movieList[this.currentPage],
                loadImage = {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://image.tmdb.org/t/p/w500${movieList[this.currentPage].posterPath}")
                            .crossfade(true)
                            .build(),
//                        model = "https://image.tmdb.org/t/p/w500${movieList[this.currentPage].posterPath}",
                        contentDescription = "Discover Poster",
                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.placeholder(
//                            visible = true,
//                            highlight = PlaceholderHighlight.shimmer()
//                        )
                    )
                },
                onItemClick = onItemClick
            )
        }


    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun DiscoverMOvieItem(
    pagerScope: PagerScope,
    currentMovie: Result,
    loadImage: @Composable () -> Unit,
    onItemClick: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .graphicsLayer {
                // Calculate the absolute offset for the current page from the
                // scroll position. We use the absolute value which allows us to mirror
                // any effects for both directions
                val pageOffset =
                    pagerScope.calculateCurrentOffsetForPage(pagerScope.currentPage).absoluteValue
                // We animate the scaleX + scaleY, between 85% and 100%
                lerp(
                    start = ScaleFactor(0.85f, 0.85f),
                    stop = ScaleFactor(1f, 1f),
                    fraction = 1f - pageOffset.coerceIn(0f, 1f),
                ).also { scale ->
                    scaleX = scale.scaleX
                    scaleY = scale.scaleY
                }


                // We animate the alpha, between 50% and 100%
                alpha = lerp(
                    start = ScaleFactor(0.5f, 0.5f),
                    stop = ScaleFactor(1f, 1f),
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                ).scaleX
            }
            .size(width = 200.dp, height = 300.dp),
        shape = RoundedCornerShape(20.dp),
        onClick = {
            onItemClick(currentMovie.id)
        }
    ) {
        loadImage.invoke()
    }

}

@Composable
fun DefaultMovieList(movieList: List<Result>, onItemClick: (Int) -> Unit = {}) {

    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(movieList){dataMovie ->
            DefaultMovieItem(
                currentMovie = dataMovie,
                onItemClick = onItemClick
            )
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DefaultMovieItem(currentMovie: Result, onItemClick: (Int) -> Unit = {}) {
    Column(
        modifier = Modifier
            .width(150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .height(220.dp),
//                .placeholder(
//                    visible = true,
//                    highlight = PlaceholderHighlight.shimmer()
//                ),
            onClick = {
                onItemClick(currentMovie.id)
            }
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500${currentMovie.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = "Airing Poster",
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = currentMovie.title,
            fontSize = 16.sp,
            fontWeight = FontWeight(600),
            color = Color.White,
            style = TextStyle(
                textAlign = TextAlign.Center,

            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    Challenge5Theme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = TmdbBlue
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            HeaderHome(favoriteClick = { /*TODO*/ }) {

            }
        }
    }
}

