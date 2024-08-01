package com.example.zomatoapp.Fragments

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.zomatoapp.Modals.RealatedtemDataModal
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentOrdersBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class OrdersFragment : Fragment() {
    var quantity: Int = 0
    lateinit var binding: FragmentOrdersBinding
    lateinit var Arrar: ArrayList<RealatedtemDataModal>
    var db = Firebase.firestore
    var pN = "Name"
    var Aut = Firebase.auth
    lateinit var progressDialog: ProgressDialog
    private var image: String = ""
    private var name: String = ""
    private var price: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(layoutInflater, container, false)
        binding.oredrProgresBar.visibility = View.VISIBLE
        binding.videoProgressBar.visibility = View.VISIBLE
        val nameTy = arguments?.getString("pN").toString()
        binding.detailItemLoginBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram, LogingFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.detailIteminUpBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram, SingUpFragment())
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
            if (Aut.currentUser != null) {
                userOrderAddDb()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.mainFram, OredrConformingFragment())
                    .addToBackStack(null)
                    .commit()
            } else {
                Toast.makeText(requireContext(),"First Create Your Acount",Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    private fun loadData(dat: String) {
        db.collection("RealatedItemCollection")
            .whereEqualTo("pN", dat)
            .get()
            .addOnSuccessListener {
                for (doc in it) {
                    image = doc.getString("iUrl") ?: ""
                    name = doc.getString("pN") ?: ""
                    price = doc.getString("pP") ?: ""
                    if (image.isNotEmpty() && name.isNotEmpty() && price.isNotEmpty()) {
                        binding.oredrProgresBar.visibility = View.GONE
                        binding.orderScreenName.text = name
                        binding.itemPrice.text = price
                        Log.d("OrdersFragment", "Image URL: $image")
                        Picasso.get()
                            .load(image)
                            .placeholder(R.drawable.pl)
                            .into(binding.orderScreenImage)
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Data Not Loaded", Toast.LENGTH_LONG).show()
            }
    }

    private fun userOrderAddDb() {
        val uid = Aut.currentUser?.uid
        val map = hashMapOf(
            "image" to image,
            "name" to name,
            "price" to price,
            "uid" to uid
        )
        if (Aut.currentUser != null) {
            db.collection("UserOrder").add(map).addOnSuccessListener {
                Toast.makeText(requireContext(), "User Order Are Added In Fire Store", Toast.LENGTH_LONG).show()
            }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "User Order Are Not Added In Fire Store", Toast.LENGTH_LONG).show()
                }
        }
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
        val obj = RealatedtemDataModal("", "", "")
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
        if (quantity > 0) {
            binding.minusIcon.alpha = 0.9f
            quantity--
            binding.quantitySize.text = quantity.toString()
        } else {
            binding.minusIcon.alpha = 0.5f
        }
    }

    private fun plusIconFun() {
        quantity++
        binding.quantitySize.text = quantity.toString()
    }

    private fun playVideo(videoUrl: String) {
        val uri = Uri.parse(videoUrl)
        binding.Video.setVideoURI(uri)
        binding.Video.setOnPreparedListener {
            binding.videoProgressBar.visibility = View.GONE
            it.start()
        }
        binding.Video.setOnCompletionListener {
            Toast.makeText(requireContext(), "Video completed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        if (Aut.currentUser != null) {
            binding.detailItemLoginBtn.visibility = View.GONE
            binding.detailIteminUpBtn.visibility = View.GONE
        }
    }
}
