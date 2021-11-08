package uz.gita.bankup.presenter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.bankup.R
import uz.gita.bankup.data.api.retrofit.respond.CardData
import uz.gita.bankup.data.api.retrofit.respond.GetAllCardsResponse

class CardAdapter(val list : ArrayList<CardData>) :RecyclerView.Adapter<CardAdapter.VH>() {
    private var listener :((Int)->Unit)? = null
    inner class VH(view: View) :RecyclerView.ViewHolder(view){
        private val owner : TextView = view.findViewById(R.id.firstName)
        private val number1 : TextView = view.findViewById(R.id.accountNumber1)
        private val number2 : TextView = view.findViewById(R.id.accountNumber2)
        private val number3 : TextView = view.findViewById(R.id.accountNumber3)
        private val number4 : TextView = view.findViewById(R.id.accountNumber4)
        private val expiry : TextView = view.findViewById(R.id.date)
        private val balance : TextView = view.findViewById(R.id.accountBalance)

        init {
            itemView.setOnLongClickListener {
                listener?.invoke(absoluteAdapterPosition)
                return@setOnLongClickListener true
            }

        }

        @SuppressLint("SetTextI18n")
        fun bind(){
            val data = list[absoluteAdapterPosition]
            owner.text = data.owner
            expiry.text = data.exp
            number1.text = data.pan.subSequence(0,4)
            number2.text = data.pan.subSequence(4,8)
            number3.text = data.pan.subSequence(8,12)
            number4.text = data.pan.subSequence(12,16)
            balance.text = "${data.balance} sum"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
       return VH(LayoutInflater.from(parent.context).inflate(R.layout.item_card2, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
       holder.bind()
    }

    override fun getItemCount(): Int = list.size

    fun setListener(f: (Int)->Unit){
        listener = f
    }
}