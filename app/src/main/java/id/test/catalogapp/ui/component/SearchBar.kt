package id.test.catalogapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SearchBarView(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
) {

    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        placeholder = {
            Text(text = "Type here")
        },
        singleLine = true,
        leadingIcon = {
            Image(imageVector = Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            if (text.isNotBlank())
                Image(modifier = Modifier.clickable {
                    text = ""
                    onValueChange.invoke("")
                }, imageVector = Icons.Default.Clear, contentDescription = "")
        },
        shape = MaterialTheme.shapes.large,
        onValueChange = {
            text = it
            onValueChange.invoke(it)
        }
    )

}

@Preview(showBackground = true)
@Composable
private fun SearchBarContentPreview() {
    SearchBarView()
}
