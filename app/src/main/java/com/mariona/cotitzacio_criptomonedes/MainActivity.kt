package com.mariona.cotitzacio_criptomonedes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    lateinit var txtInput: TextView
    lateinit var txtOutput: TextView
    lateinit var formatoDecimal: DecimalFormat

    private var btnBitcoin: Button? = null
    private var btnEtherum: Button? = null
    private var btnTether: Button? = null
    private var btnXRP: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtInput = findViewById(R.id.txtInput)
        txtOutput = findViewById(R.id.txtOutput)
        formatoDecimal = DecimalFormat("#,##")

        btnBitcoin = findViewById(R.id.btnBitcoin)
        btnEtherum = findViewById(R.id.btnEtherum)
        btnTether = findViewById(R.id.btnTether)
        btnXRP = findViewById(R.id.btnXRP)

        //declarar variables
        var diners: String = ""
        var btn0: Button
        var btn1: Button
        var btn2: Button
        var btn3: Button
        var btn4: Button
        var btn5: Button
        var btn6: Button
        var btn7: Button
        var btn8: Button
        var btn9: Button

        var btnCE: Button
        var btnBorrar: Button
        var btnComa: Button

        //find id
        btnBorrar = findViewById(R.id.btnBorrar)
        btnCE = findViewById(R.id.btnCE)
        btnComa = findViewById(R.id.btnComa)

        btn0= findViewById(R.id.btn0)
        btn1= findViewById(R.id.btn1)
        btn2= findViewById(R.id.btn2)
        btn3= findViewById(R.id.btn3)
        btn4= findViewById(R.id.btn4)
        btn5= findViewById(R.id.btn5)
        btn6= findViewById(R.id.btn6)
        btn7= findViewById(R.id.btn7)
        btn8= findViewById(R.id.btn8)
        btn9= findViewById(R.id.btn9)

        //fer setOnClickListener
        btn0.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+"0")
            txtOutput.setText("")
        }
        btn1.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+"1")
            txtOutput.setText("")
        }
        btn2.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+"2")
            txtOutput.setText("")
        }
        btn3.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+"3")
            txtOutput.setText("")
        }
        btn4.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+"4")
            txtOutput.setText("")
        }
        btn5.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+"5")
            txtOutput.setText("")
        }
        btn6.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+"6")
            txtOutput.setText("")
        }
        btn7.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+"7")
            txtOutput.setText("")
        }
        btn8.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+"8")
            txtOutput.setText("")
        }
        btn9.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+"9")
            txtOutput.setText("")
        }

        btnCE.setOnClickListener{
            txtInput.setText("")
            txtOutput.setText("")
        }

        btnBorrar.setOnClickListener{
            val numeroActual = txtInput.text.toString()
            if (numeroActual.isNotEmpty()) {
                val numeroAnterior = numeroActual.last()
                val textEditat = numeroActual.dropLast(1)
                txtInput.setText(textEditat)
                val digitBorrat = numeroAnterior.toString().toInt()
                diners = (diners.toDouble() - digitBorrat).toString()
            }
            else{
                txtInput.setText("0")
                diners = ""
            }
            txtOutput.setText("")
        }
        btnComa.setOnClickListener{
            txtInput.setText(txtInput.text.toString()+",")
            txtOutput.setText("")
        }
    }

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
                if(inputCotitzacio.isNotEmpty()){
                    try{
                        val cotitzacio = inputCotitzacio.toDouble()


                    }catch (e: Exception){
                        (this as MainActivity).supportActionBar?.setTitle("S'ha produit un error")
                    }
                }
            }
    }
}