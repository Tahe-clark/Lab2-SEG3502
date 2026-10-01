package seg3502.calculator

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class CalculatorController {

    @RequestMapping("/")
    fun home(model: Model): String {
        model.addAttribute("first", "")
        model.addAttribute("second", "")
        model.addAttribute("operation", "+")
        model.addAttribute("result", "")
        model.addAttribute("error", "")
        return "home"
    }

    @GetMapping("/calculate")
    fun calculate(
        @RequestParam(defaultValue = "") first: String,
        @RequestParam(defaultValue = "") second: String,
        @RequestParam(defaultValue = "") operation: String,
        model: Model
    ): String {
        // On renvoie toujours les valeurs saisies pour les réafficher
        model.addAttribute("first", first)
        model.addAttribute("second", second)
        model.addAttribute("operation", operation)
        model.addAttribute("result", "")
        model.addAttribute("error", "")

        // 1. Conversion texte -> nombre (null si invalide)
        val a = first.toDoubleOrNull()
        val b = second.toDoubleOrNull()
        if (a == null || b == null) {
            model.addAttribute("error", "Veuillez saisir deux nombres valides.")
            return "home"
        }

        // 2. Le calcul selon l'opération choisie
        val result: Double = when (operation) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> {
                if (b == 0.0) {
                    model.addAttribute("error", "Division par zéro impossible.")
                    return "home"
                }
                a / b
            }
            else -> {
                model.addAttribute("error", "Opération inconnue.")
                return "home"
            }
        }

        // 3. Affichage propre : 5 au lieu de 5.0, mais 2.5 reste 2.5
        val affichage = if (result % 1.0 == 0.0) result.toLong().toString() else result.toString()
        model.addAttribute("result", affichage)
        return "home"
    }
}