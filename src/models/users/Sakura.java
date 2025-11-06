package models.users;

import java.time.LocalDate;
import utils.IO;

public class Sakura extends Usuario {
    private String claveAdmin;
    private LocalDate fechaAsignacion;

    public Sakura(long id, String nombre, String email, String password, LocalDate fechaContratacion, 
                 String claveAdmin, LocalDate fechaAsignacion) {
        super(id, nombre, email, password, RolUsuario.SAKURA);
        this.claveAdmin = claveAdmin;
        this.fechaAsignacion = fechaAsignacion;
    }

    public void tomarControlTotal() {
        IO.println("Control total activado para Sakura " + nombre);
    }

    public void eliminarUsuario(Usuario usuario) {
        if (usuario == null) throw new IllegalArgumentException("Usuario no puede ser nulo");
        IO.println("Usuario " + usuario.getNombre() + " eliminado por Sakura.");
    }

    @Override
    public void mostrarInfo() {

    }
}