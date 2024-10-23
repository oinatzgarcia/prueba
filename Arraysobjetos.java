class Cuadrado{
    // Atributos
    private int lado;

    // Constructor
    public Cuadrado(int lado){
        this.lado = lado;
    }

    // Métodos
    public double calcularArea(){ // como el metodo devuelve algo se usa double en vez de void
        double area = lado*lado;
        return area;
    }
    public double  calcularPerimetro(){
        double perimetro = lado*4;
        return perimetro;
    }
}

class Rectangulo{
    // Atributos
    private int lado1;
    private int lado2;

    // Constructor
    public Rectangulo(int lado1, int lado2){
        this.lado1 = lado1;
        this.lado2 = lado2;
    }

    // Métodos
    public double calcularArea(){
        double area = lado1*lado2;
        return area;
    }
    public double  calcularPerimetro(){
        double perimetro = (lado1*2)+(lado2*2);
        return perimetro;
    }
}

class Circulo{
    // Atributos
    private int radio;

    // Constructor
    public Circulo(int radio){
        this.radio = radio;
    }

    // Métodos
    public double calcularArea(){
        double area = radio*radio*Math.PI;
        return area;
    }
    public double  calcularPerimetro(){
        double perimetro = radio*2*Math.PI;
        return perimetro;
    }
}

public class Arraysobjetos {
    public static void main(String[] args) {
        // Crear arrays de figuras
        Cuadrado[] cuadrados = new Cuadrado[3]; 
        Rectangulo[] rectangulos = new Rectangulo[2];
        Circulo[] circulos = new Circulo[4];

        // Inicializar los arrays con valores
        cuadrados[0] = new Cuadrado(5);
        cuadrados[1] = new Cuadrado(3);
        cuadrados[2] = new Cuadrado(7);

        rectangulos[0] = new Rectangulo(4, 6);
        rectangulos[1] = new Rectangulo(2, 8);

        circulos[0] = new Circulo(2);
        circulos[1] = new Circulo(5);
        circulos[2] = new Circulo(3);
        circulos[3] = new Circulo(1);

        // Recorrer y calcular el área y perímetro de cada figura
        System.out.println("Cuadradors: ");
        for (Cuadrado cuadrado : cuadrados) {
            System.out.println("Área: "+cuadrado.calcularArea()+", perímetro: "+ cuadrado.calcularPerimetro());
        }

        System.out.println("Rectángulo: ");
        for (Rectangulo rectangulo : rectangulos) {
            System.out.println("Área: "+rectangulo.calcularArea()+", perímetro: "+ rectangulo.calcularPerimetro());
        }

        System.out.println("Circulo: ");
        for (Circulo circulo : circulos) {
            System.out.println("Área: "+circulo.calcularArea()+", perímetro: "+ circulo.calcularPerimetro());
        }
    }
}