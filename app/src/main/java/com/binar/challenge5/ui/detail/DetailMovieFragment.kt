package com.binar.challenge5.ui.detail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.binar.challenge5.data.api.ApiClient
import com.binar.challenge5.data.api.model.Result
import com.binar.challenge5.data.api.model.ReviewResponse
import com.binar.challenge5.databinding.FragmentDetailMovieBinding
import com.binar.challenge5.ui.home.MovieAdapter
import com.bumptech.glide.Glide

class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val args: DetailMovieFragmentArgs by navArgs()

    private val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(DetailRepository(ApiClient.instance))
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

        detailViewModel.isLoadingDetail.observe(viewLifecycleOwner){
            if (it){
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
            }else{
                Handler(Looper.getMainLooper()).postDelayed({
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

                },2000)
            }
        }

        detailViewModel.detailMovie.observe(viewLifecycleOwner){

            Handler(Looper.getMainLooper()).postDelayed({
                binding.apply {
                    Glide.with(requireContext())
                        .load("https://image.tmdb.org/t/p/w500"+it.posterPath)
                        .into(ivPoster)
                    tvStatus.text = it.status
                    tvScore.text = (it.voteAverage*10).toString()
                    tvReleaseDate.text = it.releaseDate
                    tvLanguage.text = it.originalLanguage
                    tvTitle.text = it.title

                    val spokenLang = arrayListOf<String>()
                    it.spokenLanguages.forEach {
                        spokenLang.add(it.name)
                    }
                    val textSpokenLang =  spokenLang.joinToString()

                    //isi dari textSpokenLang = "english, tagalog, indo, bla"


                    val genreName = arrayListOf<String>()
                    it.genres.forEach {
                        genreName.add(it.name)
                    }
                    tvGenres.text = genreName.joinToString()
                    tvOverview.text = it.overview
                }
            },2000)

        }

        detailViewModel.movieReviews.observe(viewLifecycleOwner){
            Handler(Looper.getMainLooper()).postDelayed({
                showMovieReviews(it.results)
            },2000)
        }

        detailViewModel.isLoadingReview.observe(viewLifecycleOwner){
            if (it){
                binding.reviewShimmer.startShimmer()
            }else{
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.reviewShimmer.stopShimmer()
                    binding.reviewShimmer.isVisible = false
                },2000)
            }
        }

        detailViewModel.isLoadingSimilar.observe(viewLifecycleOwner){
            if (it){
                binding.similarShimmer.startShimmer()
            }else{
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.similarShimmer.stopShimmer()
                    binding.similarShimmer.isVisible = false
                },2000)
            }
        }

        detailViewModel.similarMovies.observe(viewLifecycleOwner){
            Handler(Looper.getMainLooper()).postDelayed({
                showSimilarMovies(it.results)
            },2000)
        }

        detailViewModel.getDetailMovies(movieId)
        detailViewModel.getSimilarMovies(movieId)
        detailViewModel.getMovieReviews(movieId)

    }

    private fun showMovieReviews(results: List<ReviewResponse.Result>) {
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

    private fun showSimilarMovies(results: List<Result>) {
        val adapter= MovieAdapter {}
        adapter.submitList(results)
        binding.rvSimilar.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}