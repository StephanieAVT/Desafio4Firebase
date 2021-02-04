package com.example.desafiofinal.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiofinal.model.Game
import com.google.firebase.firestore.CollectionReference


class RegisterGameViewModel(val cr: CollectionReference) : ViewModel() {
    val resSend = MutableLiveData<Boolean>()
    var imgGame = MutableLiveData<String?>()

    fun sendGame(game: Game) {

        cr.document().set(game).addOnSuccessListener {
            resSend.value = true
        }.addOnCanceledListener {
            resSend.value = false
        }
        Log.i("FIREBASE", resSend.value.toString())

    }

    fun saveUrlImage(img: String?){
        imgGame.value = img

    }


}
