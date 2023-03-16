package com.example.intentsuma

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class SecondActivity : AppCompatActivity() {

    val NUMERO_UNO = 1
    val NUMERO_DOS = 2

    var datosRecibidos:Boolean = false
    var resultadoObtenido:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        var numeroUno = findViewById<TextView>(R.id.numeroUno)
        var numeroDos = findViewById<TextView>(R.id.numeroDos)
        val resultado = findViewById<TextView>(R.id.resultadoSuma)


        var anterior = findViewById<Button>(R.id.anterior_Btn)
        var enviarResultado = findViewById<Button>(R.id.enviarResultado_Btn)

        anterior.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        enviarResultado.setOnClickListener(){
            var main = MainActivity()
            if(resultadoObtenido == true) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Resultado", resultado.text.toString()) //Enviamos al MainActivity la cadena del TextView "resultado" asignándole a dicha cadena el id "Resultado"
                setResult(Activity.RESULT_OK, intent);

                main.recibirResultado() //Avisamos a MainActivity de que ya le hemos enviado el Resultado de la suma
            }else{
                Toast.makeText(this@SecondActivity, "Lo sentimos, no existe un resultado para enviar.", Toast.LENGTH_SHORT).show()
            }
        }

        if(datosRecibidos == true){
            recogerDatos()

            var numeroUnoInt: Int = numeroUno.text.toString().toInt()
            var numeroDosInt: Int = numeroDos.text.toString().toInt()

            val resultadoInt: Int = suma(numeroUnoInt, numeroDosInt)
            val resultadoString = resultadoInt.toString()

            resultado.text = resultadoString
            resultadoObtenido = true
        }
    }


    /*Este método recibe del MainActivity una cadena de id "PrimerNumero" y la muestra en el TextView "numero1"
    y recibe otra cadena con id "SegundoNumero" y la muestra en el TextView "numero2"*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var numeroUno = findViewById<TextView>(R.id.numero1)
        var numeroDos = findViewById<TextView>(R.id.numero2)
        if(resultCode != Activity.RESULT_OK) return
        when(requestCode) {
            NUMERO_UNO -> {
                if (data != null) {
                    numeroUno.text = data.getStringExtra("PrimerNumero")
                }; }
            // Other result codes
            else -> {
                Toast.makeText(this@SecondActivity, "Lo sentimos, el primer número no ha sido enviado", Toast.LENGTH_SHORT).show()
            }
        }
        when(requestCode) {
            NUMERO_DOS -> {
                if (data != null) {
                    numeroDos.text = data.getStringExtra("SegundoNumero")
                }; }
            // Other result codes
            else -> {
                Toast.makeText(this@SecondActivity, "Lo sentimos, el segundo número no ha sido enviado.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    //Método que recibe dos numeros y devuelve la suma de ambos
    fun suma(numeroUno: Int, numeroDos: Int): Int {
        val suma: Int = numeroUno + numeroDos
        return suma
    }



    fun recogerDatos(){
        val intent = Intent(this, MainActivity::class.java)
        startActivityForResult(intent, NUMERO_UNO)
        startActivityForResult(intent, NUMERO_DOS)
    }


    /*Método que será llamado desde el MainActivity una vez que el usuario pulse el botón Enviar Datos y se hayan pasado los
    dos numeros desde el MainActivity al SecondActivity*/
    fun datosRecibidos(){
        datosRecibidos = true
    }
}