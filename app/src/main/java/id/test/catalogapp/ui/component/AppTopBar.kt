package id.test.catalogapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTopBar(
    title: String,
    showBackPressed: Boolean = false,
    onBackPressed: () -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (showBackPressed) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(16.dp)
                    .clickable {
                        onBackPressed.invoke()
                    },
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "back"
            )
        }

        val horizontalPadding = if (showBackPressed) 0.dp else 16.dp

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = horizontalPadding),
            fontSize = 20.sp,
            fontStyle = FontStyle.Normal,
            text = title
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarPreview() {
    AppTopBar(title = "Title")
}

@Preview(showBackground = true)
@Composable
private fun AppTopBarBackPressedPreview() {
    AppTopBar(title = "Title", showBackPressed = true)
}
