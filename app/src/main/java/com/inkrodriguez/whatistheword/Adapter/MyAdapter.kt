package com.inkrodriguez.whatistheword.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.inkrodriguez.whatistheword.R


class MyAdapter(private val characters: List<User>): RecyclerView.Adapter<MyAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    //SERÁ O LAYOUT DO ITEM, RECUPERA OS DADOS DO ITEMVIEW.
    class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(data: User){
            with(itemView){
                val username = findViewById<TextView>(R.id.tvNome)
                val pontuacao = findViewById<TextView>(R.id.tvPontuacao)

                username.text = data.username
                pontuacao.text = data.pontuacao
            }
        }
    }
}