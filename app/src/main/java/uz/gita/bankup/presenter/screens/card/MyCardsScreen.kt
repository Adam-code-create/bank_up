package uz.gita.bankup.presenter.screens.card

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bankup.R
import uz.gita.bankup.data.api.retrofit.request.card.DeleteCardRequest
import uz.gita.bankup.data.api.retrofit.respond.CardData
import uz.gita.bankup.databinding.ScreenMyCardsBinding
import uz.gita.bankup.presenter.adapter.CardAdapter
import uz.gita.bankup.presenter.dialog.BottomDialog
import uz.gita.bankup.presenter.viewmodelimpls.card.GetAllCardsViewModelImpl
import uz.gita.bankup.presenter.viewmodels.card.GetAllCardsViewModel

@AndroidEntryPoint
class MyCardsScreen :Fragment(R.layout.screen_my_cards) {
    private val vb  by viewBinding(ScreenMyCardsBinding::bind)
    private val viewModel : GetAllCardsViewModel by viewModels<GetAllCardsViewModelImpl>()
    private val listData = ArrayList<CardData>()
    private val adapter by lazy { CardAdapter(listData) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.addCard.setOnClickListener{
            findNavController().navigate(R.id.action_myCardsScreen_to_addCardScreen)
        }

        adapter.setListener {pos->
            val dialog = BottomDialog()
            dialog.setDeleteListener {
                viewModel.deleteCard(DeleteCardRequest(listData[pos].pan))
                listData.removeAt(pos)
                adapter.notifyItemRemoved(pos)
            }

            dialog.setEditListener {
                val bundle = Bundle()
                bundle.putInt("pos", pos)
                bundle.putString("cardNumber", listData[pos].pan)
                findNavController().navigate(R.id.action_myCardsScreen_to_editCardScreen, bundle)
            }
            
        }
        viewModel.getAllCards()
        vb.list.adapter = adapter
        vb.list.layoutManager = LinearLayoutManager(requireContext())

        vb.sendMoney.setOnClickListener {
            viewModel.openSendMoney()
        }

        viewModel.errorLivaData.observe(viewLifecycleOwner,errorLivaDataObserver)
        viewModel.progressLivedata.observe(viewLifecycleOwner, progressLivedataObserver)
        viewModel.successLivaData.observe(viewLifecycleOwner, successLivaDataObserver)
        viewModel.successDeleteLiveData.observe(viewLifecycleOwner, successDeleteLiveDataObserver)
        viewModel.failureDeleteLiveData.observe(viewLifecycleOwner,failureDeleteLiveDataObserver)
        viewModel.openSendMoneyLiveData.observe(viewLifecycleOwner, openSendMoneyLiveDataObserver)

    }

    private val errorLivaDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private val progressLivedataObserver = Observer<Boolean>{
        if (it)vb.progress.show()
        else vb.progress.hide()
    }

    private val successLivaDataObserver = Observer<List<CardData>>{
        listData.clear()
        listData.addAll(it)
        adapter.notifyDataSetChanged()
    }

    private val successDeleteLiveDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val failureDeleteLiveDataObserver = Observer<String>{
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }
    private val openSendMoneyLiveDataObserver = Observer<Boolean>{
        findNavController().navigate(R.id.action_myCardsScreen_to_transferMoneyScreen)
    }
}