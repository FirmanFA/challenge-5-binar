package com.binar.challenge5.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.binar.challenge5.R
import com.binar.challenge5.data.api.ApiClient
import com.binar.challenge5.data.api.Status
import com.binar.challenge5.data.api.model.Result
import com.binar.challenge5.data.api.model.ReviewResponse
import com.binar.challenge5.data.local.MyDatabase
import com.binar.challenge5.data.local.model.Favorite
import com.binar.challenge5.databinding.FragmentDetailMovieBinding
import com.binar.challenge5.repository.DetailRepository
import com.binar.challenge5.ui.home.MovieAdapter
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val args: DetailMovieFragmentArgs by navArgs()


    private val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(
            DetailRepository(ApiClient.instance,
            MyDatabase.getInstance(requireContext())!!.favoriteDao())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailMovieBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = args.movieId

        detailViewModel.detailMovie.observe(viewLifecycleOwner){

            when(it.status){
                Status.LOADING -> {
                    binding.detailShimmer.startShimmer()
                    binding.apply {
                        cvImage.visibility = View.INVISIBLE
                        phDate.visibility = View.INVISIBLE
                        phLang.visibility = View.INVISIBLE
                        phOverview.visibility = View.INVISIBLE
                        phScore.visibility = View.INVISIBLE
                        phStatus.visibility = View.INVISIBLE
                        tvOverview.visibility = View.INVISIBLE
                        tvGenres.visibility = View.INVISIBLE
                        tvLanguage.visibility = View.INVISIBLE
                        tvReleaseDate.visibility = View.INVISIBLE
                        tvScore.visibility = View.INVISIBLE
                        tvStatus.visibility = View.INVISIBLE
                        tvTitle.visibility = View.INVISIBLE
                    }
                }
                Status.SUCCESS -> {
                    binding.detailShimmer.stopShimmer()
                    binding.detailShimmer.isVisible = false

                    binding.apply {
                        cvImage.visibility = View.VISIBLE
                        phDate.visibility = View.VISIBLE
                        phLang.visibility = View.VISIBLE
                        phOverview.visibility = View.VISIBLE
                        phScore.visibility = View.VISIBLE
                        phStatus.visibility = View.VISIBLE
                        tvOverview.visibility = View.VISIBLE
                        tvGenres.visibility = View.VISIBLE
                        tvLanguage.visibility = View.VISIBLE
                        tvReleaseDate.visibility = View.VISIBLE
                        tvScore.visibility = View.VISIBLE
                        tvStatus.visibility = View.VISIBLE
                        tvTitle.visibility = View.VISIBLE
                    }

                    binding.apply {
                        Glide.with(requireContext())
                            .load("https://image.tmdb.org/t/p/w500"+it.data?.posterPath)
                            .into(ivPoster)
                        tvStatus.text = it.data?.status
                        tvScore.text = (it.data?.voteAverage?:0*10).toString()
                        tvReleaseDate.text = it.data?.releaseDate
                        tvLanguage.text = it.data?.originalLanguage
                        tvTitle.text = it.data?.title

                        val genreName = arrayListOf<String>()
                        it.data?.genres?.forEach {genre->
                            genreName.add(genre.name)
                        }
                        tvGenres.text = genreName.joinToString()
                        tvOverview.text = it.data?.overview
                    }

                    //favorite setup

                    binding.ivFavorite.setOnClickListener {_ ->
                        lifecycleScope.launch(Dispatchers.IO){
                            val isFavorite = detailViewModel.getFavoriteById(movieId)
                            activity?.runOnUiThread {
                                if (isFavorite == null){
                                    val newFavorite = Favorite(
                                        id = it.data?.id,
                                        overview = it.data?.overview?:"",
                                        posterPath = it.data?.posterPath,
                                        title = it.data?.title?:"",
                                        voteAverage = it.data?.voteAverage?:0.0)
                                    lifecycleScope.launch(Dispatchers.IO){
                                        detailViewModel.addToFavorite(newFavorite)
                                        runBlocking(Dispatchers.Main) {
                                            Toast.makeText(
                                                context,
                                                "Movie ditambahkan ke favorit!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                    detailViewModel.changeFavorite(true)
                                }else{
                                    lifecycleScope.launch(Dispatchers.IO){
                                        detailViewModel.removeFromFavorite(isFavorite)
                                        runBlocking(Dispatchers.Main) {
                                            Toast.makeText(
                                                context,
                                                "Movie dihapus ke favorit!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                    detailViewModel.changeFavorite(false)
                                }
                            }
                        }
                    }





                }
                Status.ERROR -> {
                    binding.detailShimmer.stopShimmer()
                    binding.detailShimmer.isVisible = false

                    binding.apply {
                        cvImage.visibility = View.VISIBLE
                        phDate.visibility = View.VISIBLE
                        phLang.visibility = View.VISIBLE
                        phOverview.visibility = View.VISIBLE
                        phScore.visibility = View.VISIBLE
                        phStatus.visibility = View.VISIBLE
                        tvOverview.visibility = View.VISIBLE
                        tvGenres.visibility = View.VISIBLE
                        tvLanguage.visibility = View.VISIBLE
                        tvReleaseDate.visibility = View.VISIBLE
                        tvScore.visibility = View.VISIBLE
                        tvStatus.visibility = View.VISIBLE
                        tvTitle.visibility = View.VISIBLE
                    }
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }

        detailViewModel.movieReviews.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    binding.reviewShimmer.startShimmer()
                }
                Status.SUCCESS -> {
                    binding.reviewShimmer.stopShimmer()
                    binding.reviewShimmer.isVisible = false
                    showMovieReviews(it.data?.results)
                }
                Status.ERROR -> {
                    binding.reviewShimmer.stopShimmer()
                    binding.reviewShimmer.isVisible = false
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        detailViewModel.similarMovies.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING -> {
                    binding.similarShimmer.startShimmer()
                }
                Status.SUCCESS -> {
                    binding.similarShimmer.stopShimmer()
                    binding.similarShimmer.isVisible = false
                    showSimilarMovies(it.data?.results)
                }
                Status.ERROR -> {
                    binding.similarShimmer.stopShimmer()
                    binding.similarShimmer.isVisible = false
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        detailViewModel.getDetailMovie(movieId)
        detailViewModel.getSimilarMovies(movieId)
        detailViewModel.getMovieReviews(movieId)

        //setup favorite icon
        detailViewModel.isFavoriteExist.observe(viewLifecycleOwner){
            if (it){
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else{
                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }

        lifecycleScope.launch(Dispatchers.IO){
            val fav = detailViewModel.getFavoriteById(movieId)
            if (fav==null){
                detailViewModel.changeFavorite(false)
            }else{
                detailViewModel.changeFavorite(true)
            }
        }

    }

    private fun showMovieReviews(results: List<ReviewResponse.Result>?) {
        val adapter= ReviewAdapter {

            val dialogBuilder = AlertDialog.Builder(requireContext())
            dialogBuilder.setTitle(it.author)
            dialogBuilder.setMessage(it.content)
            dialogBuilder.setPositiveButton("OK") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            dialogBuilder.create().show()
        }
        adapter.submitList(results)
        binding.rvReview.adapter = adapter
    }

    private fun showSimilarMovies(results: List<Result>?) {
        val adapter= MovieAdapter {}
        adapter.submitList(results)
        binding.rvSimilar.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}