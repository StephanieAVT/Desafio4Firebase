package com.example.desafiofinal.view

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desafiofinal.cr
import com.example.desafiofinal.databinding.ActivityRegisterGameBinding
import com.example.desafiofinal.model.Game
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog


class RegisterGameActivity : AppCompatActivity() {
    private lateinit var bind: ActivityRegisterGameBinding

    lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    private val CODE_IMG = 1000


    private val viewModel by viewModels<RegisterGameViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return RegisterGameViewModel(cr) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterGameBinding.inflate(layoutInflater)
        setContentView(bind.root)
        config()




        bind.ivAddGameR.setOnClickListener {
            getRes()
        }
        bind.btnSaveGame.setOnClickListener {

            if (bind.etNameGameR.text?.isNotBlank()!! &&
                    bind.etCreatedGR.text?.isNotBlank()!! &&
                    bind.etDescriptionGR.text?.isNotBlank()!!) {
                viewModel.sendGame(
                    Game(
                        bind.etNameGameR.text.toString(),
                        bind.etCreatedGR.text.toString(),
                        bind.etDescriptionGR.text.toString(),
                        viewModel.imgGame.value
                )
                )

                callHome()
            } else {
                showMsg("Preencha todas as informações.")
            }

        }
    }


    fun config() {
        var number = (0..200).random()
        var nString = number.toString()
        alertDialog = SpotsDialog.Builder().setContext(this).build()

        storageReference = FirebaseStorage.getInstance().getReference(nString)
    }

    fun getRes() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Imagem Game."), CODE_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG) {
            alertDialog.show()
            val uploadFile = data?.data?.let { storageReference.putFile(it) }
            if (uploadFile != null) {
                val task = uploadFile.continueWithTask { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Imagem carrregada com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                    storageReference!!.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        Log.i("URI", downloadUri.toString())
                        val url = downloadUri!!.toString().substring(0, downloadUri.toString().indexOf("&token"))
                        Log.i("URL da Imagem", url)
                        alertDialog.dismiss()
                        viewModel.saveUrlImage(downloadUri.toString())
                    }
                }
            } else {
                alertDialog.dismiss()
                showMsg("A imagem não foi carregada.")
            }

        }
    }

    fun callHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    fun showMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}