package com.example.appcalculadora.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class CalculadoraViewModel : ViewModel() {

    //atributo de clase, privado, como una lista de cadenas que guarda los cálculos
    private val _history = MutableLiveData< List<String>   >( emptyList() )

    //metodo get que lo obtiene de LiveData y lo devuelve con una función get
    val history: LiveData<List<String>> get() = _history

    fun addCalculation(calculation: String)
    {
        _history.value = _history.value!!.plus(calculation)
    }

}
