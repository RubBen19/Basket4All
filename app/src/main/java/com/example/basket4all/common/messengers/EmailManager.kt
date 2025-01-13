package com.example.basket4all.common.messengers

import android.content.Context
import android.util.Log
import com.example.basket4all.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmailManager(
    private val context: Context,
    private val host: String = "smtp.gmail.com",
    private val port: String = "587",
    private val username: String = "basket4allteam@gmail.com",
    private val password: String = "rsag kgpn wbkt tpro"
) {
    // SMTP Server configuration
    private fun createSession(): Session {
        val properties = java.util.Properties().apply {
            put("mail.smtp.host", host)
            put("mail.smtp.port", port)
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
        }
        return Session.getInstance(properties, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(username, password)
            }
        })
    }

    // Welcome email (Suspending function)
    suspend fun welcomeEmail(to: String, name:String, imageId: Int = R.drawable.white) {
        val subject = "Bienvenido a Basket4All"
        val body = "Encantado de conocerte $name,\n\nGracias por unirte a Basket4All! " +
                "Esperamos que disfrutes de nuestra aplicación y te ayude a sacar tu 100%\n\n" +
                "Nos vemos en las canchas,\nEquipo de soporte Basket4All"
        sendEmail(to, subject, body, imageId)
    }

    // Email template (Suspending function)
    private suspend fun sendEmail(to: String, subject: String, body: String, imageId: Int? = null) {
        try {
            val session = createSession()
            val msg = MimeMessage(session).apply {
                setFrom(InternetAddress(username))
                setRecipient(Message.RecipientType.TO, InternetAddress(to))
                this.subject = subject
                setText(body)
            }
            withContext(Dispatchers.IO) {
                Transport.send(msg)
            }

        } catch (e: javax.mail.MessagingException) {
            e.printStackTrace()
        }
    }

    // Password recovery email (Suspending function)
    suspend fun sendRecoveryMail(to: String, password: String, imageId: Int = R.drawable.white) {
        val subject = "Recuperación de contraseña"
        val body = "Hola!\n\nHemos recibido una solicitud para restablecer tu contraseña, te la enviamos:\n" +
                "$password. Recuerda cambiarla por una nueva al entrar a la aplicación\n\n" +
                "Nos vemos en las canchas,\nEquipo de soporte Basket4All"
        sendEmail(to, subject, body, imageId)
    }

    private fun createTempFileFromDrawable(drawableResId: Int): File {
        try {
            val inputStream: InputStream = context.resources.openRawResource(drawableResId)
            val tempFile = File(context.cacheDir, "temp_image.jpg")
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            return tempFile
        } catch (e: Exception) {
            Log.e("EmailManager", "Error creating temp file from drawable", e)
            throw e // Re-throw to propagate error
        }
    }

}
