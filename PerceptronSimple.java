package ing.conocimiento;

import java.util.Random;

public class PerceptronSimple {
    private double[] pesos; // Pesos de las entradas
    private double bias;    // Peso asociado al BIAS
    private double tasaAprendizaje; // Tasa de aprendizaje

    // Constructor para inicializar pesos, bias y tasa de aprendizaje
    public PerceptronSimple(int numEntradas, double tasaAprendizaje) {
        this.tasaAprendizaje = tasaAprendizaje;
        this.pesos = new double[numEntradas];
        this.bias = new Random().nextDouble(); // Inicializamos el peso del bias aleatoriamente

        // Inicializamos los pesos aleatoriamente
        Random random = new Random();
        for (int i = 0; i < numEntradas; i++) {
            pesos[i] = random.nextDouble();
        }
    }

    // Función de activación (escalón binario)
    private int funcionActivacion(double suma) {
        return suma >= 0 ? 1 : -1;
    }

    // Método para predecir la salida
    public int predecir(int[] entrada) {
        // Implementación explícita de la fórmula de la función sumatoria
        double suma = 0;
        for (int i = 0; i < entrada.length; i++) {
            suma += pesos[i] * entrada[i]; // wi * xi
        }
        suma += (1 * bias); // θ * wθ, donde θ = 1
        return funcionActivacion(suma); // Aplicamos la función de activación
    }

    // Método para entrenar el perceptrón
    public void entrenar(int[][] entradas, int[] salidasDeseadas, int epocas) {
        for (int epoca = 0; epoca < epocas; epoca++) {
            System.out.println("\nÉpoca " + (epoca + 1) + ":");
            for (int i = 0; i < entradas.length; i++) {
                int[] entrada = entradas[i];
                int salidaDeseada = salidasDeseadas[i];

                // Mostrar la entrada actual
                System.out.println("  Muestra " + (i + 1) + ":");
                System.out.print("    Entrada: ");
                for (int e : entrada) {
                    System.out.print(e + " ");
                }
                System.out.println();

                // Predecir la salida y calcular la suma ponderada
                double suma = 0;
                for (int j = 0; j < entrada.length; j++) {
                    suma += pesos[j] * entrada[j];
                }
                suma += (1 * bias); // θ * wθ
                int salidaPredicha = funcionActivacion(suma);

                // Calcular el error
                int error = salidaDeseada - salidaPredicha;

                // Mostrar los valores calculados
                System.out.println("    Suma ponderada: " + suma);
                System.out.println("    Salida predicha: " + salidaPredicha);
                System.out.println("    Salida deseada: " + salidaDeseada);
                System.out.println("    Error: " + error);

                // Actualizar pesos y bias si hay error
                if (error != 0) {
                    System.out.println("    Actualizando pesos y bias...");
                    for (int j = 0; j < pesos.length; j++) {
                        double nuevoPeso = pesos[j] + tasaAprendizaje * error * entrada[j];
                        System.out.println("      Peso[" + j + "]: " + pesos[j] + " -> " + nuevoPeso);
                        pesos[j] = nuevoPeso;
                    }
                    double nuevoBias = bias + tasaAprendizaje * error;
                    System.out.println("      Bias: " + bias + " -> " + nuevoBias);
                    bias = nuevoBias;
                } else {
                    System.out.println("    Clasificada correctamente. No se necesita actualización.");
                }
            }
        }
    }

    // Método para mostrar los pesos y bias entrenados
    public void mostrarModelo() {
        System.out.println("\nPesos finales:");
        for (int i = 0; i < pesos.length; i++) {
            System.out.println("  Peso[" + i + "] = " + pesos[i]);
        }
        System.out.println("Bias = " + bias);
    }

    public static void main(String[] args) {
        // Datos de entrenamiento
        int[][] entradas = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
        };
        int[] salidasDeseadas = {-1, -1, -1, 1}; // Salidas esperadas para AND

        // Hiperparámetros
        double tasaAprendizaje = 0.1;
        int epocas = 10;

        // Crear y entrenar el perceptrón
        PerceptronSimple perceptron = new PerceptronSimple(2, tasaAprendizaje);
        perceptron.entrenar(entradas, salidasDeseadas, epocas);

        // Mostrar los pesos y el bias entrenados
        System.out.println("\nModelo entrenado:");
        perceptron.mostrarModelo();
    }
}