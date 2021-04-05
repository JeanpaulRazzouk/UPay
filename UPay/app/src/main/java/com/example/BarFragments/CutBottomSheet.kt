package com.example.BarFragments

import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.upay.R
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CutBottomSheet : BottomSheetDialogFragment() {
    private var user: FirebaseUser? = null
    var Date = ArrayList<String>()
    var amount = ArrayList<String>()
    var Name = ArrayList<String>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view1 = inflater.inflate(R.layout.cut_bottom_sheet, container, false)
        //
        val aaChartView = view1.findViewById<AAChartView>(R.id.aa_chart_view)
        //
        user = FirebaseAuth.getInstance().currentUser
        FirebaseDatabase.getInstance().getReference("Users").child(user!!.uid).addValueEventListener(object : ValueEventListener {
            @RequiresApi(api = Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val x = dataSnapshot.child("User Data").child("Transaction count").value.toString()
                for (i in 0 until x.toInt()) {
                    Name.add(dataSnapshot.child("Transactions").child("" + i).child("Name").value.toString())
                    amount.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").value.toString())
                    Date.add(dataSnapshot.child("Transactions").child("" + i).child("Date").value.toString())
                }
                var m1 = 0.0f
                var m2 = 0.0f
                var m3 = 0.0f
                var m4 = 0.0f
                var m5 = 0.0f
                var m6 = 0.0f
                var m7 = 0.0f
                var m8 = 0.0f
                var m9 = 0.0f
                var m10 = 0.0f
                var m11 = 0.0f
                var m12 = 0.0f
                //int month_num;
                for (i in 0 until x.toInt()) {
                    val date = Date[i]
                    val dateParts = date.split("/").toTypedArray()
                    val day = dateParts[0]
                    val month = dateParts[1]
                    val year = dateParts[2]

                    // First convert to Date. This is one of the many ways.
                    val dateString = String.format("%s-%s-%s", year, month, day)
                    var d: Date? = null
                    try {
                        d = SimpleDateFormat("yyyy-MM-dd").parse(dateString)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                    // Then get the month of the year from the Date based on specific locale.
                    val month_of_year = SimpleDateFormat("M", Locale.ENGLISH).format(d)
                    try {
                        when (month_of_year) {
                            "1" -> m1 += amount[i].toFloat()
                            "2" -> m2 += amount[i].toFloat()
                            "3" -> m3 += amount[i].toFloat()
                            "4" -> m4 += amount[i].toFloat()
                            "5" -> m5 += amount[i].toFloat()
                            "6" -> m6 += amount[i].toFloat()
                            "7" -> m7 += amount[i].toFloat()
                            "8" -> m8 += amount[i].toFloat()
                            "9" -> m9 += amount[i].toFloat()
                            "10" -> m10 += amount[i].toFloat()
                            "11" -> m11 += amount[i].toFloat()
                            "12" -> m12 += amount[i].toFloat()
                        }
                    } catch (e: Exception) {
                    }
                }


                // setting data in first graph;
                val DateX = ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12))
                val AmountY = ArrayList<Float>()
                AmountY.add(m1)
                AmountY.add(m2)
                AmountY.add(m3)
                AmountY.add(m4)
                AmountY.add(m5)
                AmountY.add(m6)
                AmountY.add(m7)
                AmountY.add(m8)
                AmountY.add(m9)
                AmountY.add(m10)
                AmountY.add(m11)
                AmountY.add(m12)
                //
                AmountY.removeAll(setOf(0.0f))
                //
                for (i in 0..11) {
                    try {
                        if (AmountY[i] != 0.0f) {
                        }
                    } catch (e: Exception) {
                        DateX[i] = 0
                    }
                }
                DateX.removeAll(setOf(0))
                // continuity of the algorithm;

                val percent_value = dataSnapshot.child("User Data").child("percent value").value.toString().toFloat()
                try {
                    val final_arr: Array<Any> = arrayOf(
                            Forecast.predictForValue(0, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(0, DateX, AmountY).toInt(),
                            Forecast.predictForValue(1, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(1, DateX, AmountY).toInt(),
                            Forecast.predictForValue(2, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(2, DateX, AmountY).toInt(),
                            Forecast.predictForValue(3, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(3, DateX, AmountY).toInt(),
                            Forecast.predictForValue(4, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(4, DateX, AmountY).toInt(),
                            Forecast.predictForValue(5, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(5, DateX, AmountY).toInt(),
                            Forecast.predictForValue(6, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(6, DateX, AmountY).toInt(),
                            Forecast.predictForValue(7, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(7, DateX, AmountY).toInt(),
                            Forecast.predictForValue(8, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(8, DateX, AmountY).toInt(),
                            Forecast.predictForValue(9, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(9, DateX, AmountY).toInt(),
                            Forecast.predictForValue(10, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(10, DateX, AmountY).toInt(),
                            Forecast.predictForValue(11, DateX, AmountY).toInt() - percent_value * Forecast.predictForValue(11, DateX, AmountY).toInt()
                    )

                    val perc_for_view = percent_value * 100
                    val aaChartModel: AAChartModel = AAChartModel()
                            .chartType(AAChartType.Bubble)
                            .animationType(AAChartAnimationType.EaseInExpo)
                            .title("Recommended Spending Cut")
                            .subtitle("Based on your income/spending we recommend to cut spending by $perc_for_view % ")
                            .backgroundColor("#ffffff")
                            .dataLabelsEnabled(true)
                            .xAxisLabelsEnabled(false)
                            .series(arrayOf(
                                    AASeriesElement()
                                            .name("$ Amount")
                                            .color("#2196F3")
                                            .data(final_arr))
                            )
                    aaChartView.aa_drawChartWithChartModel(aaChartModel)
                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
        return view1
  }
}