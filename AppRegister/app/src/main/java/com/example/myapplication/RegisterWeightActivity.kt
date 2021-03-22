package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.databinding.FragmentRegisterWeightActivityBinding
import com.example.myapplication.databinding.FragmentRegisterWeightActivityBindingLandImpl
import com.example.myapplication.interfaces.LaboresAPI
import com.example.myapplication.models.Labor
import com.example.myapplication.models.RegistroPesada
import com.example.myapplication.viewmodels.RegisterActivitiyViewModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.EnumSet.of
import java.util.Optional.of

class RegisterWeightActivity : Fragment() {
    private lateinit var registroModel: RegistroPesada
    private lateinit var binding: FragmentRegisterWeightActivityBinding
    //private val args : RegisterWeightActivityArgs by navArgs()
    //private lateinit var bindingLand:FragmentRegisterWeightActivityBindingLandImpl
    private var arrayAdapter: ArrayAdapter<Labor>? = null
    private val viewModel: RegisterActivitiyViewModel by lazy{
        ViewModelProvider(this).get(RegisterActivitiyViewModel::class.java)
    }
    private var laboresList: List<Labor> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val userData = args.userType
        //binding = FragmentRegisterWeightActivityBinding.inflate(inflater)
        //val bindingLand = FragmentRegisterWeightActivityBindingLandImpl.inflate(inflater)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register_weight_activity, container, false)
        //registroModel = RegistroPesada(userData)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //binding.userInfo = userData
        Log.i("Antes de view", "AQUI ESTOY")
        Log.i("ListLenght", viewModel.listaLabores.value?.size.toString())
        viewModel.listaLabores.value?.forEach {
            Log.i("Here", it.toString())
        }
        Log.i("Status", viewModel.status.value.toString())
        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
            viewModel.listaLabores.value as MutableList<Labor>
        )

        binding.spinnerLabores.adapter = arrayAdapter
        //manageTimePicker()

        binding.textFecha.text = viewModel.DateF.value

        binding.btnCerrar?.setOnClickListener {
            view?.findNavController()?.navigate(RegisterWeightActivityDirections.actionRegisterWeightActivityToQrReaderFragment())
        }
        binding.btnGuardar.setOnClickListener {
            viewModel.postRegister()
        }
        return binding.root
    }

    private fun manageTimePicker(){
        var timePicker:TimePicker = requireView().findViewById(R.id.text_horaEntrada)
        timePicker.setOnTimeChangedListener{ timePicker: TimePicker, i: Int, i1: Int ->
            var date = DateFun()
            var dateFull = DateFun().date() + " " + i + ":" + i1
            //registroModel.fecha_s = dateFull
            Log.i("TAG", dateFull)

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun saveRegister(){

    }



}