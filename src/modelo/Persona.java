package modelo;

public class Persona {
    private int id;
    private String nombre;
    private int diaNacimiento;
    private int mesNacimiento;
    private String actividad;
    private String email;

    public Persona(int id, String nombre, int diaNacimiento, int mesNacimiento, String actividad, String email) {
        this.id = id;
        this.nombre = nombre;
        this.diaNacimiento = diaNacimiento;
        this.mesNacimiento = mesNacimiento;
        this.actividad = actividad;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDiaNacimiento() {
        return diaNacimiento;
    }

    public int getMesNacimiento() {
        return mesNacimiento;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getEmail() {
        return email;
    }
}
