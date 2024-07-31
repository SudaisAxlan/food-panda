package com.example.zomatoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentOredrConformingBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OredrConformingFragment : Fragment() {
    var db=Firebase.firestore
    var Aut=Firebase.auth
    val currentUserUid = Aut.currentUser?.uid

    lateinit var binding: FragmentOredrConformingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentOredrConformingBinding.inflate(layoutInflater,container,false)
        val pymentMethod= arrayOf("payment Method","Easypasa","Jazzcash","Bank Card","Cash On Delery")
        val spId=binding.mySpinner
        val arrAdp=ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,pymentMethod)
        binding.orderConformLoginBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram,LogingFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.orderConformSinUpBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram,SingUpFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.confirmOrderBtn.setOnClickListener {
            binding.spinner.visibility=View.VISIBLE
            conformFun()
        }
        spId.adapter=arrAdp
        return binding.root
    }
    private fun conformFun() {
        val name=binding.fullNameInput.text.toString()
        val addre=binding.addressInput.text.toString()
        val phone=binding.phoneNumberInput.text.toString()
        val notes=binding.orderNotesInput.text.toString()
        val paymentMeth=binding.mySpinner.selectedItem.toString()
        val map= hashMapOf(
            "FullName" to name,
            "Address" to addre,
            "Phone" to phone,
            "Notes" to notes,
            "PaymentMethod" to paymentMeth,
            "uid" to currentUserUid
        )
        if (name.isNotEmpty() && addre.isNotEmpty()&&phone.isNotEmpty()){
            db.collection("Customer Order")
                .document()
                .set(map)
                .addOnSuccessListener {
                    binding.spinner.visibility=View.GONE
                    addNotifaction(name)
                    Toast.makeText(requireContext(),"Order Added SuccessFully",Toast.LENGTH_LONG).show()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.mainFram,OrderConformedFragment())
                        .addToBackStack(null)
                        .commit()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(),"Order Not Added SuccessFully",Toast.LENGTH_LONG).show()

                }

        }
        else{
            Toast.makeText(requireContext(),"Something Missng",Toast.LENGTH_LONG).show()
        }
    }

    private fun addNotifaction(name:String) {
        val map= hashMapOf(
            "Title" to "Your Order",
            "Message" to "$name : Your Order is confirmed  " ,
            "Timestamp" to System.currentTimeMillis(),
            "uid" to currentUserUid
        )
        db.collection("Notifaction")
            .add(map)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Notifaction Added Succesfully",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Notifaction Added Succesfully",Toast.LENGTH_LONG).show()

            }
    }


    override fun onStart() {
        super.onStart()
        if (Aut.currentUser!=null){
            binding.orderConformLoginBtn.visibility=View.GONE
            binding.orderConformSinUpBtn.visibility=View.GONE
        }

    }


}