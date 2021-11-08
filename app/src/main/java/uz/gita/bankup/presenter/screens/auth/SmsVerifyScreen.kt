package uz.gita.bankup.presenter.screens.auth

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
import uz.gita.bankup.data.api.retrofit.request.auth.VerifyCodeRequest
import uz.gita.bankup.databinding.ScreenSmsVerifyBinding
import uz.gita.bankup.presenter.viewmodelimpls.auth.VerifyCodeViewModelImpl
import uz.gita.bankup.presenter.viewmodels.auth.VerifyCodeViewModel
import java.lang.StringBuilder

@AndroidEntryPoint
class SmsVerifyScreen :Fragment(R.layout.screen_sms_verify) {
    private val viewBinding by viewBinding (ScreenSmsVerifyBinding::bind)
    private val viewModel : VerifyCodeViewModel by viewModels<VerifyCodeViewModelImpl>()
    private var phone = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
           phone = it.getString("phone", "")
        }
        viewBinding.send.setOnClickListener {
            if (viewBinding.inputCode.text.toString().length == 6){
                viewModel.verifyCode(
                    VerifyCodeRequest(viewBinding.inputCode.text.toString(), phone)
                )
            }
        }

        viewModel.successLiveData.observe(viewLifecycleOwner,successLiveDataObserver)
    }

    private val successLiveDataObserver = Observer<Unit>{
        Toast.makeText(requireContext(), "done", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_smsVerifyScreen_to_mainScreen)
    }


}