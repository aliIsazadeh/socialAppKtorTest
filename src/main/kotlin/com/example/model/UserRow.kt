package com.example.model

import org.jetbrains.exposed.sql.Table

object UserRow : Table(name = "users") {

    val id = integer("user_id").autoIncrement()
    val name = varchar("user_name",250)
    val email = varchar("user_email",250)
    val bio = text("user_bio").default("Hey , what's up? Welcome to my social app page")
    val password = varchar("user_password", length = 100)
    val avatar = text("user_avatar").nullable()

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}

data class User(
    val id : Int ,
    val name : String,
    val email : String ,
    val bio : String ,
    val password : String ,
    val avatar : String ?
)