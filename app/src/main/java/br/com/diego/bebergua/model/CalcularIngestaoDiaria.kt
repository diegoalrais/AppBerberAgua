package br.com.diego.bebergua.model

class CalcularIngestaoDiaria {
    private val ML_JOVEM = 40.0
    private val ML_ADULTO = 35.0
    private val ML_IDOSO = 30.0
    private val ML_MAIS_DE_66_ANOS = 25.0

    private var resultadoMl = 0.0
    private var resultato_total_ml = 0.0

    fun CalculatTotalMl(peso: Double, idade: Int){

        // Transformei ml em litros
        if (idade <= 17){
            resultadoMl = peso * ML_JOVEM
            resultato_total_ml = resultadoMl / 1000
        } else if (idade <= 55){
            resultadoMl = peso * ML_ADULTO
            resultato_total_ml = resultadoMl / 1000
        } else if (idade <= 65){
            resultadoMl = peso * ML_IDOSO
            resultato_total_ml = resultadoMl / 1000
        } else {
            resultadoMl = peso * ML_MAIS_DE_66_ANOS
            resultato_total_ml = resultadoMl / 1000
        }

    }

    fun ResultadoML(): Double {
        return resultato_total_ml
    }
}