package com.example.syncup.data.model

/**
 * Group
 *
 * Data model representing a single group in the application.
 * Each group has a unique identifier and a mutable name.
 *
 * The name can only be updated through the rename() function
 * to keep changes controlled and explicit.
 */
class Group(val id: String, name:String) {
    var name: String = name
        private set
    /**
     * Updates the group's name.
     *
     * This method is the only way to modify the name,
     * preventing uncontrolled changes from outside the class.
     */
    fun rename(newName: String) {
        name = newName
    }
}