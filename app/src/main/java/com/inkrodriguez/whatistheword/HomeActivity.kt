package com.inkrodriguez.whatistheword

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.inkrodriguez.whatistheword.Utils.Firebase
import com.inkrodriguez.whatistheword.ViewModel.HomeViewModel
import com.inkrodriguez.whatistheword.databinding.ActivityHomeBinding

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var intentNome: String
    private var firebase = Firebase()
    var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        intentNome = intent.getStringExtra("nome").toString()
        viewModel.nome = intent.getStringExtra("nome").toString()
        viewModel.setContext(applicationContext)

        binding.tvWelcome.text = "Ola, $intentNome"

        val editWord = binding.editWord
        val btnCheck = binding.btnCheck
        val tvMoeda = binding.tvMoeda
        val tvVida = binding.tvVida

        binding.tvPalavra.text = viewModel.shuffleName().toString()

        viewModel.player.pontuacao.observe(this, Observer {
            tvMoeda.text = it.toString()
        })

        viewModel.player.vida.observe(this, Observer {
            tvVida.text = it.toString()
            if(it == 0){
                //Lê pontuação atual
                db.collection("users").document(intentNome).addSnapshotListener { it, error ->
                    if (it != null) {
                        var pontuacaoAtual = it.get("pontuacao")
                        if(tvMoeda.text.toString().toInt() > pontuacaoAtual.toString().toInt()) {
                            //update pontuação
                            db.collection("users").document(intentNome)
                                .update("pontuacao", tvMoeda.text).addOnCompleteListener {
                                    var intent = Intent(this, FinishActivity::class.java).putExtra(
                                        "nome",
                                        intentNome
                                    ).putExtra("pontuacao", tvMoeda.text)
                                    startActivity(intent)
                                    finish()
                                    finishAffinity()
                                }
                        } else {
                            Toast.makeText(this, "DEU RUIM ${pontuacaoAtual.toString().toInt()} e ${tvMoeda.text.toString().toInt()}", Toast.LENGTH_SHORT).show()
                                var intent = Intent(this, FinishActivity::class.java).putExtra(
                                    "nome",
                                    intentNome
                                ).putExtra("pontuacao", tvMoeda.text)
                                startActivity(intent)
                                finish()
                                finishAffinity()
                            }
                        }
                    }
                }
            })


        viewModel.palavra.resultadoFinal.observe(this, Observer {
            var palavra = it.toString()
            binding.tvPalavra.text = palavra
        })

        btnCheck.setOnClickListener {
            viewModel.checkWord("${editWord.text.trim()}")
            editWord.text.clear()
            Toast.makeText(this, "${viewModel.returnResult.value}", Toast.LENGTH_SHORT).show()
        }

    }


}