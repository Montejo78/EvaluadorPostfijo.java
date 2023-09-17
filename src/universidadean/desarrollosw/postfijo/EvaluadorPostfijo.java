/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad Ean (Bogotá - Colombia)
 * Departamento de Tecnologías de la Información y Comunicaciones
 * Licenciado bajo el esquema Academic Free License version 2.1
 * <p>
 * Proyecto Evaluador de Expresiones Postfijas
 * Fecha: Febrero 2021
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package universidadean.desarrollosw.postfijo;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.*;

/**
 * Esta clase representa una clase que evalúa expresiones en notación polaca o
 * postfija. Por ejemplo: 4 5 +
 */
public class EvaluadorPostfijo {

    /**
     * Permite saber si la expresión en la lista está balanceada
     * o no. Cada elemento de la lista es un elemento. DEBE OBlIGATORIAMENTE
     * USARSE EL ALGORITMO QUE ESTÁ EN EL ENUNCIADO.
     */
    static boolean estaBalanceada(List<String> expresion) {
        Stack<String> delimitadores = new Stack<>();

        for (String elemento : expresion) {
            if (elemento.equals("(") || elemento.equals("[") || elemento.equals("{")) {
                delimitadores.push(elemento);
            } else if (elemento.equals(")") || elemento.equals("]") || elemento.equals("}")) {
                if (delimitadores.isEmpty()) {
                    return false;
                }
                String delimitadorAbierto = delimitadores.pop();
                if (!esDelimitadorValido(delimitadorAbierto, elemento)) {
                    return false;
                }
            }
        }

        return delimitadores.isEmpty();
    }
        // TODO: Escriba el algoritmo del enunciado aquí



    /**
     * Transforma la expresión, cambiando los símbolos de agrupación
     * de corchetes ([]) y llaves ({}) por paréntesis ()
     */
    static void reemplazarDelimitadores(List<String> expresion) {
        for (int i = 0; i < expresion.size(); i++) {
            String elemento = expresion.get(i);
            if (elemento.equals("[")) {
                expresion.set(i, "(");
            } else if (elemento.equals("]")) {
                expresion.set(i, ")");
            } else if (elemento.equals("{")) {
                expresion.set(i, "(");
            } else if (elemento.equals("}")) {
                expresion.set(i, ")");
            }
        }
    }
        // TODO: Escriba el algoritmo aquí


    /**
     * Realiza la conversión de la notación infija a postfija
     * @return la expresión convertida a postfija
     * OJO: Debe usarse el algoritmo que está en el enunciado OBLIGATORIAMENTE
     */
    static List<String> convertirAPostfijo(List<String> expresion) {
        Stack<String> pila = new Stack<>();
        List<String> salida = new ArrayList<>();

        Map<String, Integer> precedencia = new HashMap<>();
        precedencia.put("+", 1);
        precedencia.put("-", 1);
        precedencia.put("*", 2);
        precedencia.put("/", 2);
        precedencia.put("%", 2);

        for (String elemento : expresion) {
            if (esOperador(elemento)) {
                while (!pila.isEmpty() && esOperador(pila.peek()) &&
                        precedencia.get(elemento) <= precedencia.get(pila.peek())) {
                    salida.add(pila.pop());
                }
                pila.push(elemento);
            } else if (elemento.equals("(")) {
                pila.push(elemento);
            } else if (elemento.equals(")")) {
                while (!pila.isEmpty() && !pila.peek().equals("(")) {
                    salida.add(pila.pop());
                }
                pila.pop();
            } else {
                salida.add(elemento);
            }
        }

        while (!pila.isEmpty()) {
            salida.add(pila.pop());
        }

        return salida;
    }

        // TODO: Escriba el algoritmo aquí



    /**
     * Realiza la evaluación de la expresión postfijo utilizando una pila
     * @param expresion una lista de elementos con números u operadores
     * @return el resultado de la evaluación de la expresión.
     */
    static int evaluarPostFija(List<String> expresion) {
        Stack<Integer> pila = new Stack<>();
        for (String elemento : expresion) {
            if (esOperador(elemento)) {
                int operand2 = pila.pop();
                int operand1 = pila.pop();
                int resultado = aplicarOperador(elemento, operand1, operand2);
                pila.push(resultado);
            } else {
                pila.push(Integer.parseInt(elemento));
            }
        }

        return pila.pop();
    }

    private static boolean esDelimitadorValido(String abierto, String cerrado) {
        return (abierto.equals("(") && cerrado.equals(")")) ||
                (abierto.equals("[") && cerrado.equals("]")) ||
                (abierto.equals("{") && cerrado.equals("}"));
    }

    private static boolean esOperador(String elemento) {
        return "+-*/%".contains(elemento);
    }

    private static int aplicarOperador(String operador, int operand1, int operand2) {
        switch (operador) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            case "%":
                return operand1 % operand2;
            default:
                throw new IllegalArgumentException("Operador no válido: " + operador);
        }
    }
}


        // TODO: Realiza la evaluación de la expresión en formato postfijo


