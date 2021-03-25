package com.example.myapplication

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.budiyev.android.codescanner.*
import com.example.myapplication.interfaces.JornalerosAPI
import com.example.myapplication.interfaces.RegistroPesadaAPI
import com.example.myapplication.models.Jornalero
import com.google.gson.GsonBuilder
import com.google.zxing.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception


private const val CAMERA_REQUEST_CODE = 101
class QrReaderFragment : Fragment(){

    private lateinit var codeScanner:CodeScanner
    private lateinit var fActivity: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qr_reader, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val codeScannerView: CodeScannerView = view.findViewById(R.id.scanner_view)
        fActivity = requireActivity()
        setupPermissions()
        codeScanner(codeScannerView, view)

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

    @SuppressLint("ShowToast")
    private fun codeScanner(scanner_view: CodeScannerView, view: View) {
        codeScanner = CodeScanner(fActivity, scanner_view)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE

            isAutoFocusEnabled = true
            isFlashEnabled = false

            //Aqui se especifica que se hace despues de leer el codigo QR
            decodeCallback = DecodeCallback {
                fActivity.runOnUiThread {
                    CoroutineScope(IO).launch {
                        val jornalero = async {
                            tryGetUser(it)
                        }.await()
                        Log.i("jornalero", jornalero.toString())
                        if (jornalero.cedula.isNotEmpty()) {
                            Log.i("Jornalero", "Not empty jornalero")
                                val typeT = async {
                                    getTransact(it)
                                }.await()
                                Log.i("Type", typeT)
                            if (typeT == "_POST_") {
                                Log.i("POST", "Aqui estoy")
                                view.findNavController().navigate(
                                    QrReaderFragmentDirections.actionQrReaderFragmentToUserProfile(
                                        jornalero
                                    )
                                )
                            } else {
                                Log.i("PUT", "Aqui estoy PUT")
                                view.findNavController()
                                    .navigate(QrReaderFragmentDirections.actionQrReaderFragmentToRegisterWFragment(jornalero))
                            }
                        } else {
                            Log.i("Jornalero E", "Empty jornalero")
                            Toast.makeText(
                                fActivity,
                                "Error en búsqueda de usuario",
                                Toast.LENGTH_SHORT
                            )
                        }
                    }
                }
            }

            errorCallback = ErrorCallback {
                fActivity.runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                    showErrorLecture(it.message.toString())
                }
            }
        }
    }

    private fun showErrorLecture(error: String) {
        val alert: AlertDialog.Builder = AlertDialog.Builder(fActivity)
        alert.setMessage("Camera initialization error: $error")
        alert.show()
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()

    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            fActivity,
            android.Manifest.permission.CAMERA
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest(fActivity)
        }
    }

    private fun makeRequest(fActivity: FragmentActivity) {
        ActivityCompat.requestPermissions(
            fActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        fActivity,
                        "You need the camera permission to be able to use this app!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //successfull request!
                }
            }
        }
    }


    private fun tryGetUser(userCode: Result):Jornalero{
        var jornalero:Jornalero = Jornalero(userCode.text, "", "", "", "")
        try{
            val response = getRetrofit().create(JornalerosAPI::class.java).getJornaleros(userCode.text).execute()
            jornalero = response.body()!!
        }catch (e:Exception){
            e.printStackTrace()
        }
        return jornalero
    }

    private fun getTransact(userCode: Result):String{
        lateinit var typeTransact:String
        try{
            val response = getRetrofit().create(RegistroPesadaAPI::class.java).getTypeTransact(userCode.text).execute()
            response.body()!!.forEach { typeTransact = it.httpResponseText }

        }catch (e:Exception){
            e.printStackTrace()
        }
        return typeTransact
    }



}
