package com.example.zomatoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatoapp.Adapter.CartAdapter
import com.example.zomatoapp.Adapter.RealatedItemAdapter
import com.example.zomatoapp.Modals.RealatedtemDataModal
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentCartBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CartFragment : Fragment() {
    var db=Firebase.firestore
    lateinit var Array:ArrayList<RealatedtemDataModal>
    lateinit var RV:RecyclerView
    lateinit var binding: FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCartBinding.inflate(layoutInflater,container,false)
        binding.cartProgressBar.visibility=View.VISIBLE
        Array= arrayListOf()
        RV=binding.cartRV
        val data=arguments?.getString("pN").toString()
        geteRealatedData(data)
        return binding.root
    }


    fun geteRealatedData(data:String){
        db.collection("RealatedItemCollection")
            .whereEqualTo("pN",data)
            .get()
            .addOnSuccessListener {
                for (doc in it){
                    val image=doc.getString("iUrl")?:""
                    val name=doc.getString("pN")?:""
                    val price=doc.getString("pP")?:""
                    Array.add(RealatedtemDataModal(image,name,price))
                }
                Toast.makeText(requireContext(),"Data  Come", Toast.LENGTH_LONG).show()
                binding.cartProgressBar.visibility=View.GONE
                RV.layoutManager= LinearLayoutManager(context)
                RV.adapter= CartAdapter(Array)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Data Not Come", Toast.LENGTH_LONG).show()

            }
    }


}