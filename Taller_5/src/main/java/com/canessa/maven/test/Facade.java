package com.canessa.maven.test;

import com.canessa.maven.test.cuentas.Aspirante;

import java.util.ArrayList;

import com.canessa.maven.test.Empresa.Empresa;
import com.canessa.maven.test.cuentas.Admin;
import com.canessa.maven.test.oferta.DescripcionOferta;
import com.canessa.maven.test.oferta.OfertaBase;
import com.canessa.maven.test.oferta.SueldoMensualOferta;
import com.canessa.maven.test.oferta.TiempoOferta;
import com.canessa.maven.test.oferta.TipoContratoOferta;

public class Facade {
    //Variables Globales
    private static Facade miFacade;
    private ArrayList<ArrayList> dataSesion;
    //Singlenton
    public static Facade getFacade() {
        if (miFacade == null){
            miFacade = new Facade();
            System.out.println("Creado");
        }else{
            System.out.println("Existe");
        }
        
        return miFacade;
    }
    // Constructors
    private Facade() {
        dataSesion = new ArrayList<ArrayList>();
    }
/*
    // Metodo Main/Separador

    public void action(String data) {
        String[] separatedData = separator(data);
        switch (separatedData[0]) {
            case "1":
                //Create User
                Usuario user = this.createUser(Integer.parseInt(separatedData[1]), separatedData[2], separatedData[3]);
                break;
            case "2":
                //Login
                
                break;
            case "3":

                break;
            case "4":

                break;
            case "5":

                break;
            case "6":

                break;
            case "7":

                break;
            case "8":

                break;
            default:
                break;
        }
    
    }*/

    public String[] separator(String data) {
        String[] separatedData = data.split("/");

        return separatedData;
    }

    // Metodos Create

    public Usuario createUser(int x, String user, String password) {
        switch (x) {
        case 1:
            // Crear Empresa
            return new Admin(user, password);
        case 2:
            // Crear Aspirante
            return new Aspirante(user, password);
        default:
            System.out.println("Ingrese una opcion valida");
            return null;
        }
    }

    public Empresa createEmpresa(String nit, String nombre, String direccion) {
        return new Empresa(nit, nombre, direccion);
    }

    public Componente createOfertaBase() {
        return new OfertaBase();
    }

    public Componente createOferta(String descipcion, String tipo, String tiempo, String sueldo) {
        return new SueldoMensualOferta(new TiempoOferta(
                new TipoContratoOferta(new DescripcionOferta(new OfertaBase(), descipcion), tipo), tiempo), sueldo);
    }

    public void getTipoUsuario(Usuario u) {
        System.out.println(u.getTipoUsuario());
    }

    // Metodos Logins
    public void login(Usuario u, String user, String password) {
        System.out.println(u.login(user, password));
    }

    // Metodos CRUD Ofertas

    public Componente addPropiedadOferta(int x, Componente oferta, String info) {
        switch (x) {
        case 1:
            oferta = new DescripcionOferta(oferta, info);
            System.out.println("Se ha añadido la propiedad correctamente");
            return oferta;
        case 2:
            oferta = new TipoContratoOferta(oferta, info);
            System.out.println("Se ha añadido la propiedad correctamente");
            return oferta;
        case 3:
            oferta = new TiempoOferta(oferta, info);
            System.out.println("Se ha añadido la propiedad correctamente");
            return oferta;
        case 4:
            oferta = new SueldoMensualOferta(oferta, info);
            System.out.println("Se ha añadido la propiedad correctamente");
            return oferta;
        default:
            System.out.println("Escoja una eleccion valida");
            return oferta;
        }
    }
    // Guardar ofertas/empresas

    public void addEmpresa(Empresa empresa1, Componente empresa2) {
        empresa1.addEmpresa(empresa2);
        System.out.println("Se ha añadido la empresa correctamente");
    }

    public void addOferta(Empresa empresa, Componente oferta) {
        empresa.addOfertaLaboral(oferta);
        System.out.println("Se ha añadido la oferta correctamente");
    }

    // Imprimir Oferta
    public void imprimirOferta(Componente c) {
        System.out.println(c.imprimirOferta());
    }

}
