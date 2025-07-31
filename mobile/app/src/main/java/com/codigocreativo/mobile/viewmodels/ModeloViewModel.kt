package com.codigocreativo.mobile.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codigocreativo.mobile.features.modelo.Modelo
import com.codigocreativo.mobile.features.modelo.ModeloApiService
import com.codigocreativo.mobile.network.DataRepository
import com.codigocreativo.mobile.network.RetrofitClient
import com.codigocreativo.mobile.utils.Estado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModeloViewModel : ViewModel() {

    private val dataRepository = DataRepository()

    val modelosList = MutableLiveData<List<Modelo>>()

    fun loadModelos(token: String) {
        viewModelScope.launch {
            try {
                val retrofit = RetrofitClient.getClient(token)
                val apiService = retrofit.create(ModeloApiService::class.java)
                val call = apiService.listarModelos("Bearer $token")

                val result = withContext(Dispatchers.IO) {
                    dataRepository.obtenerDatos(
                        token = token,
                        apiCall = { call }
                    )
                }

                result.onSuccess { modelos ->
                    modelosList.value = modelos // Actualiza la lista de modelos en LiveData
                }.onFailure { error ->

                }
            } catch (e: Exception) {

            }
        }
    }

    fun getModeloById(id: Int): Modelo? {
        return modelosList.value?.find { it.id == id }
    }

    fun actualizarEstadoModelo(id: Int, nuevoEstado: Estado) {
        modelosList.value = modelosList.value?.map { modelo ->
            if (modelo.id == id) {
                modelo.estado = nuevoEstado
            }
            modelo
        }
    }
}
