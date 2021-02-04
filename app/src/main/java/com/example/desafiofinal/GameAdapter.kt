package com.example.desafiofinal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofinal.model.Game
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.game_card.view.*

class GameAdapter(val listGames: ArrayList<Game>, val listener: OnGameClickListener): RecyclerView.Adapter<GameAdapter.GameViewHolder>(){


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_card, parent, false)
        return GameViewHolder(view)
    }


    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        var game = listGames.get(position)
        holder.tvTit.text = game.title
        holder.tvCreat.text = game.createdAt
        if (game.gameUrlImg != null){
            Picasso.get().load(game.gameUrlImg).fit().into(holder.ivGame)
        }

    }

    override fun getItemCount() = listGames.size

    interface OnGameClickListener{
        fun gameClick(position: Int, id:Int, titleG:String, createdG:String)
    }


    inner class GameViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        val tvTit: TextView = view.tvTitleGame
        val tvCreat: TextView = view.tvCreatedAtGame
        val ivGame: ImageView = view.ivImgGame

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            var id = listGames.get(position).id
            var titleG = listGames.get(position).title
            var createdG = listGames.get(position).createdAt

            if (RecyclerView.NO_POSITION != position)
                listener.gameClick(position, id, titleG, createdG)
        }
    }


}