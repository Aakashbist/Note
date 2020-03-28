package com.aakashbista.note.UI.Fragment

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.aakashbista.note.DB.Note
import com.aakashbista.note.R
import com.aakashbista.note.UI.Extension.toast
import com.aakashbista.note.UI.ViewModel.AddNotesViewModel
import kotlinx.android.synthetic.main.add_notes_fragment.*
import kotlinx.coroutines.CoroutineScope

class AddNotesFragment :Fragment() {

    companion object {
        fun newInstance() = AddNotesFragment()
    }
 var note:Note?=null
    private lateinit var viewModel:AddNotesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_notes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddNotesViewModel(Application())::class.java)

        arguments?.let{
            val id=AddNotesFragmentArgs.fromBundle(it).noteId
           note= viewModel.getNoteById(id)
            noteTilte.setText(note?.title)
            noteBody.setText(note?.note)

        }
        btn_save.setOnClickListener{

            val noteTitle=noteTilte.text.toString().trim()
            val body=noteBody.text.toString().trim()

            if(noteTitle.isEmpty()){
                noteTilte.error="required"
                noteTilte.setTextColor(Color.RED)
                noteTilte.requestFocus()
                return@setOnClickListener
            }
            if(body.isEmpty()){
                noteBody.error="required"
                noteBody.setTextColor(Color.RED)
                noteBody.requestFocus()
                return@setOnClickListener
            }

            context?.let {
                val mNote= Note(noteTitle,body)
                if(note==null){
                    viewModel.addNote(mNote)
                    it.toast("Note Added")
                }else{
                    mNote.id=note!!.id
                    viewModel.update(mNote)
                    it.toast("Note updated")
                }
            }

            val action=AddNotesFragmentDirections.actionAddNotesToNotes()
            Navigation.findNavController(it).navigate(action)
        }

    }

}
