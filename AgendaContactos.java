import java.util.HashMap;
import java.util.Scanner;

public class AgendaContactos {

    public static void main(String[] args) {
        // Crear el HashMap que almacenará los contactos
        HashMap<String, String> agenda = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        // Menú principal
        while (opcion != 6) {
            System.out.println("\n*** Agenda de Contactos ***");
            System.out.println("1. Mostrar contactos");
            System.out.println("2. Añadir contacto");
            System.out.println("3. Modificar contacto");
            System.out.println("4. Eliminar contacto");
            System.out.println("5. Vaciar agenda");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    mostrarContactos(agenda);
                    break;
                case 2:
                    añadirContacto(agenda, scanner);
                    break;
                case 3:
                    modificarContacto(agenda, scanner);
                    break;
                case 4:
                    eliminarContacto(agenda, scanner);
                    break;
                case 5:
                    vaciarAgenda(agenda);
                    break;
                case 6:
                    System.out.println("Saliendo de la agenda...");
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }

        scanner.close();
    }

    // Mostrar todos los contactos
    public static void mostrarContactos(HashMap<String, String> agenda) {
        if (agenda.isEmpty()) {
            System.out.println("La agenda está vacía.");
        } else {
            System.out.println("\nContactos en la agenda:");
            for (String nombre : agenda.keySet()) {
                System.out.println("Nombre: " + nombre + ", Teléfono: " + agenda.get(nombre));
            }
        }
    }

    // Añadir un contacto nuevo
    public static void añadirContacto(HashMap<String, String> agenda, Scanner scanner) {
        System.out.print("Introduce el nombre del contacto: ");
        String nombre = scanner.nextLine();
        if (agenda.containsKey(nombre)) {
            System.out.println("El contacto ya existe. Usa la opción de modificar.");
        } else {
            System.out.print("Introduce el número de teléfono: ");
            String telefono = scanner.nextLine();
            agenda.put(nombre, telefono);
            System.out.println("Contacto añadido correctamente.");
        }
    }

    // Modificar un contacto existente
    public static void modificarContacto(HashMap<String, String> agenda, Scanner scanner) {
        System.out.print("Introduce el nombre del contacto a modificar: ");
        String nombre = scanner.nextLine();
        if (agenda.containsKey(nombre)) {
            System.out.print("Introduce el nuevo número de teléfono: ");
            String nuevoTelefono = scanner.nextLine();
            agenda.put(nombre, nuevoTelefono);
            System.out.println("Contacto modificado correctamente.");
        } else {
            System.out.println("El contacto no existe.");
        }
    }

    // Eliminar un contacto
    public static void eliminarContacto(HashMap<String, String> agenda, Scanner scanner) {
        System.out.print("Introduce el nombre del contacto a eliminar: ");
        String nombre = scanner.nextLine();
        if (agenda.containsKey(nombre)) {
            agenda.remove(nombre);
            System.out.println("Contacto eliminado correctamente.");
        } else {
            System.out.println("El contacto no existe.");
        }
    }

    // Vaciar la agenda
    public static void vaciarAgenda(HashMap<String, String> agenda) {
        agenda.clear();
        System.out.println("La agenda ha sido vaciada.");
    }
}
