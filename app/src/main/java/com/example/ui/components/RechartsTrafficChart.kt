package com.example.ui.components

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FlowDirection
import com.example.model.TrafficFlowPoint
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.ObsidianBackground
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.QuantumPurple
import com.example.ui.theme.ThreatCrimson
import com.example.ui.theme.WarningGold
import kotlin.math.max

@Composable
fun RechartsTrafficChart(
  dataPoints: List<TrafficFlowPoint>,
  isLive: Boolean,
  selectedFilter: FlowDirection?,
  onToggleLive: () -> Unit,
  onSelectFilter: (FlowDirection?) -> Unit,
  onInjectBurst: () -> Unit,
  modifier: Modifier = Modifier
) {
  var activeHoverIndex by remember { mutableStateOf<Int?>(null) }

  // Infinite pulsing animation for the "LIVE" indicator
  val infiniteTransition = rememberInfiniteTransition(label = "pulse_live")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.35f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "live_pulse_alpha"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("recharts_traffic_chart_container"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      // Top Header with Recharts title & Live Controls
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(9.dp)
                .clip(CircleShape)
                .background(if (isLive) CyberEmerald.copy(alpha = pulseAlpha) else WarningGold)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (isLive) "RECHARTS REAL-TIME STREAM" else "FLUJO PAUSADO",
              color = if (isLive) CyberEmerald else WarningGold,
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = 1.sp
            )
          }
          Text(
            text = "Tráfico Bloqueado por el Enjambre IA",
            color = CyberTextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          // Play / Pause button
          IconButton(
            onClick = onToggleLive,
            modifier = Modifier
              .size(36.dp)
              .testTag("recharts_toggle_live_btn")
              .clip(RoundedCornerShape(8.dp))
              .background(ObsidianElevated)
          ) {
            Icon(
              imageVector = if (isLive) Icons.Default.Pause else Icons.Default.PlayArrow,
              contentDescription = if (isLive) "Pausar" else "Reanudar",
              tint = CyberCyan,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Filter Chips: All, Incoming, Outgoing
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        FilterTabChip(
          title = "Todos (Bi-Direccional)",
          isSelected = selectedFilter == null,
          color = CyberCyan,
          onClick = { onSelectFilter(null) },
          modifier = Modifier.weight(1f)
        )
        FilterTabChip(
          title = "● Entrante (Ingress)",
          isSelected = selectedFilter == FlowDirection.INCOMING,
          color = ThreatCrimson,
          onClick = { onSelectFilter(FlowDirection.INCOMING) },
          modifier = Modifier.weight(1f)
        )
        FilterTabChip(
          title = "■ Saliente (Egress)",
          isSelected = selectedFilter == FlowDirection.OUTGOING,
          color = QuantumPurple,
          onClick = { onSelectFilter(FlowDirection.OUTGOING) },
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // The Main Recharts Area & Line Canvas Chart
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(230.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(ObsidianElevated)
          .border(1.dp, ObsidianBorder.copy(alpha = 0.8f), RoundedCornerShape(12.dp))
          .pointerInput(dataPoints) {
            detectTapGestures(
              onTap = { offset ->
                if (dataPoints.isNotEmpty()) {
                  val chartPaddingLeft = 50f
                  val chartPaddingRight = 20f
                  val availableWidth = size.width - chartPaddingLeft - chartPaddingRight
                  val relativeX = (offset.x - chartPaddingLeft).coerceIn(0f, availableWidth)
                  val step = availableWidth / max(1, dataPoints.size - 1)
                  val index = (relativeX / step).toInt().coerceIn(0, dataPoints.size - 1)
                  activeHoverIndex = index
                }
              }
            )
          }
          .pointerInput(dataPoints) {
            detectDragGestures(
              onDrag = { change, _ ->
                if (dataPoints.isNotEmpty()) {
                  val chartPaddingLeft = 50f
                  val chartPaddingRight = 20f
                  val availableWidth = size.width - chartPaddingLeft - chartPaddingRight
                  val relativeX = (change.position.x - chartPaddingLeft).coerceIn(0f, availableWidth)
                  val step = availableWidth / max(1, dataPoints.size - 1)
                  val index = (relativeX / step).toInt().coerceIn(0, dataPoints.size - 1)
                  activeHoverIndex = index
                }
              },
              onDragEnd = {
                // Keep the last inspected point visible for comfort
              }
            )
          }
      ) {
        RechartsCanvasArea(
          dataPoints = dataPoints,
          selectedFilter = selectedFilter,
          hoveredIndex = activeHoverIndex
        )

        // Interactive Tooltip (Floating Scrubber Card)
        val inspectedPoint = activeHoverIndex?.let { idx ->
          if (idx in dataPoints.indices) dataPoints[idx] else null
        } ?: dataPoints.lastOrNull()

        if (inspectedPoint != null) {
          Box(
            modifier = Modifier
              .align(Alignment.TopStart)
              .padding(8.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(ObsidianSurface.copy(alpha = 0.92f))
              .border(1.dp, CyberCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
              .padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "Punto T: ${inspectedPoint.timeLabel}",
                  color = CyberTextPrimary,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "100% BLOQUEADO",
                  color = CyberEmerald,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Black
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                if (selectedFilter == null || selectedFilter == FlowDirection.INCOMING) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(CyberCyan))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "IN: ${"%.1f".format(inspectedPoint.incomingBlockedMbps)} Mbps",
                      color = CyberCyan,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.SemiBold
                    )
                  }
                }
                if (selectedFilter == null || selectedFilter == FlowDirection.OUTGOING) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(QuantumPurple))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "OUT: ${"%.1f".format(inspectedPoint.outgoingBlockedMbps)} Mbps",
                      color = QuantumPurple,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.SemiBold
                    )
                  }
                }
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Dron: ${inspectedPoint.primaryInterceptionAgent}",
                color = CyberTextMuted,
                fontSize = 9.sp
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Bottom Legend & Burst Trigger
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(CyberCyan)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Flujo Entrante (Ingress)",
              color = CyberTextSecondary,
              fontSize = 10.sp
            )
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(10.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(QuantumPurple)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Flujo Saliente (Egress)",
              color = CyberTextSecondary,
              fontSize = 10.sp
            )
          }
        }

        Button(
          onClick = onInjectBurst,
          modifier = Modifier
            .testTag("inject_traffic_burst_btn"),
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = ThreatCrimson.copy(alpha = 0.85f)),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Bolt,
            contentDescription = "Ráfaga",
            tint = Color.White,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "Ráfaga Maliciosa",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}

@Composable
fun FilterTabChip(
  title: String,
  isSelected: Boolean,
  color: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(8.dp))
      .background(if (isSelected) color.copy(alpha = 0.2f) else ObsidianElevated)
      .border(
        1.dp,
        if (isSelected) color else ObsidianBorder,
        RoundedCornerShape(8.dp)
      )
      .clickable { onClick() }
      .padding(vertical = 6.dp, horizontal = 6.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = title,
      color = if (isSelected) color else CyberTextSecondary,
      fontSize = 10.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
      maxLines = 1
    )
  }
}

@Composable
private fun RechartsCanvasArea(
  dataPoints: List<TrafficFlowPoint>,
  selectedFilter: FlowDirection?,
  hoveredIndex: Int?
) {
  Canvas(modifier = Modifier.fillMaxSize()) {
    if (dataPoints.isEmpty()) return@Canvas

    val width = size.width
    val height = size.height
    val paddingLeft = 52f
    val paddingRight = 20f
    val paddingTop = 28f
    val paddingBottom = 28f

    val chartWidth = width - paddingLeft - paddingRight
    val chartHeight = height - paddingTop - paddingBottom

    // Determine max Y value dynamically
    val maxDataVal = dataPoints.maxOfOrNull {
      max(it.incomingBlockedMbps, it.outgoingBlockedMbps)
    } ?: 200f
    val maxY = (maxDataVal * 1.25f).coerceAtLeast(150f)

    val gridPaint = Paint().apply {
      color = Color(0x334B6584).toArgb()
      strokeWidth = 1f
      isAntiAlias = true
    }

    val textPaint = Paint().apply {
      color = Color(0xFF6C7A89).toArgb()
      textSize = 24f
      isAntiAlias = true
      typeface = Typeface.MONOSPACE
    }

    // 1. Draw Cartesian Grid Lines & Y-Axis Labels
    val yStepCount = 4
    for (i in 0..yStepCount) {
      val yFraction = i / yStepCount.toFloat()
      val yPos = paddingTop + chartHeight * (1f - yFraction)
      val labelVal = (maxY * yFraction).toInt()

      // Horizontal dashed grid line
      drawLine(
        color = Color(0x2288A0BF),
        start = Offset(paddingLeft, yPos),
        end = Offset(width - paddingRight, yPos),
        strokeWidth = 1f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
      )

      // Y-axis label
      drawContext.canvas.nativeCanvas.drawText(
        "$labelVal M",
        8f,
        yPos + 8f,
        textPaint
      )
    }

    // 2. Draw X-axis timestamps
    val xStep = chartWidth / max(1, dataPoints.size - 1)
    val labelInterval = (dataPoints.size / 4).coerceAtLeast(1)
    for (i in dataPoints.indices step labelInterval) {
      val xPos = paddingLeft + i * xStep
      val label = dataPoints[i].timeLabel
      drawContext.canvas.nativeCanvas.drawText(
        label,
        xPos - 30f,
        height - 6f,
        textPaint
      )
    }

    // Coordinates mapper
    fun getPointOffset(index: Int, value: Float): Offset {
      val x = paddingLeft + index * xStep
      val y = paddingTop + chartHeight * (1f - (value / maxY).coerceIn(0f, 1f))
      return Offset(x, y)
    }

    // Cubic Bezier curve builder
    fun buildCubicPath(values: List<Float>): Path {
      val path = Path()
      if (values.isEmpty()) return path

      val first = getPointOffset(0, values[0])
      path.moveTo(first.x, first.y)

      for (i in 0 until values.size - 1) {
        val current = getPointOffset(i, values[i])
        val next = getPointOffset(i + 1, values[i + 1])
        val controlPoint1 = Offset(current.x + (next.x - current.x) / 2f, current.y)
        val controlPoint2 = Offset(current.x + (next.x - current.x) / 2f, next.y)
        path.cubicTo(
          controlPoint1.x, controlPoint1.y,
          controlPoint2.x, controlPoint2.y,
          next.x, next.y
        )
      }
      return path
    }

    // Closed Area Builder for Gradient Fill
    fun buildAreaPath(values: List<Float>): Path {
      val path = buildCubicPath(values)
      if (values.isNotEmpty()) {
        val lastPoint = getPointOffset(values.size - 1, values.last())
        val firstPoint = getPointOffset(0, values.first())
        val bottomY = paddingTop + chartHeight
        path.lineTo(lastPoint.x, bottomY)
        path.lineTo(firstPoint.x, bottomY)
        path.close()
      }
      return path
    }

    // Draw Incoming Series (CYAN) if enabled
    if (selectedFilter == null || selectedFilter == FlowDirection.INCOMING) {
      val inValues = dataPoints.map { it.incomingBlockedMbps }
      val inAreaPath = buildAreaPath(inValues)
      val inStrokePath = buildCubicPath(inValues)

      // Area fill
      drawPath(
        path = inAreaPath,
        brush = Brush.verticalGradient(
          colors = listOf(CyberCyan.copy(alpha = 0.35f), CyberCyan.copy(alpha = 0.02f)),
          startY = paddingTop,
          endY = paddingTop + chartHeight
        )
      )

      // Curve stroke
      drawPath(
        path = inStrokePath,
        color = CyberCyan,
        style = Stroke(width = 3.5f, cap = StrokeCap.Round)
      )
    }

    // Draw Outgoing Series (PURPLE) if enabled
    if (selectedFilter == null || selectedFilter == FlowDirection.OUTGOING) {
      val outValues = dataPoints.map { it.outgoingBlockedMbps }
      val outAreaPath = buildAreaPath(outValues)
      val outStrokePath = buildCubicPath(outValues)

      // Area fill
      drawPath(
        path = outAreaPath,
        brush = Brush.verticalGradient(
          colors = listOf(QuantumPurple.copy(alpha = 0.35f), QuantumPurple.copy(alpha = 0.02f)),
          startY = paddingTop,
          endY = paddingTop + chartHeight
        )
      )

      // Curve stroke
      drawPath(
        path = outStrokePath,
        color = QuantumPurple,
        style = Stroke(width = 3.5f, cap = StrokeCap.Round)
      )
    }

    // 3. Draw Scrubber line and glowing cursor dots if hovered
    val targetIndex = hoveredIndex ?: (dataPoints.size - 1)
    if (targetIndex in dataPoints.indices) {
      val currentPt = dataPoints[targetIndex]
      val xPos = paddingLeft + targetIndex * xStep

      // Vertical Recharts cursor line
      drawLine(
        color = Color(0x8800E5FF),
        start = Offset(xPos, paddingTop),
        end = Offset(xPos, paddingTop + chartHeight),
        strokeWidth = 1.5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f)
      )

      // Cursor dots
      if (selectedFilter == null || selectedFilter == FlowDirection.INCOMING) {
        val inOffset = getPointOffset(targetIndex, currentPt.incomingBlockedMbps)
        drawCircle(color = CyberCyan.copy(alpha = 0.3f), radius = 10f, center = inOffset)
        drawCircle(color = CyberCyan, radius = 5f, center = inOffset)
        drawCircle(color = ObsidianBackground, radius = 2.5f, center = inOffset)
      }

      if (selectedFilter == null || selectedFilter == FlowDirection.OUTGOING) {
        val outOffset = getPointOffset(targetIndex, currentPt.outgoingBlockedMbps)
        drawCircle(color = QuantumPurple.copy(alpha = 0.3f), radius = 10f, center = outOffset)
        drawCircle(color = QuantumPurple, radius = 5f, center = outOffset)
        drawCircle(color = ObsidianBackground, radius = 2.5f, center = outOffset)
      }
    }
  }
}
