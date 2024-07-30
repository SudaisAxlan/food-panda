package com.example.zomatoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatoapp.Adapter.MainAdapter
import com.example.zomatoapp.Modals.MainDataModal
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentDashboardBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardFragment : Fragment() ,MainAdapter.onClicked{
    lateinit var RV: RecyclerView
    lateinit var Array:ArrayList<MainDataModal>
    lateinit var binding: FragmentDashboardBinding
    var db= Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDashboardBinding.inflate(layoutInflater,container,false)
        loadData()
        binding.homeProgress.visibility=View.VISIBLE
        Array = arrayListOf()

        binding.dashBoardSngUpBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram,SingUpFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    private fun loadData() {
        db.collection("MainCollection").get().addOnSuccessListener {

            for (doc in it){
                val image=doc.get("imgUrl").toString()
                val type=doc.get("pTy").toString()
                Array.add(MainDataModal(image,type))
            }
            binding.homeProgress.visibility=View.GONE

            loaded()
        }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Data Not Loaded", Toast.LENGTH_LONG).show()
            }
    }

    private fun loaded() {
        RV=binding.mainRV
        RV.layoutManager= GridLayoutManager(requireContext(),2)
        RV.adapter= MainAdapter(this,Array)
    }
    override fun Clocked(position: Int) {
        val pos=Array[position]
        val bulda=Bundle().apply {
            putString("pTy",pos.type)
        }
        val reaF=RealatedFragment().apply {
            arguments=bulda
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainFram,reaF)
            .addToBackStack(null)
            .commit()
    }

}