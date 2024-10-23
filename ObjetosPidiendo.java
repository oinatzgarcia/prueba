import java.util.Scanner;

class Cuadrado {
    // Atributos
    private int lado;

    // Constructor
    public Cuadrado(int lado) {
        this.lado = lado;
    }

    // Métodos
    public double calcularArea() {
        double area = lado * lado;
        return area;
    }

    public double calcularPerimetro() {
        double perimetro = lado * 4;
        return perimetro;
    }
}

class Rectangulo {
    // Atributos
    private int lado1;
    private int lado2;

    // Constructor
    public Rectangulo(int lado1, int lado2) {
        this.lado1 = lado1;
        this.lado2 = lado2;
    }

    // Métodos
    public double calcularArea() {
        double area = lado1 * lado2;
        return area;
    }

    public double calcularPerimetro() {
        double perimetro = (lado1 * 2) + (lado2 * 2);
        return perimetro;
    }
}

class Circulo {
    // Atributos
    private int radio;

    // Constructor
    public Circulo(int radio) {
        this.radio = radio;
    }

    // Métodos
    public double calcularArea() {
        double area = radio * radio * Math.PI;
        return area;
    }

    public double calcularPerimetro() {
        double perimetro = radio * 2 * Math.PI;
        return perimetro;
    }
}

public class ObjetosPidiendo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear arrays de figuras
        Cuadrado[] cuadrados = new Cuadrado[3];
        Rectangulo[] rectangulos = new Rectangulo[2];
        Circulo[] circulos = new Circulo[4];

        // Solicitar datos al usuario para los cuadrados
        System.out.println("Introduce los lados de los cuadrados:");
        for (int i = 0; i < cuadrados.length; i++) {
            System.out.print("Lado del cuadrado " + (i + 1) + ": ");
            int lado = scanner.nextInt();
            cuadrados[i] = new Cuadrado(lado);
        }

        // Solicitar datos al usuario para los rectángulos
        System.out.println("Introduce los lados de los rectángulos:");
        for (int i = 0; i < rectangulos.length; i++) {
            System.out.print("Lado 1 del rectángulo " + (i + 1) + ": ");
            int lado1 = scanner.nextInt();
            System.out.print("Lado 2 del rectángulo " + (i + 1) + ": ");
            int lado2 = scanner.nextInt();
            rectangulos[i] = new Rectangulo(lado1, lado2);
        }

        // Solicitar datos al usuario para los círculos
        System.out.println("Introduce los radios de los círculos:");
        for (int i = 0; i < circulos.length; i++) {
            System.out.print("Radio del círculo " + (i + 1) + ": ");
            int radio = scanner.nextInt();
            circulos[i] = new Circulo(radio);
        }

        // Recorrer y calcular el área y perímetro de cada figura
        System.out.println("\nCuadrados:");
        for (Cuadrado cuadrado : cuadrados) {
            System.out.println("Área: " + cuadrado.calcularArea() + ", perímetro: " + cuadrado.calcularPerimetro());
        }

        System.out.println("\nRectángulos:");
        for (Rectangulo rectangulo : rectangulos) {
            System.out.println("Área: " + rectangulo.calcularArea() + ", perímetro: " + rectangulo.calcularPerimetro());
        }

        System.out.println("\nCírculos:");
        for (Circulo circulo : circulos) {
            System.out.println("Área: " + circulo.calcularArea() + ", perímetro: " + circulo.calcularPerimetro());
        }

        scanner.close();
    }
}
