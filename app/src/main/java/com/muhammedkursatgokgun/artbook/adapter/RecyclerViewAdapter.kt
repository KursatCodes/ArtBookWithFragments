package com.muhammedkursatgokgun.artbook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.muhammedkursatgokgun.artbook.databinding.RecyclerRowBinding
import com.muhammedkursatgokgun.artbook.fragments.ArtFragmentDirections
import com.muhammedkursatgokgun.artbook.model.Art

class RecyclerViewAdapter(var artList: List<Art>) : RecyclerView.Adapter<RecyclerViewAdapter.ArtHolder>() {
    class ArtHolder(var binding : RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtHolder {//holder'ı başlatıyoruz oluşturuyoruz
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtHolder(binding)
    }

    override fun getItemCount(): Int {//recylerviewin uzunluğu
        return artList.size
    }

    override fun onBindViewHolder(holder: ArtHolder, position: Int) {//bağlama işlemi yapıyoruz
        holder.binding.recylerViewTextView.text= artList.get(position).name
        holder.itemView.setOnClickListener{
            val action = ArtFragmentDirections.actionArtFragmentToUploadFragment(
                silinecek = "eski", id = artList[position].id
            )
            Navigation.findNavController(it).navigate(action)
        }
    }
}