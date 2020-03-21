package com.example.sqlite1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sqlite1.R
import com.example.sqlite1.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            val u_email = logEmail!!.text.toString()
            val u_pass = logPassword!!.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)
            if (u_email.trim() != "" && u_pass.trim() != ""){

                //validasi dan alert message

                if (databaseHandler!!.checkUser(u_email.trim { it <= ' ' })) {

                    if (databaseHandler!!.checkUser(u_email.trim { it <= ' ' },
                            u_pass.trim { it <= ' ' })) {
                        val inte = Intent(this, MainActivity::class.java)
                        inte.putExtra("EMAIL", u_email.trim { it <= ' ' })
                        logEmail.setText(null)
                        logPassword.setText(null)
                        startActivity(inte)
                    } else {
                        Toast.makeText(applicationContext, "Wrong Password", Toast.LENGTH_LONG).show()
                    }

                } else{
                    Toast.makeText(applicationContext, "Wrong Email", Toast.LENGTH_LONG).show()
                }

            } else{
                Toast.makeText(applicationContext,"Email and Password Must be Complete!", Toast.LENGTH_LONG).show()
            }


        }

        //onclik ke register
        btnToRegister.setOnClickListener {
            val inte = Intent(this, RegisterActivity::class.java)
            startActivity(inte)
        }
    }
}
