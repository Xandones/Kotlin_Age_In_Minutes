package com.cursokotlin.ageinminutescalculator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    // This app requires API 24
    // tvSelectedDate refers to the date of birth informed by the user
    private var tvSelectedDate : TextView? = null
    // tvAgeInMinutes refers to the resulting value of minutes that the user already lived
    private var tvAgeInMinutes : TextView? = null

    @RequiresApi(Build.VERSION_CODES.N)
    // Function executed right on the creation of the object
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This line is used to connect to the XML file named activity_main.xml
        setContentView(R.layout.activity_main)

        // The following lines are related to the button and its connection to the visual elements defined in the XML file mentioned.
        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        // This is the resulting value informed through the clickDatePicker function
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        // This is the result of the age in minutes
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        // Here the function clickDatePicker is called
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    // The function clickDatePicker not only captures the date of birth of the user but it also calculates the age of the user in minutes.
    private fun clickDatePicker()
    {
        val myCalendar = Calendar.getInstance() // Creates an object of the class Calendar
        val year = myCalendar.get(Calendar.YEAR) // Defines the attribute year of the object myCalendar
        val month = myCalendar.get(Calendar.MONTH) // Defines the attribute month of the object myCalendar
        val day = myCalendar.get(Calendar.DAY_OF_MONTH) // Defines the attribute day of the month of the object myCalendar
        // the value DPD is an instance of a DatePickerDialog where all the date elements are used
        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                // This element shows a message showing the date of birth selected by the user. The month is added to one to show the correct month.
                Toast.makeText(this,"Year was $selectedYear, month was ${selectedMonth + 1} and day was $selectedDayOfMonth", Toast.LENGTH_LONG).show()

                // The remaining lines will calculate the age in minutes and also convert this value in a String to show it in the screen.git
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate?.text = selectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                    }

                }
            },
            year,
            month,
            day
            )

            dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
            dpd.show()
    }
}