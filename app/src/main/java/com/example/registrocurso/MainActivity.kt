package com.example.registrocurso

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.example.registrocurso.data.model.RegistrarTecnicoPresDto
import com.example.registrocurso.data.model.ApiResponse
import com.example.registrocurso.data.model.TecEvento
import com.example.registrocurso.data.model.TecEventoPresentacionTotal
import com.example.registrocurso.data.model.modelEventoSpinner
import com.example.registrocurso.data.remote.APIService
import com.example.registrocurso.data.remote.ApiUtils
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.zxing.integration.android.IntentIntegrator
import com.iamageo.library.*
import com.jaredrummler.materialspinner.MaterialSpinner
import okhttp3.FormBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Main : AppCompatActivity() {

    private var _apiService: APIService? = null
    private var TecEventoList: ArrayList<TecEvento?>? = null
   // private var TecEventoFilterList: ArrayList<TecEvento?>? = null
    private var idEventoHDR: String? = null
    private var idTecEventoPresentacion: String? = null
    private var TecEventoPresentacionTotalList: ArrayList<TecEventoPresentacionTotal?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cbAlmacenMS = findViewById<MaterialSpinner>(R.id.cbAlmacenMS)
        cbAlmacenMS.setBackgroundResource(R.drawable.style_spinner_custom)

        _apiService = ApiUtils.getAPIService(this@Main)

        val btnfabStream = findViewById<ExtendedFloatingActionButton>(R.id.fab_stream)

        btnfabStream.setOnClickListener {
            initScanner()
        }
        cargarTecEventos()

        val btnVerRegistroTecnico = findViewById<Button>(R.id.btnVerRegistroTecnico)
        btnVerRegistroTecnico.setOnClickListener {

            val lyRegistroTecnico = findViewById<LinearLayout>(R.id.lyRegistroTecnico)
            val linearLayout1 = findViewById<LinearLayout>(R.id.linearLayout1)
            val lyLeerTecnico = findViewById<LinearLayout>(R.id.lyLeerTecnico)

            lyRegistroTecnico.visibility = View.VISIBLE
            linearLayout1.visibility = View.GONE
            lyLeerTecnico.visibility = View.GONE

        }

        val btnVerRegistroCliente = findViewById<Button>(R.id.btnVerRegistroCliente)
        btnVerRegistroCliente.setOnClickListener {

            val lyRegistroTecnico2 = findViewById<LinearLayout>(R.id.lyRegistroTecnico2)
            val linearLayout1 = findViewById<LinearLayout>(R.id.linearLayout1)
            val lyLeerTecnico = findViewById<LinearLayout>(R.id.lyLeerTecnico)

            lyRegistroTecnico2.visibility = View.VISIBLE
            linearLayout1.visibility = View.GONE
            lyLeerTecnico.visibility = View.GONE

        }

        val btnRegistrarTecnicoManual = findViewById<Button>(R.id.btnRegistrarTecnicoManual)
        btnRegistrarTecnicoManual.setOnClickListener {

            RegistrarTecnicoCursoManual()
        }

        val btnRegistrarClienteManual = findViewById<Button>(R.id.btnRegistrarClienteManual)
        btnRegistrarClienteManual.setOnClickListener {

            RegistrarClienteCursoManual()
        }

        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {

            val lyRegistroTecnico = findViewById<LinearLayout>(R.id.lyRegistroTecnico)
            val linearLayout1 = findViewById<LinearLayout>(R.id.linearLayout1)
            val lyLeerTecnico = findViewById<LinearLayout>(R.id.lyLeerTecnico)

            lyRegistroTecnico.visibility = View.GONE
            linearLayout1.visibility = View.VISIBLE
            lyLeerTecnico.visibility = View.VISIBLE

        }

        val btnVolver2 = findViewById<Button>(R.id.btnVolver2)
        btnVolver2.setOnClickListener {

            val lyRegistroTecnico2 = findViewById<LinearLayout>(R.id.lyRegistroTecnico2)
            val linearLayout1 = findViewById<LinearLayout>(R.id.linearLayout1)
            val lyLeerTecnico = findViewById<LinearLayout>(R.id.lyLeerTecnico)

            lyRegistroTecnico2.visibility = View.GONE
            linearLayout1.visibility = View.VISIBLE
            lyLeerTecnico.visibility = View.VISIBLE

        }



    }


    private fun initScanner(){
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Captura el QR code del tecnico")
        integrator.setTorchEnabled(true)
       // integrator.setBeepEnabled(true)
        integrator.initiateScan()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            } else {

                RegistrarTecnicoCurso(result.contents)

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        if (event!!.action == 1) {
            if (event.keyCode == 66) { // 66 keydown y 301 keyup
                val etCantidadLeida = findViewById<EditText>(R.id.ctCodigoError)

                Toast.makeText(getApplicationContext(),
                    "registro tecnico id: " + etCantidadLeida.text.toString(), Toast.LENGTH_LONG).show()


                RegistrarTecnicoCurso(etCantidadLeida.text.toString())
            }
        }


        return if (event.action == 1) {
            if (event.keyCode == 66) { // 66 keydown y 301 keyup
                true
            } else {
                super.dispatchKeyEvent(event)
            }
        } else {
            super.dispatchKeyEvent(event)
        }
    }


    fun RegistrarTecnicoCurso(idTecnico : String)
    {
        val nDialog: ProgressDialog
        nDialog = ProgressDialog(this@Main)
        nDialog.setMessage("Registrando..")
        nDialog.setTitle("Validando registro...")
        nDialog.isIndeterminate = false
        nDialog.setCancelable(false)
        nDialog.show()

        val token = "IkxKlMO035oXksdCwFasFw40XmgtFpeoLks"
        val registro = RegistrarTecnicoPresDto()
        registro.presentacionId = idTecEventoPresentacion!!.toInt()
        registro.tecnicoUserID = idTecnico
        registro.tokenKey = token

        val getTecInfo: Call<ApiResponse<Boolean?>?>? = _apiService?.RegistrarTecnicoPresentacion(
            registro
        )
        getTecInfo!!.enqueue(object : Callback<ApiResponse<Boolean?>?> {
            override fun onResponse(
                call: Call<ApiResponse<Boolean?>?>?,
                response: Response<ApiResponse<Boolean?>?>?
            ) {
                if (response!!.isSuccessful) {

                    if (response.body()?.isSucceeded == true) {
                        nDialog.dismiss()

                        SonarBeepBien("Se registró a la presentación con exito")
                        showToastMessage("Se registró a la presentación con exito")

                        val cbAlmacenMS = findViewById<MaterialSpinner>(R.id.cbAlmacenMS)
                        SeleccionaAlmacen(cbAlmacenMS)

                    } else {
                        response.body()?.error?.description?.let { SonarBeepError(it) }


                        BeautifulDialog.build(this@Main)
                            .title("Error al registrar", titleColor = R.color.button_grey)
                            .body(response.body()?.error?.description.toString(),  color = R.color.button_grey)
                            .type(type= BeautifulDialog.TYPE.ERROR)
                            .position(BeautifulDialog.POSITIONS.CENTER)
                            .onPositive("OK") {
                                Toast.makeText(getApplicationContext(), "confirmar", Toast.LENGTH_SHORT).show()
                            }
                            .hideNegativeButton(hide = true)


                        SonarBeepError(response.body()?.error?.description.toString())
                        showToastMessage(response.body()?.error?.description)
                    }
                } else {

                    BeautifulDialog.build(this@Main)
                        .title("Error al registrar", titleColor = R.color.button_grey)
                        .body("Error en la respuesta de la conexión, puede intentarlo más tarde",  color = R.color.button_grey)
                        .type(type= BeautifulDialog.TYPE.ERROR)
                        .position(BeautifulDialog.POSITIONS.CENTER)
                        .onPositive("OK") {
                            Toast.makeText(getApplicationContext(), "confirmar", Toast.LENGTH_SHORT).show()
                        }
                        .hideNegativeButton(hide = true)

                    SonarBeepError("Error en la respuesta de la conexión, puede intentarlo más tarde")
                    showToastMessage("Error en la respuesta de la conexión, puede intentarlo más tarde")
                }
                nDialog.dismiss()
            }

            override fun onFailure(call: Call<ApiResponse<Boolean?>?>, t: Throwable) {
                SonarBeepError("Error con la conexión, puede intentarlo más tarde")
                showToastMessage("Error con la conexión, puede intentarlo más tarde")
            }
        })
    }



    fun RegistrarClienteCursoManual()
    {

        val nDialog: ProgressDialog
        nDialog = ProgressDialog(this@Main)
        nDialog.setMessage("Registrando..")
        nDialog.setTitle("Validando registro...")
        nDialog.isIndeterminate = false
        nDialog.setCancelable(false)
        nDialog.show()

        val token = "IkxKlMO035oXksdCwFasFw40XmgtFpeoLks"
        //region Validación

        val sTxtNombre = ""
        val sTxtApellidoPatET = ""
        val sTxtApellidoMatET = ""
        val txtCelularET = findViewById<TextInputEditText>(R.id.txtCelularETCliente)
        val txtCorreoET = findViewById<TextInputEditText>(R.id.txtCorreoETCliente)

        val esTecnico = "2"



        val parametrosTokenPost: RequestBody = FormBody.Builder()
            .add("NOMBRE", sTxtNombre)
            .add("APELLIDOPATERNO", sTxtApellidoPatET)
            .add("APELLIDOMATERNO", sTxtApellidoMatET)
            .add("CELULAR", txtCelularET.text.toString())
            .add("CORREO", txtCorreoET.text.toString())
            .add("IDEVENTO", idTecEventoPresentacion!!.toString())
            .add("ESTECNICO", esTecnico)
            .add("TOKEN", token)

            .build()


        val getTecInfo: Call<ApiResponse<Boolean?>?>? = _apiService?.GuardarTecnicoEventoManual(
            parametrosTokenPost
        )
        getTecInfo!!.enqueue(object : Callback<ApiResponse<Boolean?>?> {
            override fun onResponse(
                call: Call<ApiResponse<Boolean?>?>?,
                response: Response<ApiResponse<Boolean?>?>?
            ) {
                if (response!!.isSuccessful) {

                    if (response.body()?.isSucceeded == true) {
                        nDialog.dismiss()
                        var esTecnicoDisplay = "tecnico"

                        if(esTecnico.equals("0"))
                            esTecnicoDisplay = "cliente"

                        SonarBeepBien("Se registró al " + esTecnicoDisplay + " a la presentación con exito")
                        showToastMessage("Se registró al " + esTecnicoDisplay + " a la presentación con exito")

                        val cbAlmacenMS = findViewById<MaterialSpinner>(R.id.cbAlmacenMS)
                        SeleccionaAlmacen(cbAlmacenMS)

                        LimpiarTextos()

                    } else {
                        BeautifulDialog.build(this@Main)
                            .title("Error al registrar", titleColor = R.color.button_grey)
                            .body("Error " + response.body()?.error?.description,  color = R.color.button_grey)
                            .type(type= BeautifulDialog.TYPE.ERROR)
                            .position(BeautifulDialog.POSITIONS.CENTER)
                            .onPositive("OK") {
                                Toast.makeText(getApplicationContext(), "confirmar", Toast.LENGTH_SHORT).show()
                            }
                            .hideNegativeButton(hide = true)

                        response.body()?.error?.description?.let { SonarBeepError(it) }
                        showToastMessage(response.body()?.error?.description)
                    }
                } else {
                    SonarBeepError("Error en la respuesta de la conexión, puede intentarlo más tarde")
                    showToastMessage("Error en la respuesta de la conexión, puede intentarlo más tarde")
                }
                nDialog.dismiss()
            }

            override fun onFailure(call: Call<ApiResponse<Boolean?>?>, t: Throwable) {
                SonarBeepError("Error con la conexión, puede intentarlo más tarde")
                showToastMessage("Error con la conexión, puede intentarlo más tarde")
            }
        })
    }

    fun RegistrarTecnicoCursoManual()
    {

        val nDialog: ProgressDialog
        nDialog = ProgressDialog(this@Main)
        nDialog.setMessage("Registrando..")
        nDialog.setTitle("Validando registro...")
        nDialog.isIndeterminate = false
        nDialog.setCancelable(false)
        nDialog.show()

        val token = "IkxKlMO035oXksdCwFasFw40XmgtFpeoLks"
        //region Validación
        val txtNombreET = findViewById<TextInputEditText>(R.id.txtNombreET)
        val txtApellidoPatET = findViewById<TextInputEditText>(R.id.txtApellidoPatET)
        val txtApellidoMatET = findViewById<TextInputEditText>(R.id.txtApellidoMatET)
        val txtCelularET = findViewById<TextInputEditText>(R.id.txtCelularET)
        val txtCorreoET = findViewById<TextInputEditText>(R.id.txtCorreoET)

        var esTecnico: String
        val rbTecnico = findViewById<RadioButton>(R.id.rbTecnico)
        val rbCliente = findViewById<RadioButton>(R.id.rbCliente)

        if(rbTecnico.isChecked)
            esTecnico = "1"
        else
            esTecnico = "0"

        if(rbCliente.isChecked)
            esTecnico = "0"
        else
            esTecnico = "1"

        val parametrosTokenPost: RequestBody = FormBody.Builder()
            .add("NOMBRE", txtNombreET.text.toString())
            .add("APELLIDOPATERNO", txtApellidoPatET.text.toString())
            .add("APELLIDOMATERNO", txtApellidoMatET.text.toString())
            .add("CELULAR", txtCelularET.text.toString())
            .add("CORREO", txtCorreoET.text.toString())
            .add("IDEVENTO", idTecEventoPresentacion!!.toString())
            .add("ESTECNICO", esTecnico)
            .add("TOKEN", token)

            .build()


        val getTecInfo: Call<ApiResponse<Boolean?>?>? = _apiService?.GuardarTecnicoEventoManual(
            parametrosTokenPost
        )
        getTecInfo!!.enqueue(object : Callback<ApiResponse<Boolean?>?> {
            override fun onResponse(
                call: Call<ApiResponse<Boolean?>?>?,
                response: Response<ApiResponse<Boolean?>?>?
            ) {
                if (response!!.isSuccessful) {

                    if (response.body()?.isSucceeded == true) {
                        nDialog.dismiss()
                        var esTecnicoDisplay = "tecnico"

                        if(esTecnico.equals("0"))
                            esTecnicoDisplay = "cliente"

                        SonarBeepBien("Se registró al " + esTecnicoDisplay + " " + txtNombreET.text.toString() + " a la presentación con exito")
                        showToastMessage("Se registró al " + esTecnicoDisplay + " " + txtNombreET.text.toString() + " a la presentación con exito")

                        val cbAlmacenMS = findViewById<MaterialSpinner>(R.id.cbAlmacenMS)
                        SeleccionaAlmacen(cbAlmacenMS)

                        LimpiarTextos()

                    } else {
                        BeautifulDialog.build(this@Main)
                            .title("Error al registrar", titleColor = R.color.button_grey)
                            .body("Error " + response.body()?.error?.description,  color = R.color.button_grey)
                            .type(type= BeautifulDialog.TYPE.ERROR)
                            .position(BeautifulDialog.POSITIONS.CENTER)
                            .onPositive("OK") {
                                Toast.makeText(getApplicationContext(), "confirmar", Toast.LENGTH_SHORT).show()
                            }
                            .hideNegativeButton(hide = true)

                        response.body()?.error?.description?.let { SonarBeepError(it) }
                        showToastMessage(response.body()?.error?.description)
                    }
                } else {
                    SonarBeepError("Error en la respuesta de la conexión, puede intentarlo más tarde")
                    showToastMessage("Error en la respuesta de la conexión, puede intentarlo más tarde")
                }
                nDialog.dismiss()
            }

            override fun onFailure(call: Call<ApiResponse<Boolean?>?>, t: Throwable) {
                SonarBeepError("Error con la conexión, puede intentarlo más tarde")
                showToastMessage("Error con la conexión, puede intentarlo más tarde")
            }
        })
    }


    fun LimpiarTextos() {

        val txtNombreET = findViewById<TextInputEditText>(R.id.txtNombreET)
        val txtApellidoPatET = findViewById<TextInputEditText>(R.id.txtApellidoPatET)
        val txtApellidoMatET = findViewById<TextInputEditText>(R.id.txtApellidoMatET)
        val txtCelularET = findViewById<TextInputEditText>(R.id.txtCelularET)
        val txtCorreoET = findViewById<TextInputEditText>(R.id.txtCorreoET)

        txtNombreET.setText("")
        txtApellidoPatET.setText("")
        txtApellidoMatET.setText("")
        txtCelularET.setText("")
        txtCorreoET.setText("")
    }

    fun cargarTecEventos() {

        val getAllTecEvento: Call<ApiResponse<ArrayList<TecEvento?>?>?>? = _apiService?.getAllTecEventos
        val nDialog: ProgressDialog
        nDialog = ProgressDialog(this@Main)
        nDialog.setMessage("Todos los eventos")
        nDialog.setTitle("Obteniendo..")
        nDialog.isIndeterminate = false
        nDialog.setCancelable(false)
        nDialog.show()

        if (getAllTecEvento != null) {

            getAllTecEvento.enqueue(object : Callback<ApiResponse<ArrayList<TecEvento?>?>?>{
                override fun onResponse(
                    call: Call<ApiResponse<ArrayList<TecEvento?>?>?>,
                    response: Response<ApiResponse<ArrayList<TecEvento?>?>?>
                ) {

                    if (response.isSuccessful) {
                        val eventosList = response.body()!!
                        if (eventosList.isSucceeded) {
                            TecEventoList = eventosList.data

                            val contacts: ArrayList<modelEventoSpinner> = ArrayList<modelEventoSpinner>()

                            val cbAlmacenMS = findViewById<MaterialSpinner>(R.id.cbAlmacenMS)
                            cbAlmacenMS.setBackgroundResource(R.drawable.style_spinner_custom)

                            val temEventos = ArrayList<TecEvento?>()
                            for (evento in TecEventoList!!) {
                                if (evento != null) {
                                    if (evento.isActive) {
                                        temEventos.add(evento)

                                        idEventoHDR = evento.id.toString()

                                        for (i in 0 until evento.presentaciones!!.size) {

                                            val localDateTime: LocalDateTime =
                                                LocalDateTime.parse(evento.presentaciones!!.get(i).hora_inicio.toString())
                                            val formatter: DateTimeFormatter =
                                                DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")
                                            val fechaHoraCurso: String = formatter.format(localDateTime)

                                            contacts.add(
                                                modelEventoSpinner(
                                                    evento.presentaciones!!.get(i).ciudad + " - " + evento.presentaciones!!.get(i).direccion + " - " +
                                                            fechaHoraCurso,
                                                    evento.presentaciones!!.get(i).id.toString()

                                                )
                                            )
                                        }

                                        val adapter: ArrayAdapter<modelEventoSpinner> =
                                            ArrayAdapter<modelEventoSpinner>(
                                                applicationContext,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                contacts
                                            )

                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                        cbAlmacenMS.setAdapter(adapter)
                                        cbAlmacenMS.setOnItemSelectedListener { _, _, id, item -> SeleccionaAlmacen(cbAlmacenMS) }

                                        if (adapter.getCount() > 0) SeleccionaAlmacen(cbAlmacenMS)

                                    }
                                }
                            }

                            nDialog.dismiss()
                        }
                    }
                    else
                    {
                        nDialog.dismiss()
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<ArrayList<TecEvento?>?>?>,
                    t: Throwable
                ) {

                    Toast.makeText(getApplicationContext(),
                        "Error en la aplicacion: " + t.stackTrace + " - " + t.localizedMessage, Toast.LENGTH_LONG).show()

                    nDialog.dismiss()
                }
            })
        }
    }

    private fun SeleccionaAlmacen(cbAlmacenMS: MaterialSpinner) {


        val nDialog: ProgressDialog
        nDialog = ProgressDialog(this@Main)
        nDialog.setMessage("Todos los eventos")
        nDialog.setTitle("Obteniendo..")
        nDialog.isIndeterminate = false
        nDialog.setCancelable(false)
        nDialog.show()


        val almacenSeleccionado: modelEventoSpinner =
            cbAlmacenMS.getItems<Any>()[cbAlmacenMS.selectedIndex] as modelEventoSpinner //cbAlmacenMS.getListView().getSelectedItem();

        val sAlmacenIDSeleccionado: String = almacenSeleccionado.getname().toString()
       // val sAlmacenDescripcionSeleccionado: String = almacenSeleccionado.getnamedescription().toString()

        idTecEventoPresentacion = sAlmacenIDSeleccionado
        val token = "IkxKlMO035oXksdCwFasFw40XmgtFpeoLks"

        val parametrosTokenPost: RequestBody = FormBody.Builder()
            .add("IDPRESENTACION", idTecEventoPresentacion!!.toString())
            .add("TOKEN", token)
            .build()


        val getAllTecEvento: Call<ApiResponse<ArrayList<TecEventoPresentacionTotal?>?>?>?
                = _apiService?.getMaxConteoRegistros(parametrosTokenPost)


        if (getAllTecEvento != null) {

            getAllTecEvento.enqueue(object : Callback<ApiResponse<ArrayList<TecEventoPresentacionTotal?>?>?>{
                override fun onResponse(
                    call: Call<ApiResponse<ArrayList<TecEventoPresentacionTotal?>?>?>,
                    response: Response<ApiResponse<ArrayList<TecEventoPresentacionTotal?>?>?>
                ) {

                    if (response.isSuccessful) {
                        val eventosList = response.body()!!
                        if (eventosList.isSucceeded) {
                            TecEventoPresentacionTotalList = eventosList.data

                            for (eventoMaxConteo in TecEventoPresentacionTotalList!!) {

                                if (eventoMaxConteo != null) {

                                    val etMax: TextView = findViewById(R.id.etMax)
                                    val etCli: TextView = findViewById(R.id.etCli)
                                    val etTec: TextView = findViewById(R.id.etTec)
                                    val etRep: TextView = findViewById(R.id.etRep)
                                    val etTot: TextView = findViewById(R.id.etTot)



                                    val etManualAsistencia: TextView = findViewById(R.id.etManualAsistencia)
                                    val etCliAsistencia: TextView = findViewById(R.id.etCliAsistencia)
                                    val etTecAsistencia: TextView = findViewById(R.id.etTecAsistencia)
                                    val etTotAsistencia: TextView = findViewById(R.id.etTotAsistencia)


                                    etMax.text = eventoMaxConteo.TecEventoConteoMaxCupo.toString()
                                    etCli.text = eventoMaxConteo.TecEventoConteoClientes.toString()
                                    etTec.text = eventoMaxConteo.TecEventoConteoTecnicos.toString()
                                    etRep.text = eventoMaxConteo.TecEventoConteoRepetidos.toString()

                                    etManualAsistencia.text = eventoMaxConteo.TecEventoAsistenciaConteoManual.toString()
                                    etCliAsistencia.text = eventoMaxConteo.TecEventoAsistenciaConteoClientes.toString()
                                    etTecAsistencia.text = eventoMaxConteo.TecEventoAsistenciaConteoTecnicos.toString()

                                    val iTotal : Int = (eventoMaxConteo.TecEventoConteoClientes +
                                            eventoMaxConteo.TecEventoConteoTecnicos) -
                                            eventoMaxConteo.TecEventoConteoRepetidos

                                    val iTotalAsistencia : Int = (eventoMaxConteo.TecEventoAsistenciaConteoManual +
                                            eventoMaxConteo.TecEventoAsistenciaConteoClientes) +
                                            eventoMaxConteo.TecEventoAsistenciaConteoTecnicos

                                    etTot.text = iTotal.toString()
                                    etTotAsistencia.text = iTotalAsistencia.toString()

                                }
                            }
                            nDialog.dismiss()
                        }
                    }
                    else
                    {
                        nDialog.dismiss()
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse<ArrayList<TecEventoPresentacionTotal?>?>?>,
                    t: Throwable
                ) {

                    Toast.makeText(getApplicationContext(),
                        "Error en la aplicacion: " + t.stackTrace + " - " + t.localizedMessage, Toast.LENGTH_LONG).show()

                    nDialog.dismiss()
                }
            })
        }

    }

    fun showToastMessage(message: String?) {
        val toast = Toast.makeText(
            this@Main, message,
            Toast.LENGTH_LONG
        )

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            val tView = toast.view
            tView!!.setBackgroundColor(
                ContextCompat.getColor(
                    this@Main,
                    R.color.colorRojoDuventus
                )
            )
            val txtMessage = tView.findViewById<TextView>(android.R.id.message)
            txtMessage.setTextColor(Color.WHITE)
            txtMessage.textSize = 16f
            toast.setView(tView)
        }
        toast.show()
    }

    private fun SonarBeepBien(sMensaje: String) {
        Toast.makeText(this@Main, sMensaje, Toast.LENGTH_LONG).show()
        val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
    }

    private fun SonarBeepError(sMensajeError: String) {
        Toast.makeText(this@Main, sMensajeError, Toast.LENGTH_LONG).show()
        val toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        toneGen1.startTone(ToneGenerator.TONE_CDMA_ANSWER, 150)
    }
}