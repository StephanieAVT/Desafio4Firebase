package com.example.desafiofinal.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.desafiofinal.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    private lateinit var bind: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.btnRegister.setOnClickListener {
            getData()
        }
    }

    fun getData(){
        var email = bind.etEmailR.text.toString()
        var password = bind.etPasswordR.text.toString()
        var emailNB = email.isNotBlank()
        var passwordnB = password.isNotBlank()


        if (emailNB && passwordnB){
            if(bind.etPasswordR.text.toString() == bind.etRPasswordR.text.toString()){
                sendData(email,password)

            }else{

                showMsg("As senhas não são iguais")
            }

        }else{
            showMsg("Preencha todas as informações")
        }

    }

    fun sendData(email:String, password:String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result?.user!!
                        showMsg("Usuário cadastrado com sucesso")
                        callHome()
                    }
                }
    }

    fun callHome(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    fun showMsg(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}