package com.example.zomatoapp.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.RawContacts.Data
import android.util.Log
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class SingUpFragment : Fragment() {
    private lateinit var progressDialog: ProgressDialog
    var store=Firebase.storage
    lateinit var binding: FragmentSingUpBinding
    var db=Firebase.firestore
    var Aut=Firebase.auth

    var imageUrl:Uri?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSingUpBinding.inflate(layoutInflater,container,false)
        val email=binding.emailEditText.text.toString()
        val password=binding.passwordEditText.text.toString()
        binding.loginButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram,LogingFragment()).addToBackStack(null).commit()
        }
        binding.signUpButton.setOnClickListener {
            val email=binding.emailEditText.text.toString()
            val password=binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
                binding.progressBar.visibility=View.VISIBLE
                singUp()
                progressDialog = ProgressDialog(requireContext())
                progressDialog.setMessage("Please Wait...")
                progressDialog.setCancelable(false)
                progressDialog.show()
            }
            else{
                Toast.makeText(requireContext(),"Something missing",Toast.LENGTH_LONG).show()
            }
        }
        binding.profileImagePicker.setOnClickListener {
            selectImage()
        }

        return binding.root
    }

    private fun selectImage() {
        val intent=Intent()
            intent.action=Intent.ACTION_GET_CONTENT
            intent.type="image/*"
            startActivityForResult(intent,1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1){
            imageUrl= data?.data!!
            binding.profileImagePicker.setImageURI(imageUrl)
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
                    userProfileUploadToDB()
                    if (progressDialog.isShowing) progressDialog.dismiss()

                    Toast.makeText(requireContext(),"Acount are created successfull",Toast.LENGTH_LONG).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.mainFram,DashboardFragment())
                        .addToBackStack(null)
                        .commit()

                }
            }

            .addOnFailureListener {
                Toast.makeText(requireContext(),"Acount already created ",Toast.LENGTH_LONG).show()
                if (progressDialog.isShowing) progressDialog.dismiss()

                binding.progressBar.visibility=View.GONE

            }
    }

    private fun userProfileUploadToDB() {
        val user = Firebase.auth.currentUser
        val userId = user?.uid
//        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
//        val data= Date()
//        val fileName=formatter.format(data)
//        ("$fileName")
        if (imageUrl!=null){
            val storageRef=FirebaseStorage.getInstance().getReference("UserImage").child("UserProfilemage$userId")
            storageRef.putFile(imageUrl!!)
                .addOnSuccessListener {
                    binding.profileImagePicker.setImageURI(null)
                    Toast.makeText(requireContext(),"Uploading Successfully",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Uploading Not Successfully",Toast.LENGTH_LONG).show()
                }
        }

    }
    fun userAddDb(){
        val userName=binding.firstNameEditText.text.toString()+binding.lastNameEditText.text.toString()
        val email=binding.emailEditText.text.toString()
        val password=binding.passwordEditText.text.toString()
        val uid=Aut.currentUser?.uid
        if (uid!=null){
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