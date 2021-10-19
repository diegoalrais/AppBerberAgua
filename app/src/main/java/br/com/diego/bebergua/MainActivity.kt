package br.com.diego.bebergua

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.com.diego.bebergua.databinding.ActivityMainBinding
import br.com.diego.bebergua.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private var resultadoML = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendario: Calendar
    var horaAtual = 0
    var minutosAtuais = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Refatorei aqui. Ao invés do código original que usava o binding dentro de cada função para pegar os dados
        var edit_peso = binding.edtPeso
        var edit_idade = binding.edtIdade
        var txt_resultado_ml = binding.txtResultadoMl
        var txt_hora = binding.txtHora
        var txt_minutos = binding.txtMinute
        //
        supportActionBar!!.hide()
        //IniciarComponentes()
        calcularIngestaoDiaria = CalcularIngestaoDiaria()



        binding.btnCalcular.setOnClickListener {

            if (binding.edtPeso.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_peso, Toast.LENGTH_SHORT).show()
            } else if (binding.edtIdade.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_idade, Toast.LENGTH_SHORT).show()
            } else {
                val peso = edit_peso.text.toString().toDouble()
                val idade = edit_idade.text.toString().toInt()
                calcularIngestaoDiaria.CalculatTotalMl(peso, idade)
                resultadoML = calcularIngestaoDiaria.ResultadoML()
                val formatar = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                formatar.isGroupingUsed = false
                txt_resultado_ml.text = formatar.format(resultadoML) + " " + "L"
            }
        }

        binding.btnLembrete.setOnClickListener {
            calendario = Calendar.getInstance()
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
            minutosAtuais = calendario.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this, { timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                txt_hora.text = String.format("%02d", hourOfDay)
                txt_minutos.text = String.format("%02d", minutes)
            }, horaAtual, minutosAtuais, true)
            timePickerDialog.show()
        }

        binding.btnAlarme.setOnClickListener {

            if (!txt_hora.text.toString().isEmpty() && !txt_minutos.text.toString().isEmpty()) {
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, txt_hora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, txt_minutos.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarme_mensagem))
                startActivity(intent)

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }
        }

        binding.icRefresh.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_titulo)
                .setMessage(R.string.dialog_descricao)
                .setPositiveButton("Ok", {dialogInterface, i ->
                    edit_peso.setText("")
                    edit_idade.setText("")
                    txt_resultado_ml.setText("")

                    //Coloquei esse refresh também pra hora e minutos
                    txt_hora.setText("00")
                    txt_minutos.setText("00")
                })
            alertDialog.setNegativeButton("Cancelar", {dialogInterface, i->

            })
            val dialog = alertDialog.create()
            dialog.show()
        }

        //Adcionei a função para encerrar o programa
        binding.icFinish.setOnClickListener {
            finish()
        }
    }

}
/*
// Código de professor

    private fun IniciarComponentes() {
        edit_peso = findViewById(R.id.edt_peso)
        edit_idade = findViewById(R.id.edt_idade)
        bt_calcular = findViewById(R.id.btn_calcular)
        txt_resultado_ml = findViewById(R.id.txt_result)
        ic_redefinir_dados = findViewById(R.id.ic_refresh)
        bt_lembrete = findViewById(R.id.btn_lembrete)
        bt_alarme = findViewById(R.id.btn_alarme)
        txt_hora = findViewById(R.id.txt_hora)
        txt_minutos = findViewById(R.id.txt_minute)
    }
     */