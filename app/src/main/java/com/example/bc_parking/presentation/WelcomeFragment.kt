package com.example.bc_parking.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bc_parking.R
import com.example.bc_parking.databinding.FragmentWelcomeBinding

class WelcomeFragment: Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonOutsidePark1.setOnClickListener {
            Toast.makeText(context, "HIGHGUI", Toast.LENGTH_LONG).show()
            launchChooseLevelFragment()

        }
        binding.buttonOutsidePark2.setOnClickListener {
            Toast.makeText(context, "H1111I", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_welcomeFragment_to_XFragment)

        }



    }
    private fun launchChooseLevelFragment() {
        findNavController().navigate(R.id.action_welcomeFragment_to_outsideParkingFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}