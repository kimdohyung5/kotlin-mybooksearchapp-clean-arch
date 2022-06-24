package com.kimdo.mybooksearchapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kimdo.mybooksearchapp.databinding.FragmentSearchBinding
import com.kimdo.mybooksearchapp.ui.adapter.BookSearchAdapter
import com.kimdo.mybooksearchapp.ui.adapter.BookSearchLoadStateAdapter
import com.kimdo.mybooksearchapp.ui.adapter.BookSearchPagingAdapter
import com.kimdo.mybooksearchapp.ui.viewmodel.SearchViewModel
import com.kimdo.mybooksearchapp.util.Constants.SEARCH_BOOKS_TIME_DELAY
import com.kimdo.mybooksearchapp.util.collectLatestStateFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    //private lateinit var bookSearchViewModel: BookSearchViewModel
    private val searchViewModel by viewModels<SearchViewModel>()
    //private lateinit var bookSearchAdapter: BookSearchAdapter
    private lateinit var bookSearchAdapter: BookSearchPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate( inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        searchBooks()
        setupLoadState()

//        bookSearchViewModel.searchResult.observe(viewLifecycleOwner) { response ->
//            val books = response.documents
//            bookSearchAdapter.submitList( books )
//        }

        collectLatestStateFlow(searchViewModel.searchPagingResult) {
            bookSearchAdapter.submitData(it)
        }
    }

    private fun setupRecyclerView() {
        bookSearchAdapter = BookSearchPagingAdapter()
        binding.rvBooks.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration( requireContext(), DividerItemDecoration.VERTICAL)
            )
            adapter = bookSearchAdapter.withLoadStateFooter(
                footer = BookSearchLoadStateAdapter(bookSearchAdapter::retry)
            )
        }
        bookSearchAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToBookFragment(it)
            findNavController().navigate( action )
        }
    }

    private fun searchBooks() {
        var startTime = System.currentTimeMillis()
        var endTime: Long

        binding.editText.text = Editable.Factory.getInstance().newEditable( searchViewModel.query )

        binding.editText.addTextChangedListener {  text: Editable? ->
            endTime = System.currentTimeMillis()
            if( endTime - startTime >= SEARCH_BOOKS_TIME_DELAY) {
                text?.let {
                    val query = it.toString().trim()
                    if(query.isNotEmpty()) {
//                        bookSearchViewModel.searchBooks(query)
                        searchViewModel.searchBooksPaging(query)
                        searchViewModel.query = query
                    }
                }
            }
            startTime = endTime

        }

    }

    private fun setupLoadState() {
        bookSearchAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isListEmpty = bookSearchAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            binding.tvEmptyList.isVisible = isListEmpty
            binding.rvBooks.isVisible = !isListEmpty
            binding.progressBar.isVisible = loadState.refresh is LoadState.Loading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}