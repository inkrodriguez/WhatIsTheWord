package com.inkrodriguez.whatistheword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.inkrodriguez.whatistheword.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        var intent = intent.getStringExtra("nome")

        viewModel.setContext(applicationContext)

        binding.tvWelcome.text = "Ola, $intent"

        val editWord = binding.editWord
        val btnCheck = binding.btnCheck
        val tvMoeda = binding.tvMoeda
        val tvVida = binding.tvVida

        binding.tvPalavra.text = viewModel.shuffleName().toString()

        viewModel.player.pontuacao.observe(this, Observer {
            tvMoeda.text = it.toString()
        })

        viewModel.player.life.observe(this, Observer {
            tvVida.text = it.toString()
        })

        viewModel.palavra.resultadoFinal.observe(this, Observer {
            var palavra = it.toString()
            binding.tvPalavra.text = palavra
        })

        btnCheck.setOnClickListener {
            viewModel.checkWord("${editWord.text.trim()}")
            editWord.text.clear()
            Toast.makeText(this, "${viewModel.returnResult.value}", Toast.LENGTH_SHORT).show()
            gameOver()
        }

    }

    fun gameOver(){
        if(viewModel.player.vida == 0){
            val pontuacaoFinal: Int = viewModel.player.moeda
            var intent = Intent(this, FinishActivity::class.java).putExtra("pontuacao", pontuacaoFinal)
            startActivity(intent)

            viewModel.palavra.resetVariables()
            viewModel.player.resetPlayer()

        }

    }

}