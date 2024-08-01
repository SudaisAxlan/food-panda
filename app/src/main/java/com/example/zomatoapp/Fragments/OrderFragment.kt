package com.example.zomatoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatoapp.Adapter.OrderAdapter
import com.example.zomatoapp.Adapter.RealatedItemAdapter
import com.example.zomatoapp.Modals.RealatedtemDataModal
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentOrderBinding
import com.example.zomatoapp.databinding.FragmentOrdersBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OrderFragment : Fragment() {
    var Aut=Firebase.auth
     private var uid:String=""
    var db=Firebase.firestore
    lateinit var Array:ArrayList<RealatedtemDataModal>
    lateinit var RV: RecyclerView


    lateinit var binding: FragmentOrderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentOrderBinding.inflate(layoutInflater,container,false)
        binding.oredrPB.visibility=View.VISIBLE
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
        Array = arrayListOf()
        RV = binding.orderRV
        RV.layoutManager = LinearLayoutManager(requireContext())
        RV.adapter = OrderAdapter(Array)
        loadUserOrders()
        return binding.root
    }
    fun loadUserOrders(){
            db.collection("UserOrder")
                .get()
                .addOnSuccessListener {
                    var userUid=Aut.currentUser?.uid
                    for (doc in it){
                        val uid=doc.getString("uid")?:""
                        val image=doc.getString("image")?:""
                        val name=doc.getString("name")?:""
                        val price=doc.getString("price")?:""
                        if (uid==userUid){
                            binding.oredrPB.visibility=View.GONE

                            Array.add(RealatedtemDataModal(image,name,price))

                        }
                    }
                    RV.layoutManager= LinearLayoutManager(requireContext())
                    RV.adapter= OrderAdapter(Array)
                }
                .addOnFailureListener {
                    binding.oredrPB.visibility=View.GONE

                    Toast.makeText(requireContext(),"Data Not Come", Toast.LENGTH_LONG).show()

                }
    }





    override fun onStart() {
        super.onStart()
        if (Aut.currentUser!=null){
            binding.orderLoginBtn.visibility=View.GONE
            binding.orderSngUpBtn.visibility=View.GONE
        }

    }

}