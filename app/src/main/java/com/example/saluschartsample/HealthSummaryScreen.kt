package com.example.saluschartsample

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hdil.saluschart.core.chart.ChartMark
import com.hdil.saluschart.core.chart.ProgressChartMark
import com.hdil.saluschart.core.chart.ReferenceLineSpec
import com.hdil.saluschart.core.chart.chartDraw.LineStyle
import com.hdil.saluschart.core.chart.chartDraw.ReferenceLineType
import com.hdil.saluschart.ui.compose.charts.MiniActivityRings
import com.hdil.saluschart.ui.compose.charts.MinimalBarChart
import com.hdil.saluschart.ui.compose.charts.MinimalLineChart
import com.hdil.saluschart.ui.compose.charts.MinimalRangeBarChart
import com.hdil.saluschart.ui.compose.charts.MinimalSleepChart
import com.hdil.saluschart.ui.compose.charts.SleepColumn
import com.hdil.saluschart.ui.compose.charts.SleepSegment

// ── Colors ────────────────────────────────────────────────────────────────────

private val AHBackground = Color(0xFFF2F2F7)
private val AHCard       = Color.White
private val AHBlue       = Color(0xFF007AFF)
private val AHGray       = Color(0xFF8E8E93)
private val AHSeparator  = Color(0xFFE5E5EA)

private val AHSteps      = Color(0xFFFC3D56)
private val AHHeartRate  = Color(0xFFFC3D56)
private val AHSleep      = Color(0xFF5E5CE6)
private val AHWeight     = Color(0xFFFF9F0A)

private val RingMove     = Color(0xFFFA3E5B)
private val RingExercise = Color(0xFF91F04F)
private val RingStand    = Color(0xFF00D4FF)

private val AHSleepDeep  = Color(0xFF1C3657)
private val AHSleepCore  = Color(0xFF3A7BD5)
private val AHSleepREM   = Color(0xFF7B61FF)
private val AHSleepAwake = Color(0xFFBDBDBD)

// ── Sample data ───────────────────────────────────────────────────────────────

private val stepsData = listOf(
    ChartMark(0.0, 4200.0), ChartMark(1.0, 7800.0), ChartMark(2.0, 6100.0),
    ChartMark(3.0, 9340.0), ChartMark(4.0, 5500.0), ChartMark(5.0, 8200.0),
    ChartMark(6.0, 3100.0),
)
// Two ChartMarks per day (same x, different y) → MinimalRangeBarChart groups into min/max bars
private val heartRateRangeData = listOf(
    ChartMark(0.0, 58.0), ChartMark(0.0, 82.0),
    ChartMark(1.0, 60.0), ChartMark(1.0, 79.0),
    ChartMark(2.0, 55.0), ChartMark(2.0, 85.0),
    ChartMark(3.0, 62.0), ChartMark(3.0, 80.0),
    ChartMark(4.0, 57.0), ChartMark(4.0, 76.0),
    ChartMark(5.0, 61.0), ChartMark(5.0, 83.0),
    ChartMark(6.0, 63.0), ChartMark(6.0, 71.0),
)
private val weightData = listOf(
    ChartMark(0.0, 73.2), ChartMark(1.0, 73.0), ChartMark(2.0, 72.8),
    ChartMark(3.0, 72.9), ChartMark(4.0, 72.5), ChartMark(5.0, 72.3),
    ChartMark(6.0, 72.1),
)
// 26 weekly resting heart rate readings (avg = 69 BPM)
private val hrTrendData = listOf(
    ChartMark(0.0,  74.0), ChartMark(1.0,  72.0), ChartMark(2.0,  73.0),
    ChartMark(3.0,  71.0), ChartMark(4.0,  70.0), ChartMark(5.0,  72.0),
    ChartMark(6.0,  69.0), ChartMark(7.0,  71.0), ChartMark(8.0,  68.0),
    ChartMark(9.0,  70.0), ChartMark(10.0, 67.0), ChartMark(11.0, 69.0),
    ChartMark(12.0, 68.0), ChartMark(13.0, 70.0), ChartMark(14.0, 67.0),
    ChartMark(15.0, 68.0), ChartMark(16.0, 66.0), ChartMark(17.0, 68.0),
    ChartMark(18.0, 67.0), ChartMark(19.0, 69.0), ChartMark(20.0, 66.0),
    ChartMark(21.0, 68.0), ChartMark(22.0, 65.0), ChartMark(23.0, 67.0),
    ChartMark(24.0, 65.0), ChartMark(25.0, 64.0),
)
// 26 weekly average step counts (avg = 8,189)
private val stepsTrendData = listOf(
    ChartMark(0.0,  6800.0), ChartMark(1.0,  7200.0), ChartMark(2.0,  6500.0),
    ChartMark(3.0,  7800.0), ChartMark(4.0,  7100.0), ChartMark(5.0,  8300.0),
    ChartMark(6.0,  7600.0), ChartMark(7.0,  8900.0), ChartMark(8.0,  7400.0),
    ChartMark(9.0,  8200.0), ChartMark(10.0, 9100.0), ChartMark(11.0, 7900.0),
    ChartMark(12.0, 8600.0), ChartMark(13.0, 7300.0), ChartMark(14.0, 8800.0),
    ChartMark(15.0, 8100.0), ChartMark(16.0, 9200.0), ChartMark(17.0, 7700.0),
    ChartMark(18.0, 8400.0), ChartMark(19.0, 9000.0), ChartMark(20.0, 8300.0),
    ChartMark(21.0, 8700.0), ChartMark(22.0, 9100.0), ChartMark(23.0, 8500.0),
    ChartMark(24.0, 9000.0), ChartMark(25.0, 9340.0),
)
private val exerciseMinutesData = listOf(
    ChartMark(0.0, 28.0), ChartMark(1.0, 35.0), ChartMark(2.0, 22.0),
    ChartMark(3.0, 41.0), ChartMark(4.0, 18.0), ChartMark(5.0, 38.0),
    ChartMark(6.0, 30.0),
)
// Min/max dB range per day for headphone audio
private val headphoneData = listOf(
    ChartMark(0.0, 65.0), ChartMark(0.0, 78.0),
    ChartMark(1.0, 70.0), ChartMark(1.0, 82.0),
    ChartMark(2.0, 60.0), ChartMark(2.0, 75.0),
    ChartMark(3.0, 72.0), ChartMark(3.0, 85.0),
    ChartMark(4.0, 63.0), ChartMark(4.0, 74.0),
    ChartMark(5.0, 68.0), ChartMark(5.0, 80.0),
    ChartMark(6.0, 66.0), ChartMark(6.0, 77.0),
)
private val sleepTrendData = listOf(
    ChartMark(0.0, 6.5), ChartMark(1.0, 7.0), ChartMark(2.0, 6.8),
    ChartMark(3.0, 7.2), ChartMark(4.0, 7.5), ChartMark(5.0, 7.7),
    ChartMark(6.0, 7.7),
)
private val sleepColumns = listOf(
    SleepColumn(listOf(SleepSegment(20f, AHSleepDeep), SleepSegment(180f, AHSleepCore), SleepSegment(60f, AHSleepREM), SleepSegment(15f, AHSleepAwake))),
    SleepColumn(listOf(SleepSegment(30f, AHSleepDeep), SleepSegment(200f, AHSleepCore), SleepSegment(50f, AHSleepREM), SleepSegment(20f, AHSleepAwake))),
    SleepColumn(listOf(SleepSegment(25f, AHSleepDeep), SleepSegment(190f, AHSleepCore), SleepSegment(55f, AHSleepREM), SleepSegment(10f, AHSleepAwake))),
    SleepColumn(listOf(SleepSegment(40f, AHSleepDeep), SleepSegment(170f, AHSleepCore), SleepSegment(70f, AHSleepREM), SleepSegment(25f, AHSleepAwake))),
    SleepColumn(listOf(SleepSegment(15f, AHSleepDeep), SleepSegment(210f, AHSleepCore), SleepSegment(45f, AHSleepREM), SleepSegment(30f, AHSleepAwake))),
    SleepColumn(listOf(SleepSegment(35f, AHSleepDeep), SleepSegment(195f, AHSleepCore), SleepSegment(65f, AHSleepREM), SleepSegment(5f, AHSleepAwake))),
    SleepColumn(listOf(SleepSegment(28f, AHSleepDeep), SleepSegment(185f, AHSleepCore), SleepSegment(58f, AHSleepREM), SleepSegment(18f, AHSleepAwake))),
)
// Blood oxygen 26 weeks — every slot has a bar (100% → min).
// Dots are occasional dip readings that fell below the bar min, at their actual SpO2 value.
private val bloodOxygenBarMins = listOf(
    96.0, 97.5, 98.5, 95.0, 97.0, 96.5, 97.0, 96.5, 97.5, 98.0,
    96.5, 96.0, 97.5, 98.5, 96.0, 95.5, 97.5, 97.0, 96.0, 96.5,
    97.5, 95.5, 98.5, 96.0, 97.0, 97.5
)
private val bloodOxygenDots: List<List<Double>> = listOf(
    listOf(98.5, 97.0),        // 0  above ref
    listOf(94.5, 98.0),        // 1  one below, one above
    listOf(99.0),              // 2  above ref
    listOf(93.0, 97.5),        // 3  one below, one above
    listOf(98.0, 96.5),        // 4  above ref
    listOf(94.0, 93.5, 97.0),  // 5  two below, one above
    listOf(99.0, 97.5),        // 6  above ref
    listOf(98.5),              // 7  above ref
    listOf(94.0, 97.0, 98.5),  // 8  one below, two above
    listOf(99.0, 96.5),        // 9  above ref
    listOf(93.5, 98.0),        // 10 one below, one above
    listOf(97.5, 99.0),        // 11 above ref
    listOf(98.0, 96.5),        // 12 above ref
    listOf(94.5, 97.0),        // 13 one below, one above
    listOf(98.5, 99.0),        // 14 above ref
    listOf(93.0, 94.0, 98.0),  // 15 two below, one above
    listOf(97.5),              // 16 above ref
    listOf(94.5, 99.0, 97.0),  // 17 one below, two above
    listOf(98.5, 96.5),        // 18 above ref
    listOf(99.0, 97.5),        // 19 above ref
    listOf(93.5, 98.0),        // 20 one below, one above
    listOf(96.5, 98.5),        // 21 above ref
    listOf(94.0, 97.0),        // 22 one below, one above
    listOf(99.0, 98.0),        // 23 above ref
    listOf(97.5, 96.0),        // 24 above ref
    listOf(94.0, 98.5),        // 25 one below, one above
)

private val activityRings = listOf(
    ProgressChartMark(x = 0.0, current = 420.0, max = 500.0, label = "Move",     unit = "kcal"),
    ProgressChartMark(x = 1.0, current = 25.0,  max = 30.0,  label = "Exercise", unit = "min"),
    ProgressChartMark(x = 2.0, current = 10.0,  max = 12.0,  label = "Stand",    unit = "hr"),
)
private val ringColors = listOf(RingMove, RingExercise, RingStand)

// ── Root screen ───────────────────────────────────────────────────────────────

@Composable
fun HealthSummaryScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AHBackground)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        // Empty space at top
        Spacer(Modifier.height(60.dp))

        // Summary title + profile picture
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Summary",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFD1D1D6)),
                contentAlignment = Alignment.Center
            ) {
                Text("S", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }

        SectionHeader("Pinned", "Edit")
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StepsPinnedCard()
            HeartRatePinnedCard()
            SleepPinnedCard()
            ActivityPinnedCard()
            WeightPinnedCard()
        }

        SectionHeader("Trends", "Show All Trends")
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HeartRateTrendCard()
            StepsTrendCard()
            BloodOxygenTrendCard()
        }

        SectionHeader("Highlights", null)
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StepsHighlightCard()
            ExerciseHighlightCard()
            HeadphoneHighlightCard()
        }

        Spacer(Modifier.height(40.dp))
    }
}

// ── Section header ────────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(title: String, action: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        if (action != null) Text(action, color = AHBlue, fontSize = 15.sp)
    }
}

// ── Card shell ────────────────────────────────────────────────────────────────

@Composable
private fun AHCardContainer(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AHCard),
        content = content
    )
}

@Composable
private fun CardHairline() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .background(AHSeparator)
    )
}

// ── Pinned cards (horizontal: info left, chart right) ─────────────────────────

@Composable
private fun PinnedCard(
    icon:       String,
    title:      String,
    titleColor: Color = AHGray,
    value:      String,
    unit:       String,
    period:     String,
    chart:      @Composable BoxScope.() -> Unit
) {
    AHCardContainer {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: icon+title on top, period+value pinned to bottom
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top: icon + title
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(icon, fontSize = 14.sp)
                    Spacer(Modifier.width(5.dp))
                    Text(title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = titleColor)
                }
                // Bottom: period label just above the big value
                Column {
                    Text(period, fontSize = 12.sp, color = AHGray)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(value, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                        if (unit.isNotEmpty()) {
                            Spacer(Modifier.width(3.dp))
                            Text(unit, fontSize = 13.sp, color = AHGray,
                                modifier = Modifier.offset(y = 4.dp))
                        }
                    }
                }
            }
            // Right: chart
            Box(modifier = Modifier.width(110.dp).fillMaxHeight(), content = chart)
        }
    }
}

@Composable
private fun StepsPinnedCard() {
    PinnedCard("🔥", "Steps", titleColor = Color(0xFFFF9500), "9,340", "steps", "") {
        MinimalBarChart(
            modifier = Modifier.fillMaxSize().padding(top = 16.dp),
            data = stepsData,
            color = Color(0xFFFF9500),
            barWidthRatio = 0.55f,
            roundTopOnly = true,
            barCornerRadiusFraction = 0.3f
        )
    }
}

@Composable
private fun HeartRatePinnedCard() {
    PinnedCard("♥", "Heart Rate", titleColor = AHHeartRate, "67", "BPM", "Latest") {
        MinimalRangeBarChart(
            modifier = Modifier.fillMaxSize().padding(top = 16.dp),
            data = heartRateRangeData,
            color = Color(0xFFD1D1D6),
            barWidthRatio = 0.35f,
            barCornerRadiusFraction = 1f,
            roundTopOnly = false
        )
    }
}

@Composable
private fun SleepPinnedCard() {
    PinnedCard("🛏", "Sleep", titleColor = AHSleep, "7h 42m", "", "Time Asleep") {
        MinimalSleepChart(
            modifier = Modifier.fillMaxSize().padding(top = 16.dp),
            columns = sleepColumns,
            barWidthRatio = 0.65f,
            cornerRadiusRatio = 0.2f
        )
    }
}

@Composable
private fun ActivityPinnedCard() {
    AHCardContainer {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            // Left: icon+title at top, stat columns at bottom
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🔥", fontSize = 14.sp)
                    Spacer(Modifier.width(5.dp))
                    Text("Activity", fontSize = 13.sp, fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFFF9500))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ActivityStatColumn("Move",     "420", "kcal", RingMove,     modifier = Modifier.weight(1f))
                    VerticalHairline()
                    ActivityStatColumn("Exercise", "25",  "min",  RingExercise, modifier = Modifier.weight(1f))
                    VerticalHairline()
                    ActivityStatColumn("Stand",    "10",  "hr",   RingStand,    modifier = Modifier.weight(1f))
                }
            }
            Spacer(Modifier.width(16.dp))
            // Right: rings bottom-aligned
            MiniActivityRings(
                modifier = Modifier.size(56.dp),
                rings = activityRings,
                colors = ringColors,
                strokeWidth = 16f
            )
        }
    }
}

@Composable
private fun ActivityStatColumn(label: String, value: String, unit: String, labelColor: Color, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 8.dp), horizontalAlignment = Alignment.Start) {
        Text(label, fontSize = 12.sp, color = labelColor, fontWeight = FontWeight.SemiBold)
        Row(verticalAlignment = Alignment.Bottom) {
            Text(value, fontSize = 17.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(2.dp))
            Text(unit, fontSize = 12.sp, color = AHGray, modifier = Modifier.offset(y = 2.dp))
        }
    }
}

@Composable
private fun VerticalHairline() {
    Box(
        modifier = Modifier
            .width(0.5.dp)
            .height(44.dp)
            .background(AHSeparator)
    )
}

@Composable
private fun WeightPinnedCard() {
    PinnedCard("🧍", "Weight", titleColor = Color(0xFFFF2D55), "72.1", "kg", "Average") {
        MinimalLineChart(
            modifier = Modifier.fillMaxSize().padding(top = 16.dp),
            data = weightData,
            color = AHWeight,
            strokeWidth = 2f,
            showPoints = true
        )
    }
}

// ── Trend cards (text top, hairline, chart bottom) ────────────────────────────

@Composable
private fun HeartRateTrendCard() {
    AHCardContainer {
        // Top: title + description
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("♥", fontSize = 16.sp)
                Spacer(Modifier.width(6.dp))
                Text(
                    "Resting Heart Rate",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AHHeartRate
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "On average, your resting heart rate was 69 BPM over the last 26 weeks.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp
            )
        }
        CardHairline()
        // Chart area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            MinimalLineChart(
                modifier = Modifier.fillMaxSize(),
                data = hrTrendData,
                color = Color(0xFFD1D1D6),
                strokeWidth = 4f,
                padding = 0f,
                referenceLines = listOf(
                    ReferenceLineSpec(
                        type        = ReferenceLineType.THRESHOLD,
                        y           = 69.0,
                        color       = AHHeartRate,
                        strokeWidth = 3.dp,
                        style       = LineStyle.SOLID
                    )
                )
            )
        }
        Text(
            "26 weeks",
            fontSize = 11.sp,
            color = AHHeartRate,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, top = 4.dp)
        )
    }
}

@Composable
private fun StepsTrendCard() {
    val stepOrange = Color(0xFFFF9500)
    AHCardContainer {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🔥", fontSize = 16.sp)
                Spacer(Modifier.width(6.dp))
                Text(
                    "Steps",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = stepOrange
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "On average, you took 8,189 steps per day over the past 26 weeks.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp
            )
        }
        CardHairline()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            MinimalBarChart(
                modifier = Modifier.fillMaxSize(),
                data = stepsTrendData,
                color = Color(0xFFD1D1D6),
                barWidthRatio = 0.55f,
                roundTopOnly = true,
                barCornerRadiusFraction = 0.3f,
                padding = 0f,
                referenceLines = listOf(
                    ReferenceLineSpec(
                        type        = ReferenceLineType.THRESHOLD,
                        y           = 8189.0,
                        color       = stepOrange,
                        strokeWidth = 3.dp,
                        style       = LineStyle.SOLID
                    )
                )
            )
        }
        Text(
            "26 weeks",
            fontSize = 11.sp,
            color = stepOrange,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, top = 4.dp)
        )
    }
}

@Composable
private fun BloodOxygenTrendCard() {
    val skyBlue = Color(0xFF5AC8FA)
    val barGrey = Color(0xFFD1D1D6)
    val n = bloodOxygenBarMins.size

    // Y domain: bars cluster 95–100%, dots dip to 93–94%, reference line at 95%
    val yDomainMax = 100.5
    val yDomainMin = 91.5
    val yRange = yDomainMax - yDomainMin  // 9.0

    AHCardContainer {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🫁", fontSize = 16.sp)
                Spacer(Modifier.width(6.dp))
                Text(
                    "Blood Oxygen",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = skyBlue
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "On average, your blood oxygen level was 98.1% over the last 26 weeks.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 20.sp
            )
        }
        CardHairline()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val slotW = size.width / n
                val barW = slotW * 0.45f
                val dotR = slotW * 0.20f

                // 100.5% → y=0 (top), 94% → y=height (bottom)
                fun valueToY(v: Double): Float =
                    ((yDomainMax - v) / yRange * size.height).toFloat()

                // All 26 slots: bar from 100% down to min, plus optional dip dots below
                for (i in 0 until n) {
                    val cx = (i + 0.5f) * slotW
                    val topY = valueToY(100.0)
                    val botY = valueToY(bloodOxygenBarMins[i])
                    val h = (botY - topY).coerceAtLeast(4f)
                    val r = (barW / 2f).coerceAtMost(h / 2f)
                    drawRoundRect(
                        color = barGrey,
                        topLeft = Offset(cx - barW / 2f, topY),
                        size = Size(barW, h),
                        cornerRadius = CornerRadius(r)
                    )
                    for (dotVal in bloodOxygenDots[i]) {
                        drawCircle(
                            color = barGrey,
                            radius = dotR,
                            center = Offset(cx, valueToY(dotVal))
                        )
                    }
                }

                // Sky blue reference line at 95% — below bar cluster, above dip dots
                val refY = valueToY(95.0)
                drawLine(
                    color = skyBlue,
                    start = Offset(0f, refY),
                    end = Offset(size.width, refY),
                    strokeWidth = 7f
                )
            }
        }
        Text(
            "26 weeks",
            fontSize = 11.sp,
            color = skyBlue,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, top = 4.dp)
        )
    }
}


// ── Highlight cards ───────────────────────────────────────────────────────────

@Composable
private fun StepsHighlightCard() {
    val color = Color(0xFFFF9500)
    AHCardContainer {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🔥", fontSize = 16.sp)
                Spacer(Modifier.width(6.dp))
                Text("Steps", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = color)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "You averaged 7,445 steps a day over the last 7 days.",
                fontSize = 15.sp, fontWeight = FontWeight.Bold, lineHeight = 20.sp
            )
        }
        CardHairline()
        Box(modifier = Modifier.fillMaxWidth().height(120.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)) {
            MinimalBarChart(
                modifier = Modifier.fillMaxSize(),
                data = stepsData,
                color = Color(0xFFD1D1D6),
                barWidthRatio = 0.55f,
                roundTopOnly = true,
                barCornerRadiusFraction = 0.15f,
                padding = 0f,
                referenceLines = listOf(
                    ReferenceLineSpec(
                        type = ReferenceLineType.THRESHOLD, y = 7445.0,
                        color = color, strokeWidth = 3.dp, style = LineStyle.SOLID
                    )
                )
            )
        }
        Text("7 days", fontSize = 11.sp, color = color, fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, top = 4.dp))
    }
}

@Composable
private fun ExerciseHighlightCard() {
    val color = Color(0xFFFF9500)
    AHCardContainer {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🏃", fontSize = 16.sp)
                Spacer(Modifier.width(6.dp))
                Text("Exercise Minutes", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = color)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "On average, you're earning 9 more Exercise minutes in 2026 than 2025.",
                fontSize = 15.sp, fontWeight = FontWeight.Bold, lineHeight = 20.sp
            )
        }
        CardHairline()
        Box(modifier = Modifier.fillMaxWidth().height(120.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)) {
            MinimalBarChart(
                modifier = Modifier.fillMaxSize(),
                data = exerciseMinutesData,
                color = Color(0xFFD1D1D6),
                barWidthRatio = 0.55f,
                roundTopOnly = true,
                barCornerRadiusFraction = 0.15f,
                padding = 0f,
                referenceLines = listOf(
                    ReferenceLineSpec(
                        type = ReferenceLineType.THRESHOLD, y = 30.0,
                        color = color, strokeWidth = 3.dp, style = LineStyle.SOLID
                    )
                )
            )
        }
        Text("2025 vs 2026", fontSize = 11.sp, color = color, fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, top = 4.dp))
    }
}

@Composable
private fun HeadphoneHighlightCard() {
    val color = AHBlue
    AHCardContainer {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🎧", fontSize = 16.sp)
                Spacer(Modifier.width(6.dp))
                Text("Headphone Audio Levels", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = color)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "Your average headphone audio levels were OK over the last seven days.",
                fontSize = 15.sp, fontWeight = FontWeight.Bold, lineHeight = 20.sp
            )
        }
        CardHairline()
        Box(modifier = Modifier.fillMaxWidth().height(120.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)) {
            MinimalRangeBarChart(
                modifier = Modifier.fillMaxSize(),
                data = headphoneData,
                color = Color(0xFFD1D1D6),
                barWidthRatio = 0.5f,
                barCornerRadiusFraction = 0.5f,
                roundTopOnly = false
            )
            // Reference line at 75 dB — data spans 60–85, so 75 sits ~40% from top
            Canvas(modifier = Modifier.fillMaxSize()) {
                val refY = size.height * 0.40f
                drawLine(
                    color = color,
                    start = Offset(0f, refY),
                    end = Offset(size.width, refY),
                    strokeWidth = 7f
                )
            }
        }
        Text("7 days", fontSize = 11.sp, color = color, fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, top = 4.dp))
    }
}
