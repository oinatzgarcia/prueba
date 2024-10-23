
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Agenda {
    public static void main(String[] args) {
       Map<String,String> Agenda = new HashMap<>();
       Scanner scanner = new Scanner(System.in);
       System.out.println("Menú: ");
       System.out.println("   1 - Mostrar contactos ");
       System.out.println("   2 - Añadir contacto ");
       System.out.println("   3 - Modificar contacto ");
       System.out.println("   4 - Eliminar contacto ");
       System.out.println("   5 - Vaciar agenda ");
       int opcionmenu = scanner.nextInt();
       
       switch (opcionmenu) {

         case 1:
         System.out.println("   1 - Mostrar contactos ");
         System.out.println(Agenda);
         break;

         case 2:
         System.out.println("ingresa nombre contacto: ");
         String nombrecontacto = scanner.nextLine();

         System.out.println("ingresa número contacto: ");
         String numerocontacto = scanner.nextLine();

         Agenda.put(nombrecontacto, numerocontacto);
         break;

         case 3:
            
         break;

         case 4:
            
         break;

         case 5:
            
         break;
      
         default:
         System.out.println("Introduce un valor válido");
            break;
      }
        
       
      
    }
    }

