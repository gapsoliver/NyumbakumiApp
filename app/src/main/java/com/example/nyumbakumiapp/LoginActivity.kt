package com.example.nyumbakumiapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var edtemail: EditText
    lateinit var edtpwd: EditText
    lateinit var btnlogin: Button
    lateinit var tvreg: TextView
    lateinit var tvreset: TextView
    lateinit var progress: ProgressDialog
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edtemail=findViewById(R.id.editTextTextEmailAddress)
        edtpwd=findViewById(R.id.editTextTextPassword)
        btnlogin=findViewById(R.id.buttonlogin)
        tvreg=findViewById(R.id.mTvreg)
        tvreset=findViewById(R.id.mtvreset)
        progress= ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait")

        mAuth = FirebaseAuth.getInstance()

        tvreg.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        btnlogin.setOnClickListener {
            var email= edtemail.text.toString().trim()
            var password= edtpwd.text.toString().trim()
            if (email.isEmpty()){
                edtemail.setError("Please fill this input")
                edtemail.requestFocus()
            }else if (password.isEmpty()){
                edtpwd.setError("Please fill this input")
                edtpwd.requestFocus()
            }
            progress.show()
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    progress.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }else{
                        displayMessage("ERROR", it.exception!!.message.toString())
                    }
                }
        }
        tvreset.setOnClickListener {

        }
    }
    fun displayMessage(title:String, message:String){
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Ok", null)
        alertDialog.create().show()
    }
}