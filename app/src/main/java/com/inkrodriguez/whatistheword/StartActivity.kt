package com.inkrodriguez.whatistheword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.inkrodriguez.whatistheword.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var viewModel: StartViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[StartViewModel::class.java]
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.setContext(applicationContext)

        binding.btnPlay.setOnClickListener {
            viewModel.editNome.value = binding.editNome.text.toString().toUpperCase().trim()
            viewModel.editSenha.value = binding.editSenha.text.toString().toUpperCase().trim()
            viewModel.signInOrRegister()
        }

    }
}