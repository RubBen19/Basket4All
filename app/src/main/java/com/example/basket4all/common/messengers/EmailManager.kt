package com.example.basket4all.common.messengers

import android.content.Context
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Session

class EmailManager (
    private val context: Context,
    private val host: String = "smtp.gmail.com",
    private val port: String = "587",
    private val username: String,
    private val password: String
) {
    // SMTP Server configuration
    private fun createSession(): Session {
        val properties = Properties().apply {
            put("mail.smtp.host", host)
            put("mail.smtp.port", port)
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
        }
        return Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })
    }

    // Welcome email
    fun welcomeEmail(to: String, username: String, imagePath: String) {
        val subject = "Bienvenido a Basket4All, $username"
        val body = "Encantado de conocerte $username,\n\nGracias por unirte a Basket4All! " +
                "Esperamos que disfrutes de nuestra aplicación y te ayude a sacar tu 100%\n\n" +
                "Nos vemos en las canchas,\nEquipo de soporte Basket4All"
        sendEmail(to, subject, body, imagePath)
    }

    // Email template
    fun sendEmail(to: String, subject: String, body: String, imagePath: String?=null) {

    }
    // Password recovery email
}