package com.household.finances.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.household.finances.ui.theme.*

@Composable
fun PieChart(
    data: Map<String, Double>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    title: String? = null
) {
    val total = data.values.sum()
    if (total == 0.0) return

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Box(
            modifier = Modifier.size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val canvasSize = size.minDimension
                val radius = canvasSize / 2
                val strokeWidth = 40f
                val center = Offset(size.width / 2, size.height / 2)

                var startAngle = -90f

                data.entries.forEachIndexed { index, entry ->
                    val sweepAngle = (entry.value / total * 360).toFloat()
                    val color = colors[index % colors.size]

                    drawArc(
                        color = color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(
                            center.x - radius,
                            center.y - radius
                        ),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Butt
                        )
                    )

                    startAngle += sweepAngle
                }
            }

            // 中心显示总额
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "总计",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formatCurrency(total),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // 图例
        Column(
            modifier = Modifier.padding(top = 16.dp)
        ) {
            data.entries.forEachIndexed { index, entry ->
                val percentage = (entry.value / total * 100)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Canvas(
                        modifier = Modifier.size(12.dp)
                    ) {
                        drawCircle(
                            color = colors[index % colors.size],
                            radius = size.minDimension / 2
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = entry.key,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${String.format("%.1f", percentage)}%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun BarChart(
    data: Map<String, Double>,
    color: Color,
    modifier: Modifier = Modifier,
    title: String? = null
) {
    val maxValue = data.values.maxOrNull() ?: 0.0
    if (maxValue == 0.0) return

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val barWidth = size.width / data.size * 0.7f
            val spacing = size.width / data.size * 0.3f
            val maxHeight = size.height - 40f

            data.entries.forEachIndexed { index, entry ->
                val barHeight = (entry.value / maxValue * maxHeight).toFloat()
                val x = index * (barWidth + spacing) + spacing / 2
                val y = size.height - barHeight

                drawRect(
                    color = color,
                    topLeft = Offset(x, y),
                    size = Size(barWidth, barHeight)
                )
            }
        }

        // 标签
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            data.keys.forEach { label ->
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun LineChart(
    data: List<Pair<String, Double>>,
    color: Color,
    modifier: Modifier = Modifier,
    title: String? = null
) {
    if (data.isEmpty()) return

    val maxValue = data.maxOf { it.second }
    val minValue = data.minOf { it.second }
    val range = maxValue - minValue

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            val stepX = size.width / (data.size - 1).coerceAtLeast(1)
            val maxHeight = size.height - 40f

            val points = data.mapIndexed { index, (_, value) ->
                val x = index * stepX
                val y = if (range > 0) {
                    size.height - ((value - minValue) / range * maxHeight).toFloat() - 20f
                } else {
                    size.height / 2
                }
                Offset(x, y)
            }

            // 画线
            for (i in 0 until points.size - 1) {
                drawLine(
                    color = color,
                    start = points[i],
                    end = points[i + 1],
                    strokeWidth = 3f,
                    cap = StrokeCap.Round
                )
            }

            // 画点
            points.forEach { point ->
                drawCircle(
                    color = color,
                    radius = 5f,
                    center = point
                )
            }
        }

        // 标签
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            data.take(3).forEach { (label, _) ->
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (data.size > 3) {
                Text(
                    text = "...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DonutChart(
    data: Map<String, Double>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    title: String? = null
) {
    val total = data.values.sum()
    if (total == 0.0) return
    val surfaceColor = MaterialTheme.colorScheme.surface

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Box(
            modifier = Modifier.size(180.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val canvasSize = size.minDimension
                val outerRadius = canvasSize / 2
                val innerRadius = outerRadius * 0.6f
                val center = Offset(size.width / 2, size.height / 2)

                var startAngle = -90f

                data.entries.forEachIndexed { index, entry ->
                    val sweepAngle = (entry.value / total * 360).toFloat()
                    val color = colors[index % colors.size]

                    drawArc(
                        color = color,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(
                            center.x - outerRadius,
                            center.y - outerRadius
                        ),
                        size = Size(outerRadius * 2, outerRadius * 2)
                    )

                    startAngle += sweepAngle
                }

                // 内圆（使用surface色，形成环形，适配深色主题）
                drawCircle(
                    color = surfaceColor,
                    radius = innerRadius,
                    center = center
                )
            }

            // 中心文字
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "总计",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formatCurrency(total),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
