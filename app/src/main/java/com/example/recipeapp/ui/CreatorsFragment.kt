package com.example.recipeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.databinding.FragmentCreatorsBinding

class CreatorsFragment : Fragment() {

    private var _binding: FragmentCreatorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val creatorsList = listOf(
            Creator("Diana Adel"),
            Creator("Menna Mohamed"),
            Creator("Nourhan Tark"),
            Creator("Hadir")
        )

        val adapter = CreatorsAdapter(creatorsList)
        binding.creatorsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.creatorsRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
