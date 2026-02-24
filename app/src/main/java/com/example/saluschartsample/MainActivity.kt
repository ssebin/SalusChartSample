package com.example.saluschartsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hdil.saluschart.core.chart.ChartMark
import com.hdil.saluschart.core.chart.InteractionType
import com.hdil.saluschart.core.chart.ProgressChartMark
import com.hdil.saluschart.core.chart.RangeChartMark
import com.hdil.saluschart.ui.compose.charts.PieChart
import com.hdil.saluschart.ui.compose.charts.ProgressChart
import com.hdil.saluschart.ui.compose.charts.RangeBarChart

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { App() }
    }
}

@Composable
private fun App() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            SampleHome()
        }
    }
}

private enum class Screen { MENU, PIE, PROGRESS, RANGE }

@Composable
private fun SampleHome() {
    var screen by remember { mutableStateOf(Screen.MENU) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        if (screen != Screen.MENU) {
            Text(
                text = "← Back",
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .clickable { screen = Screen.MENU }
            )
        }

        when (screen) {
            Screen.MENU -> {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    MenuItem("Pie / Donut Chart") { screen = Screen.PIE }
                    MenuItem("Progress Chart") { screen = Screen.PROGRESS }
                    MenuItem("Range Bar Chart") { screen = Screen.RANGE }
                }
            }
            Screen.PIE -> PieChartExample()
            Screen.PROGRESS -> ProgressChartExample()
            Screen.RANGE -> RangeBarChartExample()
        }
    }
}

@Composable
private fun MenuItem(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Text(title, modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun PieChartExample() {
    val data = listOf(
        ChartMark(x = 0.0, y = 30.0, label = "Mon"),
        ChartMark(x = 1.0, y = 20.0, label = "Tue"),
        ChartMark(x = 2.0, y = 25.0, label = "Wed"),
        ChartMark(x = 3.0, y = 15.0, label = "Thu"),
    )

    PieChart(
        modifier = Modifier.fillMaxWidth().height(260.dp),
        data = data,
        title = "Weekly activity",
        isDonut = true,
        colors = listOf(
            Color(0xFF7C4DFF),
            Color(0xFF26A69A),
            Color(0xFFFF9800),
            Color(0xFFFFEB3B),
        ),
        showLegend = true,
        showLabel = true,
        interactionsEnabled = true
    )
}

@Composable
private fun ProgressChartExample() {
    val data = listOf(
        ProgressChartMark(x = 0.0, current = 420.0, max = 600.0, label = "Move", unit = "kcal"),
        ProgressChartMark(x = 1.0, current = 28.0, max = 45.0, label = "Exercise", unit = "min"),
        ProgressChartMark(x = 2.0, current = 8.0, max = 12.0, label = "Stand", unit = "hr"),
    )

    ProgressChart(
        modifier = Modifier.fillMaxWidth(),
        data = data,
        title = "Daily progress",
        isDonut = true,        // try false too
        isPercentage = true,
        colors = listOf(
            Color(0xFF00C7BE),
            Color(0xFFFF6B35),
            Color(0xFF3A86FF),
        ),
        strokeWidth = 20.dp,
        showLegend = true,
        interactionsEnabled = true,
        tooltipEnabled = true
    )
}

@Composable
private fun RangeBarChartExample() {
    val rangeMarks = listOf(
        RangeChartMark(
            x = 0.0,
            minPoint = ChartMark(x = 0.0, y = 55.0, label = "Day 1"),
            maxPoint = ChartMark(x = 0.0, y = 150.0, label = "Day 1"),
            label = "Day 1"
        ),
        RangeChartMark(
            x = 1.0,
            minPoint = ChartMark(x = 1.0, y = 54.0, label = "Day 2"),
            maxPoint = ChartMark(x = 1.0, y = 160.0, label = "Day 2"),
            label = "Day 2"
        ),
        RangeChartMark(
            x = 2.0,
            minPoint = ChartMark(x = 2.0, y = 65.0, label = "Day 3"),
            maxPoint = ChartMark(x = 2.0, y = 145.0, label = "Day 3"),
            label = "Day 3"
        ),
        RangeChartMark(
            x = 3.0,
            minPoint = ChartMark(x = 3.0, y = 58.0, label = "Day 4"),
            maxPoint = ChartMark(x = 3.0, y = 125.0, label = "Day 4"),
            label = "Day 4"
        ),
        RangeChartMark(
            x = 4.0,
            minPoint = ChartMark(x = 4.0, y = 70.0, label = "Day 5"),
            maxPoint = ChartMark(x = 4.0, y = 140.0, label = "Day 5"),
            label = "Day 5"
        )
    )

    RangeBarChart(
        modifier = Modifier.fillMaxWidth().height(520.dp),
        data = rangeMarks,
        title = "Daily heart rate range",
        yLabel = "bpm",
        xLabel = "Day",
        barWidthRatio = 0.8f,
        barColor = Color(0xFFE91E63),
        interactionType = InteractionType.RangeBar.TOUCH_AREA,
        unit = "bpm",
        // try paging or scrolling:
        // pageSize = 3,
        // windowSize = 3,
    )
}