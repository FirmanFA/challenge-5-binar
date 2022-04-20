package com.binar.challenge5.ui.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.binar.challenge5.MainActivity
import com.binar.challenge5.R
import com.binar.challenge5.data.api.ApiClient
import com.binar.challenge5.data.local.MyDatabase
import com.binar.challenge5.data.local.model.User
import com.binar.challenge5.databinding.FragmentHomeBinding
import com.binar.challenge5.databinding.FragmentProfileBinding
import com.binar.challenge5.ui.home.HomeRepository
import com.binar.challenge5.ui.home.HomeViewModel
import com.binar.challenge5.ui.home.HomeViewModelFactory
import com.binar.challenge5.utils.AESEncryption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepository(MyDatabase.getInstance(requireContext())!!.userDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreference = context?.getSharedPreferences(MainActivity.SHARED_FILE, Context.MODE_PRIVATE)



        val email = sharedPreference?.getString("islogin","")
        var iduser: Int? = -1
        authViewModel.user.observe(viewLifecycleOwner){
            binding.apply {
                etName.setText(it?.name)
                etPassword.setText(AESEncryption.decrypt(it?.password))
            }
            iduser = it?.id
        }

        lifecycleScope.launch(Dispatchers.IO){
            authViewModel.getUser(email.toString())
        }
        

        binding.btnUpdate.setOnClickListener {
            val name = binding.etName.text.toString()
            val rawPassword = binding.etPassword.text.toString()
            val password = AESEncryption.encrypt(rawPassword).toString()
            val user = User(iduser,name,email.toString(),password)

            lifecycleScope.launch(Dispatchers.IO){
                val updateUser = authViewModel.updateUser(user)

                activity?.runOnUiThread {
                    if (updateUser!=0){
                        lifecycleScope.launch(Dispatchers.IO){
                            authViewModel.getUser(email.toString())
                        }
                        Toast.makeText(requireContext(), "update berhasil", Toast.LENGTH_SHORT).show()
                        val editor = sharedPreference!!.edit()
                        editor.putString("name",name)
                        editor.apply()
                    }
                }
            }
            
            
        }


        binding.tvLogout.setOnClickListener {
            sharedPreference?.edit {
                this.clear()
                this.apply()
            }
            it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}