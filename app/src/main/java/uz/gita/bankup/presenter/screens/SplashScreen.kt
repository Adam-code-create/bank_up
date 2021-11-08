package uz.gita.bankup.presenter.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bankup.R
import uz.gita.bankup.presenter.viewmodelimpls.SplashSCreenViewModelImpl
import uz.gita.bankup.presenter.viewmodels.SplashViewModel

@AndroidEntryPoint
class SplashScreen :Fragment(R.layout.screen_splash) {
    private val viewModel : SplashViewModel by viewModels <SplashSCreenViewModelImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openAuthScreenLiveData.observe(viewLifecycleOwner, openAuthScreenLiveDataObserver)
        viewModel.openMainScreenLiveData.observe(viewLifecycleOwner, openMainScreenLiveDataObserver)
    }

    private val openAuthScreenLiveDataObserver = Observer<Unit>{
        findNavController().navigate(R.id.action_splashScreen_to_authScreen)
    }

    private val openMainScreenLiveDataObserver = Observer<Unit>{
        findNavController().navigate(R.id.action_splashScreen_to_mainScreen)
    }


}