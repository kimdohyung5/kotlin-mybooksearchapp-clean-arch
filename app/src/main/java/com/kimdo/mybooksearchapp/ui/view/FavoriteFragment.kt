package com.kimdo.mybooksearchapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kimdo.mybooksearchapp.databinding.FragmentFavoriteBinding
import com.kimdo.mybooksearchapp.ui.viewmodel.BookSearchViewModel


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    val binding: FragmentFavoriteBinding get() = _binding!!

    private lateinit var bookSearchViewModel: BookSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate( inflater, container, false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}