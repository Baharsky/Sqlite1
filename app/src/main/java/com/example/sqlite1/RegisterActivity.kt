package com.example.sqlite1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlite1.R
import com.example.sqlite1.`object`.LogModelClass
import com.example.sqlite1.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //fungsi register
        btnRegister.setOnClickListener {
            val r_id = regId!!.text.toString()
            val r_email = regEmail!!.text.toString()
            val r_pass = regPassword!!.text.toString()
            val databaseHandler: DatabaseHandler = DatabaseHandler(this)

            if (r_email != "" && r_pass != ""){

                //email konfirmasi sudah dipakai atau belum
                    if (!databaseHandler!!.checkUser(r_email.trim())){

                        var user = LogModelClass(logEmail = r_email.trim(),
                                                logPassword = r_pass.trim())


                        databaseHandler!!.addUser(user)

                        //alert message
                        Toast.makeText(applicationContext,"Register Succes!", Toast.LENGTH_LONG).show()

                        val inte = Intent(this, LoginActivity::class.java)
                        startActivity(inte)

                    } else{
                        Toast.makeText(applicationContext,"Email already use!", Toast.LENGTH_LONG).show()
                    }
            } else{
                Toast.makeText(applicationContext,"Email and Password Must be Complete!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
