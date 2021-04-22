package com.canessa.maven.test.Empresa;

import java.util.ArrayList;

import com.canessa.maven.test.Componente;

// Clase empresa, implementa interfaz Componente
public class Empresa implements Componente {
// Variables globales de datos de empresa y contenedores
    private String nit;
    private String nombre;
    private String direccion;
    private ArrayList<Componente> ofertasLaborales;
    private ArrayList<Componente> empresas;
// Constructor
    public Empresa(String n, String nom, String dir) {
        nit = n;
        nombre = nom;
        direccion = dir;
        ofertasLaborales = new ArrayList<Componente>();
        empresas = new ArrayList<Componente>();
    }

// Metodos Clase
    public String imprimirOferta() {

        String data = "";

        data = "\nLa Empresa " + this.nombre + ",\n" + "con Nit: " + this.nit + ",\n" + "y Direccion: " + this.direccion
                + ",\n" + "tiene las siguientes ofertas: \n";

        if (this.ofertasLaborales.size() != 0) {
            for (Componente o : this.ofertasLaborales) {
                data += o.imprimirOferta();
            }
        } else {
            data += "\nNo tiene ofertas actualmente\n";
        }

        data += "\nLa empresa contiene las siguientes empresas\n";

        if (this.empresas.size() != 0) {
            for (Componente e : this.empresas) {
                data += e.imprimirOferta();
            }
        } else {
            data+= "\nNo tiene empresas afiliadas actualmente\n";
        }

        return data;
    }
    public String optionalGetId(){
        return "";
    }
    
    public void addOfertaLaboral(Componente oferta) {
        this.ofertasLaborales.add(oferta);
    }

    public void addEmpresa(Componente empresa) {
        this.empresas.add(empresa);
    }

// Setters
    public void setNit(String nit) {
        this.nit = nit;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setOfertasLaborales(ArrayList<Componente> ofertasLaborales) {
        this.ofertasLaborales = ofertasLaborales;
    }

    public void setEmpresas(ArrayList<Componente> empresa) {
        this.empresas = empresa;
    }

// Getters
    public String getNit() {
        return this.nit;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public ArrayList<Componente> getOfertasLaborales() {
        return this.ofertasLaborales;
    }

    public ArrayList<Componente> getEmpresas() {
        return this.empresas;
    }


}
