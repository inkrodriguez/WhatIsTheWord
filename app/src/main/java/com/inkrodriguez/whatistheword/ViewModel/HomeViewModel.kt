package com.inkrodriguez.whatistheword.ViewModel

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inkrodriguez.whatistheword.FinishActivity
import com.inkrodriguez.whatistheword.HomeActivity
import com.inkrodriguez.whatistheword.Utils.Firebase

class HomeViewModel: ViewModel() {

    private var firebase = Firebase()
    private lateinit var context: Context
    fun setContext(context: Context){
        this.context = context
    }

    var returnResult = MutableLiveData<String>()
    var palavra = Palavra()
    var nome = ""

    class Player{
        var vida = MutableLiveData<Int>()
        var pontuacao = MutableLiveData<Int>()
    }

    var player = Player()

    init {
        player.vida.value = 3
        player.pontuacao.value = 0
    }


    fun receiveCoin(){
        player.pontuacao.value = player.pontuacao.value?.plus(1)
    }

    fun loseLife(){
        player.vida.value = player.vida.value?.minus(1)
    }

    fun gameOver(){
        palavra.resetVariables()
    }

    class Palavra {

        var nameRandom: String = ""
        var nameSplit = mutableListOf("")
        var nameSplitRandom: String = ""
        var resultadoFinal = MutableLiveData<String>()

        var emptyList = mutableListOf<String>()
        var nameList = mutableListOf(
            "CARRO",
            "ABACAXI",
            "PEPINO",
            "MELANCIA",
            "JACA",
            "COPO",
            "JANELA",
            "SAPATO",
            "VERDE",
            "APONTADOR",
            "APAGADOR",
            "QUADRO",
            "CACHORRO",
            "JARDIM",
            "PANELA",
            "FOLHA",
            "PAPEL",
            "CELULAR",
            "IMPRESSORA",
            "MACA",
            "RADIO",
            "CHAVE",
            "TELEVISAO",
            "CADEIRA",
            "MICROONDAS",
            "GELADEIRA",
            "FORNO",
            "MOTO",
            "CAPACETE",
            "GATO",
            "ELEFANTE",
            "JABUTI",
            "LEOPARDO",
            "ANEL",
            "AZULEJO",
            "BENGALA",
            "BOLA",
            "FUTEBOL",
            "BRINCO",
            "CABIDE",
            "CALCULADORA",
            "CANIVETE",
            "FACA",
            "GARFO",
            "COLHER",
            "CHICOTE",
            "CHINELO",
            "COMPUTADOR",
            "DADO",
            "DARDO",
            "DENTADURA",
            "DENTE",
            "DISCO",
            "DRONE",
            "ESPADA",
            "ESTOJO",
            "EXTINTOR",
            "FAROL",
            "FERRADURA",
            "FLAUTA",
            "FOGUETE",
            "FRIGOBAR",
            "FURADEIRA",
            "MARTELO",
            "PREGO",
            "SERRA",
            "MADEIRA",
            "ARVORE",
            "GANCHO",
            "GARRAFA",
            "VIDRO",
            "GAVETA",
            "GRANADA",
            "GRAMPO",
            "GUARDANAPO",
            "GUITARRA",
            "VIOLAO",
            "SHAMPOO",
            "KETCHUP",
            "MOSTARDA",
            "ISOPOR",
            "INTERFONE",
            "JALECO",
            "JARRO",
            "JOGO",
            "LUVA",
            "ESPELHO",
            "PARAFUSO",
            "CANETA",
            "BOCA",
            "LINGUA",
            "CABELO",
            "BARRIGA",
            "CANELA",
            "DEDO",
            "UNHA",
            "PELE",
            "COXA",
            "IGREJA",
            "JOELHO",
            "AQUECEDOR"
        )


        fun randomName() {
            nameRandom = nameList.random().uppercase()
        }

        fun splitName() {
            nameSplit = nameRandom.split("") as MutableList<String>
        }

        fun splitNameRandom() {
            nameSplitRandom = nameSplit.random()
        }

        fun createWord(){
            var i = 0
            while (i <= nameRandom.length + 1) {
                splitNameRandom()
                emptyList.add(nameSplitRandom)
                nameSplit.remove(nameSplitRandom)
                i++
            }
        }

        fun joinString(){
            resultadoFinal.value = emptyList.joinToString("")
        }

        fun resetVariables(){
            nameSplitRandom = ""
            nameRandom = ""
            nameSplit.clear()
            emptyList.clear()
        }

        fun removeWordList(){
            nameList.remove(nameRandom)
        }


    }

    fun shuffleName() {
        palavra.randomName()
        palavra.splitName()
        palavra.splitNameRandom()
        palavra.createWord()
        palavra.joinString()
        return
    }

    fun checkWord(editWord: String){
        if(editWord.uppercase() == palavra.nameRandom){
            palavra.removeWordList()
            palavra.resetVariables()
            shuffleName()
            returnResult.value = "Parabéns, você acertou!"
            receiveCoin()
        } else {
            returnResult.value = "Opa, acho que não!"
            loseLife()
        }
    }



}