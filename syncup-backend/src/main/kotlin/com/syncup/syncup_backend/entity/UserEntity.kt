package com.syncup.syncup_backend.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
@Table(name = "users")
class UserEntity(
    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    //TODO: Encrypt password + Validation
    var password: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}