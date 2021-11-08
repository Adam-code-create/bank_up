package uz.gita.bankup.presenter.screens.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bankup.R
import uz.gita.bankup.databinding.ScreenAuthBinding
import uz.gita.bankup.presenter.viewmodelimpls.auth.AuthViewModelImpl
import uz.gita.bankup.presenter.viewmodels.auth.AuthViewModel

@AndroidEntryPoint
class AuthScreen :Fragment(R.layout.screen_auth) {
    private val viewBinding by viewBinding (ScreenAuthBinding::bind)
    private val viewModel : AuthViewModel by viewModels<AuthViewModelImpl> ()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.login.setOnClickListener {
            viewModel.openLogin()
        }
        viewBinding.register.setOnClickListener {
            viewModel.openRegister()
        }

        viewModel.openLoginLiveData.observe(viewLifecycleOwner, openLoginLiveDataObserver)
        viewModel.openRegisterLiveData.observe(viewLifecycleOwner, openRegisterLiveDataObserver)
    }

    private val openLoginLiveDataObserver = Observer<Unit>{
        findNavController().navigate(R.id.action_authScreen_to_loginScreen)
    }
    private val openRegisterLiveDataObserver = Observer<Unit>{
        findNavController().navigate(R.id.action_authScreen_to_registerScreen)
    }
}