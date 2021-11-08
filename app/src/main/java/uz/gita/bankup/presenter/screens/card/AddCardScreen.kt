package uz.gita.bankup.presenter.screens.card

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
import uz.gita.bankup.data.api.retrofit.request.card.AddCardRequest
import uz.gita.bankup.databinding.ScreenAddCardBinding
import uz.gita.bankup.presenter.viewmodelimpls.card.AddCardViewModelImpl
import uz.gita.bankup.presenter.viewmodels.card.AddCardViewModel
import javax.inject.Inject

@AndroidEntryPoint
class AddCardScreen @Inject constructor(): Fragment(R.layout.screen_add_card) {
    private val vb by viewBinding(ScreenAddCardBinding::bind)
    private val viewModel : AddCardViewModel by viewModels<AddCardViewModelImpl>()
    private var number = false
    private var month = false
    private var year = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.saveCard.isEnabled = false
        vb.back.setOnClickListener {
            findNavController().popBackStack()
        }

        vb.cardNumber.addTextChangedListener {
            it?.let {
                number = it.length == 16
                check()
            }
        }

        vb.month.addTextChangedListener {
            it?.let {
                var count = 0
                for (i in it){
                   if (i.code in 48..57){
                       count++
                   }
                }
                month = count ==2
                check()
            }
        }

        vb.year.addTextChangedListener {
            it?.let {
                var count = 0
                for (i in it){
                    if (i.code in 48..57){
                        count++
                    }
                }
                year = count == 2 && it.toString().toInt() > 20
                check()
            }
        }

        vb.saveCard.setOnClickListener {
            val s = StringBuilder()
            s.append(vb.month.text.toString())
            s.append("/")
            s.append(vb.year.text.toString())

            viewModel.addCard(AddCardRequest(
                vb.fullName.text.toString(),vb.cardNumber.text.toString(),s.toString()
            ))
        }

        viewModel.enabledRegisterLiveData.observe(viewLifecycleOwner, enabledRegisterLiveDataObserver)
        viewModel.errorLivaData.observe(viewLifecycleOwner, errorLivaDataObserver)
        viewModel.progressLivedata.observe(viewLifecycleOwner,progressLivedataObserver)
        viewModel.successLivaData.observe(viewLifecycleOwner,successLivaDataObserve)

    }

    private fun check(){
        vb.saveCard.isEnabled  = number && month && year
    }


    private val enabledRegisterLiveDataObserver = Observer<Boolean>{
        vb.saveCard.isEnabled = it
    }
    private val errorLivaDataObserver = Observer<String> {
       // vb.saveCard.isEnabled = false
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val progressLivedataObserver = Observer<Boolean> {
        if (it)vb.progress.show()
        else vb.progress.hide()
    }
    private val successLivaDataObserve = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        val bundle = Bundle()
        bundle.putString("cardNumber", vb.cardNumber.text.toString())
        findNavController().navigate(R.id.action_addCardScreen_to_verifyCardScreen, bundle)
    }
}
