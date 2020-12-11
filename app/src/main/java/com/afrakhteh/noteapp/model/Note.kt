package com.afrakhteh.noteapp.model

class Note {

    var id: Int? = 0
    var title: String? = null
    var description: String? = null
    var date: String? = null

    constructor(id: Int, title: String, description: String, date: String) {
        this.id = id
        this.title = title
        this.description = description
        this.date = date

    }
}