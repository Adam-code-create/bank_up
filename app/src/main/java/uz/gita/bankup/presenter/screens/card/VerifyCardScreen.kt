package uz.gita.bankup.presenter.screens.card

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
import uz.gita.bankup.data.api.retrofit.request.card.VerifyCardRequest
import uz.gita.bankup.databinding.ScreenVerifyCardBinding
import uz.gita.bankup.presenter.viewmodelimpls.card.VerifyCardViewModelImpl
import uz.gita.bankup.presenter.viewmodels.card.VerifyCardViewModel

@AndroidEntryPoint
class VerifyCardScreen :Fragment(R.layout.screen_verify_card) {
    private val vb by viewBinding(ScreenVerifyCardBinding::bind)
    private val viewModel : VerifyCardViewModel by viewModels<VerifyCardViewModelImpl> ()
    private var cardNumber = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            cardNumber = it.getString("cardNumber", "")
        }
        vb.verifyCode.setOnClickListener {
            viewModel.verifyCode(VerifyCardRequest(
                vb.inputCode.text.toString(), cardNumber
            ))
        }

        viewModel.enabledLiveData.observe(viewLifecycleOwner, enabledLiveDataObserver)
        viewModel.errorLivedata.observe(viewLifecycleOwner, errorLivedataObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner, progressLiveDataObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner,successLiveDataObserver)
    }

    private val enabledLiveDataObserver = Observer<Boolean>{
        vb.verifyCode.isEnabled = it
    }

    private val errorLivedataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val progressLiveDataObserver = Observer<Boolean>{
        if (it)vb.progress.show()
        else vb.progress.hide()
    }

    private val successLiveDataObserver = Observer<Boolean>{
        if (it) findNavController().popBackStack()
    }

}