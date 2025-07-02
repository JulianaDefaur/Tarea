package modelo;

import java.time.LocalDate;

public class Amistad {
    private int id1;
    private int id2;
    private LocalDate fecha;

    public Amistad(int id1, int id2, LocalDate fecha) {
        this.id1 = id1;
        this.id2 = id2;
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}

