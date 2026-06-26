package com.madhumarga.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.madhumarga.app.MadhuMargaApp
import com.madhumarga.app.data.ActivityLevel
import com.madhumarga.app.data.HarvestEntity
import com.madhumarga.app.data.HiveEntity
import com.madhumarga.app.data.HiveRepository
import com.madhumarga.app.data.HoneyFlowLevel
import com.madhumarga.app.data.InspectionEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HiveViewModel(private val repo: HiveRepository) : ViewModel() {

    val hives: StateFlow<List<HiveEntity>> = repo.observeHives()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val mostRecentInspection: StateFlow<InspectionEntity?> = repo.observeMostRecentInspection()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val allHarvests: StateFlow<List<HarvestEntity>> = repo.observeAllHarvests()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun inspectionsFor(hiveId: Long) = repo.observeInspections(hiveId)
    fun harvestsFor(hiveId: Long) = repo.observeHarvests(hiveId)
    fun hive(id: Long) = repo.observeHive(id)

    fun addHive(tag: String, location: String, notes: String, onDone: (Long) -> Unit = {}) {
        viewModelScope.launch {
            val id = repo.upsertHive(HiveEntity(tag = tag, location = location, notes = notes))
            onDone(id)
        }
    }

    fun deleteHive(id: Long) {
        viewModelScope.launch { repo.deleteHive(id) }
    }

    fun addInspection(
        hiveId: Long,
        queenSeen: Boolean,
        pestsSeen: Boolean,
        honeyFlow: HoneyFlowLevel,
        activityLevel: ActivityLevel,
        temperatureC: Float?,
        notes: String,
        onDone: () -> Unit = {}
    ) {
        viewModelScope.launch {
            repo.addInspection(
                InspectionEntity(
                    hiveId = hiveId,
                    queenSeen = queenSeen,
                    pestsSeen = pestsSeen,
                    honeyFlow = honeyFlow,
                    activityLevel = activityLevel,
                    temperatureC = temperatureC,
                    notes = notes
                )
            )
            onDone()
        }
    }

    fun addHarvest(hiveId: Long, quantityKg: Float, notes: String, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repo.addHarvest(HarvestEntity(hiveId = hiveId, quantityKg = quantityKg, notes = notes))
            onDone()
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MadhuMargaApp
                HiveViewModel(app.repository)
            }
        }
    }
}
