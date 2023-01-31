package com.example.bc_parking.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bc_parking.R
import com.example.bc_parking.databinding.FragmentOutsideParking1Binding
import com.example.bc_parking.databinding.FragmentWelcomeBinding

class OutsideParkingFragment: Fragment() {

    private var _binding: FragmentOutsideParking1Binding? = null
    private val binding: FragmentOutsideParking1Binding
        get() = _binding ?: throw RuntimeException("FragmentOutsideParking1Binding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOutsideParking1Binding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
//    private fun launchChooseLevelFragment() {
//        findNavController().navigate(R.id.)
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}