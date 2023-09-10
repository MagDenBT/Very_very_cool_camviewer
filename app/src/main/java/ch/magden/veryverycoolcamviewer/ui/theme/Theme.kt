package ch.magden.veryverycoolcamviewer.ui.theme

import android.app.Activity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = white,
    onPrimary = black_light,
    background = white,
    onBackground = black_full,
    onSurface = black_full,
    surfaceVariant = white,
    onSurfaceVariant = gray_400,
    error = red,
    onError = black_full
)

val shapes = Shapes(
    medium = RoundedCornerShape(12.dp)
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
        shapes = shapes

    )
}
