package com.example.roomcrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomcrud.dao.AppDatabase
import com.example.roomcrud.dao.Cliente
import com.example.roomcrud.dao.ClienteRepository
import com.example.roomcrud.dao.ClienteViewModel
import com.example.roomcrud.dao.ClienteViewModelFactory
import com.example.roomcrud.ui.theme.RoomCRUDTheme
import kotlinx.coroutines.launch

class ClienteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear la base de datos y el repositorio
        val database = AppDatabase.getInstance(this)
        val repository = ClienteRepository(database.clienteDao())

        setContent {
            RoomCRUDTheme {
                ClienteScreen(repository = repository)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClienteScreen(repository: ClienteRepository) {
    val viewModel: ClienteViewModel = viewModel(
        factory = ClienteViewModelFactory(repository)
    )

    // Observa los datos de clientes en el ViewModel
    val clientes by viewModel.clientes.collectAsState(initial = emptyList())

    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var membresia by remember { mutableStateOf("") }
    var isEdit by remember { mutableStateOf(false) }
    var selectedCliente: Cliente? by remember { mutableStateOf(null) }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Gestión de Clientes") }) },
        content = { padding ->
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(clientes.size) { index ->
                        val cliente = clientes[index]
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = "Nombre: ${cliente.nombre}")
                                Text(text = "Edad: ${cliente.edad}")
                                Text(text = "Membresía: ${cliente.membresia}")
                            }
                            Row {
                                IconButton(onClick = {
                                    isEdit = true
                                    selectedCliente = cliente
                                    nombre = cliente.nombre
                                    edad = cliente.edad.toString()
                                    membresia = cliente.membresia
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                                }
                                IconButton(onClick = {
                                    scope.launch { viewModel.eliminarCliente(cliente) }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Campos de texto y botón
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = { Text("Nombre") }
                )
                TextField(
                    value = edad,
                    onValueChange = { edad = it },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = { Text("Edad") }
                )
                TextField(
                    value = membresia,
                    onValueChange = { membresia = it },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    label = { Text("Membresía") }
                )

                Button(
                    onClick = {
                        println("Botón de agregar/actualizar presionado")
                        val edadInt = edad.toIntOrNull()
                        if (edadInt == null) {
                            println("Error: Edad no válida")
                            // Manejar error: edad no válida
                            return@Button
                        }
                        if (isEdit && selectedCliente != null) {
                            println("Actualizando cliente: $selectedCliente")
                            scope.launch {
                                viewModel.actualizarCliente(
                                    Cliente(
                                        id = selectedCliente!!.id,
                                        nombre = nombre,
                                        edad = edadInt,
                                        membresia = membresia
                                    )
                                )
                                println("Cliente actualizado correctamente")
                                // Resetear los campos y estado
                                isEdit = false
                                selectedCliente = null
                                nombre = ""
                                edad = ""
                                membresia = ""
                            }
                        } else {
                            println("Agregando cliente: Nombre=$nombre, Edad=$edadInt, Membresía=$membresia")
                            scope.launch {
                                viewModel.agregarCliente(
                                    Cliente(
                                        nombre = nombre,
                                        edad = edadInt,
                                        membresia = membresia
                                    )
                                )
                                println("Cliente agregado correctamente")
                                // Limpiar los campos
                                nombre = ""
                                edad = ""
                                membresia = ""
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text(if (isEdit) "Actualizar Cliente" else "Agregar Cliente")
                }

            }
        }
    )
}
