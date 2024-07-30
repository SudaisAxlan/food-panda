package com.example.zomatoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentLogingBinding

class LogingFragment : Fragment() {
    lateinit var binding: FragmentLogingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLogingBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

}