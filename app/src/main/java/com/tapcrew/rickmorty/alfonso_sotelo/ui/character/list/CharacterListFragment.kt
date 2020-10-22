package com.tapcrew.rickmorty.alfonso_sotelo.ui.character.list

import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.tapcrew.rickmorty.alfonso_sotelo.R
import com.tapcrew.rickmorty.alfonso_sotelo.base.BaseFragment
import com.tapcrew.rickmorty.alfonso_sotelo.utils.getMessageString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_character_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment: BaseFragment(R.layout.fragment_character_list) {

    private val adapter by lazy { CharacterAdapter() }
    private val viewModel: CharacterListViewModel by viewModels()

    private var loadJob: Job? = null

    private fun loadCharacters(searchByName: String? = null) {
        loadJob?.cancel()
        loadJob = lifecycleScope.launch {
            viewModel.loadCharacters(searchByName).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter

        super.onActivityCreated(savedInstanceState)
    }

    override fun addObservers() {
        loadCharacters()

        etSearchByName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateListFromInput()
                true
            } else {
                false
            }
        }

        etSearchByName.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateListFromInput()
                true
            } else {
                false
            }
        }

        adapter.addLoadStateListener { loadState ->
            loadState.getMessageString(requireContext())?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateListFromInput() {
        etSearchByName.text?.trim()?.toString()?.let {
            loadCharacters(it)
        }
    }
}