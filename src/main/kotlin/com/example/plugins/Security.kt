package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.model.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

private val jwtAudience = System.getenv("jwt.audience")
private val jwtDomain = System.getenv("jwt.domain")
private val jwtSecret = System.getenv("jwt.secret")
private const val CLAIM : String = "email"
fun Application.configureSecurity() {

    authentication {

        jwt {
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtDomain)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim(CLAIM).asString() != null){
                    JWTPrincipal(payload = credential.payload)
                }else {
                    null
                }
            }

            challenge {_, _ ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = AuthResponse(
                        errorMessage = "Token is not valid or has expiered"
                    )
                )
            }
        }
    }
}

fun generateToken(email : String) : String {
    return JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtDomain)
        .withClaim(CLAIM , email)
        .sign(Algorithm.HMAC256(jwtSecret))
}
