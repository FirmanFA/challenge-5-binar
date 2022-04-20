package com.binar.challenge5.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.binar.challenge5.utils.AESEncryption
import com.binar.challenge4.utils.ValidationForm.isValid
import com.binar.challenge5.data.local.MyDatabase
import com.binar.challenge5.data.local.model.User
import com.binar.challenge5.databinding.FragmentRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null

//    lateinit var authRepository: AuthRepository
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepository(MyDatabase.getInstance(requireContext())!!.userDao()))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        authRepository = AuthRepository(requireContext())

        binding.btnRegister.setOnClickListener {

            if ((binding.etPassword.text.toString() == binding.etPassword2.text.toString()) and
                    binding.etEmail.isValid() and binding.etName.isValid() and
                    binding.etPassword.isValid()){

                val email = binding.etEmail.text.toString()
                val nama = binding.etName.text.toString()
                val password = binding.etPassword.text.toString()

                val encryptedPassword = AESEncryption.encrypt(password).toString()

                val user = User(null,nama,email,encryptedPassword)

                lifecycleScope.launch(Dispatchers.IO) {

//                    val isEmailExist = authRepository.checkEmailIfExist(email)
                    val isEmailExist = authViewModel.checkIfEmailExist(email)

                    activity?.runOnUiThread {
                        if (isEmailExist == null){
                            registerUser(user)
                        }else{
                            Toast.makeText(context, "Email sudah didaftarkan", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }


        }

    }
    
    private fun registerUser(user: User){
        lifecycleScope.launch(Dispatchers.IO) {
            
//            val registeredUser = myDatabase?.userDao()?.insertUser(user)

            val registeredUser = authViewModel.register(user)

            activity?.runOnUiThread {
                if (registeredUser == (0).toLong()){
                    Toast.makeText(context, "Gagal register", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "sukses register", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}