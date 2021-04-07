package com.example.BarFragments

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.upay.R
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.eazegraph.lib.charts.ValueLineChart
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*
import java.util.stream.Collectors
import java.util.stream.IntStream
import kotlin.collections.ArrayList

class Forecast : AppCompatActivity() {
    private var user: FirebaseUser? = null
    var imageButton: ImageButton? = null
    var Date: ArrayList<String>? = null
    var amount: ArrayList<String>? = null
    var Name: ArrayList<String>? = null
    var mCubicValueLineChart: ValueLineChart? = null
    var textView: TextView? = null
    var textView2: TextView? = null
    var textView3: TextView? = null
    private var mDatabase: DatabaseReference? = null
    var aaChartView : AAChartView? = null
    //
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        if (Build.VERSION.SDK_INT >= 21) {
            this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        mCubicValueLineChart = findViewById<View>(R.id.cubiclinechart) as ValueLineChart
        aaChartView = findViewById<AAChartView>(R.id.aa_chart_view)
        textView = findViewById(R.id.textView20)
        textView2 = findViewById(R.id.textView27)
        textView3 = findViewById(R.id.textView28)
        imageButton = findViewById(R.id.imageButton11)
        // methods;
        LRegression()
        //
        imageButton?.setOnClickListener(View.OnClickListener {
            val bottomSheet = CutBottomSheet()
            bottomSheet.show(supportFragmentManager, "TAG")
        })
    }

    fun LRegression() {
        Date = ArrayList()
        amount = ArrayList()
        Name = ArrayList()
        //
        user = FirebaseAuth.getInstance().currentUser
        FirebaseDatabase.getInstance().getReference("Users").child(user!!.uid).addValueEventListener(object : ValueEventListener {
            @RequiresApi(api = Build.VERSION_CODES.O)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val x = dataSnapshot.child("User Data").child("Transaction count").value.toString()
                for (i in 0 until x.toInt()) {
                    Name!!.add(dataSnapshot.child("Transactions").child("" + i).child("Name").value.toString())
                    amount!!.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").value.toString())
                    Date!!.add(dataSnapshot.child("Transactions").child("" + i).child("Date").value.toString())
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
                    val date = Date!![i]
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
                            "1" -> m1 += amount!![i].toFloat()
                            "2" -> m2 += amount!![i].toFloat()
                            "3" -> m3 += amount!![i].toFloat()
                            "4" -> m4 += amount!![i].toFloat()
                            "5" -> m5 += amount!![i].toFloat()
                            "6" -> m6 += amount!![i].toFloat()
                            "7" -> m7 += amount!![i].toFloat()
                            "8" -> m8 += amount!![i].toFloat()
                            "9" -> m9 += amount!![i].toFloat()
                            "10" -> m10 += amount!![i].toFloat()
                            "11" -> m11 += amount!![i].toFloat()
                            "12" -> m12 += amount!![i].toFloat()
                        }
                    } catch (e: Exception) {
                    }
                }


                // setting data in first graph;
                val series = ValueLineSeries()
                series.color = -0xde690d
                series.addPoint(ValueLinePoint("", 0.0f))
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
                for (i in 0..11) {
                    try {
                        if (AmountY[i] != 0.0f) {
                            series.addPoint(ValueLinePoint("" + i, AmountY[i]))
                        }
                    } catch (e: Exception) {
                        if (predictForValue(i, DateX, AmountY) >= 0) {
                            series.addPoint(ValueLinePoint("" + i, ("" + predictForValue(i, DateX, AmountY)).toFloat()))
                        } else {
                        }
                    }
                }
                series.addPoint(ValueLinePoint("", 0.0f))
                mCubicValueLineChart!!.addSeries(series)
                mCubicValueLineChart!!.startAnimation()
                // CumData
                val date = Date()
                val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                val month = localDate.monthValue
                //
                textView!!.text = "$" + predictForValue(month, DateX, AmountY)
                // present to next month increase decrease;
                val this_month = dataSnapshot.child("User Data").child("This Month").value.toString().toFloat()
                if (this_month < predictForValue(month, DateX, AmountY)) {
                    val final_value = ("" + predictForValue(month, DateX, AmountY)).toFloat() - this_month
                    textView2!!.text = "Spending is Likely \n to Increase \n by +$$final_value"
                } else if (this_month > predictForValue(month, DateX, AmountY)) {
                    val final_value = this_month - ("" + predictForValue(month, DateX, AmountY)).toFloat()
                    textView2!!.text = "Spending is Likely \n to Decrease \n by $$final_value"
                }
                mDatabase = FirebaseDatabase.getInstance().reference
                try {
                    // cut % algorithm;
                    val income = dataSnapshot.child("User Data").child("income").value.toString()
                    var percent_value = 0.0f
                    var `val` = 0.0f
                    for (j in AmountY.indices) {
                        if (AmountY[j] != 0.0f) {
                            `val` += AmountY[j]
                            percent_value = `val` / income.toFloat() - ("" + predictForValue(month, DateX, AmountY) / income.toFloat()).toFloat()
                        }
                    }
                    textView3!!.text = """Future spending to be cut by ${Math.round(percent_value * 100)}%"""

                    for (j in AmountY.indices) {
                        user = FirebaseAuth.getInstance().currentUser
                        val values = HashMap<String, Any>()
                        values["percent value"] = percent_value
                        mDatabase!!.child("Users").child(user!!.uid).child("User Data").updateChildren(values)
                    }
                    //
                    // TEST NOT REALLLL;
                    val final_arr: Array<Any> = arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5)
                    val final_arr_2: Array<Any> = arrayOf(17.0, 16.9, 19.5, 4.5, 8.2, 1.5, 5.2)
                    val final_arr_3: Array<Any> = arrayOf(7.0, 16.9, 19.5, 40.5, 38.2, 13.5, 35.2)
                    //
                    val aaChartModel: AAChartModel = AAChartModel()
                            .chartType(AAChartType.Spline)
                            .animationType(AAChartAnimationType.EaseInExpo)
                            .subtitle("Places where you are likely to spend next Week ")
                            .backgroundColor("#fafafa")
                            .dataLabelsEnabled(true)
                            .xAxisLabelsEnabled(false)
                            .series(arrayOf(
                                    AASeriesElement()
                                            .name("Mc'Donalds")
                                            .color("#FFFACD")
                                            .data(final_arr),
                                    AASeriesElement()
                                            .name("Starbucks")
                                            .color("#008000")
                                            .data(final_arr_2),
                                    AASeriesElement()
                                            .name("Carrefour")
                                            .color("#000080")
                                            .data(final_arr_3))
                            )

                    aaChartView?.aa_drawChartWithChartModel(aaChartModel)

                } catch (e: Exception) {
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun cut(view: View?) {}

    // Red
    // Green
    // blue
    // generate random object
    val color: String
    // generate a red color code
    // generate green color code
    // color code generating blue

        // determine the number of bits Code Red
        // determine the number of bits of green code
        // code to determine the number of bits of blue
        // generate hexadecimal color values
        get() {
            // Red
            var red: String
            // Green
            var green: String
            // blue
            var blue: String
            // generate random object
            val random = Random()
            // generate a red color code
            red = Integer.toHexString(random.nextInt(256)).toUpperCase()
            // generate green color code
            green = Integer.toHexString(random.nextInt(256)).toUpperCase()
            // color code generating blue
            blue = Integer.toHexString(random.nextInt(256)).toUpperCase()

            // determine the number of bits Code Red
            red = if (red.length == 1) "0$red" else red
            // determine the number of bits of green code
            green = if (green.length == 1) "0$green" else green
            // code to determine the number of bits of blue
            blue = if (blue.length == 1) "0$blue" else blue
            // generate hexadecimal color values
            return "#$red$green$blue"
        }

    companion object {
        @RequiresApi(api = Build.VERSION_CODES.N)
        fun predictForValue(predictForDependentVariable: Int, xx: ArrayList<Int>, y: ArrayList<Float>): Double {
            check(xx.size == y.size) { "Must have equal X and Y data points" }
            val numberOfDataValues = xx.size
            val xSquared = xx
                    .stream()
                    .map { position: Int -> Math.pow(position.toDouble(), 2.0) }
                    .collect(Collectors.toList())
            val xMultipliedByY = IntStream.range(0, numberOfDataValues)
                    .map { i: Int -> (xx[i] * y[i]).toInt() }
                    .boxed()
                    .collect(Collectors.toList())
            val xSummed = xx
                    .stream()
                    .reduce { prev: Int, next: Int -> prev + next }
                    .get()
            val ySummed = y
                    .stream()
                    .reduce { prev: Float, next: Float -> prev + next }
                    .get()
            val sumOfXSquared = xSquared
                    .stream()
                    .reduce { prev: Double, next: Double -> prev + next }
                    .get()
            val sumOfXMultipliedByY = xMultipliedByY
                    .stream()
                    .reduce { prev: Int, next: Int -> prev + next }
                    .get()
            val slopeNominator = (numberOfDataValues * sumOfXMultipliedByY - ySummed * xSummed).toInt()
            val slopeDenominator = numberOfDataValues * sumOfXSquared - Math.pow(xSummed.toDouble(), 2.0)
            val slope = slopeNominator / slopeDenominator
            val interceptNominator = ySummed - slope * xSummed
            val interceptDenominator = numberOfDataValues.toDouble()
            val intercept = interceptNominator / interceptDenominator
            return java.lang.Double.valueOf(Math.round(slope * predictForDependentVariable + intercept).toDouble())
        }
    }
}