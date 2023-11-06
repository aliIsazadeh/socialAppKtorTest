package com.example.repository.user

import com.example.dao.user.UserDao
import com.example.model.AuthResponse
import com.example.model.AuthResponseData
import com.example.model.SignInParams
import com.example.model.SignUpParams
import com.example.plugins.generateToken
import com.example.security.hashPassword
import com.example.util.Response
import io.ktor.http.*

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override suspend fun singUp(params: SignUpParams): Response<AuthResponse> {
        return if (userAlreadyExist(params.email)) {
            Response.Error(
                code = HttpStatusCode.Conflict,
                data = AuthResponse(errorMessage = "A user with this email already exist!")
            )
        } else {
            val insertedUser = userDao.insert(params)
            if (insertedUser == null) {
                Response.Error(
                    code = HttpStatusCode.InternalServerError,
                    data = AuthResponse(
                        errorMessage = "Oops, sorry we could not register the user , please try again later!"
                    )

                )
            } else {
                Response.Success(
                    data = AuthResponse(
                        data = AuthResponseData(
                            id = insertedUser.id,
                            name = insertedUser.name,
                            bio = insertedUser.bio,
                            token = generateToken(params.email)
                        )
                    )
                )
            }
        }
    }

    override suspend fun signIn(params: SignInParams): Response<AuthResponse> {
        val user = userDao.findByEmail(params.email)
      return if (user == null){
            Response.Error(
                code = HttpStatusCode.NotFound,
                data = AuthResponse(
                    errorMessage = "Invalid credentials , no user with this email"
                )
            )
        }else {
            val hashPassword = hashPassword(params.password)
            if (user.password == hashPassword){
                Response.Success(
                    data = AuthResponse(
                        data = AuthResponseData(
                            id = user.id,
                            name = user.name,
                            bio = user.bio,
                            token = generateToken(params.email)
                        )
                    )
                )
            }else {
                Response.Error(
                    code = HttpStatusCode.Forbidden,
                    data = AuthResponse(
                        errorMessage = "Invalid credentials , wrong password!"
                    )
                )
            }
        }

    }

    private suspend fun userAlreadyExist(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }
}