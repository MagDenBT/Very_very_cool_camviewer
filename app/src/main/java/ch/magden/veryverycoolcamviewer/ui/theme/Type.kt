package ch.magden.veryverycoolcamviewer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ch.magden.veryverycoolcamviewer.R

val circeFamily = FontFamily(
    Font(R.font.circe_regular, FontWeight.Normal)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = circeFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 25.06.sp

    )
)

val roomTitleTextStyle = TextStyle(
    fontFamily = circeFamily,
    fontWeight = FontWeight.W300,
    fontSize = 21.sp,
    lineHeight = 30.95.sp,
    color = black_medium
)

val tabTextStyle = TextStyle(
    fontFamily = circeFamily,
    fontSize = 17.sp,
    lineHeight = 16.sp,
    color = black_medium
)

val appHeaderTextStyle = TextStyle(
    fontFamily = circeFamily,
    fontSize = 21.sp,
    lineHeight = 26.sp,
    color = black_medium
)
