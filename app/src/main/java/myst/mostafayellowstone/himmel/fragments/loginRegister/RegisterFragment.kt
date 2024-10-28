package myst.mostafayellowstone.himmel.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import myst.mostafayellowstone.himmel.R
import myst.mostafayellowstone.himmel.data.User
import myst.mostafayellowstone.himmel.databinding.FragmentRegisterBinding
import myst.mostafayellowstone.himmel.util.RegisterValidation
import myst.mostafayellowstone.himmel.util.Resource
import myst.mostafayellowstone.himmel.viewmodels.RegisterViewModel

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private val viewModel by viewModels<RegisterViewModel>()
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.doyouHaveAnAccountText.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.apply {
            btnRegisterFragment.setOnClickListener {
                val user = User(
                    edFirstName.text.toString().trim(),
                    edLastName.text.toString().trim(),
                    edEmail.text.toString().trim(),
                )
                val password = edPassword.text.toString()
                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Resource.Error -> {
                        Log.e("test1", it.message.toString())
                        binding.registerProgressBar.visibility = View.GONE
                    }

                    is Resource.Loading -> binding.registerProgressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        Log.d("test", it.data.toString())
                        binding.registerProgressBar.visibility = View.GONE
                    }

                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect { validation ->
                if (validation.email is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.edEmail.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }
                if (validation.passwaord is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.edPassword.apply {
                            requestFocus()
                            error = validation.passwaord.message
                        }
                    }
                }
            }
        }
    }
}