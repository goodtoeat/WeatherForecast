package com.example.weatherforecast.utils

import android.graphics.Color
import com.example.weatherforecast.dto.RiverData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

class MPChartHelp {

    class MyFillFormatter : IFillFormatter {
        override fun getFillLinePosition(dataSet: ILineDataSet, dataProvider: LineDataProvider): Float {
            return dataProvider.yChartMin
        }
    }

    fun getChartData(data: RiverData): List<MutableList<Entry>> {
        var chartData: MutableList<MutableList<Entry>> = mutableListOf()
        //初始化數據大小
        initEntity(data.data[0].河流圖資料[0].本益比股價基準.size, chartData)
        //預設只抓本益比資料
        data.data[0].河流圖資料.forEachIndexed{ index, 河流圖資料 ->
            河流圖資料.本益比股價基準.forEachIndexed{ indexInner, s ->
                chartData[indexInner].add(Entry((data.data[0].河流圖資料.size - index).toFloat(), s.toFloat()))
            }
        }
        chartData = chartData.reversed().toMutableList()

        val entryList = mutableListOf<Entry>()
        data.data[0].河流圖資料.forEachIndexed{ index, 河流圖資料 ->
            entryList.add(Entry((data.data[0].河流圖資料.size - index).toFloat(), 河流圖資料.月平均收盤價.toFloat()))
        }
        chartData.add(entryList)

        chartData.forEachIndexed { index, entries ->
            chartData[index] = entries.reversed().toMutableList()
        }
        return chartData
    }

    fun getLabels(data: RiverData): List<String> {
        val chartLabels: MutableList<String> = mutableListOf()
        data.data[0].河流圖資料.forEachIndexed { index, 河流圖資料 ->
            chartLabels.add(河流圖資料.年月)
        }
        return chartLabels
    }

    fun getLineData(data: RiverData, chartData: List<List<Entry>>): LineData {
        val dataSets = ArrayList<ILineDataSet>()
        chartData.forEachIndexed { index, _ ->

            val dataSet = if (index==6){
                LineDataSet(chartData[index], "股價")
            }else{
                LineDataSet(chartData[index], "${data.data[0].本益比基準[index]} 倍 ${chartData[index].last().y}")
            }
            dataSet.axisDependency = AxisDependency.RIGHT
            dataSet.color = getLineColor(index)
            dataSet.setCircleColor(Color.TRANSPARENT)
            dataSet.lineWidth = getLineWidth(index)
            dataSet.circleRadius = 0f
            dataSet.setDrawCircleHole(false)
            dataSet.isHighlightEnabled = true
            dataSet.highLightColor = Color.rgb(244, 117, 117)
            dataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER

            if (enableFill(index)) {
                dataSet.setDrawFilled(true) // 启用填充
                dataSet.fillFormatter = MyFillFormatter()
                dataSet.fillAlpha = getFillAlpha(index)
                dataSet.fillColor = getFillColor(index) // 设置填充颜色
            }else{
                dataSet.setDrawFilled(false)

            }
            dataSets.add(dataSet)
        }

        val lineData = LineData(dataSets)
        lineData.setValueTextColor(Color.WHITE)
        lineData.setValueTextSize(9f)
        return lineData
    }

    private fun initEntity(size: Int, chartData: MutableList<MutableList<Entry>>){
        repeat(size) {
            val entryList = mutableListOf<Entry>()
            chartData.add(entryList)
        }
    }

    fun initMPChart(chart: LineChart){

//        chart.setOnChartValueSelectedListener(this)

        // no description text
        chart.description.isEnabled = true

        // enable touch gestures
        chart.setTouchEnabled(false)

//        chart.dragDecelerationFrictionCoef = 0.9f

        // enable scaling and dragging
//        chart.isDragEnabled = true
//        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(false)
        chart.isHighlightPerDragEnabled = true
        chart.setHardwareAccelerationEnabled(true) // 启用硬件加速
        chart.setDrawBorders(false) // 禁用图表的边框

        // if disabled, scaling can be done on x- and y-axis separately
//        chart.setPinchZoom(true)

        // set an alternative background color
        chart.setBackgroundColor(Color.DKGRAY)

        chart.animateX(1500)

        // get the legend (only possible after setting data)
        val l = chart.legend

        // modify the legend ...
        l.form = LegendForm.LINE
        l.textSize = 11f
        l.textColor = Color.WHITE
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)

        // l.setYOffset(11f);
        val xAxis = chart.xAxis
        xAxis.textSize = 11f
        xAxis.textColor = Color.WHITE
        xAxis.setDrawGridLines(true)
        xAxis.setDrawAxisLine(false)

        val leftAxis = chart.axisLeft
        leftAxis.isEnabled = false
//        leftAxis.textColor = ColorTemplate.getHoloBlue()
//        leftAxis.axisMaximum = 1200f
//        leftAxis.axisMinimum = 0f
//        leftAxis.setDrawGridLines(true)
//        leftAxis.isGranularityEnabled = true

        val rightAxis = chart.axisRight
        rightAxis.textColor = Color.GRAY
//        rightAxis.axisMaximum = 900f
//        rightAxis.axisMinimum = -200f
//        rightAxis.setDrawGridLines(false)
//        rightAxis.setDrawZeroLine(false)
//        rightAxis.isGranularityEnabled = false
    }

    private fun getFillColor(position: Int): Int{
        return when (position){
            0 -> Color.parseColor("#FB7376")
            1 -> Color.parseColor("#FBA861")
            2 -> Color.parseColor("#FFD4AE")
            3 -> Color.parseColor("#BED8FF")
            4 -> Color.parseColor("#6287D6")
            5 -> Color.TRANSPARENT
            else -> Color.TRANSPARENT
        }
    }

    private fun getLineColor(position: Int): Int{
        return when (position){
            5 -> Color.parseColor("#247778")
            6 -> Color.RED
            else -> Color.TRANSPARENT
        }
    }

    private fun getLineWidth(position: Int): Float{
        return when (position){
            5 -> 5f
            6 -> 5f
            else -> 0f
        }
    }

    private fun enableFill(position: Int): Boolean{
        return when (position){
            5 -> false
            6 -> false
            else -> true
        }
    }

    private fun getFillAlpha(position: Int): Int{
        return when (position){
            5 -> 0
            6 -> 0
            else -> 255
        }
    }

}