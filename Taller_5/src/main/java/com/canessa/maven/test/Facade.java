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
import com.canessa.maven.test.proxy.AESEncript;

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
        dataKeys = new ArrayList<String>();
        dataEmpresas = new ArrayList<Empresa>();
        dataOfertas = new ArrayList<Componente>();
        relacionador = new ArrayList<String>();
    }
    // Metodo Main/Separador/Add data Users

    public String action(String data) {
        String dataUncripted = AESEncript.decrypt(data);
        String[] separatedData = separator(dataUncripted);

        switch (separatedData[0]) {
        case "1": // Crear Usuario
            this.createUser(separatedData[1], separatedData[2], separatedData[3]);
            return AESEncript.encrypt("User creado.");
        case "2": // LogOut
            return AESEncript.encrypt(this.logOut(separatedData[1]));
        case "3": // Crear Empresa
            return AESEncript.encrypt(
                    this.createEmpresa(separatedData[1], separatedData[2], separatedData[3], separatedData[4]));
        case "4": // Crear Oferta Base
            return AESEncript.encrypt(this.createOfertaBase(separatedData[1]));
        case "5": // Crear Oferta Completa
            return AESEncript.encrypt(this.createOferta(separatedData[1], separatedData[2], separatedData[3],
                    separatedData[4], separatedData[5]));
        case "6": // Añadir Propiedad a Oferta
            return AESEncript.encrypt(
                    this.addPropiedadOferta(separatedData[1], separatedData[2], separatedData[3], separatedData[4]));
        case "7": // Añadir Empresa
            return AESEncript.encrypt(this.addEmpresa(separatedData[1], separatedData[2], separatedData[3]));
        case "8": // Añadir Oferta
            return AESEncript.encrypt(this.addOferta(separatedData[1], separatedData[2], separatedData[3]));
        case "9": // Imprimir Componente
            return AESEncript.encrypt(this.imprimirOferta(separatedData[1], separatedData[2]));
        default:
            return "Opcion Incorrecta";
        }
    }

    // Metodos de Utilidad
    private String[] separator(String data) {
        String[] separatedData = data.split("/");

        return separatedData;
    }

    private boolean verificadorKey(String key) {
        boolean existe = false;

        for (String localKey : dataKeys) {
            String localKeySinAES = AESEncript.decrypt(localKey);
            if (localKeySinAES.equals(key)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    private boolean veriTipoUsuario(String key, String type) {
        String tipo = "";
        String username = "";
        boolean response = false;

        for (String relacion : relacionador) {
            String[] data = separator(AESEncript.decrypt(relacion));
            if (data[1].equals(key)) {
                username = data[0];
                break;
            }
        }
        if (!username.equals("")) {
            for (Usuario user : dataUsers) {
                if (user.getUsername().equals(username)) {
                    tipo = user.getTipoUsuario();
                    break;
                }
            }
            if (!tipo.equals("")) {
                switch (type) {
                case "1":
                    if (tipo.equals("Tipo: Admin")) {
                        response = true;
                    }
                    break;
                case "2":
                    if (tipo.equals("Tipo: Aspirante")) {
                        response = true;
                    }
                    break;
                default:
                    break;
                }
            }
        }
        return response;
    }

    // Metodos Logins
    public void login(Usuario user, String key) {
        dataUsers.add(user);
        dataKeys.add(AESEncript.encrypt(key));
        String uData = AESEncript.encrypt(user.getUsername() + "/" + key);
        relacionador.add(uData);
    }

    private String logOut(String key) {
        String response = "";
        if (verificadorKey(key)) {
            String username = "";
            boolean delete = false;
            boolean complete = false;
            for (String relacion : relacionador) {
                String[] data = separator(AESEncript.decrypt(relacion));
                if (data[1].equals(key)) {
                    username = data[0];
                    int index = relacionador.indexOf(relacion);
                    relacionador.remove(index);
                    delete = true;
                    break;
                }
            }
            if (delete) {
                for (String k : dataKeys) {
                    String dk = AESEncript.decrypt(k);
                    if (dk.equals(key)) {
                        int index = dataKeys.indexOf(k);
                        dataKeys.remove(index);
                        break;
                    }
                }
                for (Usuario user : dataUsers) {
                    if (user.getUsername().equals(username)) {
                        int index = dataUsers.indexOf(user);
                        dataUsers.remove(index);
                        complete = true;
                        break;

                    }
                }
            }
            if (complete) {
                response = "Su sesion ha sido cerrada correctamente, vuelva pronto";
            } else {
                response = "No se ha podido cerrar la sesion. Intente nuevamente";
            }
        }
        return response;
    }

    // Metodos Create
    public Usuario createUser(String x, String user, String password) {
        switch (x) {
        case "1":
            // Crear Empresa
            return new Admin(user, password);
        case "2":
            // Crear Aspirante
            return new Aspirante(user, password);
        default:
            System.out.println("Ingrese una opcion valida");
            return null;
        }
    }

    private String createEmpresa(String key, String nit, String nombre, String direccion) {
        String response = "";
        if (verificadorKey(key)) {
            if (veriTipoUsuario(key, "1")) {
                String username = "";
                Empresa empresa = new Empresa(nit, nombre, direccion);
                dataEmpresas.add(empresa);
                for (String relacion : relacionador) {
                    String[] data = separator(AESEncript.decrypt(relacion));
                    if (data[1].equals(key)) {
                        username = data[0];
                        break;
                    }
                }
                String uData = username + "/" + nit;
                relacionador.add(AESEncript.encrypt(uData));
                response = "La empresa se ha creado exitosamente";
            }else{
                response = "Usted no tiene acceso para realizar esta acción!!!";
            }
        } else {
            response = "La sesion no existe, ah caducado.";
        }
        return response;
    }

    private String createOfertaBase(String key) {
        String response = "";
        if (verificadorKey(key)) {
            if (veriTipoUsuario(key, "1")) {
                String username = "";
                int id = dataOfertas.size() + 1;
                Componente oferta = new OfertaBase(String.valueOf(id));
                dataOfertas.add(oferta);
                for (String relacion : relacionador) {
                    String[] data = separator(AESEncript.decrypt(relacion));
                    if (data[1].equals(key)) {
                        username = data[0];
                        break;
                    }
                }
                String uData = username + "/" + id;
                relacionador.add(AESEncript.encrypt(uData));
                response = "La oferta base, ah sido creada correctamente. El ID de la oferta es: " + oferta.optionalGetId();
            }else{
                response = "Usted no tiene acceso para realizar esta acción!!!";
            }
        } else {
            response = "La sesion no existe, ah caducado.";
        }
        return response;
    }

    private String createOferta(String key, String descipcion, String tipo, String tiempo, String sueldo) {
        String response = "";
        if (verificadorKey(key)) {
            if(veriTipoUsuario(key, "1")){
                String username = "";
                int id = dataOfertas.size() + 1;
                Componente oferta = new SueldoMensualOferta(new TiempoOferta(
                        new TipoContratoOferta(new DescripcionOferta(new OfertaBase(String.valueOf(id)), descipcion), tipo),
                        tiempo), sueldo);
                dataOfertas.add(oferta);
                for (String relacion : relacionador) {
                    String[] data = separator(AESEncript.decrypt(relacion));
                    if (data[1].equals(key)) {
                        username = data[0];
                        break;
                    }
                }
                String uData = username + "/" + id;
                relacionador.add(AESEncript.encrypt(uData));
                response = "La oferta base, ah sido creada correctamente. El ID de la oferta es: " + oferta.optionalGetId();
            }else{
                response = "Usted no tiene acceso para realizar esta acción!!!";
            }
        } else {
            response = "La sesion no existe, ah caducado.";
        }
        return response;
    }
    // Metodos CRUD Ofertas

    private String addPropiedadOferta(String key, String x, String id, String info) {
        String response = "";
        if (verificadorKey(key)) {
            if(veriTipoUsuario(key, "1")){
                String username = "";
                int iD = Integer.parseInt(id) - 1;
                for (String relacion : relacionador) {
                    String[] data = separator(AESEncript.decrypt(relacion));
                    if (data[1].equals(key)) {
                        username = data[0];
                        break;
                    }
                }
    
                for (String relacion : relacionador) {
                    String[] data = separator(AESEncript.decrypt(relacion));
                    if (data[0].equals(username) && data[1].equals(id)) {
                        Componente oferta = dataOfertas.get(iD);
                        switch (x) {
                        case "1":
                            oferta = new DescripcionOferta(oferta, info);
                            dataOfertas.set(iD, oferta);
                            response = "Se ha añadido la propiedad correctamente";
                            break;
                        case "2":
                            oferta = new TipoContratoOferta(oferta, info);
                            dataOfertas.set(iD, oferta);
                            response = "Se ha añadido la propiedad correctamente";
                            break;
                        case "3":
                            oferta = new TiempoOferta(oferta, info);
                            dataOfertas.set(iD, oferta);
                            response = "Se ha añadido la propiedad correctamente";
                            break;
                        case "4":
                            oferta = new SueldoMensualOferta(oferta, info);
                            dataOfertas.set(iD, oferta);
                            response = "Se ha añadido la propiedad correctamente";
                            break;
                        default:
                            response = "Escoja una eleccion valida";
                            break;
    
                        }
                    }
                }
            }else{
                response = "Usted no tiene acceso para realizar esta acción!!!";
            }
        } else {
            response = "La sesion no existe, ah caducado.";
        }
        return response;
    }
    // Guardar ofertas/empresas

    private String addEmpresa(String key, String nEmpresa1, String nEmpresa2) {
        String response = "";
        if (verificadorKey(key)) {
            if(veriTipoUsuario(key, "1")){
                // Encontrar Username
                String username = "";
                Empresa empresa1 = null;
                Empresa empresa2 = null;
                for (String relacion : relacionador) {
                    String[] data = separator(AESEncript.decrypt(relacion));
                    if (data[1].equals(key)) {
                        username = data[0];
                        break;
                    }
                }
                // Encontrar empresa relacionada con username si se encontro el usuario
                if (!username.equals("")) {
                    for (String relacion : relacionador) {
                        String[] data = separator(AESEncript.decrypt(relacion));
                        if (data[0].equals(username) && data[1].equals(nEmpresa1)) {
                            for (Empresa empresa : dataEmpresas) {
                                if (empresa.getNit().equals(nEmpresa1)) {
                                    empresa1 = empresa;
                                    break;
                                }
                            }
                            for (Empresa empresa : dataEmpresas) {
                                if (empresa.getNit().equals(nEmpresa2)) {
                                    empresa2 = empresa;
                                    break;
                                }
                            }
                        }
                    }
                    if (empresa1 != null && empresa2 != null) {
                        empresa1.addEmpresa(empresa2);
                        response = "Se ha añadido la empresa correctamente";
                    } else {
                        response = "La informacion de las empresas que ha ingresado no se encuentra registrada. Intente nuevamente";
                    }
                } else {
                    response = "El usuario no esta registrado en el sistema";
                }
            }else{
                response = "Usted no tiene acceso para realizar esta acción!!!";
            }
        } else {
            response = "La sesion no existe, ah caducado.";
        }
        return response;
    }

    private String addOferta(String key, String nEmpresa1, String id) {
        String response = "";
        if (verificadorKey(key)) {
            if(veriTipoUsuario(key, "1")){
                // Encontrar Username
                String username = "";
                Empresa empresa1 = null;
                Componente oferta1 = null;
                for (String relacion : relacionador) {
                    String[] data = separator(AESEncript.decrypt(relacion));
                    if (data[1].equals(key)) {
                        username = data[0];
                        break;
                    }
                }
                // Encontrar empresa relacionada con username si se encontro el usuario
                if (!username.equals("")) {
                    for (String relacion : relacionador) {
                        String[] data = separator(AESEncript.decrypt(relacion));
                        if (data[0].equals(username) && data[1].equals(nEmpresa1)) {
                            for (Empresa empresa : dataEmpresas) {
                                if (empresa.getNit().equals(nEmpresa1)) {
                                    empresa1 = empresa;
                                    break;
                                }
                            }
                            for (Componente oferta : dataOfertas) {
                                if (oferta.optionalGetId().equals(id)) {
                                    oferta1 = oferta;
                                    break;
                                }
                            }
                        }
                    }
                    if (empresa1 != null && oferta1 != null) {
                        empresa1.addOfertaLaboral(oferta1);
                        response = "Se ha añadido la oferta correctamente";
                    } else {
                        response = "La informacion de las empresas que ha ingresado no se encuentra registrada. Intente nuevamente";
                    }
                } else {
                    response = "El usuario no esta registrado en el sistema";
                }
            }else {
                response = "Usted no tiene acceso para realizar esta acción!!!";
            }
        } else {
            response = "La sesion no existe, ah caducado.";
        }
        return response;
    }

    // Imprimir Oferta
    private String imprimirOferta(String key, String nEmpresa) {
        String response = "";
        if (verificadorKey(key)) {
            // Encontrar Username
            String username = "";
            Empresa empresa1 = null;
            for (String relacion : relacionador) {
                String[] data = separator(AESEncript.decrypt(relacion));
                if (data[1].equals(key)) {
                    username = data[0];
                }
            }
            if (!username.equals("")) {
                for (String relacion : relacionador) {
                    String[] data = separator(AESEncript.decrypt(relacion));
                    if (data[0].equals(username) && data[1].equals(nEmpresa)) {
                        for (Empresa empresa : dataEmpresas) {
                            if (empresa.getNit().equals(nEmpresa)) {
                                empresa1 = empresa;
                                break;
                            }
                        }
                    }
                }
                if (empresa1 != null) {
                    response = empresa1.imprimirOferta();
                } else {
                    response = "La empresa no se encuentra registrada.";
                }
            } else {
                response = "El usuario no esta registrado en el sistema";
            }
        } else {
            response = "La sesion no existe, ah caducado.";
        }
        return response;
    }
}
