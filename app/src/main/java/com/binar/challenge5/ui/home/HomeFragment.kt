package com.binar.challenge5.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.binar.challenge5.R
import com.binar.challenge5.data.api.ApiClient
import com.binar.challenge5.data.api.Status
import com.binar.challenge5.data.api.model.Result
import com.binar.challenge5.databinding.FragmentHomeBinding
import com.binar.challenge5.datastore.UserDataStoreManager
import com.binar.challenge5.repository.HomeRepository
import com.binar.challenge5.utils.HorizontalMarginItemDecoration
import kotlin.math.abs


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var once = false

    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(HomeRepository(ApiClient.instance,
        UserDataStoreManager(requireContext())
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val sharedPreference = context?.getSharedPreferences(MainActivity.SHARED_FILE, Context.MODE_PRIVATE)
//        val nameLogin = sharedPreference?.getString("name","")
//        binding.tvName.text = "Welcome,\n$nameLogin!"

        homeViewModel.namaPreference.observe(viewLifecycleOwner){
            if (it!=""){
                binding.tvName.text = "Welcome,\n$it!"
            }

        }


        binding.ivAccount.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }
        binding.ivFavorite.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }

        homeViewModel.airingMovies.observe(viewLifecycleOwner){

            when(it.status){
                Status.LOADING -> {
                    binding.airingShimmer.startShimmer()
                }
                Status.SUCCESS -> {
                    binding.airingShimmer.stopShimmer()
                    binding.airingShimmer.isVisible = false
                    showAiringMovies(it.data?.results)
                }
                Status.ERROR -> {
                    binding.airingShimmer.stopShimmer()
                    binding.airingShimmer.isVisible = false
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }

        homeViewModel.upcomingMovies.observe(viewLifecycleOwner){

            when(it.status){
                Status.LOADING -> {
                    binding.upcomingShimmer.startShimmer()
                }
                Status.SUCCESS -> {
                    binding.upcomingShimmer.stopShimmer()
                    binding.upcomingShimmer.isVisible = false
                    showUpcomingMovies(it.data?.results)
                }
                Status.ERROR -> {
                    binding.upcomingShimmer.stopShimmer()
                    binding.upcomingShimmer.isVisible = false
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }

        homeViewModel.topRatedMovies.observe(viewLifecycleOwner){

            when(it.status){
                Status.LOADING -> {
                    binding.topratedShimmer.startShimmer()
                }
                Status.SUCCESS -> {
                    binding.topratedShimmer.stopShimmer()
                    binding.topratedShimmer.isVisible = false
                    showTopRatedMovies(it.data?.results)
                }
                Status.ERROR -> {
                    binding.topratedShimmer.stopShimmer()
                    binding.topratedShimmer.isVisible = false
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }

        }

        homeViewModel.isLoadingDiscover.observe(viewLifecycleOwner){

            if (it){
                binding.discoverShimmer.startShimmer()
            }else{
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.discoverShimmer.stopShimmer()
                    binding.discoverShimmer.isVisible = false
                },2000)
            }
        }

//        var once = false
        homeViewModel.discoverMovies.observe(viewLifecycleOwner){
//            when(it.status){
//                Status.LOADING -> {
//                    binding.discoverShimmer.startShimmer()
//                }
//                Status.SUCCESS -> {
//
//                    binding.discoverShimmer.stopShimmer()
//                    binding.discoverShimmer.isVisible = false
//                    showDiscoverMovies(it.data?.results, once)
//                    once = true
//                }
//                Status.ERROR -> {
//                    binding.discoverShimmer.stopShimmer()
//                    binding.discoverShimmer.isVisible = false
//                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
//                }
//            }

            showDiscoverMovies(it.results)

        }

        homeViewModel.apply {
//            getDiscoverMovies()
            getUpcomingMovies()
            getAiringMovies()
            getTopRatedMovies()
        }


    }

    private fun showTopRatedMovies(results: List<Result>?) {
        val adapter= MovieAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(it.id)
            findNavController().navigate(action)
        }
        adapter.submitList(results)
        binding.rvToprated.adapter = adapter
    }

    private fun showUpcomingMovies(results: List<Result>?) {
        val adapter= MovieAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(it.id)
            findNavController().navigate(action)
        }
        adapter.submitList(results)
        binding.rvUpcoming.adapter = adapter
    }

    private fun showDiscoverMovies(results: List<Result>?){
        val discoverAdapter = DiscoverAdapter{
            val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(it.id)
            findNavController().navigate(action)
        }
        discoverAdapter.submitList(results)
        binding.vpDiscover.adapter = discoverAdapter
        binding.vpDiscover.offscreenPageLimit = 1
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // If you want a fading effect uncomment the next line:
            page.alpha = 0.4f + (1 - abs(position))
            page.scaleY = 1 - (0.2f * abs(position))
            page.rotation = (7* position)

        }
        binding.vpDiscover.setPageTransformer(pageTransformer)

        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )

//        binding.vpDiscover.removeItemDecorationAt(0)
        binding.vpDiscover.addItemDecoration(itemDecoration)

        binding.vpDiscover.currentItem = 1
    }

    private fun showAiringMovies(results: List<Result>?) {
        val adapter= MovieAdapter {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(it.id)
            findNavController().navigate(action)
        }
        adapter.submitList(results)
        binding.rvAiring.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}