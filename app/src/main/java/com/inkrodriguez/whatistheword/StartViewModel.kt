package com.inkrodriguez.whatistheword

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class StartViewModel: ViewModel() {
    var db = FirebaseFirestore.getInstance()
    var editNome = MutableLiveData<String>()
    var editSenha = MutableLiveData<String>()

    @SuppressLint("StaticFieldLeak")

    private lateinit var context: Context
    fun setContext(context: Context){
        this.context = context
    }

    fun signInOrRegister(){
        var nome = editNome.value.toString()
        var senha = editSenha.value.toString()

        db.collection("users").document(editNome.value.toString()).addSnapshotListener { it, error ->
            if(it?.getString("username") == editNome.value.toString() && it?.getString("senha") == editSenha.value.toString()){
                nextActivity()
            } else if(it?.getString("username")?.isNotEmpty() == true){
                Toast.makeText(context, "Este usuário já existe, sua senha está incorreta!", Toast.LENGTH_SHORT).show()
            }
            else {
                cadastrarUser()
                nextActivity()
            }
        }
    }

    fun nextActivity(){
        var intent = Intent(context, HomeActivity::class.java).addFlags(FLAG_ACTIVITY_NEW_TASK).putExtra("nome", editNome.value)
        startActivity(context, intent, null)
    }

    fun cadastrarUser(){
        var userMap = hashMapOf(
            "username" to editNome.value,
            "senha" to editSenha.value,
            "pontuacao" to 0
        )

        db.collection("users").document(editNome.value.toString()).set(userMap).addOnCompleteListener {
            if(it.isSuccessful) {
                Toast.makeText(context, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT)
                    .show()
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Erro!", Toast.LENGTH_SHORT).show()
        }
    }

}

