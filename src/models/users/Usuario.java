package models.users;

public abstract class Usuario {
    protected long id;
    protected String nombre;
    protected String email;
    protected String passwordHash;
    protected RolUsuario rol;

    public Usuario(long id, String nombre, String email, String passwordHash, RolUsuario rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public RolUsuario getRol() { return rol; }
    public String getPasswordHash() { return passwordHash; }
    
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmail(String email) { this.email = email; }

    public abstract void mostrarInfo();
}