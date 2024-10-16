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

    //variables que encara no s'inicialitzen
    lateinit var txtInput: TextView
    lateinit var txtOutput: TextView
    lateinit var formatoDecimal: DecimalFormat

    //variables
    private var numeroActual: String = "" //suma de numeros
    private var cotitzacio: Double = 0.0 //variable per la cotitzacio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            //busquem id de les variables del text
            txtInput = findViewById(R.id.txtInput)
            txtOutput = findViewById(R.id.txtOutput)
            formatoDecimal = DecimalFormat("#,##00")

            //inicialitzem variables de les criptomonedes
            val btnBitcoin: Button = findViewById(R.id.btnBitcoin)
            val btnEtherum: Button = findViewById(R.id.btnEtherum)
            val btnTether: Button = findViewById(R.id.btnTether)
            val btnXRP: Button = findViewById(R.id.btnXRP)

            //fem un set listener i les dirigim cap a la funcio del dialog
            //per així que posi elvalor
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
        }catch (e: Exception){
            supportActionBar?.title = "S'ha produit un error"
        }
    }

    //funcio per cuan apreta algun numero
    fun apretarNumero(view: View){

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
                    mostraError("No pots més de 2 decimals.")
                }
            } else {
                numeroActual += numero
                txtInput.text = numeroActual
                calcularCotitzacio()
            }
        } catch (e: Exception) {
            supportActionBar?.title = "S'ha produit un error"
        }
    }

    //funció per si borra només un numero
    fun borrarNumero(view: View){

        if (numeroActual.isNotEmpty()) {
            numeroActual = numeroActual.dropLast(1)
            txtInput.text = numeroActual
            calcularCotitzacio()
        }
    }

    //funcio per borrar tot CE
    fun borrarTot(view: View){

        try {
            numeroActual = ""
            txtInput.text = ""
            txtOutput.text = ""
            calcularCotitzacio()
        }catch (e: Exception){
            mostraError("Error al añadir la coma: ${e.message}")
        }
    }

    //funció de la coma
    fun Coma(view: View){

        try {
            if (!numeroActual.contains(",")) {
                numeroActual += ","
                txtInput.text = numeroActual
            } else {
                mostraError("No puedes tener más de una coma.")
            }
        } catch (e: Exception) {
            mostraError("Error al añadir la coma: ${e.message}")
        }
    }

    //funció per calcular la cotitzacio
    fun calcularCotitzacio(){
        var resultat: Double = 0.0

        try {
            if (cotitzacio != 0.0 && numeroActual.isNotEmpty()) {
                val diners = numeroActual.replace(",", ".").toDouble()
                resultat = diners * cotitzacio
                txtOutput.text = formatoDecimal.format(resultat)
            } else {
                txtOutput.text = "0"
            }
        } catch (e: NumberFormatException) {
            mostraError("Error al calcular la cotización: ${e.message}")
        } catch (e: Exception) {
            mostraError("Error inesperado al calcular: ${e.message}")
        }
    }

    //funcio del dialog per mostar el valor de la cripto
    fun dialogSelecioCripto(){
        val edtDada: EditText = EditText(this)
        MaterialAlertDialogBuilder(this)
            .setTitle("Intodueix la cotització de la criptomoneda")
            .setCancelable(false)
            .setMessage("Quant costa la criptomoneda?")
            .setView(edtDada)
            .setNegativeButton("Cancelar",null)
            .setPositiveButton("Aceptar"){ dialog, which ->
                val inputCotitzacio = edtDada.text.toString()
                if(inputCotitzacio.isNotEmpty() && numeroActual.isNotEmpty()){
                    try{
                        cotitzacio = inputCotitzacio.toDouble()
                    }catch (e: Exception){
                        mostraError("Error inesperado al calcular: ${e.message}")
                    }
                }else{

                    mostraError("Possa un numerovàlid")
                }
            }
            .show()
    }

    //funcio del snacbar dels errors
    fun mostraError(missatge: String){
        Snackbar.make(findViewById(android.R.id.content),
            missatge, Snackbar.LENGTH_SHORT).show()
    }
}