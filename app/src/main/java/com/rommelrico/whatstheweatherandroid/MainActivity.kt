package com.rommelrico.whatstheweatherandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var editText: EditText? = null
    var resultTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getWeather(view: View) {
        // TODO: Implement me.
    }
}
