package com.navideous.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.round
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    var calculating: Boolean = true
    var onClrPressed = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        if (calculating) {
            input.append((view as Button).text)
            lastNumeric = true
            onClrPressed = false
        } else if (!calculating) {
            input.text = ""
            input.append((view as Button).text)
            lastNumeric = true
            calculating = true
        }
    }

    fun onClear(view: View) {
        onClrPressed = true
        input.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            input.append(".")
            lastNumeric = false
            lastDot = true
            calculating = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(input.text.toString())) {
            input.append((view as Button).text)
            lastNumeric = false
            lastDot = false
            calculating = true
            onClrPressed = false
        }

        if (onClrPressed && !lastNumeric && view.tag == "1") {
            input.append((view as Button).text)
            onClrPressed = false
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var value = input.text.toString()
            var prefix = ""
            try {

                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1)
                }

                if (value.contains("/")) {
                    val splitedValue = value.split("/")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    input.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (value.contains("*")) {
                    val splitedValue = value.split("*")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    input.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (value.contains("-")) {

                    val splitedValue = value.split("-")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    input.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (value.contains("+")) {

                    val splitedValue = value.split("+")

                    var one = splitedValue[0]
                    val two = splitedValue[1]

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    input.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                calculating = false
                lastDot = false
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        val tempString = value.substring(1, value.length)
        var tempBool = false

        if (tempString.contains("/")
            || tempString.contains("*")
            || tempString.contains("-")
            || tempString.contains("+")
        ) {
            tempBool = true
        }
        return tempBool
    }

    private fun removeZeroAfterDot(result: String): String {

        var value = result
        if (value.contains(".")) {
            if (value.substring(value.indexOf(".") + 1, value.length).toLong() > 0) {
                if (value.substring(value.indexOf(".") + 1, value.length).length > 3) {
                    value = result.substring(0, result.indexOf(".") + 5)

                } else {
                    value = result
                }
            } else {
                value = result.toDouble().roundToInt().toString()
            }
        }
        return value
    }
}
