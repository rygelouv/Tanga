package app.books.tanga.coreui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TangaBottomSheetContainer(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        // Show the full sheet when expanded
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            onDismiss()
        },
        sheetState = bottomSheetState
    ) {
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TangaBottomSheet(
    data: BottomSheetData,
    modifier: Modifier = Modifier
) {
    val bottomSheetState = rememberModalBottomSheetState(
        // Show the full sheet when expanded
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = {
            data.onDismiss()
        },
        sheetState = bottomSheetState
    ) {
        ActionContent(
            data = data.actionData,
            modifier = modifier
        )
    }
}

data class BottomSheetData(
    val actionData: ActionData,
    val onDismiss: () -> Unit = {}
)
