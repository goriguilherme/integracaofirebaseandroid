package firebaseapp.android.goriguilherme.com.firebaseapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_resetar_senha.*


const val RC_SIGN_IN = 9001;

class MainActivity : AppCompatActivity() {

    //private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //mAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener { login(textEmail.text.toString(), textPassword.text.toString(),
            this@MainActivity) }

        //    startActivity(Intent(this@Main2Activity, MainActivity::class.java))
        //
        btnCadastrarUsuario.setOnClickListener {
            startActivity(Intent(this@MainActivity, CadastroActivity::class.java ))
        }

        btnEsqueceuSenha.setOnClickListener {
            startActivity(Intent(this@MainActivity, ResetarSenhaActivity::class.java))
        }

        btnLoginGoogle.setOnClickListener{loginGoogle()}
    }

    private fun login(email: String, password: String, activity: Activity){

        val mAuth = FirebaseAuth.getInstance()

        mAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(applicationContext, Main2Activity::class.java))
                } else {
                    Toast.makeText(applicationContext, "Usuário e/ou senha incorreto(s)",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun loginGoogle(){
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(applicationContext, "Login pelo google falhou",
                    Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val mAuth = FirebaseAuth.getInstance()

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this@MainActivity, Main2Activity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@MainActivity, "Login pelo google no firebase falhou",
                        Toast.LENGTH_SHORT).show()
                }

            }
    }
}
