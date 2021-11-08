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
import uz.gita.bankup.data.api.retrofit.request.card.EditCardRequest
import uz.gita.bankup.databinding.ScreenEditOwnerNameBinding
import uz.gita.bankup.presenter.viewmodelimpls.card.EditCardViewModelImpl
import uz.gita.bankup.presenter.viewmodels.card.EditCardViewModel

@AndroidEntryPoint
class EditCardScreen : Fragment(R.layout.screen_edit_owner_name) {
    private val vb by viewBinding(ScreenEditOwnerNameBinding::bind)
    private val viewModel : EditCardViewModel by viewModels<EditCardViewModelImpl> ()
    private var pos = 0
    private var cardNamber = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.saveNewName.isEnabled = false
        arguments?.let {
            pos = it.getInt("pos")
            cardNamber = it.getString("cardNumber","")
        }

        vb.inputNewName.addTextChangedListener {
            check()
        }

        vb.saveNewName.setOnClickListener {
            viewModel.editCard( EditCardRequest(
                vb.inputNewName.text.toString(), cardNamber
            ))
        }

        viewModel.errorLivaData.observe(viewLifecycleOwner,errorLivaDataObserver)
        viewModel.progressLivedata.observe(viewLifecycleOwner,progressLivedataObserver)
        viewModel.successLivaData.observe(viewLifecycleOwner,successLivaDataObserver)

    }

    private val errorLivaDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val progressLivedataObserver = Observer<Boolean>{

    }
    private val successLivaDataObserver = Observer<String> {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun check(){
        vb.saveNewName.isEnabled =  vb.inputNewName.text.toString().length < 3
    }
}