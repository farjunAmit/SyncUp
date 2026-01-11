package com.example.syncup.data.model

class Group(val id: String, name:String) {
    var name: String = name
        private set
    //Setter for name
    fun rename(newName: String) {
        name = newName
    }
}