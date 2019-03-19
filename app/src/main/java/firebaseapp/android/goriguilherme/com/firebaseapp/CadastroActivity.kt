package firebaseapp.android.goriguilherme.com.firebaseapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.content.Intent
import android.os.CountDownTimer
import kotlinx.android.synthetic.main.activity_cadastro.*


class CadastroActivity : AppCompatActivity() {

    val timer = object: CountDownTimer(500,1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        btnConfirmarCadastro.setOnClickListener {
            cadastrarUsuario(txtEmail.text.toString(), txtPassword.text.toString())
        }
    }

    private fun cadastrarUsuario(email: String, password: String) {
        val mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(applicationContext, "Cadastro efetuado com sucesso!",
                        Toast.LENGTH_SHORT).show()
                    timer.start()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        applicationContext, "Não foi possível cadastrar o usuário.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
