package com.example.exampleroom.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exampleroom.Room.Note
import com.example.exampleroom.databinding.AdapterMainBinding

class MainAdapter(var note : ArrayList<Note>,private val listener : OnAdapterListerner) : RecyclerView.Adapter<MainAdapter.UserViewHolder>() {
    inner class UserViewHolder(var binding : AdapterMainBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(AdapterMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.apply {
            textTitle.text = note[position].title
            textTitle.setOnClickListener {
                listener.Onclick(note[position])
            }
            iconEdit.setOnClickListener {
                listener.OnUpdate(note[position])
            }
            iconDelete.setOnClickListener {
                listener.OnDelete(note[position])
            }
        }
    }

    override fun getItemCount(): Int {
       return note.size
    }

    fun setData(data : List<Note>){
        note.clear()
        note.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListerner{
        fun Onclick (notes : Note)
        fun OnUpdate (notes : Note)
        fun OnDelete (notes : Note)
    }
}