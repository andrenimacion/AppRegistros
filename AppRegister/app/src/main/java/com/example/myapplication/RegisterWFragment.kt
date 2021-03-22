package com.example.myapplication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.example.myapplication.databinding.FragmentRegisterWBinding
import com.example.myapplication.viewmodels.RegisterActivitiyViewModel

class RegisterWFragment : Fragment() {
    private lateinit var binding: FragmentRegisterWBinding
    private val viewModel: RegisterWViewModel by lazy{
        ViewModelProvider(this).get(RegisterWViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_register_w, container, false)

        binding.textHoraEntrada.setOnTimeChangedListener{ timePicker: TimePicker, i: Int, i1: Int ->
            var date = DateFun()
            var dateFull = DateFun().date() + " " + i + ":" + i1
            //registroModel.fecha_s = dateFull
            Log.i("TAG", dateFull)

        }
        return binding.root


    }

}