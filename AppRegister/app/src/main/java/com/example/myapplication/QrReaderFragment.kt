package com.example.myapplication

import android.content.pm.PackageManager
import android.graphics.Path
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
import com.example.myapplication.interfaces.LaboresAPI
import com.example.myapplication.models.Jornalero
import com.google.gson.GsonBuilder
import com.google.zxing.Result
import kotlinx.android.synthetic.main.fragment_qr_reader.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


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
                    val jornalero = tryGetUser(it)
                    //texto.text = it.text
                    view.findNavController().navigate(QrReaderFragmentDirections.actionQrReaderFragmentToUserProfile(jornalero))

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
    private fun tryGetUser(userCode: Result): Jornalero {
        var jornalero = Jornalero(userCode.text, "", "", "", "")
        val response = getRetrofit().create(JornalerosAPI::class.java).postJornaleros(jornalero).enqueue(
                object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Log.d("En response", response.body().isNullOrEmpty().toString())
                        texto.text = response.body()
                        val result = response.body()?.trim()?.split(" ")

                        result?.forEach {
                            Log.d("Result", it)
                            val res = it.split(":")
                            Log.d("valores", res[0])
                            var iter = res.iterator()
                            while(iter.hasNext()){
                                when(iter.next()){
                                    "cedula" -> jornalero.cedula = iter.next()
                                    "nombre" -> jornalero.nombre = iter.next()
                                    "apellido" -> jornalero.apellido = iter.next()
                                    "cond_jor" -> jornalero.cond_jor = iter.next()
                                }
                            }
                        }
                        texto.text = jornalero.toString()

                        Toast.makeText(fActivity, response.message(), Toast.LENGTH_SHORT)
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.d("En Failure", t.toString())
                        Log.d("En Failure", t.message)
                        Toast.makeText(fActivity, t.message, Toast.LENGTH_SHORT)
                    }
                })

        Log.d("Final try user", response.toString())
        return jornalero
    }


    private fun getContent(){

        GlobalScope.launch(Dispatchers.IO){
            val response = getRetrofit().create(LaboresAPI::class.java).getLabores().awaitResponse()
            if(response.isSuccessful){
                val data = response.body()!!
                Log.d("MainActivity", data[0].codlab)
                withContext(Dispatchers.Main){
                    data.forEach{
                        texto.text = it.toString()
                    }
                    //texto.text = data[0].codlab
                    print(texto.text)
                }
            }
        }

    }


}
