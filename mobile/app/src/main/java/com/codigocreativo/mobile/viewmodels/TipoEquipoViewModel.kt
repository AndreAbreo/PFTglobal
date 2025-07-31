package com.codigocreativo.mobile.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codigocreativo.mobile.features.modelo.Modelo
import com.codigocreativo.mobile.features.tipoEquipo.TipoEquipo
import com.codigocreativo.mobile.features.tipoEquipo.TipoEquipoApiService
import com.codigocreativo.mobile.network.DataRepository
import com.codigocreativo.mobile.network.RetrofitClient
import com.codigocreativo.mobile.utils.Estado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TipoEquipoViewModel : ViewModel() {

    private val dataRepository = DataRepository()

    val tipoEquipoList = MutableLiveData<List<TipoEquipo>>()

    fun loadModelos(token: String) {
        viewModelScope.launch {
            try {
                val retrofit = RetrofitClient.getClient(token)
                val apiService = retrofit.create(TipoEquipoApiService::class.java)
                val call = apiService.listarTipoEquipos("Bearer $token")

                val result = withContext(Dispatchers.IO) {
                    dataRepository.obtenerDatos(
                        token = token,
                        apiCall = { call }
                    )
                }

                result.onSuccess { tipoEquipos ->
                    tipoEquipoList.value = tipoEquipos // Actualiza la lista de modelos en LiveData
                }.onFailure { error ->

                }
            } catch (e: Exception) {

            }
        }
    }

    fun getModeloById(id: Int): TipoEquipo? {
        return tipoEquipoList.value?.find { it.id == id }
    }

    fun actualizarEstadoModelo(id: Int, nuevoEstado: Estado) {
        tipoEquipoList.value = tipoEquipoList.value?.map { tipoEquipo ->
            if (tipoEquipo.id == id) {
                tipoEquipo.estado = nuevoEstado
            }
            tipoEquipo
        }
    }
}
