package com.binar.challenge5.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.binar.challenge5.R
import com.binar.challenge5.data.api.ApiClient
import com.binar.challenge5.databinding.FragmentDetailMovieBinding
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
        detailViewModel.detailMovie.observe(viewLifecycleOwner){
            binding.apply {
                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500"+it.posterPath)
                    .into(ivPoster)
                tvStatus.text = it.status
                tvScore.text = (it.voteAverage*10).toString()
                tvReleaseDate.text = it.releaseDate
                tvLanguage.text = it.originalLanguage
            }
        }

        detailViewModel.getDetailMovies(movieId)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}