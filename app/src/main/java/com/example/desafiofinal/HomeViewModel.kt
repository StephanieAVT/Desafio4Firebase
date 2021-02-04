package com.example.desafiofinal


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiofinal.model.Game
import com.google.firebase.firestore.CollectionReference

class HomeViewModel(val cr: CollectionReference):ViewModel() {

    var resListGame = MutableLiveData<ArrayList<Game>>()


    fun getGames() {

        cr.get().addOnSuccessListener { documents ->
            var listGame = arrayListOf<Game>()
            var cont = 0
            for (document in documents) {
                val itemG: Game = document.toObject(Game::class.java)
               itemG.id = cont
                listGame.add(itemG)
                cont += 1

            }
            resListGame.value = listGame

        }

    }



}