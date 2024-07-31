package com.example.zomatoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentLogingBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogingFragment : Fragment() {
    var Aut=Firebase.auth
    lateinit var binding: FragmentLogingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLogingBinding.inflate(layoutInflater,container,false)
        binding.signUpButton.setOnClickListener {
            moveFragment(SingUpFragment())
        }
        binding.signUpButton.setOnClickListener {
            moveFragment(SingUpFragment())
        }
        binding.loginButton.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE
            loginFun()
        }
        return binding.root
    }

    private fun loginFun() {
        val email=binding.emailEditText.text.toString()
        val password=binding.passwordEditText.text.toString()
        Aut.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if (it.isSuccessful){
                binding.progressBar.visibility=View.GONE

                moveFragment(DashboardFragment())
            }
        }.addOnFailureListener {
            binding.progressBar.visibility=View.GONE
            Toast.makeText(requireContext(),"Email Or Password Invlaid",Toast.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.emailEditText.text.clear()
        binding.passwordEditText.text.clear()
    }
    fun moveFragment(frag:Fragment){
    parentFragmentManager.beginTransaction()
        .replace(R.id.mainFram,frag)
        .addToBackStack(null)
        .commit()
}
}