package khani.behnam.to_docompose.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val lightGray = Color(0xFFFCFCFC)
val mediumGray = Color(0xFF6200EE)
val DarkGray = Color(0xFF1C1C1C)

val HighPriorityColor = Color(0xFFFF4646)
val MediumPriorityColor = Color(0xFFFFC114)
val LowPriorityColor = Color(0xFF00C980)
val NonePriorityColor = Color(0xFFFFFFFF)

val Colors.taskItemTextColor: Color
    get() = if (isLight) DarkGray else Color.LightGray

val Colors.taskItemBackgroundColor: Color
    @Composable
    get() = if (isLight) Color.White else DarkGray

// Add an Extension Property to Colors class that returns
// a color based on theme
val Colors.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) Color.White else Color.LightGray

val Colors.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Purple500 else Color.Black

val Colors.fabBarBackgroundColor: Color
    @Composable
    get() = if (isLight) Teal200 else Purple700
