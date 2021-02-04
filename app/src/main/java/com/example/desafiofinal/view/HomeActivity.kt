package com.example.desafiofinal.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.desafiofinal.GameAdapter
import com.example.desafiofinal.HomeViewModel
import com.example.desafiofinal.cr
import com.example.desafiofinal.databinding.ActivityHomeBinding
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), GameAdapter.OnGameClickListener {
    private lateinit var bind: ActivityHomeBinding
    lateinit var adapter: GameAdapter

    private val viewModel by viewModels<HomeViewModel>{
        object: ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HomeViewModel(cr) as T
            }
        }
    }




    override fun onStart() {
        super.onStart()
        viewModel.resListGame.observe(this){

            adapter = GameAdapter(it, this)
            rvGames.adapter = adapter
            rvGames.layoutManager = GridLayoutManager(this, 2)

        }
        viewModel.getGames()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.fbAddGame.setOnClickListener {
            startActivity(Intent(this, RegisterGameActivity::class.java))
        }


    }

    fun callDetails(id:Int, title:String, date:String){
        var intent = Intent(this, GameDetailsActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("title", title)
        intent.putExtra("created", date)

        startActivity(intent)
    }

    override fun gameClick(position: Int, id:Int, title:String, date: String) {
        callDetails(id, title, date)
    }


}