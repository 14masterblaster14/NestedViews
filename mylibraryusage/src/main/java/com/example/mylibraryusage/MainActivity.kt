package com.example.mylibraryusage

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.example.mylibrary.ValidaTor

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

//https://www.raywenderlich.com/185299/building-android-library-tutorial


class MainActivity : AppCompatActivity() {

    private lateinit var validaTor: ValidaTor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        setUpUiWidgets()

        validaTor = ValidaTor()
    }


    private fun setUpUiWidgets() {
        btn_validate.setOnClickListener {
            validateEmailField(edt_email as EditText)
            validatePasswordField(edt_password as EditText)
            validateCreditCardField(edt_creditcard as EditText)
        }
    }

    private fun validateCreditCardField(editText: EditText) {
        val str = editText.text.toString()

        if (validaTor.isEmpty(str)) {
            editText.error = "Field is empty!"
        }

        if (!validaTor.validateCreditCard(str)) {
            editText.error = "Invalid Credit Card number!"
        } else {

            Toast.makeText(this, "Valid Credit Card Number!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validatePasswordField(editText: EditText) {
        val str = editText.text.toString()

        if (validaTor.isEmpty(str)) {
            editText.error = "Field is empty!"
        }

        if (validaTor.isAtleastLength(str, 8)
                && validaTor.hasAtleastOneDigit(str)
                && validaTor.hasAtleastOneUppercaseCharacter(str)
                && validaTor.hasAtleastOneSpecialCharacter(str)) {
            Toast.makeText(this, "Valid Password!", Toast.LENGTH_SHORT).show()
        } else {
            editText.error = "Password needs to be of minimum length of 8 characters and should " +
                    "have " + "atleast 1 digit, 1 upppercase letter and 1 special character "
        }
    }

    private fun validateEmailField(editText: EditText) {
        val str = editText.text.toString()

        if (validaTor.isEmpty(str)) {
            editText.error = "Field is empty!"
        }

        if (!validaTor.isEmail(str)) {
            editText.error = "Invalid Email entered!"
        } else {
            Toast.makeText(this, "Valid Email!", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
