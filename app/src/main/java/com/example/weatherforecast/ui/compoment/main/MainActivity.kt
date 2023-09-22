package com.example.weatherforecast.ui.compoment.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.data.Resource
import com.example.weatherforecast.dto.RiverData
import com.example.weatherforecast.ui.base.BaseActivity
import com.example.weatherforecast.utils.MPChartHelp
import com.example.weatherforecast.utils.SingleEvent
import com.example.weatherforecast.utils.observe
import com.example.weatherforecast.utils.setupSnackbar
import com.example.weatherforecast.utils.showToast
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mpChartHelp: MPChartHelp

    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mpChartHelp.initMPChart(binding.chart)
        viewModel.getRiverRawData()
    }

    override fun observeViewModel() {
        observe(viewModel.riverData, ::drawMPChart)
        observeToast(viewModel.showToast)
        observeSnackBarMessages(viewModel.showSnackBar)
    }

    private fun drawMPChart(states: Resource<RiverData>){
        when (states){
            is Resource.Success -> {
                var chartData: List<List<Entry>> = mpChartHelp.getChartData(states.data!!)
                var chartLabels: List<String> = mpChartHelp.getLabels(states.data!!)
                var lineData: LineData = mpChartHelp.getLineData(states.data!!, chartData)

                val xAxis = binding.chart.xAxis
                xAxis.valueFormatter = IndexAxisValueFormatter(chartLabels)

                if (binding.chart.data != null && binding.chart.data.dataSetCount > 0){
                    binding.chart.data = lineData
                    binding.chart.data.notifyDataChanged()
                    binding.chart.notifyDataSetChanged()
                }else{
                    binding.chart.data = lineData
                }
            }
            is Resource.DataError -> {
                states.errorCode?.let { viewModel.showSnackBarMessage(it) }
            }

            else -> {}
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }
}