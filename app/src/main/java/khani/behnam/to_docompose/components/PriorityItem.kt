package khani.behnam.to_docompose.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import khani.behnam.to_docompose.data.models.Priority
import khani.behnam.to_docompose.ui.theme.LARGE_PADDING
import khani.behnam.to_docompose.ui.theme.PRIORITY_INDICATOR_SIZE
import khani.behnam.to_docompose.ui.theme.Typography

@Composable
fun PriorityItem(priority: Priority){
    Row(verticalAlignment = Alignment.CenterVertically){
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)){
            // onDraw is a lambda that is the last argument of Canvas, so we can set a body for it
            drawCircle(color = priority.color)
        }
        Text(modifier = Modifier.padding(start = LARGE_PADDING),
            text = priority.name,
            style = Typography.subtitle1,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
@Preview
fun PriorityItemPreview(){
    PriorityItem(priority = Priority.LOW)
}