package com.binar.challenge5.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.binar.challenge5.R
import com.binar.challenge5.data.api.ApiClient
import com.binar.challenge5.data.api.model.Result
import com.binar.challenge5.databinding.FragmentHomeBinding
import com.binar.challenge5.utils.HorizontalMarginItemDecoration
import kotlin.math.abs


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(HomeRepository(ApiClient.instance))
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

        binding.ivAccount.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner){
//            if (it){
////                binding.progressBar.visibility = View.VISIBLE
////                binding.shimmerContainer.tim
//                binding.shimmerContainer.startShimmer()
//            }else{
////                binding.progressBar.visibility = View.GONE
//                Handler(Looper.getMainLooper()).postDelayed({
//                    binding.shimmerContainer.stopShimmer()
//                    binding.shimmerContainer.isVisible = false
//                },2000)
//            }
        }

        homeViewModel.discoverMovies.observe(viewLifecycleOwner){
            Handler(Looper.getMainLooper()).postDelayed({
                showDiscoverMovies(it.results)
            },2000)
        }

        homeViewModel.airingMovies.observe(viewLifecycleOwner) {
            Handler(Looper.getMainLooper()).postDelayed({
                showListMovie(it.results)
            },2000)
        }


    }

    private fun showDiscoverMovies(results: List<Result>?){
        val discoverAdapter = DiscoverAdapter{}
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

        binding.vpDiscover.addItemDecoration(itemDecoration)
        binding.vpDiscover.currentItem = 1
    }

    private fun showListMovie(results: List<Result>?) {
        val adapter= MovieAdapter {
            //pindah fragment sambil bawa it.id
//            val intent = Intent(this, DetailActivity::class.java)
//            intent.putExtra("movieId", it.id)
//            startActivity(intent)
//            mainViewModel.getDetailMovies(it.id)
        }
        adapter.submitList(results)
        binding.rvAiring.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}