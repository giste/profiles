package org.giste.profiles.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.giste.profiles.R

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileScreen(
        name = "Profile 1",
        onNameChange = {}
    )
}

@Composable
fun ProfileScreen(
    name: String,
    onNameChange: (String) -> Unit
) {
    Column(modifier = Modifier.padding(8.dp)) {
        ProfileName(
            name = name,
            onChange = onNameChange
        )
    }
}

@Composable
fun ProfileName(
    name: String,
    onChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = name,
            onValueChange = onChange,
            singleLine = true,
            placeholder = { Text(stringResource(id = R.string.profile_screen_name_label)) },
            modifier = Modifier.weight(1F)
        )
    }
}

@Composable
fun ProfileToolBar() {
    TopAppBar { Text(stringResource(id = R.string.profile_screen_title)) }
}