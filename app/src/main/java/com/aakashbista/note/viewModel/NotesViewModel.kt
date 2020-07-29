package com.aakashbista.note.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.aakashbista.note.BaseViewModel
import com.aakashbista.note.db.Note
import com.aakashbista.note.repository.NoteRepository
import com.aakashbista.note.state.ToolbarState
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : BaseViewModel(application) {

    private var repository: NoteRepository = NoteRepository(application)
    lateinit var notes: LiveData<List<Note>>


    private val _selectedNotes: MutableLiveData<List<Note>> = MutableLiveData()
    val selectedNotes: LiveData<List<Note>>
        get() = _selectedNotes

    private val _toolbarState: MutableLiveData<ToolbarState> = MutableLiveData(
        ToolbarState.NormalViewState
    )

    val toolbarState: LiveData<ToolbarState>
        get() = _toolbarState


    init {
        getAllNotes()
    }

    private fun getAllNotes() {
        notes = repository.getNotes()
    }

    fun isMultiSelectionStateActive(): Boolean {
        return _toolbarState.value == ToolbarState.MultiSelectionState
    }

    fun deleteNote(note: Note) {
        launch {
            repository.deleteNote(note)
        }
    }

    fun setToolbarState(state: ToolbarState) {
        _toolbarState.value = state
    }

    fun getSelectedNotes(): List<Note> = _selectedNotes.value ?: emptyList()


    fun addOrRemoveNoteFromSelectedList(note: Note) {
        val list = _selectedNotes.value?.toMutableList() ?: mutableListOf()
        if (list.contains(note)) {
            list.remove(note)
        } else {
            list.add(note)
        }
        if(list.isEmpty()){
            setToolbarState(ToolbarState.NormalViewState)
        }
        _selectedNotes.value = list
    }

    fun clearSelectedNotes() {
        _selectedNotes.value = null
    }


}
