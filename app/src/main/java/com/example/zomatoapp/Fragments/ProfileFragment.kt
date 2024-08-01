package com.example.zomatoapp.Fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    var Aut = Firebase.auth
    var db=Firebase.firestore
    lateinit var imageUrl: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        iusermage()
        userInfoData()
        binding.profileLoginBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram, LogingFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.profileSinUpBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram, SingUpFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.logoutBtn.setOnClickListener {
            sngOut()
        }
        return binding.root
    }
    private fun userInfoData() {
      if (Aut.currentUser!=null){
          val uid=Aut.currentUser?.uid
          db.collection("Users").get().addOnSuccessListener {
              for (doc in it){
                  val userName=doc.get("UserName").toString()
                  val email=doc.get("email").toString()
                  binding.username.text=userName
                  binding.email.text=email
              }
          }
      }
    }

    private fun iusermage() {
        val user=Firebase.auth.currentUser
        val uid=user?.uid
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("UserImage/UserProfilemage$uid")
        if (Aut.currentUser!=null){
            storageRef.downloadUrl
                .addOnSuccessListener { uri ->
                    imageUrl = uri
                    Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.pl)
                        .error(R.drawable.er)
                        .into(binding.userProfileImage)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "No image found", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun sngOut() {
        if (Aut.currentUser != null) {
            Aut.signOut()
            cartFun()
        } else {
            Toast.makeText(requireContext(), "Currently not logged in", Toast.LENGTH_LONG).show()
        }
    }

    private fun cartFun() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainFram, DashboardFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        if (Aut.currentUser != null) {
            binding.profileLoginBtn.visibility = View.GONE
            binding.profileSinUpBtn.visibility = View.GONE
        }
        else {
            binding.logoutBtn.isCheckable=false
            binding.logoutBtn.isEnabled = false
        }
    }
}
