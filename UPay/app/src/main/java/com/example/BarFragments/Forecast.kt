package com.example.BarFragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Adapters.ForecastAdapter
import com.example.upay.ForecastParam
import com.example.upay.R
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import org.eazegraph.lib.charts.ValueLineChart
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import java.text.DecimalFormat
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
    private var itemsArrayList: ArrayList<ForecastParam>? = null
    var p: ArrayList<ForecastParam>? = null
    var recycler : RecyclerView? = null
    private var adapter: ForecastAdapter? = null
    var progressbar :ProgressBar? = null
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
        //
        progressbar = findViewById(R.id.progressBar5)
        progressbar?.visibility =View.VISIBLE

        //
        recycler = findViewById(R.id.recycler)
        recycler?.setLayoutManager(LinearLayoutManager(applicationContext))
        itemsArrayList = ArrayList<ForecastParam>()
        adapter = ForecastAdapter(applicationContext, itemsArrayList)
        recycler?.setAdapter(adapter)
        // methods;
        LRegression()
        //
//        imageButton?.setOnClickListener(View.OnClickListener {
//            val bottomSheet = CutBottomSheet()
//            bottomSheet.show(supportFragmentManager, "TAG")
//        })
    }

    fun LRegression() {
        Date = ArrayList()
        amount = ArrayList()
        Name = ArrayList()
        //
        user = FirebaseAuth.getInstance().currentUser
        FirebaseDatabase.getInstance().getReference("Users").child(user!!.uid).addListenerForSingleValueEvent(object : ValueEventListener {
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
                    Log.d("TestingNowDEA", month_of_year)
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
                        Log.d("TestingNOW", ""+m1);
                        Log.d("TestingNOW", ""+m9);
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

                val array_date: ArrayList<String> = arrayListOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
                        "AUG", "SEPT", "OCT", "NOV", "DEC")

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
                            series.addPoint(ValueLinePoint(array_date.get(i), AmountY[i]))
                        }
                    } catch (e: Exception) {
                        if (predictForValue(i, DateX, AmountY) >= 0) {
                            series.addPoint(ValueLinePoint(array_date.get(i), ("" + predictForValue(i, DateX, AmountY)).toFloat()))
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
                var Currency = dataSnapshot.child("Currency").child("Currency").value.toString()
                if (Currency == "$") {
                    textView!!.text = "$" +  DecimalFormat("##.##").format(predictForValue(month, DateX, AmountY))
                } else if (Currency == "€") {
                    val `val`: Double = 0.83 * predictForValue(month, DateX, AmountY)
                    textView!!.text = "€" + DecimalFormat("##.##").format(`val`)
                } else if (Currency == "\$CA") {
                    val `val`: Double = 1.23 * predictForValue(month, DateX, AmountY)
                    textView!!.text = "\$CA" + DecimalFormat("##.##").format(`val`)
                }
                // present to next month increase decrease;
                try {
                    val this_month = dataSnapshot.child("User Data").child("This Month").value.toString().toFloat()
                    if (this_month < predictForValue(month, DateX, AmountY)) {
                        val final_value = ("" + predictForValue(month, DateX, AmountY)).toFloat() - this_month
                        if (Currency == "$") {
                            textView2!!.text = "Spending is Likely \n to Increase \n by +$"+DecimalFormat("##.##").format(final_value)
                        } else if (Currency == "€") {
                            val `val`: Double = 0.83 * final_value
                            textView2!!.text = "Spending is Likely \n to Increase \n by +€" + DecimalFormat("##.##").format(`val`)
                        } else if (Currency == "\$CA") {
                            val `val`: Double = 1.23 * predictForValue(month, DateX, AmountY)
                            textView2!!.text = "Spending is Likely \n to Increase \n by +\$CA" + DecimalFormat("##.##").format(`val`)
                        }

                    } else if (this_month > predictForValue(month, DateX, AmountY)) {
                        val final_value = this_month - ("" + predictForValue(month, DateX, AmountY)).toFloat()
                        if (Currency == "$") {
                            textView2!!.text = "Spending is Likely \n to Decrease \n by $"+DecimalFormat("##.##").format(final_value)
                        } else if (Currency == "€") {
                            val `val`: Double = 0.83 * final_value
                            textView2!!.text = "Spending is Likely \n to Decrease \n by +€" + DecimalFormat("##.##").format(`val`)
                        } else if (Currency == "\$CA") {
                            val `val`: Double = 1.23 * predictForValue(month, DateX, AmountY)
                            textView2!!.text = "Spending is Likely \n to Decrease \n by +\$CA" + DecimalFormat("##.##").format(`val`)
                        }
                    }
                } catch (e: java.lang.Exception) {
                }
                mDatabase = FirebaseDatabase.getInstance().reference
                try {
                    // cut % algorithm;
                    val income = dataSnapshot.child("Income").child("income").value.toString()
                    var percent_value = 0.0f
                    var `val` = 0.0f
                    for (j in AmountY.indices) {
                        if (AmountY[j] != 0.0f) {
                            `val` += AmountY[j]
                            percent_value = `val` / income.toFloat() - ("" + predictForValue(month, DateX, AmountY) / income.toFloat()).toFloat()
                        }
                    }
                    textView3!!.text = """Lower spending by ${Math.round(percent_value * 100)}%"""

                    for (j in AmountY.indices) {
                        user = FirebaseAuth.getInstance().currentUser
                        val values = HashMap<String, Any>()
                        values["percent value"] = percent_value
                        mDatabase!!.child("Users").child(user!!.uid).child("Percent Val").updateChildren(values)
                    }

                } catch (e: Exception) {
                }

                val array_location: ArrayList<String> = arrayListOf();
                val array_amount: ArrayList<String> = arrayListOf();
                val array_Date: ArrayList<String> = arrayListOf();
                //
                var aaSeriesElement: AASeriesElement
                var aaSeries: ArrayList<AASeriesElement> = arrayListOf()

                var TheMethod: ArrayList<ArrayList<Float>> = arrayListOf()
                //
                var TheMethodName: ArrayList<String> = arrayListOf()
                //
                val Count = dataSnapshot.child("User Data").child("Transaction count").value.toString()
                for (i in 0 until Count.toInt()) {
                    array_location?.add(dataSnapshot.child("Transactions").child(i.toString()).child("Name").value.toString())
                    array_amount?.add(dataSnapshot.child("Transactions").child(i.toString()).child("Amount").value.toString())
                    array_Date?.add(dataSnapshot.child("Transactions").child(i.toString()).child("Date").value.toString())
                }

                for (j in 0 until Count.toInt()) {
                    for (i in j + 1 until Count.toInt()) {
                        if (array_location.get(j) == array_location.get(i)) {

                            if (TheMethodName.contains(array_location.get(j))) {
                                val c = TheMethodName.indexOf(array_location.get(j))
                                TheMethod.get(c).add(array_amount.get(i).toFloat())
                            } else {
                                var TheMethod2: ArrayList<Float> = arrayListOf()
                                TheMethodName.add(array_location.get(i))
                                TheMethod2.add(array_amount.get(i).toFloat())
                                TheMethod2.add(array_amount.get(j).toFloat())
                                TheMethod.add(TheMethod2)
                            }
                        } else {
                            if (TheMethodName.contains(array_location.get(j))) {
                                if (i < 1) {
                                    val c = TheMethodName.indexOf(array_location.get(j))
                                    TheMethod.get(c).add(array_amount.get(j).toFloat())
                                }
                            } else {
                                var TheMethod3: ArrayList<Float> = arrayListOf()
                                TheMethodName.add(array_location.get(j))
                                TheMethod3.add(array_amount.get(j).toFloat())
                                TheMethod.add(TheMethod3)
                            }
                        }
                    }
                }

                var TheMethodEXTRA: ArrayList<ArrayList<Float>> = arrayListOf()

                for (j in 0 until TheMethod.size) {
                    TheMethodEXTRA.add(TheMethod.get(j).distinct().toCollection(ArrayList()))
                }


                for (j in 0 until TheMethodName.size) {
                    aaSeriesElement = AASeriesElement().name(TheMethodName.get(j))
                            .data(TheMethodEXTRA.get(j).toTypedArray());
                    aaSeries.add(aaSeriesElement)
                }

                val aaChartModel: AAChartModel = AAChartModel()
                        .chartType(AAChartType.Spline)
                        .animationType(AAChartAnimationType.SwingTo)
                        .backgroundColor("#fafafa")
                        .dataLabelsEnabled(true)
                        .xAxisLabelsEnabled(false)
                        .series(aaSeries.toTypedArray())
                //
                progressbar?.visibility = View.INVISIBLE
                //
                aaChartView?.aa_drawChartWithChartModel(aaChartModel)
                // The Forecasting section;
                var size: ArrayList<Float> = arrayListOf()
                for (i in 0 until TheMethodName.size) {
                    size.add(TheMethodEXTRA.get(i).size.toFloat())
                }

                var Number: ArrayList<ArrayList<Int>> = arrayListOf()
                for (i in 0 until TheMethodName.size) {
                    var range: List<Int>? = null
                    range = IntStream.rangeClosed(1, size.get(i).toInt()).boxed().collect(Collectors.toList())
                    Number.add(range.toCollection(ArrayList()))
                }

                for (i in 0 until TheMethodName.size) {
                    var output = " Spending at " + TheMethodName.get(i) + "will increase by" +
                            predictForValue(size.get(i).toInt() + 1, Number.get(i), TheMethodEXTRA.get(i))
                }

                // TODO()
                val t3 = arrayOfNulls<Int>(2)
                t3[0] = R.drawable.ic_growth
                t3[1] = R.drawable.ic_loss
                //

                Log.d("IMAGEDEA",""+t3[0])
                p = ArrayList<ForecastParam>()
                for (i in 0 until TheMethodName.size) {
                    if (predictForValue(size.get(i).toInt() + 1, Number.get(i), TheMethodEXTRA.get(i)) > TheMethodEXTRA.get(i).maxOrNull()?.toDouble()!!) {
                        p?.add(ForecastParam(TheMethodName.get(i),
                                predictForValue(size.get(i).toInt() + 1, Number.get(i), TheMethodEXTRA.get(i)).toString(), t3[0]))
                        recycler?.adapter = ForecastAdapter(applicationContext, p)
                    } else {
                        p?.add(ForecastParam(TheMethodName.get(i),
                                predictForValue(size.get(i).toInt() + 1, Number.get(i), TheMethodEXTRA.get(i)).toString(), t3[1]))
                        recycler?.adapter = ForecastAdapter(applicationContext, p)
                    }

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