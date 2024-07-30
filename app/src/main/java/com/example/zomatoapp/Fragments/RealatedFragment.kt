package com.example.zomatoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatoapp.Adapter.RealatedItemAdapter
import com.example.zomatoapp.Modals.RealatedtemDataModal
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentRealatedBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RealatedFragment : Fragment(),RealatedItemAdapter.onClickedListener {
    lateinit var Array:ArrayList<RealatedtemDataModal>
    var db=Firebase.firestore
    lateinit var type:String
    lateinit var RV:RecyclerView
    lateinit var binding: FragmentRealatedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentRealatedBinding.inflate(layoutInflater,container,false)
        binding.relatedProgreesBar.visibility=View.VISIBLE
        Array= arrayListOf()
        RV=binding.realatedItemRV
//        getData()
        type=arguments?.getString("pTy").toString()

        if (type!=null){
            geteRealatedData(type)
        }
        return binding.root
    }

    fun getData(){
        db.collection("Check").get().addOnSuccessListener {
            for (doc in it){
                val image=doc.get("imageUrl").toString()
                val price=doc.get("pPrice").toString()
                val name=doc.get("pName").toString()
                Array.add(RealatedtemDataModal(image,price,name))
            }
            Toast.makeText(requireContext(),"Data  Come",Toast.LENGTH_LONG).show()
            binding.relatedProgreesBar.visibility=View.GONE

        }
            .addOnFailureListener {
                binding.relatedProgreesBar.visibility=View.GONE

                Toast.makeText(requireContext(),"Data Not Come",Toast.LENGTH_LONG).show()

            }
    }

    fun geteRealatedData(data:String){
        db.collection("RealatedItemCollection")
            .whereEqualTo("pTy",data)
            .get()
            .addOnSuccessListener {
                for (doc in it){
                    val image=doc.getString("iUrl")?:""
                    val name=doc.getString("pN")?:""
                    val price=doc.getString("pP")?:""

                    Array.add(RealatedtemDataModal(image,name,price))
                }

                binding.relatedProgreesBar.visibility=View.GONE

                RV.layoutManager=LinearLayoutManager(context)
                RV.adapter=RealatedItemAdapter(this,Array)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Data Not Come",Toast.LENGTH_LONG).show()

            }
    }
    override fun cliked(position: Int) {
        val post=Array[position]
        val bulda=Bundle().apply {
            putString("pN",post.itemName)
        }
        val orderFrag=OrdersFragment().apply {
                arguments=bulda
            }


//        orderFrag.arguments
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainFram,orderFrag)
            .addToBackStack(null)
            .commit()
    }

//    private fun setAdap() {
//        RV=binding.realatedItemRV
//        RV.layoutManager=LinearLayoutManager(requireContext())
//        RV.adapter=RealatedItemAdapter(Array)
//    }

}