package com.example.syncup.ui.event.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.syncup.data.model.events.Vote

@Composable
fun VotingProgress(
    voteSummaryForSlot: Map<Vote, Int>?,
) {
    val yes = voteSummaryForSlot?.get(Vote.YES) ?: 0
    val yesBut = voteSummaryForSlot?.get(Vote.YES_BUT) ?: 0
    val no = voteSummaryForSlot?.get(Vote.NO) ?: 0
    val totalVotes = yes + yesBut + no

    Box(
        modifier = Modifier.size(96.dp),
        contentAlignment = Alignment.Center
    ){
        Canvas(Modifier.size(96.dp)) {
            val sw = 6.dp.toPx()

            if (totalVotes == 0) {
                drawArc(
                    color = Color.LightGray,
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = sw, cap = StrokeCap.Round)
                )
                return@Canvas
            }

            fun sweep(count: Int) = 360f * (count.toFloat() / totalVotes.toFloat())

            var start = -90f

            val sYes = sweep(yes)
            if (sYes > 0f) {
                drawArc(
                    color = Color(0xFF2E7D32),
                    startAngle = start,
                    sweepAngle = sYes,
                    useCenter = false,
                    style = Stroke(width = sw, cap = StrokeCap.Round)
                )
                start += sYes
            }

            val sYesBut = sweep(yesBut)
            if (sYesBut > 0f) {
                drawArc(
                    color = Color(0xFFF9A825),
                    startAngle = start,
                    sweepAngle = sYesBut,
                    useCenter = false,
                    style = Stroke(width = sw, cap = StrokeCap.Round)
                )
                start += sYesBut
            }

            val sNo = sweep(no)
            if (sNo > 0f) {
                drawArc(
                    color = Color(0xFFC62828),
                    startAngle = start,
                    sweepAngle = sNo,
                    useCenter = false,
                    style = Stroke(width = sw, cap = StrokeCap.Round)
                )
            }
        }
        Text(
            text = totalVotes.toString(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
