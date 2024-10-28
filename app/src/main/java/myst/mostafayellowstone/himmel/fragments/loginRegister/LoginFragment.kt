package myst.mostafayellowstone.himmel.fragments.loginRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import myst.mostafayellowstone.himmel.R
import myst.mostafayellowstone.himmel.activities.ShoppingActivity
import myst.mostafayellowstone.himmel.databinding.FragmentLoginBinding
import myst.mostafayellowstone.himmel.dialog.setupBottomSheetDialog
import myst.mostafayellowstone.himmel.util.Resource
import myst.mostafayellowstone.himmel.viewmodels.LoginViewModel

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private val viewModel by viewModels<LoginViewModel>()
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDontHaveAnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.tvForgotPassword.setOnClickListener {
            setupBottomSheetDialog { email ->
                viewModel.resetPassword(email)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect {
                when(it){
                    is Resource.Error -> {
                        Snackbar.make(requireView(), "Error: ${it.message}", Snackbar.LENGTH_LONG).show()
                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        Snackbar.make(requireView(), "Reset link was sent to your email", Snackbar.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }

        binding.apply {
            btnLoginFragment.setOnClickListener{
                val email = edEmailLogin.text.toString().trim()
                val password = edPasswordLogin.text.toString()
                viewModel.login(email, password)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.login.collect{
                when(it){
                    is Resource.Error -> {
                        binding.loginProgressBar.visibility = View.GONE
                        Toast.makeText(requireActivity() , it.message  , Toast.LENGTH_SHORT).show()

                    }
                    is Resource.Loading -> {
                        binding.loginProgressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.loginProgressBar.visibility = View.GONE
                        Intent(requireActivity(), ShoppingActivity::class.java).also {
                            intent: Intent -> intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}