package com.aakashbista.note.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aakashbista.note.BaseViewModel
import com.aakashbista.note.db.Note
import com.aakashbista.note.repository.NoteRepository
import com.aakashbista.note.state.ToolbarState
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : BaseViewModel(application) {

    companion object{
        const val PAGE_SIZE=20
        const val MAX_SIZE=200
    }

    private var repository: NoteRepository = NoteRepository(application)

    private val _selectedNotes: MutableLiveData<List<Note>> = MutableLiveData()
    val selectedNotes: LiveData<List<Note>>
        get() = _selectedNotes

    private val _toolbarState: MutableLiveData<ToolbarState> = MutableLiveData(
        ToolbarState.NormalViewState
    )

    val toolbarState: LiveData<ToolbarState>
        get() = _toolbarState


    val noteLists = Pager(
        PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true,
            maxSize = MAX_SIZE
        )
    ) {
        repository.getPagedNotes()
    }.flow


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
        if (list.isEmpty()) {
            setToolbarState(ToolbarState.NormalViewState)
        }
        _selectedNotes.value = list
    }

    fun clearSelectedNotes() {
        _selectedNotes.value = null
    }
}
