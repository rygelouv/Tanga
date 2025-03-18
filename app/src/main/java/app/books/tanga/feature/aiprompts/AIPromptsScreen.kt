package app.books.tanga.feature.aiprompts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.books.tanga.coreui.components.ContentSize
import app.books.tanga.coreui.components.SimpleTopBar
import app.books.tanga.coreui.components.Tag
import app.books.tanga.coreui.resources.asString

@Composable
fun AIPromptsScreen(
    state: AIPromptsUiState,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onPromptClick: (String) -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            SimpleTopBar(onNavigateBack)
        }
    ) {
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            state.prompts?.let {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(it.size) { index ->
                        val prompt = it[index]
                        Tag(
                            text = prompt.title.asString(context.resources),
                            modifier = Modifier.height(54.dp),
                            shape = RoundedCornerShape(8.dp),
                            hasBorder = true,
                            icon = prompt.icon,
                            isSelectable = false,
                            isSelected = true,
                            contentSize = ContentSize.Medium,
                            onSelect = { onPromptClick(prompt.id) },
                            onUnselect = { }
                        )
                    }
                }
            }
        }
    }
}
