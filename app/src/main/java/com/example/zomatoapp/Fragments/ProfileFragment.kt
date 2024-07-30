package com.example.zomatoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(layoutInflater,container,false)
        binding.YourCart.setOnClickListener {
            cartFun()
        }
        return binding.root
    }

    private fun cartFun() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainFram,CartFragment())
            .addToBackStack(null)
            .commit()
    }

}