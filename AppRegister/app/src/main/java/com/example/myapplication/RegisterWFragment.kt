package com.example.myapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentRegisterWBinding
import com.example.myapplication.interfaces.LaboresAPI
import com.example.myapplication.models.Labor
import com.example.myapplication.models.RegistroPesada
import com.example.myapplication.viewmodels.RegisterWViewModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegisterWFragment : Fragment(){
    private lateinit var binding: FragmentRegisterWBinding
    private val args : RegisterWFragmentArgs by navArgs()
    private val viewModel: RegisterWViewModel by lazy{
        ViewModelProvider(this).get(RegisterWViewModel::class.java)
    }
    private var arrayAdapter: ArrayAdapter<Labor>? = null
    private lateinit var register: RegistroPesada
    private var laboresList: MutableList<Labor> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("Estoy en Register", args.userType.toString())

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register_w, container, false)
        //laboresList.add(Labor("0001", "Jo", "as", "as"))
        requireActivity().runOnUiThread{
            CoroutineScope(IO).launch {
                async {
                    getLaboresList()
                }.await()
            }
        }
        binding.viewModel = viewModel
        binding.userInfo = args.userType
        Log.i("Fragment", "Estoy antes de register")
        register = RegistroPesada(binding.userInfo!!)
        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,
            laboresList as MutableList<Labor>
        )

        arrayAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.btnGuardar.setOnClickListener {
            saveRegister()
        }

        binding.spinnerLabores.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.btnGuardar.isClickable = true
                register.cod_labor = laboresList[p2]
                binding.textCantidad.isFocusable = laboresList[p2].can_hor == "C"
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                binding.btnGuardar.isClickable = false
            }

        }

        binding.spinnerLabores.adapter = arrayAdapter
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

    private fun getLaboresList(){
        try {
            val response = LaboresAPI.retrofitService.getLaboresSynchro().execute()
            laboresList = (response.body() as MutableList<Labor>?)!!
            Log.i("Estoy en labores",laboresList.toString())
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun saveRegister(){
        viewModel.registro.value?.cod_labor = binding.spinnerLabores.selectedItem as Labor?
        viewModel.registro.value?.cantidad = (binding.textCantidad.text.toString() as Int).toFloat()
        //register.observaciones = binding.textComentario.text
        /*var hour = binding.textHoraFinal.hour.toString()
        var minutes = binding.textHoraFinal.minute.toString()
        var time = "$hour:$minutes"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            viewModel.registro.value?.fecha_s = LocalDateTime.parse(time,formatter)
        }*/
        viewModel.postRegister()
        view?.findNavController()?.navigate(RegisterWFragmentDirections.actionRegisterWFragmentToQrReaderFragment())

    }




}