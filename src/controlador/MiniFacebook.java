package controlador;

import java.time.LocalDate;
import java.util.*;
import modelo.*;

public class MiniFacebook {
    private List<Persona> personas;
    private List<Amistad> amistades;
    private int contId = 0;

    public MiniFacebook() {
        personas = new ArrayList();
        amistades = new ArrayList();
    }

    public void addUsuario(int id, String nombre, int dia, int mes, String actividad, String mail) {
        Persona usuario = new Persona(id, nombre, dia, mes, actividad, mail);
    }

    public void modificarUsuario(String nombrePrev, String nuevoNombre, String actividad) {
        int k = findId(nombrePrev);
        personas.get(k).setNombre(nuevoNombre);
        personas.get(k).setActividad(actividad);
    }

    public int nivelAmistad(int id1, int id2){
        int na = 0;
        return na;
    }

    private int findId(String nombre) {
        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).getNombre().equals(nombre)) {
                return personas.get(i).getId();
            }
        }
        return -1;
    }
}
//todo clases addAmistad, bloquear, nivelAmistad
