package com.inkrodriguez.whatistheword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.*
import com.inkrodriguez.whatistheword.Adapter.MyAdapter
import com.inkrodriguez.whatistheword.Adapter.User
import com.inkrodriguez.whatistheword.Utils.Firebase
import com.inkrodriguez.whatistheword.ViewModel.FinishViewModel
import com.inkrodriguez.whatistheword.databinding.ActivityFinishBinding
import kotlinx.coroutines.*
import java.util.Objects

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding
    private lateinit var viewModel: FinishViewModel
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        var nome = intent.getStringExtra("nome")
        var pontuacao = intent.getStringExtra("pontuacao")

        viewModel = ViewModelProvider(this)[FinishViewModel::class.java]

        binding.btnBack.setOnClickListener {
            finish()
            startActivity(Intent(this, HomeActivity::class.java).putExtra("nome", nome))
        }

        var lista3: MutableList<User> = mutableListOf()
        var lista: MutableList<String> = mutableListOf()
        db = FirebaseFirestore.getInstance()
        db.collection("users").orderBy("pontuacao", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                value?.documents?.forEach {

                    lista3.add(User(it.get("username").toString(), it.get("pontuacao").toString()))

                    var recyclerView = binding.recyclerView
                    var adapter = MyAdapter(lista3)
                    recyclerView.adapter = adapter

                    lista.add(it.get("username").toString())

                    try {
                        binding.primeiroGanhador.text = lista3[0].username
                        binding.segundoGanhador.text = lista3[1].username
                        binding.terceiroGanhador.text = lista3[2].username

                    } catch (e: Exception) {
                        binding.primeiroGanhador.text = e.message
                    }

                }
            }


    }

}


