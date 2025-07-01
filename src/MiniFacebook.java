import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class MiniFacebook {
    private ArrayList<Persona> personas = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> grafo = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> bloqueos = new ArrayList<>();
    private ArrayList<Amistad> amistades = new ArrayList<>();
    private Scanner lector = new Scanner(System.in);

    public void cargarPersonasDesdeArchivo(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                int id = Integer.parseInt(datos[0].trim()) - 1;
                String nombre = datos[1].trim();
                int dia = Integer.parseInt(datos[2].trim());
                int mes = Integer.parseInt(datos[3].trim());
                String actividad = datos[4].trim();
                String email = datos[5].trim();

                Persona p = new Persona(id, nombre, dia, mes, actividad, email);
                personas.add(p);
                grafo.add(new ArrayList<>());
                bloqueos.add(new ArrayList<>());
            }
            System.out.println("Personas cargadas exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public void agregarAmistad() {
        System.out.print("ID de la primera persona: ");
        int id1 = lector.nextInt() - 1;
        System.out.print("ID de la segunda persona: ");
        int id2 = lector.nextInt() - 1;
        lector.nextLine();

        if (!valido(id1) || !valido(id2)) {
            System.out.println("ID no válido.");
            return;
        }

        LocalDate fecha = LocalDate.now();
        if (!grafo.get(id1).contains(id2)) grafo.get(id1).add(id2);
        if (!grafo.get(id2).contains(id1)) grafo.get(id2).add(id1);
        amistades.add(new Amistad(id1, id2, fecha));

        System.out.println(personas.get(id1).nombre + " y " + personas.get(id2).nombre + " son ahora amigos desde " + fecha);

        for (int amigo : grafo.get(id2)) {
            if (amigo != id1)
                System.out.println("Correo a " + personas.get(amigo).email + " — Tal vez conoces a " + personas.get(id1).nombre);
        }

        for (int amigo : grafo.get(id1)) {
            if (amigo != id2)
                System.out.println("Correo a " + personas.get(amigo).email + " — Tal vez conoces a " + personas.get(id2).nombre);
        }
    }

    public void bloquear() {
        System.out.print("ID de quien bloquea: ");
        int id1 = lector.nextInt() - 1;
        System.out.print("ID de quien es bloqueado: ");
        int id2 = lector.nextInt() - 1;
        lector.nextLine();

        if (!valido(id1) || !valido(id2)) {
            System.out.println("ID no válido.");
            return;
        }

        if (!bloqueos.get(id1).contains(id2)) {
            bloqueos.get(id1).add(id2);
            System.out.println(personas.get(id1).nombre + " bloqueó a " + personas.get(id2).nombre);
        }
    }

    public void cumpleañosProximos() {
        System.out.print("¿Cuántos días hacia adelante quieres revisar? ");
        int k = lector.nextInt();
        lector.nextLine();
        LocalDate hoy = LocalDate.now();

        for (Persona p : personas) {
            LocalDate cumple = LocalDate.of(hoy.getYear(), p.mesNacimiento, p.diaNacimiento);
            if (cumple.isBefore(hoy)) cumple = cumple.plusYears(1);
            long dias = java.time.temporal.ChronoUnit.DAYS.between(hoy, cumple);
            if (dias <= k) {
                for (int amigoId : grafo.get(p.id)) {
                    System.out.println("Correo a " + personas.get(amigoId).email + ": Tu amigo " + p.nombre + " cumple en " + dias + " días.");
                }
            }
        }
    }

    public void nivelAmistad() {
        System.out.print("ID de la primera persona: ");
        int id1 = lector.nextInt() - 1;
        System.out.print("ID de la segunda persona: ");
        int id2 = lector.nextInt() - 1;
        lector.nextLine();

        int nivel = bfs(id1, id2);
        if (nivel == Integer.MAX_VALUE) {
            System.out.println("No hay conexión entre estas personas (∞)");
        } else {
            System.out.println("Nivel de amistad: " + nivel);
        }
    }

    private int bfs(int id1, int id2) {
        boolean[] visitado = new boolean[personas.size()];
        int[] nivel = new int[personas.size()];
        Arrays.fill(nivel, Integer.MAX_VALUE);

        Queue<Integer> cola = new LinkedList<>();
        cola.add(id1);
        visitado[id1] = true;
        nivel[id1] = 0;

        while (!cola.isEmpty()) {
            int actual = cola.poll();
            if (actual == id2) return nivel[actual];
            for (int vecino : grafo.get(actual)) {
                if (!visitado[vecino]) {
                    visitado[vecino] = true;
                    nivel[vecino] = nivel[actual] + 1;
                    cola.add(vecino);
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    private boolean valido(int id) {
        return id >= 0 && id < personas.size();
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║             MiniFacebook               ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║    1. Agregar nueva amistad            ║");
            System.out.println("║    2. Bloquear a un usuario            ║");
            System.out.println("║    3. Cumpleaños próximos              ║");
            System.out.println("║    4. Ver nivel de amistad             ║");
            System.out.println("║    5. Salir del sistema                ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print(" Elige una opción (1-5): ");

            while (!lector.hasNextInt()) {
                System.out.print("Por favor, ingresa un número válido (1-5): ");
                lector.next(); // ignorar entrada inválida
            }

            opcion = lector.nextInt();
            lector.nextLine(); // limpiar buffer

            System.out.println(); // línea en blanco
            switch (opcion) {
                case 1 -> agregarAmistad();
                case 2 -> bloquear();
                case 3 -> cumpleañosProximos();
                case 4 -> nivelAmistad();
                case 5 -> System.out.println(" ¡Hasta luego! Gracias por usar MiniFacebook.");
                default -> System.out.println(" Opción inválida. Intenta nuevamente.");
            }
        } while (opcion != 5);
    }

    public static void main(String[] args) {
        MiniFacebook red = new MiniFacebook();
        red.cargarPersonasDesdeArchivo("src/personasProcesar");
        red.mostrarMenu();
    }
}
