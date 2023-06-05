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


class MainActivity : AppCompatActivity() {

    lateinit var edtemail:EditText
    lateinit var edtpwd:EditText
    lateinit var btnreg:Button
    lateinit var tvlogin:TextView
    lateinit var progress:ProgressDialog
    lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtemail=findViewById(R.id.editTextTextEmailAddress)
        edtpwd=findViewById(R.id.editTextTextPassword)
        btnreg=findViewById(R.id.buttonlogin)
        tvlogin=findViewById(R.id.mTvreg)
        progress= ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait")
        mAuth = FirebaseAuth.getInstance()

        btnreg.setOnClickListener {
            var email= edtemail.text.toString().trim()
            var password= edtpwd.text.toString().trim()
            if (email.isEmpty()){
                edtemail.setError("Please fill this input")
                edtemail.requestFocus()
            }else if (password.isEmpty()){
                edtpwd.setError("Please fill this input")
                edtpwd.requestFocus()
            } else if(password.length < 6) {
                edtpwd.setError("Password is too short")
                edtpwd.requestFocus()
            }else{
                progress.show()
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{
                        progress.dismiss()
                        if (it.isSuccessful){
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }else{
                            displayMessage("ERROR", it.exception!!.message.toString())
                        }
                    }
            }


        }
        tvlogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
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