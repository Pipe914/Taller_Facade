package com.canessa.maven.test;

import com.canessa.maven.test.cuentas.Aspirante;

import java.util.ArrayList;
import java.util.List;

import com.canessa.maven.test.Empresa.Empresa;
import com.canessa.maven.test.cuentas.Admin;
import com.canessa.maven.test.oferta.DescripcionOferta;
import com.canessa.maven.test.oferta.OfertaBase;
import com.canessa.maven.test.oferta.SueldoMensualOferta;
import com.canessa.maven.test.oferta.TiempoOferta;
import com.canessa.maven.test.oferta.TipoContratoOferta;

public class Facade {
    // Variables Globales
    private static Facade instance;
    private ArrayList<Usuario> dataUsers;
    private ArrayList<String> dataKeys;
    private ArrayList<Empresa> dataEmpresas;
    private ArrayList<Componente> dataOfertas;
    private ArrayList<String> relacionador;

    // Singlenton
    public static Facade getFacade() {
        if (instance == null) {
            instance = new Facade();
            System.out.println("Creado y cargado");
        } else {
            System.out.println("Existe y cargado");
        }

        return instance;
    }

    // Constructors
    private Facade() {
        dataUsers = new ArrayList<Usuario>();
    }
    /*
     * // Metodo Main/Separador/Add data Users
     * 
     * public void action(String data) { String[] separatedData = separator(data);
     * switch (separatedData[0]) { case "1": //Create User Usuario user =
     * this.createUser(Integer.parseInt(separatedData[1]), separatedData[2],
     * separatedData[3]); break; case "2": //Login
     * 
     * break; case "3":
     * 
     * break; case "4":
     * 
     * break; case "5":
     * 
     * break; case "6":
     * 
     * break; case "7":
     * 
     * break; case "8":
     * 
     * break; default: break; }
     * 
     * }
     */

    public String[] separator(String data) {
        String[] separatedData = data.split("/");

        return separatedData;
    }

    private boolean verificadorKey(String key) {
        boolean existe = false;
        for (String localKey : dataKeys) {
            if (localKey.equals(key)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    // Metodos Logins
    public void login(Usuario user, String key) {
        dataUsers.add(user);
        dataKeys.add(key);
        String uData = user.getUsername() + "/" + key;
        relacionador.add(uData);
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

    public void createEmpresa(String key, String nit, String nombre, String direccion) {
        if (verificadorKey(key)) {
            Empresa empresa = new Empresa(nit, nombre, direccion);
            dataEmpresas.add(empresa);
            String uData = key + "/" + nit;
            relacionador.add(uData);
            System.out.println("La empresa se ha creado exitosamente");
        } else {
            System.out.println("La sesion no existe, ah caducado.");
        }
    }

    public void createOfertaBase(String key) {
        if (verificadorKey(key)) {
            Componente oferta = new OfertaBase();
            dataOfertas.add(oferta);
            String uData = key + "/" + dataOfertas.size() + 1;
            relacionador.add(uData);
            System.out.println(
                    "La oferta base, ah sido creada correctamente. El ID de la oferta es: " + dataOfertas.size() + 1);
        } else {
            System.out.println("La sesion no existe, ah caducado.");
        }
    }

    public void createOferta(String key, String descipcion, String tipo, String tiempo, String sueldo) {
        if (verificadorKey(key)) {
        Componente oferta =  new SueldoMensualOferta(new TiempoOferta(
                            new TipoContratoOferta(new DescripcionOferta(new OfertaBase(), descipcion), tipo), tiempo), sueldo) ;
        dataOfertas.add(oferta);
        String uData = key + "/" + dataOfertas.size()+1;
        relacionador.add(uData);
        System.out.println("La oferta base, ah sido creada correctamente. El ID de la oferta es: " + dataOfertas.size()+1);
    } else {
        System.out.println("La sesion no existe, ah caducado.");
    }
    }

    public void getTipoUsuario(Usuario u) {
        System.out.println(u.getTipoUsuario());
    }

    // Metodos CRUD Ofertas

    public void addPropiedadOferta(int x, String key, String id, String info) {
        if (verificadorKey(key)) {
            for (String relacion : relacionador) {
                String[] data = separator(relacion);
                if (data[0].equals(key) && data[1].equals(id)) {
                    Componente oferta = dataOfertas.get(Integer.parseInt(id) - 1);
                    switch (x) {
                    case 1:
                        oferta = new DescripcionOferta(oferta, info);
                        dataOfertas.set(Integer.parseInt(id) - 1, oferta);
                        System.out.println("Se ha añadido la propiedad correctamente");

                    case 2:
                        oferta = new TipoContratoOferta(oferta, info);
                        dataOfertas.set(Integer.parseInt(id) - 1, oferta);
                        System.out.println("Se ha añadido la propiedad correctamente");

                    case 3:
                        oferta = new TiempoOferta(oferta, info);
                        dataOfertas.set(Integer.parseInt(id) - 1, oferta);
                        System.out.println("Se ha añadido la propiedad correctamente");

                    case 4:
                        oferta = new SueldoMensualOferta(oferta, info);
                        dataOfertas.set(Integer.parseInt(id) - 1, oferta);
                        System.out.println("Se ha añadido la propiedad correctamente");

                    default:
                        System.out.println("Escoja una eleccion valida");

                    }
                } else {
                    System.out.println("La oferta no existe o no puede ser modificada por este usuario.");
                }

            }
        } else {
            System.out.println("La sesion no existe, ah caducado.");
        }
    }
    // Guardar ofertas/empresas

    public void addEmpresa(String key, String nEmpresa1, String nEmpresa2) {
        for (String relacion : relacionador) {
            String[] data = separator(relacion);

        }

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
