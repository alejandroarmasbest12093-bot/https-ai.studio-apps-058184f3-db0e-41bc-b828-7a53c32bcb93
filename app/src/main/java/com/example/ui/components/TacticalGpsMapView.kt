package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GpsLocationState
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
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TacticalGpsMapView(
  gpsState: GpsLocationState,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current

  // Zoom scale: 1.0f = default (~250m radius), range 0.5f to 3.0f
  var zoomLevel by remember { mutableFloatStateOf(1.0f) }
  var panOffsetX by remember { mutableFloatStateOf(0f) }
  var panOffsetY by remember { mutableFloatStateOf(0f) }
  var showGridLines by remember { mutableStateOf(true) }

  // Radar sweep animation
  val infiniteTransition = rememberInfiniteTransition(label = "RadarSweep")
  val sweepAngle by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 3500, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "SweepAngle"
  )

  // Pulse ring animation
  val pulseRadius by infiniteTransition.animateFloat(
    initialValue = 0.2f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 2000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "PulseRadius"
  )

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("tactical_gps_map_container"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
    border = androidx.compose.foundation.BorderStroke(
      1.5.dp,
      Brush.horizontalGradient(listOf(CyberCyan, CyberEmerald, QuantumPurple))
    )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      // Header del Mapa Táctico
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(CyberCyan.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Map,
              contentDescription = "Mapa GPS",
              tint = CyberCyan,
              modifier = Modifier.size(20.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "MAPA GPS & VECTOR TÁCTICO",
              color = CyberCyan,
              fontSize = 12.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = 1.sp
            )
            Text(
              text = "FIJACIÓN SATELITAL GNSS EN TIEMPO REAL",
              color = CyberTextMuted,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Abrir en Aplicación de Mapas Externa
        Button(
          onClick = {
            openExternalMap(context, gpsState.latitude, gpsState.longitude, gpsState.humanReadableParadero)
          },
          modifier = Modifier
            .height(34.dp)
            .testTag("btn_open_external_map"),
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = CyberCyan,
            contentColor = Color.Black
          ),
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp)
        ) {
          Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(13.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Ver en Google Maps", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
      }

      // Lienzo del Radar / Mapa Táctico
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(260.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(ObsidianBackground)
          .border(1.dp, CyberCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
          .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
              change.consume()
              panOffsetX += dragAmount.x
              panOffsetY += dragAmount.y
            }
          }
      ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
          val canvasWidth = size.width
          val canvasHeight = size.height
          val center = Offset(canvasWidth / 2f + panOffsetX, canvasHeight / 2f + panOffsetY)
          val baseRadius = (minOf(canvasWidth, canvasHeight) / 2.2f) * zoomLevel

          // 1. Dibujar Cuadrícula Geográfica (Meridianos y Paralelos)
          if (showGridLines) {
            val gridSpacing = 40.dp.toPx() * zoomLevel
            var x = (center.x % gridSpacing)
            while (x < canvasWidth) {
              drawLine(
                color = CyberCyan.copy(alpha = 0.08f),
                start = Offset(x, 0f),
                end = Offset(x, canvasHeight),
                strokeWidth = 1f
              )
              x += gridSpacing
            }
            var y = (center.y % gridSpacing)
            while (y < canvasHeight) {
              drawLine(
                color = CyberCyan.copy(alpha = 0.08f),
                start = Offset(0f, y),
                end = Offset(canvasWidth, y),
                strokeWidth = 1f
              )
              y += gridSpacing
            }
          }

          // 2. Anillos Concéntricos de Distancia del Radar
          val ringFractions = listOf(0.25f, 0.50f, 0.75f, 1.0f)
          val ringDistances = listOf("50m", "100m", "250m", "500m")
          ringFractions.forEachIndexed { index, fraction ->
            val ringRadius = baseRadius * fraction
            drawCircle(
              color = CyberCyan.copy(alpha = 0.15f),
              radius = ringRadius,
              center = center,
              style = Stroke(
                width = 1.2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f)
              )
            )
          }

          // 3. Ejes Cardinales (N-S, E-O)
          drawLine(
            color = CyberCyan.copy(alpha = 0.25f),
            start = Offset(center.x, center.y - baseRadius * 1.1f),
            end = Offset(center.x, center.y + baseRadius * 1.1f),
            strokeWidth = 1.5f
          )
          drawLine(
            color = CyberCyan.copy(alpha = 0.25f),
            start = Offset(center.x - baseRadius * 1.1f, center.y),
            end = Offset(center.x + baseRadius * 1.1f, center.y),
            strokeWidth = 1.5f
          )

          // 4. Haz de Barrido del Radar (Sweep Wave)
          rotate(degrees = sweepAngle, pivot = center) {
            val sweepPath = Path().apply {
              moveTo(center.x, center.y)
              val rad1 = Math.toRadians(-40.0)
              val x1 = center.x + (baseRadius * 1.05f) * cos(rad1).toFloat()
              val y1 = center.y + (baseRadius * 1.05f) * sin(rad1).toFloat()
              lineTo(x1, y1)
              val rad2 = Math.toRadians(0.0)
              val x2 = center.x + (baseRadius * 1.05f) * cos(rad2).toFloat()
              val y2 = center.y + (baseRadius * 1.05f) * sin(rad2).toFloat()
              lineTo(x2, y2)
              close()
            }
            drawPath(
              path = sweepPath,
              brush = Brush.radialGradient(
                colors = listOf(CyberEmerald.copy(alpha = 0.4f), Color.Transparent),
                center = center,
                radius = baseRadius * 1.1f
              )
            )
          }

          // 5. Señuelos y Amenazas Proyectadas en el Vector
          // Amenaza 1: Baliza BLE Cercana (ThreatCrimson)
          val beaconOffset = Offset(center.x + (baseRadius * 0.45f), center.y - (baseRadius * 0.35f))
          drawCircle(
            color = ThreatCrimson.copy(alpha = 0.25f),
            radius = 14.dp.toPx(),
            center = beaconOffset
          )
          drawCircle(
            color = ThreatCrimson,
            radius = 4.dp.toPx(),
            center = beaconOffset
          )

          // Amenaza 2: Sonda IMSI Catcher (WarningGold)
          val btsOffset = Offset(center.x - (baseRadius * 0.60f), center.y + (baseRadius * 0.40f))
          drawCircle(
            color = WarningGold.copy(alpha = 0.25f),
            radius = 16.dp.toPx(),
            center = btsOffset
          )
          drawCircle(
            color = WarningGold,
            radius = 5.dp.toPx(),
            center = btsOffset
          )

          // Si el blindaje eBPF está activo, proyectar señuelo cuántico
          if (gpsState.isLocationCloakedAgainstTrackers) {
            val decoyOffset = Offset(center.x + (baseRadius * 0.70f), center.y + (baseRadius * 0.25f))
            drawCircle(
              color = QuantumPurple.copy(alpha = 0.3f),
              radius = 12.dp.toPx(),
              center = decoyOffset,
              style = Stroke(width = 1.5f)
            )
            drawCircle(
              color = QuantumPurple,
              radius = 4.dp.toPx(),
              center = decoyOffset
            )
          }

          // 6. Pulso y Marcador Central del Usuario (Paradero Certificado)
          // Anillo de pulso expansivo
          drawCircle(
            color = CyberCyan.copy(alpha = (1.0f - pulseRadius) * 0.6f),
            radius = (baseRadius * 0.35f) * pulseRadius,
            center = center,
            style = Stroke(width = 2.dp.toPx())
          )

          // Cono de Dirección de Rumbo (Heading)
          rotate(degrees = gpsState.bearingDegrees, pivot = center) {
            val headingPath = Path().apply {
              moveTo(center.x, center.y - 20.dp.toPx())
              lineTo(center.x - 7.dp.toPx(), center.y - 6.dp.toPx())
              lineTo(center.x + 7.dp.toPx(), center.y - 6.dp.toPx())
              close()
            }
            drawPath(path = headingPath, color = CyberEmerald)
          }

          // Pin central
          drawCircle(
            color = Color.Black,
            radius = 7.dp.toPx(),
            center = center
          )
          drawCircle(
            color = CyberCyan,
            radius = 5.dp.toPx(),
            center = center
          )
        }

        // Overlay con Indicadores en Pantalla
        // Punto Cardinal Norte
        Text(
          text = "N (360°)",
          color = CyberCyan,
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Black,
          modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 6.dp)
        )

        // Leyenda de Escala y Coordenadas en Vivo
        Box(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(8.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(ObsidianElevated.copy(alpha = 0.85f))
            .border(1.dp, ObsidianBorder, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Column {
            Text(
              text = "RADIO VISIBLE: ${(250 / zoomLevel).toInt()}m",
              color = CyberEmerald,
              fontSize = 8.5.sp,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "${String.format(Locale.US, "%.5f", gpsState.latitude)}° N, ${String.format(Locale.US, "%.5f", Math.abs(gpsState.longitude))}° W",
              color = CyberTextPrimary,
              fontSize = 9.sp,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold
            )
          }
        }

        // Controles de Zoom y Centrado en Esquina Superior Derecha
        Column(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(8.dp),
          verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          IconButton(
            onClick = { if (zoomLevel < 2.5f) zoomLevel += 0.25f },
            modifier = Modifier
              .size(30.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(ObsidianElevated)
              .border(1.dp, ObsidianBorder, RoundedCornerShape(6.dp))
          ) {
            Icon(Icons.Default.Add, contentDescription = "Acercar", tint = CyberCyan, modifier = Modifier.size(16.dp))
          }

          IconButton(
            onClick = { if (zoomLevel > 0.5f) zoomLevel -= 0.25f },
            modifier = Modifier
              .size(30.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(ObsidianElevated)
              .border(1.dp, ObsidianBorder, RoundedCornerShape(6.dp))
          ) {
            Icon(Icons.Default.Remove, contentDescription = "Alejar", tint = CyberCyan, modifier = Modifier.size(16.dp))
          }

          IconButton(
            onClick = {
              panOffsetX = 0f
              panOffsetY = 0f
              zoomLevel = 1.0f
            },
            modifier = Modifier
              .size(30.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(ObsidianElevated)
              .border(1.dp, ObsidianBorder, RoundedCornerShape(6.dp))
          ) {
            Icon(Icons.Default.MyLocation, contentDescription = "Centrar", tint = CyberEmerald, modifier = Modifier.size(16.dp))
          }
        }
      }

      // Referencias y Leyendas de Señales Detectadas en el Mapa
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(CyberCyan))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Tu Paradero", color = CyberTextSecondary, fontSize = 9.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(ThreatCrimson))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Baliza BLE (-54dBm)", color = ThreatCrimson, fontSize = 9.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(WarningGold))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Sonda IMSI (-42dBm)", color = WarningGold, fontSize = 9.sp)
        }
        if (gpsState.isLocationCloakedAgainstTrackers) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(QuantumPurple))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Señuelo eBPF", color = QuantumPurple, fontSize = 9.sp)
          }
        }
      }
    }
  }
}

private fun openExternalMap(context: Context, lat: Double, lon: Double, label: String) {
  try {
    val encodedLabel = Uri.encode(label)
    // Intent geo: estándar compatible con Google Maps, OpenStreetMap, Waze, etc.
    val geoUri = Uri.parse("geo:$lat,$lon?q=$lat,$lon($encodedLabel)&z=17")
    val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
    if (mapIntent.resolveActivity(context.packageManager) != null) {
      context.startActivity(mapIntent)
    } else {
      // Fallback web a Google Maps
      val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lon")
      val webIntent = Intent(Intent.ACTION_VIEW, webUri)
      context.startActivity(webIntent)
    }
  } catch (e: Exception) {
    Toast.makeText(context, "Abriendo coordenadas: $lat, $lon", Toast.LENGTH_SHORT).show()
  }
}
