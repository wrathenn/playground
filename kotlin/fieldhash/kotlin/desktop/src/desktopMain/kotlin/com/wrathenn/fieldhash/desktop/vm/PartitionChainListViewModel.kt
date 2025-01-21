package com.wrathenn.fieldhash.desktop.vm

import androidx.compose.runtime.*
import com.wrathenn.fieldhash.api.model.NamedDto
import com.wrathenn.fieldhash.desktop.service.PartitionChainsHttp
import kotlinx.coroutines.*

class PartitionChainListViewModel {
    var items by mutableStateOf<List<NamedDto<Long>>>(emptyList())
        private set

    // Holds the error state
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Fetch all items from the repository
    fun fetchItems() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = PartitionChainsHttp.getAll()
                withContext(Dispatchers.Main) {
                    items = result
                    errorMessage = null
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    errorMessage = "Failed to fetch items: ${e.localizedMessage}"
                }
            }
        }
    }
}