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
import uz.gita.bankup.data.api.retrofit.request.auth.RegisterRequest
import uz.gita.bankup.databinding.ScreenRegisterBinding
import uz.gita.bankup.presenter.viewmodelimpls.auth.RegisterViewModelImpl
import uz.gita.bankup.presenter.viewmodels.auth.RegisterViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RegisterScreen @Inject constructor() :Fragment(R.layout.screen_register) {
    private val viewBinding by viewBinding (ScreenRegisterBinding::bind)
    private val viewModel : RegisterViewModel by viewModels <RegisterViewModelImpl>()
    private var boolFirstName = false
    private var boolLastName = false
    private var boolPassword = false
    private var boolConfirmPassword = false
    private var boolPhoneNumber = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.send.isEnabled = false
        viewBinding.firstname.addTextChangedListener {
            it?.let {
                boolFirstName = it.length > 3
                check()
            }
        }

        viewBinding.lastName.addTextChangedListener {
            it?.let {
                boolLastName = it.length > 3
                check()
            }
        }
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

        viewBinding.send.setOnClickListener {
           // pref.phoneNumber = viewBinding.phoneNumber.text.toString()
            viewModel.registerLivaUser(
                RegisterRequest(
                    viewBinding.firstname.text.toString(),
                    viewBinding.lastName.text.toString(),
                    viewBinding.password.text.toString(),
                    viewBinding.phoneNumber.text.toString(),
                    "0"
                )
            )
        }

        viewModel.disabledRegisterLiveData.observe(viewLifecycleOwner,disabledRegisterObserver )
        viewModel.enabledRegisterLiveData.observe(viewLifecycleOwner,enabledRegisterObserver )
        viewModel.errorLivaData.observe(viewLifecycleOwner,errorObserver)
        viewModel.progressLivedata.observe(viewLifecycleOwner,progressObserver )
        viewModel.successLivaData.observe(viewLifecycleOwner,successObserver)


    }

    private fun check(){
        viewBinding.send.isEnabled = boolFirstName && boolLastName && (boolPassword||boolConfirmPassword) && boolPhoneNumber
    }

    private val disabledRegisterObserver = androidx.lifecycle.Observer<Unit>{
        viewBinding.send.isEnabled = false
    }

    private val enabledRegisterObserver = Observer<Unit>{
        viewBinding.send.isEnabled = true
    }

    private val errorObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val progressObserver = Observer<Boolean>{
        if (it)viewBinding.progress.show()
        else viewBinding.progress.hide()
    }

    private val successObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        bundle.putString("phone", viewBinding.phoneNumber.text.toString())
        arguments =  bundle
        findNavController().navigate(R.id.action_registerScreen_to_smsVerifyScreen, bundle)

    }
}