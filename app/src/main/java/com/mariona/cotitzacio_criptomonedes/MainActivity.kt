package com.mariona.cotitzacio_criptomonedes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import kotlin.Exception

class MainActivity : AppCompatActivity() {

    // Variables que aún no se inicializan
    lateinit var txtInput: TextView
    lateinit var txtOutput: TextView
    lateinit var formatoDecimal: DecimalFormat

    // Variables
    private var numeroActual: String = "" // suma de números
    private var cotitzacio: Double = 0.0 // variable para la cotización
    private var criptoSelecionada: Boolean = false
    private var dobleSeleccio: Boolean = false
    private var errorGeneral: String = getString(R.string.errorGeneral)
    private var errorCalcular: String = getString(R.string.errorCalcular)
    private var errorComa: String = getString(R.string.errorComa)
    private var errorValid: String = getString(R.string.errorValid)
    private var errorCriptomoneda: String = getString(R.string.errorCriptomoneda)
    private var errorDecimal: String = getString(R.string.errorDecimal)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            // Buscar ID de las variables de texto
            txtInput = findViewById(R.id.txtInput)
            txtOutput = findViewById(R.id.txtOutput)
            formatoDecimal = DecimalFormat("#,##0.00")

            // Inicializar variables de las criptomonedas
            val btnBitcoin: Button = findViewById(R.id.btnBitcoin)
            val btnEtherum: Button = findViewById(R.id.btnEtherum)
            val btnTether: Button = findViewById(R.id.btnTether)
            val btnXRP: Button = findViewById(R.id.btnXRP)

            // Set listener y dirigirlas a la función del diálogo para que ponga el valor
            btnBitcoin.setOnClickListener {
                dialogSelecioCripto()
            }
            btnEtherum.setOnClickListener {
                dialogSelecioCripto()
            }
            btnTether.setOnClickListener {
                dialogSelecioCripto()
            }
            btnXRP.setOnClickListener {
                dialogSelecioCripto()
            }

        } catch (e: Exception) {

            mostraError(errorGeneral)
        }
    }

    // Función para cuando se aprieta algún número
    fun apretarNumero(view: View) {

        if (!criptoSelecionada) {
            mostraError(errorCriptomoneda)
            return
        }

        try {
            // Variable para utilizar uno de los números como botón
            val button = view as Button

            // Variable para que almacene los números seleccionados
            val numero = button.text.toString()

            // Verificar si ya hay una coma en el número actual
            if (numeroActual.contains(",")) {
                // Verificar si ya tiene dos decimales
                if (numeroActual.substringAfter(",").length < 2) {
                    numeroActual += numero
                    txtInput.text = numeroActual
                    calcularCotitzacio()

                } else {
                    mostraError(errorDecimal)
                }
            } else {
                numeroActual += numero
                txtInput.text = numeroActual
                calcularCotitzacio()
            }
        } catch (e: Exception) {
            mostraError(errorGeneral)
        }
    }

    // Función para borrar solo un número
    fun borrarNumero(view: View) {

        if (numeroActual.isNotEmpty()) {
            numeroActual = numeroActual.dropLast(1)
            txtInput.text = numeroActual
            calcularCotitzacio()
        }
    }

    // Función para borrar todo (CE)
    fun borrarTot(view: View) {

        try {
            numeroActual = "0"
            txtInput.text = "0"
            txtOutput.text = "0"
            calcularCotitzacio()
        } catch (e: Exception) {
            mostraError(errorGeneral)
        }
    }

    // Función para la coma
    fun Coma(view: View) {

        if (!criptoSelecionada) {
            mostraError(errorCriptomoneda)
            return
        }


        try {
            if (!numeroActual.contains(",")) {
                numeroActual += ","
                txtInput.text = numeroActual
            } else {
                mostraError(errorValid)
            }
        } catch (e: Exception) {
            mostraError(errorGeneral)
        }
    }

    // Función para calcular la cotización
    fun calcularCotitzacio() {
        var resultat: Double = 0.0
        try {
            if (cotitzacio != 0.0 && numeroActual.isNotEmpty()) {
                val diners = numeroActual.replace(",", ".").toDouble()
                resultat = diners * cotitzacio
                txtOutput.text = resultat.toString()
            } else {
                txtOutput.text = "0"
            }
        } catch (e: NumberFormatException) {
            mostraError(errorValid)
        } catch (e: Exception) {
            mostraError(errorGeneral)
        }
    }

    // Función del diálogo para mostrar el valor de la cripto
    fun dialogSelecioCripto() {

        if(dobleSeleccio){
            mostraError("Només pots seleccionar una vegada la criptomoneda")
            return
        }

        val edtDada: EditText = EditText(this)
        MaterialAlertDialogBuilder(this)
            .setTitle("Introdueix la cotització de la criptomoneda")
            .setCancelable(false)
            .setMessage("Quant costa la criptomoneda?")
            .setView(edtDada)
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Aceptar") { dialog, which ->
                val inputCotitzacio = edtDada.text.toString()
                if (inputCotitzacio.isNotEmpty()) {
                    try {
                        cotitzacio = inputCotitzacio.toDouble()
                        criptoSelecionada = true
                        dobleSeleccio = true
                    } catch (e: Exception) {
                        mostraError(errorGeneral)
                    }
                } else {
                    mostraError(errorValid)
                }
            }
            .show()
    }

    // Función del snackbar de los errores
    fun mostraError(missatge: String) {

        Snackbar.make(findViewById(android.R.id.content),
            missatge, Snackbar.LENGTH_SHORT).show()
    }
}
