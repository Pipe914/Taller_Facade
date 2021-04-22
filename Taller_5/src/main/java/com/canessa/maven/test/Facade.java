package com.canessa.maven.test;

import com.canessa.maven.test.cuentas.Aspirante;
import com.canessa.maven.test.factorys.EmpresaFactory;
import com.canessa.maven.test.factorys.OfertaFactory;
import com.canessa.maven.test.factorys.RelacionadorFactory;
import com.canessa.maven.test.factorys.UsuarioFactory;

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
    private UsuarioFactory dataUsers;
    private EmpresaFactory dataEmpresas;
    private OfertaFactory dataOfertas;
    private RelacionadorFactory relacionador;
    private ArrayList<String> dataKeys;

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
        dataUsers = new UsuarioFactory();
        dataEmpresas = new EmpresaFactory();
        dataOfertas = new OfertaFactory();
        dataKeys = new ArrayList<String>();
        relacionador = new RelacionadorFactory();
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
        String[] separatedData = null;
        if (data != null) {
            separatedData = data.split("/");
        }else{
            
        }

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

    private boolean veriTipoUsuario(String key, String type) {
        String username = relacionador.getKeyUsuario(key);
        boolean response = false;

        if (!username.equals("")) {
            Usuario u = dataUsers.getUsuario(username);
            if (u != null) {
                switch (type) {
                case "1":
                    if (u.getTipoUsuario().equals("Tipo: Admin")) {
                        response = true;
                    }
                    break;
                case "2":
                    if (u.getTipoUsuario().equals("Tipo: Aspirante")) {
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

    private boolean existeData(String[] data, String search) {
        boolean response = false;
        if(data!=null){
            for (String d : data) {
                if (d.equals(search)) {
                    response = true;
                    break;
                }
            }
        }
        return response;
    }

    // Metodos Logins
    public void login(Usuario user, String key) {
        dataUsers.saveUsuario(user.getUsername(), user);
        dataKeys.add(key);
        relacionador.saveKeyUsuario(key, user.getUsername());
    }

    private String logOut(String key) {
        String response = "";
        if (verificadorKey(key)) {
            if(relacionador.deleteKeyUsuario(key)){
                int index = dataKeys.indexOf(key);
                dataKeys.remove(index);
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
                String username = relacionador.getKeyUsuario(key);
                Empresa empresa = new Empresa(nit, nombre, direccion);
                dataEmpresas.saveEmpresa(nit, empresa);
                if(relacionador.getUsuarioEmpresa(username) == null){
                    relacionador.saveUsuarioEmpresa(username, (nit + "/"));
                }else{
                    relacionador.saveUsuarioEmpresa(username, (relacionador.getUsuarioEmpresa(username) + nit + "/"));
                }
                response = "La empresa se ha creado exitosamente";
            } else {
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
                String username = relacionador.getKeyUsuario(key);
                int id = dataOfertas.getSize() + 1;
                Componente oferta = new OfertaBase(String.valueOf(id));
                dataOfertas.saveOferta(String.valueOf(id), oferta);
                if(relacionador.getUsuarioOferta(username) == null){
                    relacionador.saveUsuarioOferta(username, (id + "/"));
                }else{
                    relacionador.saveUsuarioOferta(username, (relacionador.getUsuarioOferta(username) + id + "/"));
                }
                response = "La oferta base, ah sido creada correctamente. El ID de la oferta es: "
                        + oferta.optionalGetId();
            } else {
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
            if (veriTipoUsuario(key, "1")) {
                String username = relacionador.getKeyUsuario(key);
                int id = dataOfertas.getSize() + 1;
                Componente oferta = new SueldoMensualOferta(
                        new TiempoOferta(
                                new TipoContratoOferta(
                                        new DescripcionOferta(new OfertaBase(String.valueOf(id)), descipcion), tipo),
                                tiempo),
                        sueldo);
                dataOfertas.saveOferta(String.valueOf(id), oferta);
                if(relacionador.getUsuarioOferta(username) == null){
                    relacionador.saveUsuarioOferta(username, (id + "/"));
                }else{
                    relacionador.saveUsuarioOferta(username, (relacionador.getUsuarioOferta(username) + id + "/"));
                }
                response = "La oferta base, ah sido creada correctamente. El ID de la oferta es: "
                        + oferta.optionalGetId();
            } else {
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
            if (veriTipoUsuario(key, "1")) {
                String username = relacionador.getKeyUsuario(key);
                if (existeData(separator(relacionador.getUsuarioOferta(username)), id)) {
                    Componente oferta = dataOfertas.getOferta(id);
                    switch (x) {
                    case "1":
                        oferta = new DescripcionOferta(oferta, info);
                        dataOfertas.saveOferta(id, oferta);
                        response = "Se ha añadido la propiedad correctamente";
                        break;
                    case "2":
                        oferta = new TipoContratoOferta(oferta, info);
                        dataOfertas.saveOferta(id, oferta);
                        response = "Se ha añadido la propiedad correctamente";
                        break;
                    case "3":
                        oferta = new TiempoOferta(oferta, info);
                        dataOfertas.saveOferta(id, oferta);
                        response = "Se ha añadido la propiedad correctamente";
                        break;
                    case "4":
                        oferta = new SueldoMensualOferta(oferta, info);
                        dataOfertas.saveOferta(id, oferta);
                        response = "Se ha añadido la propiedad correctamente";
                        break;
                    default:
                        response = "Escoja una eleccion valida";
                        break;

                    }
                }
            } else {
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
            if (veriTipoUsuario(key, "1")) {
                String username = relacionador.getKeyUsuario(key);
                if (username != null) {
                    if (existeData(separator(relacionador.getUsuarioEmpresa(username)), nEmpresa1)
                            && existeData(separator(relacionador.getUsuarioEmpresa(username)), nEmpresa2)) {
                        Empresa empresa1 = dataEmpresas.getEmpresa(nEmpresa1);
                        Empresa empresa2 = dataEmpresas.getEmpresa(nEmpresa2);
                        empresa1.addEmpresa(empresa2);
                        response = "Se ha añadido la empresa correctamente";
                    } else {
                        response = "La informacion de las empresas que ha ingresado no se encuentra registrada. Intente nuevamente";
                    }
                } else {
                    response = "El usuario no se encuentra registrado.";
                }
            } else {
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
            if (veriTipoUsuario(key, "1")) {
                String username = relacionador.getKeyUsuario(key);
                if (username != null) {
                    if (existeData(separator(relacionador.getUsuarioEmpresa(username)), nEmpresa1)
                            && existeData(separator(relacionador.getUsuarioOferta(username)), id)) {
                        Empresa empresa = dataEmpresas.getEmpresa(nEmpresa1);
                        Componente oferta = dataOfertas.getOferta(id);
                        empresa.addOfertaLaboral(oferta);
                        response = "Se ha añadido la oferta correctamente";
                    } else {
                        response = "La informacion de las empresas que ha ingresado no se encuentra registrada. Intente nuevamente";
                    }
                } else {
                    response = "El usuario no se encuentra registrado.";
                }
            } else {
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
            Empresa empresa = dataEmpresas.getEmpresa(nEmpresa);
            if (empresa != null) {
                response = empresa.imprimirOferta();
            } else {
                response = "La empresa no se encuentra registrada.";
            }
        } else {
            response = "La sesion no existe, ah caducado.";
        }
        return response;
    }
}
