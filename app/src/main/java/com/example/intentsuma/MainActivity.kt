package com.example.intentsuma

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var resultadoRecibido:Boolean = false
    val RESULTADO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var hacerFoto = findViewById<Button>(R.id.foto_Btn)
        var enviarDatos = findViewById<Button>(R.id.envioDatos_Btn)
        var siguiente = findViewById<Button>(R.id.siguiente_Btn)

        hacerFoto.setOnClickListener(){

        }

        enviarDatos.setOnClickListener(){
            val second = SecondActivity()
            val intent = Intent(this, SecondActivity::class.java)
4
            var numeroUno: EditText = findViewById<EditText>(R.id.numeroUno)
            var numeroDos: EditText = findViewById<EditText>(R.id.numeroDos)

            intent.putExtra("PrimerNumero", numeroUno.text.toString()) //Enviamos al SecondActivity la cadena del TextView "numeroUno" asignándole a dicha cadena el id "PrimerNumero"
            intent.putExtra("SegundoNumero", numeroDos.text.toString()) //Enviamos al SecondActivity la cadena del TextView "numeroDos" asignándole a dicha cadena el id "SegundoNumero"
            setResult(Activity.RESULT_OK, intent);

            second.datosRecibidos() //Avisamos a SecondActivity de que ya le hemos enviado los datos
        }

        siguiente.setOnClickListener(){
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        if(resultadoRecibido == true){
            val intent = Intent(this, SecondActivity::class.java)
            startActivityForResult(intent, RESULTADO)
        }

    }

    //Este método recibe del SecondActivity una cadena con id "Resultado" y la muestra en el TextView "Resultado"
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val Resultado = findViewById<TextView>(R.id.Resultado)
        if(resultCode != Activity.RESULT_OK) return
        when(requestCode) {
            RESULTADO -> {
                if (data != null) {
                    Resultado.text = data.getStringExtra("Resultado")
                }; }
            // Other result codes
            else -> {}
        }
    }

    /*Método que será llamado desde el SecondActivity cuando el usuario pulse el botón Enviar Resultado
    y se haya pasado el resultado del SecondActivity al MainActivity*/
    fun recibirResultado(){
        resultadoRecibido = true
    }
}