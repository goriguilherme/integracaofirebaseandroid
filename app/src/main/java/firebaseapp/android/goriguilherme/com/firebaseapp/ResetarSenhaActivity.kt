package firebaseapp.android.goriguilherme.com.firebaseapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.ContactsContract
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_resetar_senha.*

class ResetarSenhaActivity : AppCompatActivity() {

    val timer = object: CountDownTimer(500,1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resetar_senha)

        btnResetarSenha.setOnClickListener {
            enviarEmailRecuperacaoSenha(txtEmail.text.toString())
        }
    }


    private fun enviarEmailRecuperacaoSenha(email: String) {
        val auth = FirebaseAuth.getInstance()

        auth.sendPasswordResetEmail(email).addOnCompleteListener {
                task ->  if (task.isSuccessful) {
            Toast.makeText(applicationContext, "E-mail enviado com sucesso!",
                Toast.LENGTH_SHORT).show()
            timer.start()
        } else {
            Toast.makeText(applicationContext, "Não foi possível resetar a senha",
                Toast.LENGTH_SHORT).show()
        }

        }
    }

}



//private fun sendPasswordReset() {
//    // [START send_password_reset]
//    val auth = FirebaseAuth.getInstance()
//    val emailAddress = "user@example.com"
//
//    auth.sendPasswordResetEmail(emailAddress)
//        .addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Log.d(TAG, "Email sent.")
//            }
//        }
//    // [END send_password_reset]
//}