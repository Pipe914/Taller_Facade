package com.canessa.maven.test;

public abstract class Usuario {
    protected String username;
    protected String password;
    
    public Usuario (String user, String pass){
        this.username = user;
        this.password = pass;
    };

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
    
    abstract public String login (String user, String pass);
    abstract public String getTipoUsuario ();
    
}