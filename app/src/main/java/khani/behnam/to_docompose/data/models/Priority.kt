package khani.behnam.to_docompose.data.models

import androidx.compose.ui.graphics.Color
import khani.behnam.to_docompose.ui.theme.HighPriorityColor
import khani.behnam.to_docompose.ui.theme.LowPriorityColor
import khani.behnam.to_docompose.ui.theme.MediumPriorityColor
import khani.behnam.to_docompose.ui.theme.NonePriorityColor

enum class Priority(var color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}