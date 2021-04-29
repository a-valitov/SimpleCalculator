package com.avalitov.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private lateinit var tvInput: TextView

    var lastNumeric : Boolean = false   // the last inputted character is a number
    var lastDot: Boolean = false        // the last inputted character is a dot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {

        if(tvInput.text.toString() == "0") {
            tvInput.text = ""
        }

        tvInput.append((view as Button).text)
        lastNumeric = true

    }

    fun onClear(view: View) {
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if(lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)  // throw away the 1st character in the string
                }

                // SUBTRACTION
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    val result : String = (one.toDouble() - two.toDouble()).toString()
                    tvInput.text = removeZerosAfterDot(result)
                }

                // ADDITION
                if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    val result : String = (one.toDouble() + two.toDouble()).toString()
                    tvInput.text = removeZerosAfterDot(result)
                }

                // MULTIPLICATION
                if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    val result : String = (one.toDouble() * two.toDouble()).toString()
                    tvInput.text = removeZerosAfterDot(result)
                }

                // DIVISION
                if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    val result : String = (one.toDouble() / two.toDouble()).toString()
                    tvInput.text = removeZerosAfterDot(result)
                }

                //TODO: when result with a dot is shown, forbid user to input another dot
                //i.e.   0.7 -> 0.7.7

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onBackspace(view: View) {

        var result = tvInput.text

        if (result.length == 0) {
            return
        }

        if (result.length == 1) {
            tvInput.text = "0"
            return
        }

        result = tvInput.text.dropLast(1)
        val last = result.last()
        if (last.equals(".")) {
            lastDot = true
            lastNumeric = false
        } else if (last.isDigit()) {
            lastNumeric = true
            lastDot = false
        }
        tvInput.text = result
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }

    private fun removeZerosAfterDot(result: String) : String {
        var value = result
        if(result.endsWith(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }

}