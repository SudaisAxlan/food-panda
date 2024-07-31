package com.example.zomatoapp.Fragments

import android.icu.text.Transliterator.Position
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.zomatoapp.Modals.RealatedtemDataModal
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentOrdersBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class OrdersFragment : Fragment() {
     var quantity:Int=0
    lateinit var binding: FragmentOrdersBinding
    lateinit var Arrar:ArrayList<RealatedtemDataModal>
     var db=Firebase.firestore
    var pN="Name"
    var Aut=Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentOrdersBinding.inflate(layoutInflater,container,false)
        binding.oredrProgresBar.visibility=View.VISIBLE
        binding.videoProgressBar.visibility=View.VISIBLE
        val nameTy=arguments?.getString("pN").toString()
        binding.detailItemLoginBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram,LogingFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.detailIteminUpBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram,SingUpFragment())
                .addToBackStack(null)
                .commit()
        }
        loadData(nameTy)
        video()
        binding.plusIcon.setOnClickListener {
            plusIconFun()
        }
        binding.minusIcon.setOnClickListener {
            mnusIconFun()
        }
        binding.addoCartBtn.setOnClickListener {
            cartFun()
        }

        binding.orderNowtBtn.setOnClickListener {
            if (Aut.currentUser!=null){
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainFram,OredrConformingFragment())
                    .addToBackStack(null)
                    .commit()
            }
            else{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainFram,LogingFragment())
                    .addToBackStack(null)
                    .commit()
            }
//            else{
//                val dia=AlertDialog.Builder(requireContext())
//                dia.setIcon(R.drawable.avt)
//                dia.setTitle("Login")
//                dia.setMessage("Login Your Acount")
//                dia.setPositiveButton("yes"){a , w ->
//                    parentFragmentManager.beginTransaction()
//
//                        .replace(R.id.mainFram,LogingFragment())
//                        .addToBackStack(null)
//                        .commit()
//                }
//                dia.setNegativeButton("No"){a,h->
//                }
//                dia.setNeutralButton("No"){a,h->
//
//                }


//                }
            }


        return binding.root
    }

    private fun video() {
        db.collection("video").get().addOnSuccessListener {
            for (doc in it) {
                val vid = doc.getString("mUrl") ?: ""
                playVideo(vid)


            }
        }
    }
    private fun cartFun() {
        val obj=RealatedtemDataModal("","","")
        val bulde = Bundle().apply {
            putString("pN", obj.itemName)
        }
        val cartFrag = CartFragment().apply {
            arguments = bulde
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.mainFram, cartFrag)
            .addToBackStack(null)
            .commit()
    }
    private fun mnusIconFun() {
        if (quantity>0){
            binding.minusIcon.alpha=0.9f

            quantity --
            binding.quantitySize.text=quantity.toString()
        }
        else{
            binding.minusIcon.alpha=0.5f
        }

    }

    private fun plusIconFun() {
        quantity++
        binding.quantitySize.text=quantity.toString()
    }


    private fun loadData(dat:String) {
        db.collection("RealatedItemCollection")
            .whereEqualTo("pN", dat)
            .get()
            .addOnSuccessListener {
                for (doc in it) {
                    val image = doc.getString("iUrl") ?: ""
                    val name = doc.getString("pN") ?: ""
                    val price = doc.getString("pP") ?: ""

                    if (image != null && name != null && price != null) {
                        binding.oredrProgresBar.visibility=View.GONE
                        binding.orderScreenName.text = name
                        binding.itemPrice.text = price
                        // Log the image URL to verify it
                        Log.d("ReadFragment", "Image URL: $image")
                        Picasso.get()
                            .load(image)
                            .placeholder(R.drawable.pl)
                            .into(binding.orderScreenImage)
                    }


                }

            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Data Not Loaded",Toast.LENGTH_LONG).show()
            }
    }

    private fun playVideo(videoUrl: String) {
        val uri = Uri.parse(videoUrl)
        binding.Video.setVideoURI(uri)


        binding.Video.setOnPreparedListener {
            binding.videoProgressBar.visibility=View.GONE
            it.start()
        }

        binding.Video.setOnCompletionListener {
            Toast.makeText(requireContext(), "Video completed", Toast.LENGTH_SHORT).show()
        }


    }
    override fun onStart() {
        super.onStart()
        if (Aut.currentUser!=null){
            binding.detailItemLoginBtn.visibility=View.GONE
            binding.detailIteminUpBtn.visibility=View.GONE
        }

    }


}