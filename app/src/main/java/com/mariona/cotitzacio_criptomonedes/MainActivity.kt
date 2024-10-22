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
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.Exception
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Variables que aún no se inicializan
    lateinit var txtInput: TextView
    lateinit var txtOutput: TextView
    lateinit var formatoDecimal: DecimalFormat

    // Variables
    private var numeroActual: String = "" // suma de números
    private var cotitzacio: Double = 0.0 // variable para la cotización
    private var criptoSelecionada: Boolean = false
    private var dobleBitcoin: Boolean = false
    private var dobleEtherum: Boolean = false
    private var dobleTether: Boolean = false
    private var dobleXRP: Boolean = false

    private var selectedButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val errorCriptomoneda: String = getString(R.string.errorCriptomoneda)

        try {
            // Buscar ID de las variables de texto
            txtInput = findViewById(R.id.txtInput)
            txtOutput = findViewById(R.id.txtOutput)

            // Configurar el formato decimal con coma
            val symbols = DecimalFormatSymbols(Locale.getDefault())
            symbols.decimalSeparator = ','
            symbols.groupingSeparator = '.'
            formatoDecimal = DecimalFormat("#,##0.00", symbols)

            // Inicializar variables de las criptomonedas
            val btnBitcoin: Button = findViewById(R.id.btnBitcoin)
            val btnEtherum: Button = findViewById(R.id.btnEtherum)
            val btnTether: Button = findViewById(R.id.btnTether)
            val btnXRP: Button = findViewById(R.id.btnXRP)

            // Set listener y dirigirlas a la función del diálogo para que ponga el valor
            btnBitcoin.setOnClickListener {
                if (!dobleBitcoin) {
                    dobleBitcoin = true
                    selectButton(btnBitcoin)
                    dialogSelecioCripto()
                } else {
                    mostraError(errorCriptomoneda)
                }
            }
            btnEtherum.setOnClickListener {
                if (!dobleEtherum) {
                    dobleEtherum = true
                    selectButton(btnEtherum)
                    dialogSelecioCripto()
                } else {
                    mostraError(errorCriptomoneda)
                }
            }
            btnTether.setOnClickListener {
                if (!dobleTether) {
                    dobleTether = true
                    selectButton(btnTether)
                    dialogSelecioCripto()
                } else {
                    mostraError(errorCriptomoneda)
                }
            }
            btnXRP.setOnClickListener {
                if (!dobleXRP) {
                    dobleXRP = true
                    selectButton(btnXRP)
                    dialogSelecioCripto()
                } else {
                    mostraError(errorCriptomoneda)
                }
            }

            // Restaurar el estado si existe
            savedInstanceState?.let {
                numeroActual = it.getString("numeroActual", "")
                cotitzacio = it.getDouble("cotitzacio", 0.0)
                criptoSelecionada = it.getBoolean("criptoSelecionada", false)
                dobleBitcoin = it.getBoolean("dobleBitcoin", false)
                dobleEtherum = it.getBoolean("dobleEtherum", false)
                dobleTether = it.getBoolean("dobleTether", false)
                dobleXRP = it.getBoolean("dobleXRP", false)

                txtInput.text = numeroActual
                calcularCotitzacio()
            }

        } catch (e: Exception) {
            mostraError(errorCriptomoneda)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("numeroActual", numeroActual)
        outState.putDouble("cotitzacio", cotitzacio)
        outState.putBoolean("criptoSelecionada", criptoSelecionada)
        outState.putBoolean("dobleBitcoin", dobleBitcoin)
        outState.putBoolean("dobleEtherum", dobleEtherum)
        outState.putBoolean("dobleTether", dobleTether)
        outState.putBoolean("dobleXRP", dobleXRP)
    }

    // Función para cuando se aprieta algún número
    fun apretarNumero(view: View) {
        val errorDoble: String = getString(R.string.errorDoble)
        val errorGeneral: String = getString(R.string.errorGeneral)
        val errorDecimal: String = getString(R.string.errorDecimal)

        if (!criptoSelecionada) {
            mostraError(errorDoble)
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
        val errorGeneral: String = getString(R.string.errorGeneral)
        try {
            numeroActual = ""
            txtInput.text = ""
            txtOutput.text = "0"
            calcularCotitzacio()
        } catch (e: Exception) {
            mostraError(errorGeneral)
        }
    }

    // Función para la coma
    fun Coma(view: View) {
        val errorComa: String = getString(R.string.errorComa)
        val errorCriptomoneda: String = getString(R.string.errorCriptomoneda)
        val errorNoComa: String = getString(R.string.errorNoComa)

        try {
            if (numeroActual.isNotEmpty()) {

                if (!criptoSelecionada) {
                    mostraError(errorCriptomoneda)
                    return
                }

                if (!numeroActual.contains(",")) {
                    numeroActual += ","
                    txtInput.text = numeroActual
                } else {
                    mostraError(errorComa)
                }
            } else {
                mostraError(errorNoComa)
            }
        } catch (e: Exception) {
            mostraError(errorComa)
        }
    }

    // Función para calcular la cotización
    fun calcularCotitzacio() {
        var resultat: Double = 0.0
        val errorValid: String = getString(R.string.errorValid)
        val errorGeneral: String = getString(R.string.errorGeneral)
        try {
            if (cotitzacio != 0.0 && numeroActual.isNotEmpty()) {
                val diners = numeroActual.replace(",", ".").toDouble()
                resultat = diners * cotitzacio
                txtOutput.text = formatoDecimal.format(resultat)
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
        val errorValid: String = getString(R.string.errorValid)
        val errorGeneral: String = getString(R.string.errorGeneral)

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
                        cotitzacio = inputCotitzacio.replace(",", ".").toDouble()
                        criptoSelecionada = true
                    } catch (e: Exception) {
                        mostraError(errorGeneral)
                    }
                } else {
                    mostraError(errorValid)
                }
            }
            .show()
    }

    // Función para seleccionar el botón y cambiar su color
    private fun selectButton(button: Button) {
        // Resetear el color del botón previamente seleccionado
        selectedButton?.setBackgroundColor(ContextCompat.getColor(this, R.color.defaultColor))
        // Cambiar el color del nuevo botón seleccionado
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.selectedColor))
        // Actualizar el botón seleccionado
        selectedButton = button
    }

    // Función del snackbar de los errores
    fun mostraError(missatge: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            missatge, Snackbar.LENGTH_SHORT
        ).show()
    }

    // Función para resetear el estado
    /*public fun resetState() {
        numeroActual = ""
        cotitzacio = 0.0
        criptoSelecionada = false
        txtInput.text = ""
        txtOutput.text = "0"
        dobleBitcoin = false
        dobleEtherum = false
        dobleTether = false
        dobleXRP = false
    }*/
}
