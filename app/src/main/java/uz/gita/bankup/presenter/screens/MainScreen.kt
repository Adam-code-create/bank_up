package uz.gita.bankup.presenter.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bankup.R
import uz.gita.bankup.databinding.ScreenMainBinding
import uz.gita.bankup.domain.repositoryImpl.MainScreenRepositoryImpl
import uz.gita.bankup.presenter.viewmodelimpls.MainScreenViewModelImpl
import uz.gita.bankup.presenter.viewmodels.MainScreenViewModel

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {
    private val viewBinding by viewBinding (ScreenMainBinding::bind)
    private val viewModel :MainScreenViewModel by viewModels<MainScreenViewModelImpl> ()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.accountSetting.setOnClickListener {
            viewModel.openEditAccount()
        }

        viewBinding.logout.setOnClickListener {
            viewModel.logOut()
        }

        viewBinding.myCard.setOnClickListener {
            viewModel.openMyCards()
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner,errorLiveDataObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressLiveDataObserver)
        viewModel.successEditAccountLiveData.observe(viewLifecycleOwner,successEditAccountLiveDataObserver )
        viewModel.successLogoutLiveData.observe(viewLifecycleOwner, successLogoutLiveDataObserver)
        viewModel.successMyCardsLiveData.observe(viewLifecycleOwner, successMyCardsLiveDataObserver)

    }

    private val errorLiveDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val progressLiveDataObserver = Observer<Boolean>{

    }
    private val successEditAccountLiveDataObserver =Observer<Unit>{
        findNavController().navigate(R.id.action_mainScreen_to_editAccountScreen)
    }
    private val successLogoutLiveDataObserver = Observer<Unit>{
        findNavController().navigate(R.id.action_mainScreen_to_loginScreen)
    }
    private val successMyCardsLiveDataObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_mainScreen_to_myCardsScreen)
    }



}