package com.example.bc_parking.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class ChooseLevelFragment: Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding:FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonTest.setOnClickListener {
                launchLevel(Level.TEST)
            }
            buttonEasy.setOnClickListener {
                launchLevel(Level.EASY)
            }
            buttonMedium.setOnClickListener {
                launchLevel(Level.MEDIUM)
            }
            buttonHard.setOnClickListener {
                launchLevel(Level.HARD)
            }
        }
    }

    private fun launchLevel(level: Level){
        findNavController().navigate(ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}