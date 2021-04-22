package com.canessa.maven.test;
// Clase padre para implementar usuarios en la aplicacion
public abstract class Usuario {
// Variables globales para todos los elementos de tipo usuario
    protected String username;
    protected String password;
    
   // Constructor
    public Usuario (String user, String pass){
        this.username = user;
        this.password = pass;
    };

// Metodos set y get
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Declaracion metodos abstractos 
    abstract public String login (String user, String pass);
    abstract public String getTipoUsuario ();
    
}