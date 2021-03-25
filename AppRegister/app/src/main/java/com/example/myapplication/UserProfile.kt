
package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentUserProfileBinding
import com.example.myapplication.interfaces.RegistroPesadaAPI
import com.example.myapplication.models.Jornalero
import com.example.myapplication.models.RegistroPesada
import com.example.myapplication.viewmodels.RegisterWViewModel
import com.example.myapplication.viewmodels.UserProfileViewModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.coroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class UserProfile : Fragment() {

    private val args : UserProfileArgs by navArgs()
    private lateinit var binding: FragmentUserProfileBinding
    private val viewModel: UserProfileViewModel by lazy{
        ViewModelProvider(this).get(UserProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userData = args.userType
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_profile, container, false)
        binding.viewModel = viewModel
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

        binding.entranceButton.setOnClickListener {
            //postEntrance()
        }



        return binding.root

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

    /*private fun postEntrance(){
        var registro:RegistroPesada = RegistroPesada(binding.userInfo as Jornalero)
        getRetrofit().create(RegistroPesadaAPI::class.java).postWeightRegister("_POST_", registro).enqueue(
            object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){

                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }
        )
    }*/



    private fun cancelEntrance(){
        view?.findNavController()?.navigate(UserProfileDirections.actionUserProfileToQrReaderFragment(binding.userInfo!!))
    }

}


