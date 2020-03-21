package com.example.sqlite1.helper

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.sqlite1.R

class MyAdapter(private val context: Activity, private val id: Array<String>,
                private val name: Array<String>, private val email: Array<String>, private val hobi: Array<String>)
    : ArrayAdapter<String>(context, R.layout.custom_list, name) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        //id isian data pada mainactivity
        val idText = rowView.findViewById(R.id.textViewId) as TextView
        val nameText = rowView.findViewById(R.id.textViewName) as TextView
        val emailText = rowView.findViewById(R.id.textViewEmail) as TextView
        val hobiText = rowView.findViewById(R.id.textViewHobi) as TextView

        //tampilan data
        idText.text = " ${id[position]}"
        nameText.text = "Name: ${name[position]}"
        emailText.text = "Email: ${email[position]}"
        hobiText.text = "Hobi: ${hobi[position]}"

        return rowView
    }
}