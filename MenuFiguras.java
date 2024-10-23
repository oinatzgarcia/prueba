import java.util.Scanner;

class Cuadrado {
    private int lado;

    public Cuadrado(int lado) {
        this.lado = lado;
    }

    public double calcularArea() {
        return lado * lado;
    }

    public double calcularPerimetro() {
        return lado * 4;
    }
}

class Rectangulo {
    private int lado1;
    private int lado2;

    public Rectangulo(int lado1, int lado2) {
        this.lado1 = lado1;
        this.lado2 = lado2;
    }

    public double calcularArea() {
        return lado1 * lado2;
    }

    public double calcularPerimetro() {
        return (lado1 * 2) + (lado2 * 2);
    }
}

class Circulo {
    private int radio;

    public Circulo(int radio) {
        this.radio = radio;
    }

    public double calcularArea() {
        return radio * radio * Math.PI;
    }

    public double calcularPerimetro() {
        return radio * 2 * Math.PI;
    }
}

public class MenuFiguras {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 4) { // Opción 4 será para salir
            System.out.println("\n*** Menú de Figuras ***");
            System.out.println("1. Calcular Cuadrado");
            System.out.println("2. Calcular Rectángulo");
            System.out.println("3. Calcular Círculo");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    calcularCuadrado(scanner);
                    break;
                case 2:
                    calcularRectangulo(scanner);
                    break;
                case 3:
                    calcularCirculo(scanner);
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }

        scanner.close();
    }

    public static void calcularCuadrado(Scanner scanner) {
        System.out.print("Introduce el lado del cuadrado: ");
        int lado = scanner.nextInt();
        Cuadrado cuadrado = new Cuadrado(lado);
        System.out.println("Área del cuadrado: " + cuadrado.calcularArea());
        System.out.println("Perímetro del cuadrado: " + cuadrado.calcularPerimetro());
    }

    public static void calcularRectangulo(Scanner scanner) {
        System.out.print("Introduce el lado 1 del rectángulo: ");
        int lado1 = scanner.nextInt();
        System.out.print("Introduce el lado 2 del rectángulo: ");
        int lado2 = scanner.nextInt();
        Rectangulo rectangulo = new Rectangulo(lado1, lado2);
        System.out.println("Área del rectángulo: " + rectangulo.calcularArea());
        System.out.println("Perímetro del rectángulo: " + rectangulo.calcularPerimetro());
    }

    public static void calcularCirculo(Scanner scanner) {
        System.out.print("Introduce el radio del círculo: ");
        int radio = scanner.nextInt();
        Circulo circulo = new Circulo(radio);
        System.out.println("Área del círculo: " + circulo.calcularArea());
        System.out.println("Perímetro del círculo: " + circulo.calcularPerimetro());
    }
}
