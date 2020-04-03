package com.navideous.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {

    var lastNumeric:Boolean=false
    var lastDot:Boolean=false
    var calculating:Boolean=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view:View){

        if (calculating) {
            input.append((view as Button).text)
            lastNumeric = true
        }else if (!calculating){
            input.text=""
            input.append((view as Button).text)
            lastNumeric = true
        }
    }

    fun onClear(view: View){

        input.text=""
        lastNumeric=false
        lastDot=false
    }

    fun onDecimalPoint(view: View){

        if (lastNumeric  && !lastDot){

            input.append(".")
            lastNumeric=false
            lastDot=true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(input.text.toString())) {
            input.append((view as Button).text)
            lastNumeric = false
            lastDot = false
            calculating=true
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var value = input.text.toString()
            var prefix = ""
            try {

                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1);
                }

                if (value.contains("/")) {
                    val splitedValue = value.split("/")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    input.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    calculating=false
                } else if (value.contains("*")) {
                    val splitedValue = value.split("*")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    input.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    calculating=false
                } else if (value.contains("-")) {

                    val splitedValue = value.split("-")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    input.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    calculating=false
                } else if (value.contains("+")) {

                    val splitedValue = value.split("+")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    
                    input.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    calculating=false
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    fun isOperatorAdded(value:String):Boolean{
        return if (value.startsWith("-")) {
            false
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+"))
        }
    }

    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}
