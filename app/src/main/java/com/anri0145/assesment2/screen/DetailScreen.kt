package com.anri0145.assesment2.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.anri0145.assesment2.R
import com.anri0145.assesment2.navigation.Screen
import com.anri0145.assesment2.ui.theme.Assesment2Theme
import com.anri0145.assesment2.util.ViewModelFactory


const val KEY_ID_PENGELUARAN = "idPengeluaran"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var jumlah by remember { mutableStateOf("") }
    var judul by remember { mutableStateOf("") }
    var pengeluaran by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    val radioOptions = listOf(
        "Senin",
        "Selasa",
        "Rabu",
        "Kamis",
        "Jumat",
        "Sabtu",
        "Minggu"
    )

    var dayOption by remember { mutableStateOf(radioOptions[0]) }

    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getPengeluaran(id) ?: return@LaunchedEffect
        judul = data.judul
        pengeluaran = data.belanja
        jumlah = data.jumlah
        dayOption = data.hari
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.surfaceBright
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_pengeluaran))
                    else
                        Text(text = stringResource(id = R.string.edit_pengeluaran))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.surfaceBright
                ),
                actions = {
                    IconButton(onClick = {
                        if (judul == "" || pengeluaran == "" || jumlah == "") {
                            Toast.makeText(context, R.string.invalid, Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(judul, pengeluaran, jumlah, dayOption)
                        } else {
                            viewModel.update(id, judul, pengeluaran, jumlah, dayOption)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.surfaceBright
                        )
                    }
                    if (id != null) {
                        DeleteAction { showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false },
                            onConfirmation = {
                                showDialog = false
                                viewModel.cycleDelete(id)
                                navController.navigate(Screen.Main.route)
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->
        FormCatatan(
            title = judul,
            onTitleChange = { judul = it },
            desc = pengeluaran,
            onDescChange = { pengeluaran = it },
            jumlah = jumlah,
            onjumlahChange = { jumlah = it },
            day = dayOption,
            ondayChange = { dayOption = it },
            radioOption = radioOptions,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.surfaceBright
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormCatatan(
    title: String, onTitleChange: (String) -> Unit,
    desc: String, onDescChange: (String) -> Unit,
    jumlah: String, onjumlahChange: (String) -> Unit,
    day: String, ondayChange: (String) -> Unit,
    radioOption: List<String>,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(R.string.judul)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = desc,
            onValueChange = { onDescChange(it) },
            label = { Text(text = stringResource(R.string.isi_pengeluaran)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = jumlah,
            onValueChange = { onjumlahChange(it) },
            label = { Text(text = stringResource(R.string.isi_jumlah)) },
            trailingIcon = { Text(text = "Rp") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ) {
            radioOption.forEach { dayLabel ->
                DayOption(
                    label = dayLabel,
                    isSelected = day == dayLabel,
                    onClick = { ondayChange(dayLabel) }
                )
            }
        }
    }
}

@Composable
fun DayOption(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    Assesment2Theme  {
        DetailScreen(rememberNavController())
    }
}