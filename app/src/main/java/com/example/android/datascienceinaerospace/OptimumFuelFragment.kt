package com.example.android.datascienceinaerospace

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.fragment_optimum_fuel.*
import org.json.JSONObject

class OptimumFuelFragment : Fragment(R.layout.fragment_optimum_fuel) {

//    previously hosted on heroku
//    private val url = "https://op-fuel-app.herokuapp.com/predict"

//    now hosted on render
    private val url = "https://optimumfuelvk2.onrender.com/predict"

    private lateinit var arr : Array<String>; private lateinit var entry1 : Array<String>
    private lateinit var entry2 : Array<String>; private lateinit var entry3 : Array<String>
    private lateinit var entry4 : Array<String>; private lateinit var entry5 : Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Optimum Fuel"

        setupArrays()
        subscribeToClickable()
    }

    private fun subscribeToClickable(){

        btnPredict.setOnClickListener {
            pbOptimumFuelLoader?.visibility = View.VISIBLE
            tvOptimumFuel.text = ""

            val stringRequest = object : StringRequest(Method.POST, url,
                {
                    val jsonObject = JSONObject(it)
                    val optimumFuel = jsonObject.getString("optimum fuel ")
                    tvOptimumFuel?.text = optimumFuel
                    pbOptimumFuelLoader?.visibility = View.INVISIBLE
                }, {
                    tvOptimumFuel?.text = "Error"
                    pbOptimumFuelLoader?.visibility = View.INVISIBLE
                }){

                override fun getParams(): MutableMap<String, String> {
                    val map = HashMap<String, String>()
                    map["a1"] = etEmptyWeight.text.toString()
                    map["a2"] = etGrossWeight.text.toString()
                    map["a3"] = etLength.text.toString()
                    map["a4"] = etHeight.text.toString()
                    map["a5"] = etWingSpan.text.toString()
                    map["a6"] = etRange.text.toString()
                    map["a7"] = etGroundRun.text.toString()
                    map["a8"] = etServiceCeiling.text.toString()
                    return map
                }
            }

            context?.let { MySingleton.getInstance(it).addToRequestQueue(stringRequest) }
        }

        btnReset.setOnClickListener {
            resetData()
        }

        spDefaultEntryFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                when(position){
                    0 -> {resetData(); return}
                    1 -> {arr = entry1}
                    2 -> {arr = entry2}
                    3 -> {arr = entry3}
                    4 -> {arr = entry4}
                    5 -> {arr = entry5}
                }

                tvOptimumFuel.text = ""

                etGroundRun.setText(arr[7])
                etServiceCeiling.setText(arr[8])
                etEmptyWeight.setText(arr[1])
                etLength.setText(arr[3])
                etHeight.setText(arr[4])
                etWingSpan.setText(arr[5])
                etRange.setText(arr[6])
                etGrossWeight.setText(arr[2])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun resetData(){
        tvOptimumFuel.text = ""

        etGroundRun.setText("")
        etServiceCeiling.setText("")
        etEmptyWeight.setText("")
        etLength.setText("")
        etHeight.setText("")
        etWingSpan.setText("")
        etRange.setText("")
        etGrossWeight.setText("")

        spDefaultEntryFilter.setSelection(0)
    }

    private fun setupArrays(){
        arr = Array(9) { "" }
        entry1 = arrayOf("", "4275", "6315", "34.8", "12", "36.6", "868", "1950", "25000")
        entry2 = arrayOf("", "9955", "15000", "46.6", "14", "57.9", "1806", "3300", "35000")
        entry3 = arrayOf("", "6881", "12500", "51.7", "19.5", "65", "300", "860", "26700")
        entry4 = arrayOf("", "42600", "73900", "89.3", "25.2", "77.8", "4350", "5450", "45000")
        entry5 = arrayOf("", "11500", "20700", "52.2", "15.7", "44.6", "1450", "4600", "45000")
    }
}