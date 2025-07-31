package com.codigocreativo.mobile.features.modelo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codigocreativo.mobile.R
import com.codigocreativo.mobile.features.proveedores.DetalleProveedorFragment
import com.codigocreativo.mobile.features.proveedores.IngresarProveedorFragment
import com.codigocreativo.mobile.features.proveedores.Proveedor
import com.codigocreativo.mobile.features.proveedores.ProveedorAdapter
import com.codigocreativo.mobile.features.proveedores.ProveedoresApiService
import com.codigocreativo.mobile.main.DashboardActivity
import com.codigocreativo.mobile.network.RetrofitClient
import com.codigocreativo.mobile.network.DataRepository
import com.codigocreativo.mobile.utils.Estado
import com.codigocreativo.mobile.utils.SessionManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.Locale

class ModelosActivity : AppCompatActivity() {

    private lateinit var adapter: ModeloAdapter
    private lateinit var recyclerView: RecyclerView
    private var modeloList = mutableListOf<Modelo>() // Lista dinámica de modelos cargados desde el API
    private var filteredList = mutableListOf<Modelo>()
    private val dataRepository = DataRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modelos)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ModeloAdapter(filteredList, this) { modelo ->
            showDetalleModeloFragment(modelo)
        }
        recyclerView.adapter = adapter

        val token = SessionManager.getToken(this)
        if (token != null) {

            loadModelos(token)
        } else {
            Snackbar.make(
                findViewById(R.id.main),
                "Token no encontrado, por favor inicia sesión",
                Snackbar.LENGTH_LONG
            ).show()
        }

        setupFilters()

        findViewById<Button>(R.id.btn_ingresar).setOnClickListener {
            val bottomSheetFragment = IngresarModeloFragment { modelo ->
                if (token != null) {
                    val retrofit = RetrofitClient.getClient(token)
                    val apiService = retrofit.create(ModeloApiService::class.java)

                    lifecycleScope.launch {
                        val result = dataRepository.guardarDatos(
                            token = token,
                            apiCall = {
                                apiService.crearModelo("Bearer $token", modelo)
                            })

                        result.onSuccess {
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Modelo ingresado correctamente",
                                Snackbar.LENGTH_LONG
                            ).show()
                            loadModelos(token)
                        }.onFailure { error ->
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                "Error al ingresar el modelo: ${error.message}",
                                Snackbar.LENGTH_LONG
                            ).show()
                            Log.e("ModelosActivity", "Error al ingresar el modelo: ${error.message}\nPayload: ${modelo}")
                        }
                    }
                } else {
                    Snackbar.make(
                        findViewById(R.id.main),
                        "Token no encontrado, por favor inicia sesión",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        findViewById<Button>(R.id.btn_volver_menu).setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val modelo = adapter.modeloList[position]

                AlertDialog.Builder(this@ModelosActivity).apply {
                    setTitle("Confirmar baja")
                    setMessage("¿Estás seguro que deseas dar de baja al modelo ${modelo.nombre}?")
                    setPositiveButton("Si") { _, _ ->
                        val token = SessionManager.getToken(this@ModelosActivity)
                        if (token != null) {
                            val retrofit = RetrofitClient.getClient(token)
                            val apiService = retrofit.create(ModeloApiService::class.java)

                            lifecycleScope.launch {
                                val result = dataRepository.guardarDatos(
                                    token = token,
                                    apiCall = { apiService.eliminarModelo("Bearer $token",
                                        modelo.id!!
                                    ) }
                                )

                                result.onSuccess {
                                    Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Modelo dado de baja correctamente",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    loadModelos(token)
                                }.onFailure { error ->
                                    Snackbar.make(
                                        findViewById(android.R.id.content),
                                        "Error al dar de baja al modelo: ${error.message}",
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                    Log.e("ModelosActivity", "Error al dar de baja al modelo: ${error.message}")
                                }
                            }
                        } else {
                            Snackbar.make(
                                findViewById(R.id.main),
                                "Token no encontrado, por favor inicia sesión",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                        adapter.notifyItemChanged(position)
                    }
                    create()
                    show()
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun loadModelos(token: String, nombre: String? = null, estado: String? = null) {
        val retrofit = RetrofitClient.getClient(token)
        val apiService = retrofit.create(ModeloApiService::class.java)

        lifecycleScope.launch {
            val result = dataRepository.obtenerDatos(
                token = token,
                apiCall = { apiService.listarModelos("Bearer $token") }
            )

            result.onSuccess { modelos ->
                modeloList.clear()
                modeloList.addAll(modelos) // Agregar los modelos obtenidos
                adapter.updateList(modeloList) // Actualizar el RecyclerView con los modelos
            }.onFailure { error ->
                Log.e("ModelosActivity", "Error al cargar los modelos: ${error.message}")
            }
        }
    }

    private fun showDetalleModeloFragment(modelo: Modelo) {
        val fragment = DetalleModeloFragment(modelo) { updatedModelo ->
            val token = SessionManager.getToken(this)
            if (token != null) {
                val retrofit = RetrofitClient.getClient(token)
                val apiService = retrofit.create(ModeloApiService::class.java)

                lifecycleScope.launch {
                    val result = dataRepository.guardarDatos(
                        token = token,
                        apiCall = { apiService.actualizar("Bearer $token", updatedModelo) }
                    )

                    result.onSuccess {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Modelo actualizado correctamente",
                            Snackbar.LENGTH_LONG
                        ).show()
                        loadModelos(token)
                    }.onFailure { error ->
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Error al actualizar el modelo: ${error.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                        Log.e("ModelosActivity", "Error al actualizar el modelo: ${error.message}\nPayload: ${updatedModelo}")
                    }
                }
            } else {
                Snackbar.make(
                    findViewById(R.id.main),
                    "Token no encontrado, por favor inicia sesión",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        fragment.show(supportFragmentManager, "DetalleModeloFragment")
    }

    private fun setupFilters() {
        val filterName: EditText = findViewById(R.id.filter_name)
        val filterStatus: Spinner = findViewById(R.id.filter_status)

        val statusAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, Estado.values())
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filterStatus.adapter = statusAdapter

        val applyFilters = {
            val token = getSharedPreferences("app_prefs", MODE_PRIVATE).getString("jwt_token", null)
            val nombre = filterName.text.toString().takeIf { it.isNotEmpty() }
            val estado = (filterStatus.selectedItem as Estado).name
            if (token != null) {
                loadModelos(token = token, nombre = nombre, estado = estado)
            }
        }

        filterName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        filterStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                applyFilters()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun filterModelos() {
        val nameFilter =
            findViewById<EditText>(R.id.filter_name).text.toString().lowercase(Locale.getDefault())
        val statusFilter = findViewById<Spinner>(R.id.filter_status).selectedItem as Estado

        filteredList = modeloList.filter { modelos ->
            modelos.nombre.lowercase(Locale.getDefault()).contains(nameFilter) &&
                    (modelos.estado == statusFilter)
        }.toMutableList()

        adapter.updateList(filteredList)
    }
}