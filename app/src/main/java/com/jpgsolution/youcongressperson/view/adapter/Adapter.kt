package com.jpgsolution.youcongressperson.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jpgsolution.youcongressperson.R
import com.jpgsolution.youcongressperson.view.adapter.Adapter.MyViewHolder
import com.jpgsolution.youcongressperson.databinding.ItemListCongresspersonBinding
import com.jpgsolution.youcongressperson.model.datamodel.CongressPerson
import com.squareup.picasso.Picasso

class Adapter(
    private val listCongressperson: List<CongressPerson>,
    private val selectedCongressPerson: SelectedCongressPerson
): RecyclerView.Adapter<MyViewHolder>() {

    interface SelectedCongressPerson{
        fun selected(id: String)
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemListCongresspersonBinding.bind(view)
        private val nome = binding.name
        private val siglaPartido = binding.partido
        private val siglaUf = binding.ufState
        private val imageView = binding.imgPhoto

        fun bind(congressperson: CongressPerson){
            nome.text = congressperson.nome
            siglaPartido.text = congressperson.siglaPartido
            siglaUf.text = congressperson.siglaUf
            Picasso.get().load(congressperson.urlFoto).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_congressperson,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listCongressperson[position])
        holder.itemView.setOnClickListener {
            selectedCongressPerson.selected(listCongressperson[position].id)
        }
    }

    override fun getItemCount(): Int {
        return listCongressperson.size
    }
}
