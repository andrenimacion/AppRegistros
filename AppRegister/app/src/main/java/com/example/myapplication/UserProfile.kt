
package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentUserProfileBinding
import com.example.myapplication.interfaces.LaboresAPI
import com.example.myapplication.models.Jornalero
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class UserProfile : Fragment() {

    private val args : UserProfileArgs by navArgs()
    private lateinit var binding: FragmentUserProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userData = args.userType
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_profile, container, false)
        binding.userInfo = userData
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val time = LocalDateTime.now()
            val timeFormat = DateTimeFormatter.ofPattern("HH:mm")
            binding.iniTime = time.format(timeFormat)
        }else{
            val time = Calendar.getInstance().time
            val stringTime = time.hours.toString()+":"+time.minutes
            binding.iniTime = stringTime
        }

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun acceptEntrance(){

    }

}


