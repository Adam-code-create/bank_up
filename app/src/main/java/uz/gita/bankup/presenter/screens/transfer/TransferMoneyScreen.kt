package uz.gita.bankup.presenter.screens.transfer

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
import uz.gita.bankup.data.api.retrofit.request.card.RequestOwnerByPan
import uz.gita.bankup.data.api.retrofit.request.transfer.RequestMoneyTransfer
import uz.gita.bankup.data.api.retrofit.request.transfer.TransferFeeRequest
import uz.gita.bankup.data.api.retrofit.respond.transfer.ResponseTransferFee
import uz.gita.bankup.databinding.ScreenMoneyTransferBinding
import uz.gita.bankup.presenter.viewmodelimpls.transfer.SendMoneyViewModelImpl
import uz.gita.bankup.presenter.viewmodels.transfer.SendMoneyViewModel

@AndroidEntryPoint
class TransferMoneyScreen : Fragment(R.layout.screen_money_transfer) {
    private val viewBinding by viewBinding (ScreenMoneyTransferBinding::bind)
    private val viewModel :SendMoneyViewModel by viewModels <SendMoneyViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val senderId = 3

        viewBinding.inputCardNumber.addTextChangedListener {
            viewModel.getName(RequestOwnerByPan((viewBinding.inputCardNumber.text.toString())))
        }

        viewBinding.inputAmount.addTextChangedListener {
            viewModel.transferFee(TransferFeeRequest(
                viewBinding.inputAmount.text.toString().toInt(),
                viewBinding.inputCardNumber.text.toString(),
                senderId
            ))
        }

        viewBinding.sendMoney.setOnClickListener {
            viewModel.sendMoney(RequestMoneyTransfer(
                viewBinding.inputAmount.text.toString().toInt(),
                viewBinding.inputCardNumber.text.toString(),
                senderId
            ))
        }
        viewModel.enabledLiveData.observe(viewLifecycleOwner,enabledLiveDataObserver)
        viewModel.errorLiveData.observe(viewLifecycleOwner, errorLiveDataObserver)
        viewModel.progressLiveData.observe(viewLifecycleOwner,progressLiveDataObserver )
        viewModel.successNameLiveData.observe(viewLifecycleOwner, successNameLiveDataObserver)
        viewModel.successTransferFeeLiveData.observe(viewLifecycleOwner, successTransferFeeLiveDataObserver)
        viewModel.successLiveData.observe(viewLifecycleOwner,successLiveDataObserver )

    }

    private val enabledLiveDataObserver = Observer<Boolean>{
        viewBinding.sendMoney.isEnabled = it
    }
    private val errorLiveDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val progressLiveDataObserver = Observer<Boolean>{
        if (it)viewBinding.progress.show()
        else viewBinding.progress.hide()
    }

    private val successNameLiveDataObserver = Observer<String>{
       viewBinding.inputNumberLayout.helperText = it
    }
    private val successTransferFeeLiveDataObserver = Observer<ResponseTransferFee>{
        viewBinding.amountLayout.helperText = "Transfer fee :${it.data}"
    }

    private val successLiveDataObserver = Observer<Unit>{
        Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }
}