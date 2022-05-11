package com.binar.challenge5.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import com.binar.challenge5.utils.URIPathHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private var imageUri: Uri? = null
    private var selectedImage = false
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


        binding.ivPhotoProfile.setOnClickListener {
            openGallery()
        }


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
            if (imageUri==null){
                Toast.makeText(context, "Default avatar", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, URIPathHelper().getPath(requireContext(), imageUri!!), Toast.LENGTH_SHORT).show()
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

    private var galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            imageUri = data?.data
            binding.ivPhotoProfile.setImageURI(imageUri)
            selectedImage = true
        }
    }


    private fun openGallery() {
        val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        galleryLauncher.launch(intentGallery)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}