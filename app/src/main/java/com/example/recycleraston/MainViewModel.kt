package com.example.recycleraston

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = Repository()
    private val _contactsData = MutableStateFlow<List<ContactsInfo>>(emptyList())
    val contactsData: StateFlow<List<ContactsInfo>> = _contactsData.asStateFlow()

    init {
        getContactsData()
    }

    private fun getContactsData() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.createContactsList(100)
            }.fold(
                onSuccess = { _contactsData.value = it },
                onFailure = { Log.d("ViewModel", it.message ?: "") }
            )
        }
    }
}