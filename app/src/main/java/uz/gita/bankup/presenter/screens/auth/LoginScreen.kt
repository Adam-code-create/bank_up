package uz.gita.bankup.presenter.screens.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bankup.R
import uz.gita.bankup.data.api.retrofit.request.auth.RequestLogin
import uz.gita.bankup.databinding.ScreenLoginBinding
import uz.gita.bankup.presenter.viewmodelimpls.auth.LoginViewModelImpl
import uz.gita.bankup.presenter.viewmodels.auth.LoginViewModel

@AndroidEntryPoint
class LoginScreen :Fragment(R.layout.screen_login) {
    private val viewBinding by viewBinding (ScreenLoginBinding::bind)
    private val viewModel : LoginViewModel by viewModels<LoginViewModelImpl>()
    private var boolPassword = false
    private var boolConfirmPassword = false
    private var boolPhoneNumber = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  viewBinding.login.isEnabled = false
        viewBinding.phoneNumber.addTextChangedListener {
            it?.let {
                boolPhoneNumber = it.length ==13 && it.startsWith("+998")
                check()
            }
        }
        viewBinding.password.addTextChangedListener {
            it?.let {
                boolPassword = it.length>6 && it.toString() == viewBinding.confirmPassword.text.toString()
                check()
            }
        }
        viewBinding.confirmPassword.addTextChangedListener {
            it?.let {
                boolConfirmPassword = it.length>6 && it.toString() == viewBinding.password.text.toString()
                check()
            }
        }

        viewBinding.login.setOnClickListener {
            viewModel.loginUser(RequestLogin(
                viewBinding.password.text.toString(),
                viewBinding.phoneNumber.text.toString()

            ))
        }

        viewBinding.reset.setOnClickListener {
            viewModel.resetPassword()
        }

        viewModel.enableLiveData.observe(viewLifecycleOwner,enableLiveDataObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorLiveDataObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressLiveDataObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner, successLiveDataObserver)
        viewModel.successResetLiveData.observe(viewLifecycleOwner, successResetLiveDataObserver)
    }

    private val enableLiveDataObserver = Observer<Boolean>{
        Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        viewBinding.login.isEnabled = it
    }
    private val errorLiveDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val progressLiveDataObserver = Observer<Boolean>{
        if (it)viewBinding.progress.show()
        else viewBinding.progress.hide()
    }
    private val successLiveDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        bundle.putString("phone", viewBinding.phoneNumber.text.toString())
        arguments = bundle
        findNavController().navigate(R.id.action_loginScreen_to_smsVerifyScreen, bundle)
    }
    private val successResetLiveDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_loginScreen_to_resetScreen)
    }

    private fun check(){
       // viewBinding.login.isEnabled = boolConfirmPassword && boolPassword && boolPhoneNumber
    }
}