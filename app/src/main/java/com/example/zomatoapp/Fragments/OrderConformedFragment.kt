package com.example.zomatoapp.Fragments

import android.app.Notification
import android.app.Notification.Builder
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import com.example.zomatoapp.R
import com.example.zomatoapp.databinding.FragmentOrderConformedBinding

class OrderConformedFragment : Fragment() {
    val channalName="channalName"
    val channalID="channalId"
    lateinit var  notiMang:NotificationManager
    lateinit var bulder:Builder

    lateinit var binding: FragmentOrderConformedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentOrderConformedBinding.inflate(layoutInflater,container,false)
        if (context !=null){
            notiMang= requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        binding.conformedOredrAnimation.playAnimation()
        binding.conformedOredrAnimation.loop(true)
        notifiaction()
        Handler().postDelayed({
            binding.conformedOredrAnimation.pauseAnimation()
            binding.goBackBtn.visibility=View.VISIBLE
        },5000)
        binding.goBackBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.mainFram,DashboardFragment())
                .addToBackStack(null)
                .commit()
        }
        return binding.root
    }

    private fun notifiaction() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channal=NotificationChannel(channalID,channalName,NotificationManager.IMPORTANCE_HIGH)
            notiMang.createNotificationChannel(channal)
            bulder=Builder(requireContext(),channalID)
                .setSmallIcon(R.drawable.order)
                .setContentTitle("Order")
                .setContentText("Your Order is conformed")

        }
        notiMang.notify(11,bulder.build())
    }


}