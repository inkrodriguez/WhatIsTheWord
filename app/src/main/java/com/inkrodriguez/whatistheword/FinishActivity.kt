package com.inkrodriguez.whatistheword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.inkrodriguez.whatistheword.Adapter.MyAdapter
import com.inkrodriguez.whatistheword.Adapter.User
import com.inkrodriguez.whatistheword.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding
    private lateinit var viewModel: FinishViewModel
    private var resultado: MutableList<User> = mutableListOf()
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[FinishViewModel::class.java]
        var intent = intent.getStringExtra("pontuacao")

            var lista3: MutableList<User> = mutableListOf()
            db = FirebaseFirestore.getInstance()
            db.collection("users").orderBy("pontuacao", Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    value?.documents?.forEach {

                        lista3.add(User(it.get("username").toString(), it.get("pontuacao").toString()))

                        resultado = lista3

                        var recyclerView = binding.recyclerView
                        var adapter = MyAdapter(resultado)
                        recyclerView.adapter = adapter

                    }

                }

    }
    }


