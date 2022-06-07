package com.binar.challenge5.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.challenge5.MainActivity.Companion.SHARED_FILE
import com.binar.challenge5.R
import com.binar.challenge5.ui.auth.AuthViewModel
import com.binar.challenge5.ui.auth.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

//import com.binar.challenge5.ui.auth.AuthViewModelFactory

class SplashFragment : Fragment() {


//    private val authViewModel: AuthViewModel by viewModels {
//        AuthViewModelFactory(AuthRepository(MyDatabase.getInstance(
//            requireContext())!!.userDao(),
//            UserDataStoreManager(requireContext())
//        ))
//    }

    private val authViewModel by viewModel<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.emailPreference().observe(viewLifecycleOwner){
            Handler(Looper.getMainLooper()).postDelayed({
                if (it == "") {
//                    val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
//                    findNavController().navigate(action)
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            },2000)
        }





    }


}