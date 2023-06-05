package com.example.nyumbakumiapp

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.system.exitProcess

class HomeActivity : AppCompatActivity() {
    lateinit var logouticon: ImageView
    lateinit var progress: ProgressDialog

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        logouticon=findViewById(R.id.logouticon)
        progress= ProgressDialog(this)
        progress.setTitle("Logging Out")
        progress.setMessage("Please wait")

        logouticon.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Sure to Log Out")
            alertDialog.setMessage("Log out?")
            alertDialog.setNegativeButton("No", null)
            alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                startActivity(Intent(this, LoginActivity::class.java))
                finish()

            })
            alertDialog.create().show()
            progress.show()

        }
    }
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime > 2000) {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
            backPressedTime = currentTime
        } else {
            super.onBackPressed()
        }
    }
}