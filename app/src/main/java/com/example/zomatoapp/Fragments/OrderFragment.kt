package com.example.zomatoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentOrderBinding
import com.example.zomatoapp.databinding.FragmentOrdersBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class OrderFragment : Fragment() {
    var Aut=Firebase.auth
    lateinit var binding: FragmentOrderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOrderBinding.inflate(layoutInflater,container,false)
        binding.orderLoginBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram,LogingFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.orderSngUpBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram,SingUpFragment())
                .addToBackStack(null)
                .commit()
        }
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        if (Aut.currentUser!=null){
            binding.orderLoginBtn.visibility=View.GONE
            binding.orderSngUpBtn.visibility=View.GONE
        }

    }

}