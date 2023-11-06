package com.example.dao.user

import com.example.dao.DatabaseFactory
import com.example.model.SignUpParams
import com.example.model.User
import com.example.model.UserRow
import org.jetbrains.exposed.sql.insert

class UserDaoImpl : UserDao {
    override suspend fun insert(params: SignUpParams): User? {

        DatabaseFactory.dbQuery {
            val insertStatement = UserRow.insert {
                it[name] = params.name
            }
        }
    }

    override suspend fun findByEmail(email: String): User? {
        TODO("Not yet implemented")
    }
}