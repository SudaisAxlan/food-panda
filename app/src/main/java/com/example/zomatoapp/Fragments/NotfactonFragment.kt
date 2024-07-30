package com.example.zomatoapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatoapp.Adapter.NotifationAdapter
import com.example.zomatoapp.Modals.NotDataModal
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentNotfactonBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotfactonFragment : Fragment() {
     var db=Firebase.firestore
    lateinit var RV:RecyclerView
    lateinit var Array:ArrayList<NotDataModal>
    lateinit var binding: FragmentNotfactonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentNotfactonBinding.inflate(layoutInflater,container,false)
        Array = arrayListOf()
        loadaNoti()
        return binding.root
    }

    private fun loadaNoti() {
        db.collection("Notifaction").get()
            .addOnSuccessListener {
                for (doc in it){
                    val title=doc.getString("Title")?:""
                    val mess=doc.getString("Message")?:""
                    Array.add(NotDataModal(title,mess))
                }
                RV=binding.notiRV
                RV.layoutManager=LinearLayoutManager(requireContext())
                RV.adapter=NotifationAdapter(Array)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Noti Not loaded",Toast.LENGTH_LONG).show()
            }

    }

}