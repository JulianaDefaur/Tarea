public class Persona {
    public int id;
    public String nombre;
    public int diaNacimiento;
    public int mesNacimiento;
    public String actividad;
    public String email;

    public Persona(int id, String nombre, int diaNacimiento, int mesNacimiento, String actividad, String email) {
        this.id = id;
        this.nombre = nombre;
        this.diaNacimiento = diaNacimiento;
        this.mesNacimiento = mesNacimiento;
        this.actividad = actividad;
        this.email = email;
    }
}