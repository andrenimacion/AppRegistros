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
import android.widget.TimePicker
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.FragmentRegisterWBinding
import com.example.myapplication.models.Labor
import com.example.myapplication.models.RegistroPesada
import com.example.myapplication.viewmodels.RegisterWViewModel
import kotlinx.android.synthetic.main.fragment_register_w.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegisterWFragment : Fragment(){
    private lateinit var binding: FragmentRegisterWBinding
    private val viewModel: RegisterWViewModel by lazy{
        ViewModelProvider(this).get(RegisterWViewModel::class.java)
    }
    private var arrayAdapter: ArrayAdapter<Labor>? = null
    private lateinit var register: RegistroPesada
    private var laboresList: List<Labor> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register_w, container, false)
        binding.viewModel = viewModel
        register = RegistroPesada(binding.userInfo!!)
        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
            viewModel.listaLabores.value as MutableList<Labor>
        )
        binding.textHoraEntrada.setOnTimeChangedListener{ timePicker: TimePicker, i: Int, i1: Int ->
            var date = DateFun()
            var dateFull = DateFun().date() + " " + i + ":" + i1
            //registroModel.fecha_s = dateFull
            Log.i("TAG", dateFull)

        }

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
        return binding.root

    }

    private fun saveRegister(){
        viewModel.registro.value?.cod_labor = binding.spinnerLabores.selectedItem as Labor?
        viewModel.registro.value?.cantidad = (binding.textCantidad.text.toString() as Int).toFloat()
        //register.observaciones = binding.textComentario.text
        var hour = binding.textHoraFinal.hour.toString()
        var minutes = binding.textHoraFinal.minute.toString()
        var time = "$hour:$minutes"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            viewModel.registro.value?.fecha_s = LocalDateTime.parse(time,formatter)
        }
        viewModel.postRegister()

    }




}