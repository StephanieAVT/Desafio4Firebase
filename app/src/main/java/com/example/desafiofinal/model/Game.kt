package com.example.desafiofinal.model

class Game(var title:String = "", var createdAt:String = "", var description:String = "", var gameUrlImg: String? = ""){
    var id = 0

    constructor(title:String, createdAt: String, description: String, gameUrlImg: String?, id: Int): this(title, createdAt, description, gameUrlImg)
}

