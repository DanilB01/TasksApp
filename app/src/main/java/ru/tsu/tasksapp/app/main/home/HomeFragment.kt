package ru.tsu.tasksapp.app.main.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.tsu.tasksapp.R
import ru.tsu.tasksapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val viewBinding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()


}