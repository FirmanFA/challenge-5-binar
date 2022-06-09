package com.binar.challenge5.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.challenge5.data.local.MyDatabase
import com.binar.challenge5.data.local.model.Favorite
import com.binar.challenge5.databinding.FragmentFavoriteBinding
import com.binar.challenge5.repository.FavoriteRepository
import com.binar.challenge5.ui.home.FavoriteAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

//    private val favoriteViewModel by viewModels<FavoriteViewModel> {
//        FavoriteViewModelFactory(
//            FavoriteRepository(
//                MyDatabase.getInstance(requireContext())!!.favoriteDao()
//            )
//        )
//    }

    private val favoriteViewModel by viewModel<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel.allFavorites.observe(viewLifecycleOwner){
            //show adapter
            showFavoriteMovies(it)
        }

        favoriteViewModel.getAllFavorites()

    }

    private fun showFavoriteMovies(list: List<Favorite?>?) {
        val adapter= FavoriteAdapter {
//            val action = FavoriteFragmentDirections
//                .actionFavoriteFragmentToDetailMovieFragment(it.id!!)
//            findNavController().navigate(action)
        }
        adapter.submitList(list)
        binding?.rvFavorite?.adapter = adapter
    }
}