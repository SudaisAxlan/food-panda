package com.example.zomatoapp.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentSingUpBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SingUpFragment : Fragment() {
    lateinit var binding: FragmentSingUpBinding
    var db=Firebase.firestore
    var Aut=Firebase.auth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSingUpBinding.inflate(layoutInflater,container,false)
        binding.loginButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram,LogingFragment()).addToBackStack(null).commit()
        }
        binding.signUpButton.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE
            singUp()
        }
        binding.profileImagePicker.setOnClickListener {
            uploadImage()
        }
        return binding.root
    }

    private fun uploadImage() {
        val intent=Intent()
        intent.action=Intent.ACTION_GET_CONTENT
        intent.type="image/"
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1){
            val imageUri:Uri?=data?.data
            binding.profileImagePicker.setImageURI(imageUri)
        }
    }
    private fun singUp() {
        val email=binding.emailEditText.text.toString()
        val password=binding.passwordEditText.text.toString()
        Aut.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    binding.progressBar.visibility=View.GONE
                    userAddDb()
                    Toast.makeText(requireContext(),"Acount are created successfull",Toast.LENGTH_LONG).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.mainFram,DashboardFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }

            .addOnFailureListener {
                Toast.makeText(requireContext(),"Acount already created ",Toast.LENGTH_LONG).show()

                binding.progressBar.visibility=View.GONE

            }
    }
    fun userAddDb(){
        val userName=binding.firstNameEditText.text.toString()+binding.lastNameEditText.text.toString()
        val email=binding.emailEditText.text.toString()
        val password=binding.passwordEditText.text.toString()
        val uid=Aut.currentUser?.uid
        val map= hashMapOf(
            "UserName" to userName,
            "email" to email,
            "password" to password,
            "uid" to uid
        )
        db.collection("Users")
            .add(map)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"User Not Added in DB",Toast.LENGTH_LONG).show()
            }
    }

    fun moveFragment(frag:Fragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainFram,frag)
            .addToBackStack(null)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        clearText()
    }

    private fun clearText() {
        binding.firstNameEditText.text.clear()
        binding.lastNameEditText.text.clear()
        binding.emailEditText.text.clear()
        binding.passwordEditText.text.clear()
    }

}