package com.example.sqlite1


import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.sqlite1.`object`.EmpModelClass
import com.example.sqlite1.helper.MyAdapter
import com.example.sqlite1.model.DatabaseHandler
import kotlinx.android.synthetic.main.activity_main.*
import android.content.DialogInterface
import kotlinx.android.synthetic.main.update_dialog.*
import java.sql.Types.NULL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //manmpilkan login sebagai
        val intentObject = intent
        val email = intentObject.getStringExtra("EMAIL")
        emailLoh.text = "Logined as $email"

        viewRecord()
    }

    //insert data
    fun saveRecord(view: View){
        val name = u_name.text.toString()
        val email = u_email.text.toString()
        val hobi = u_hobi.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        if(name.trim()!="" && email.trim()!=""){
            val status = databaseHandler.addEmployee(EmpModelClass(NULL, name, email, hobi))
            if(status > -1){
                Toast.makeText(applicationContext,"record save",Toast.LENGTH_LONG).show()
                u_name.text.clear()
                u_email.text.clear()
                u_hobi.text.clear()
                viewRecord()
            }
        }else{
            Toast.makeText(applicationContext,"id and email must complete",Toast.LENGTH_LONG).show()
        }

    }
    // fungsi untuk membaca data dari database dan menampilkannya dari listview
    fun viewRecord(){
        // membuat instanisasi databasehandler
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)

        // memamnggil fungsi viewemployee dari databsehandler untuk mengambil data
        val emp: List<EmpModelClass> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayEmail = Array<String>(emp.size){"null"}
        val empArrayHobi = Array<String>(emp.size){"null"}
        var index = 0

        // setiap data yang didapatkan dari database akan dimasukkan ke array
        for(e in emp){
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName
            empArrayEmail[index] = e.userEmail
            empArrayHobi[index] = e.userHobi
            index++
        }

        // membuat customadapter untuk view UI
        val myListAdapter = MyAdapter(this,empArrayId,empArrayName,empArrayEmail,empArrayHobi)
        listView.adapter = myListAdapter
    }

    // fungsi untuk memperbarui data sesuai id user
    fun updateRecord(view: View){
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val edtId = dialogView.findViewById(R.id.updateId) as EditText
        val edtName = dialogView.findViewById(R.id.updateName) as EditText
        val edtEmail = dialogView.findViewById(R.id.updateEmail) as EditText

        dialogBuilder.setTitle("Data Update")
        dialogBuilder.setMessage("Insert this table")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateId = edtId.text.toString()
            val updateName = edtName.text.toString()
            val updateEmail = edtEmail.text.toString()
            val updateKerjaan = edtEmail.text.toString()

            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            if(updateId.trim()!="" && updateName.trim()!="" && updateEmail.trim()!=""){

                val status = databaseHandler.updateEmployee(EmpModelClass(Integer.parseInt(updateId),updateName, updateEmail, updateKerjaan))
                if(status > -1){
                    Toast.makeText(applicationContext,"Data Updated",Toast.LENGTH_LONG).show()
                    viewRecord()
                }
            }else{
                Toast.makeText(applicationContext,"id and email must complete",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            // tidak melakukan apa2 :)
        })
        val b = dialogBuilder.create()
        b.show()
    }

    // fungsi untuk menghapus data berdasarkan id
    fun deleteRecord(view: View){
        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Delete data")
        dialogBuilder.setMessage("Enter id yang will delete")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

            val deleteId = dltId.text.toString()

            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
            if(deleteId.trim()!=""){

                val status = databaseHandler.deleteEmployee(EmpModelClass(Integer.parseInt(deleteId),"","", ""))
                if(status > -1){
                    Toast.makeText(applicationContext,"Delete Succes",Toast.LENGTH_LONG).show()
                    viewRecord()
                }
            }else{
                Toast.makeText(applicationContext,"Id Must Complete",Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            // tidak melakukan apa2 :)
        })
        val b = dialogBuilder.create()
        b.show()
    }
}
