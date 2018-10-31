package com.example.charts

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mMonths = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    //private var mLineColor = Color.rgb(0, 114, 114)
    private var mLineColor = Color.rgb(7, 48, 132)
    private lateinit var graphPoints: List<GraphPointModel>
    private var selectedPeriod: Period = Period.ONE_YEAR
    private val xLimitLinePoints: HashMap<Float, String> = HashMap()
    private val yLimitLinePoints: HashMap<Float, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        radio_group_buttons.clearCheck()
        setGraphData(Period.ONE_MONTH)

        radio_group_buttons.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {

                R.id.radio_button_1month -> {
                    selectedPeriod = Period.ONE_MONTH
                    xLimitLinePoints.clear()
                    yLimitLinePoints.clear()
                    setGraphData(selectedPeriod)
                }
                R.id.radio_button_1Year -> {
                    selectedPeriod = Period.ONE_YEAR
                    xLimitLinePoints.clear()
                    yLimitLinePoints.clear()
                    setGraphData(selectedPeriod)
                }
                R.id.radio_button_3year -> {
                    selectedPeriod = Period.THREE_YEARS
                    xLimitLinePoints.clear()
                    yLimitLinePoints.clear()
                    setGraphData(selectedPeriod)
                }
                else -> {// 5 years
                    selectedPeriod = Period.FIVE_YEARS
                    xLimitLinePoints.clear()
                    yLimitLinePoints.clear()
                    setGraphData(selectedPeriod)
                }
            }
        }
    }

    private fun setGraphData(period: Period) {

        chartProgress.visibility = View.VISIBLE
        disableRadioButtons()

        // Getting the Graph data

        graphPoints = DBHelper.getDaysFromJson(this, "temperature_logger_response.json")
        Log.i("MasterBlaster", "graphPoints received :- ${graphPoints}")

        graphPoints.let {

            setGraphXYValues()
        }
    }


    private fun setGraphXYValues() {

        chartProgress.visibility = View.GONE
        enableRadioButtons()

        // It will hold the XY coordinates of the points
        var xyValues: ArrayList<Entry> = ArrayList()
        var startDate: Date? = null
        var startYear: LocalDate.Property? = null
        var startPoint = 0F


        // ByDefault Period is One Year, so filtering the data
        for (graphPointModel: GraphPointModel in graphPoints) {


            //var dateformat : DateFormat = SimpleDateFormat("dd.mm.yyyy", Locale.US)
            //var date : Date = dateformat.parse(graphPointModel.date)


            /*var dateFormat1  = Date
            var dateFormat  = DateTimeFormat.forPattern("dd.mm.yyyy")
            var date = dateFormat.parseDateTime(graphPointModel.date)

            var date: LocalDate = LocalDate.parse(graphPointModel.date)
            Log.i("MasterBlaster", " Converted date :-> ${date}")

            if (selectedPeriod == Period.ONE_YEAR) {

                if (startDate == null) {
                    startDate = date
                    startYear = date.year()
                }

                if (startYear == date.year()) {

                    var entry = Entry(startPoint,graphPointModel.temperature.toFloat())
                    xyValues?.add(entry)
                    //Increasing X value
                    startPoint ++
                }else{

                    Log.i("MasterBlaster","Skipping the date :- $graphPointModel}")
                }
            }*/


            // Putting the xLimitLinePoints for 1 Year only
            try {

                var dateString = graphPointModel.date
                var dateFormat = SimpleDateFormat("dd.mm.yyyy")
                var date = dateFormat.parse(dateString)
                var dateStringNew = dateFormat.format(date)
                var dateArray = dateStringNew.split(".")
                Log.i("MasterBlaster", "dateArray :- ${dateArray}")

                when (selectedPeriod) {


                    Period.ONE_MONTH -> Log.i("MasterBlaster", "Skipping xlimitline record")


                    else -> // One /Three / Five years
                    {

                        if ((mMonths[dateArray[1].toInt() - 1]) == "Jan") {

                            xLimitLinePoints.put(startPoint, dateArray[2])

                        }
                    }

                }

            } catch (e: Exception) {
                Log.i("MasterBlaster", "Exception occured -> ${e}")
            }


            var entry = Entry(startPoint, graphPointModel.temperature.toFloat())
            xyValues.add(entry)
            Log.i("MasterBlaster", " entries :-> $entry")
            startPoint++

        }
        Log.i("MasterBlaster", " Final entries :-> $xyValues")


        var linechartDataset = LineDataSet(xyValues, "Label")
        linechartDataset.axisDependency = YAxis.AxisDependency.LEFT
        linechartDataset.color = mLineColor
        linechartDataset.setDrawFilled(true)
        var drawable = ContextCompat.getDrawable(this, R.drawable.gradient_graph_color)
        linechartDataset.fillDrawable = drawable
        linechartDataset.setDrawCircles(false)
        linechartDataset.mode = LineDataSet.Mode.CUBIC_BEZIER
        linechartDataset.lineWidth = 1.5f
        linechartDataset.isHighlightEnabled = false

        var iLineDataset: ArrayList<ILineDataSet> = ArrayList()
        iLineDataset.add(linechartDataset)

        var lineData = LineData(iLineDataset)
        lineData.setDrawValues(false)


        /*// Getting Font

        var appFont: Typeface? = null
        if (isAdded()) {                          // For fragment
           var appFont = Typeface.createFromAsset(this.getAssets(), "fonts/FedraSansAltPro-Light.otf")
        }*/

        //  Setting bottom X Axis

        var xAxis = linechart.xAxis
        xAxis.isEnabled = true
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        //xAxis.gridLineWidth --> kept by default
        xAxis.gridColor = ContextCompat.getColor(this, R.color.graph_xAxis_grid_line_color)
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.setDrawAxisLine(true)
        //xAxis.axisLineWidth --> kept by default
        xAxis.axisLineColor = ContextCompat.getColor(this, R.color.graph_axis_color)
        xAxis.setDrawLabels(true)
        //var appFont = Typeface.createFromAsset(this.getAssets(), "fonts/fedrasansaltpro_light.otf")
        //xAxis.typeface = appFont
        xAxis.textColor = R.color.graph_axis_text_color
        xAxis.textSize = 9f

        xAxis.setValueFormatter { value, axis -> xAxisValueFormatter(value, axis) }    // Formatting the X Axis Values


        // Setting X axis Limit Lines i.e. any warning / cut off  suggestion data on X axis
        xAxis.removeAllLimitLines()
        drawXLimitLines(xAxis, xLimitLinePoints)


        // Setting Left-Y Axis

        var yAxisLeft = linechart.axisLeft
        yAxisLeft.isEnabled = true
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)

        // To avoid overlap on X axis when Y axis label count is more than 5.
        yAxisLeft.labelCount = 5
        yAxisLeft.yOffset = 0f

        //Setting offset when count is > 5
        if (yAxisLeft.labelCount > 5) {
            yAxisLeft.yOffset = Utils.convertDpToPixel(-2f)
        }

        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.axisLineColor = ContextCompat.getColor(this, R.color.graph_axis_color)
        //yAxisLeft.typeface = appFont

        yAxisLeft.setValueFormatter { value, axis -> yAxisValueFormatter(value, axis) }     // Formatting Y Axis Values


        //Setting Y axis Limit Lines i.e. any warning / cut off  suggestion data on Y axis
        yLimitLinePoints.put(40f, "Cutoff")
        drawYLimitLines(yAxisLeft, yLimitLinePoints)

        // Setting Right-Y Axis

        var yAxisRight = linechart.axisRight
        yAxisRight.isEnabled = false
        yAxisRight.setDrawGridLines(false)
        yAxisRight.setDrawAxisLine(false)
        yAxisRight.setDrawLabels(false)


        // Setting Line chart

        linechart.xAxis.axisMinimum = 0f
        linechart.data = lineData
        linechart.notifyDataSetChanged()

        linechart.setBackgroundColor(Color.TRANSPARENT)
        linechart.setDrawGridBackground(false)
        linechart.setDrawBorders(false)
        var description = Description()
        description.text = ""
        linechart.description = description
        linechart.setScaleEnabled(false)
        linechart.setTouchEnabled(false)
        linechart.isDragEnabled = false
        linechart.setPinchZoom(false)
        linechart.isDoubleTapToZoomEnabled = false

        linechart.legend.isEnabled = false
        linechart.axisRight.isEnabled = true
        linechart.animateXY(1000, 0)
        //Easing (accelerate/de accelerate options)
        //linechart.animateX(1000,Easing.EasingOption.EaseInOutBack)

        //Reset the view port before setting to new one
        linechart.resetViewPortOffsets()

        //To set padding <left, top, right, bottom>
        linechart.setViewPortOffsets(Utils.convertDpToPixel(4f), Utils.convertDpToPixel(4f), Utils.convertDpToPixel(4f), Utils.convertDpToPixel(14f))

        //Setting Legend

        var legend: Legend = linechart.legend
        legend.isEnabled = true
        legend.form = Legend.LegendForm.SQUARE
        legend.textSize = 9f
        legend.textColor = Color.RED
        //legend.typeface = appFont
        legend.isWordWrapEnabled = true
        legend.maxSizePercent = 0.9f    // i.e. 90% , default is 0.95f (95%)
        legend.position = Legend.LegendPosition.BELOW_CHART_LEFT
        legend.xEntrySpace = 7f
        legend.yEntrySpace = 7f
        legend.xOffset = 14f
        legend.yOffset = 0f

        //We can set the Marker //To be implemented

        linechart.invalidate()
    }


    private fun xAxisValueFormatter(value: Float, axis: AxisBase): String {

        if (graphPoints != null && value < graphPoints.size) {

            if (value > axis.axisMaximum || value < 0) {
                return ""
            }

            try {

                var dateString = graphPoints[value.toInt()].date
                var dateFormat = SimpleDateFormat("dd.mm.yyyy")
                var date = dateFormat.parse(dateString)
                var dateStringNew = dateFormat.format(date)
                var dateArray = dateStringNew.split(".")
                Log.i("MasterBlaster", "dateArray :- ${dateArray}")

                return when (selectedPeriod) {

                    Period.ONE_MONTH -> dateArray[0]

                    Period.ONE_YEAR -> mMonths[dateArray[1].toInt() - 1]

                    else -> // Three / Five years
                        dateArray[2]
                }

            } catch (e: Exception) {
                Log.i("MasterBlaster", "Exception occured -> ${e}")
            }

        }
        return ""
    }

    private fun yAxisValueFormatter(value: Float, axis: AxisBase): String {

        if (value <= 80) {

            return (value.toInt().toString() + "d")
        }

        //
        if (value > 80 && value <= 999) {

            return ((value / 100).toInt().toString() + "h")
        }

        //if(value >= 1000 && value <= 99999){
        if (value in 1000..99999) {

            return ((value / 1000).toInt().toString() + "K")
        }

        return ""
    }


    private fun drawXLimitLines(x_axis: XAxis, xLimitLinePoints: HashMap<Float, String>) {

        for (limitLine in xLimitLinePoints.keys) {

            Log.i("MasterBlaster", "LimitValue :- ${limitLine} ; LimitDescription :- ${xLimitLinePoints[limitLine]} ")
            var xLimitLine: LimitLine = LimitLine(limitLine, xLimitLinePoints[limitLine])
            xLimitLine.lineColor = Color.RED
            xLimitLine.lineWidth = 0.25f
            xLimitLine.textColor = R.color.graph_axis_text_color
            xLimitLine.textSize = 9f

            x_axis.addLimitLine(xLimitLine)
            x_axis.setDrawLimitLinesBehindData(true)
        }
    }

    private fun drawYLimitLines(y_axis: YAxis, yLimitLinePoints: HashMap<Float, String>) {

        for (limitline in yLimitLinePoints.keys) {

            Log.i("MasterBlaster", "LimitValue :- ${limitline} ; LimitDescription :- ${yLimitLinePoints[limitline]} ")
            var yLimitLine: LimitLine = LimitLine(limitline, yLimitLinePoints[limitline])
            yLimitLine.lineColor = Color.RED
            yLimitLine.lineWidth = 0.25f
            yLimitLine.textColor = R.color.graph_axis_text_color
            yLimitLine.textSize = 9f

            y_axis.addLimitLine(yLimitLine)
            y_axis.setDrawLimitLinesBehindData(true)
        }

    }

    private fun disableRadioButtons() {

        radio_group_buttons?.let {
            for (radioButton in 0 until radio_group_buttons.childCount) {

                radio_group_buttons.getChildAt(radioButton).isEnabled = false
            }
        }
    }

    private fun enableRadioButtons() {

        radio_group_buttons?.let {
            for (radioButton in 0 until radio_group_buttons.childCount) {

                radio_group_buttons.getChildAt(radioButton).isEnabled = true
            }
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
