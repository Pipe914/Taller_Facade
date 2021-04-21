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

    public void action(String data) {

        System.out.println(data);
        String dataUncripted = AESEncript.decrypt(data);
        System.out.println(dataUncripted);
        String[] separatedData = separator(dataUncripted);

        switch (separatedData[0]) {
            case "1": // Crear Usuario
                this.createUser(separatedData[1], separatedData[2], separatedData[3]);
                break;
            case "2": // LogOut
                this.logOut(separatedData[1]);
                break;
            case "3": // Crear Empresa
                this.createEmpresa(separatedData[1], separatedData[2], separatedData[3], separatedData[4]);
                break;
            case "4": // Crear Oferta Base
                this.createOfertaBase(separatedData[1]);
                break;
            case "5": // Crear Oferta Completa
                this.createOferta(separatedData[1], separatedData[2], separatedData[3], separatedData[4], separatedData[5]);
                break;
            case "6": // Añadir Propiedad a Oferta
                this.addPropiedadOferta(separatedData[1], separatedData[2], separatedData[3], separatedData[4]);
                break;
            case "7": // Añadir Empresa
                this.addEmpresa(separatedData[1], separatedData[2], separatedData[3]);
                break;
            case "8": // Añadir Oferta
                this.addOferta(separatedData[1], separatedData[2], separatedData[3]);
                break;
            case "9": // Imprimir Componente
                this.imprimirOferta(separatedData[1], separatedData[2]);
                break;
            default:
                System.out.println("Opcion Incorrecta");
                break;
        }
    }

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

    // Metodos Logins
    public void login(Usuario user, String key) {
        dataUsers.add(user);
        dataKeys.add(AESEncript.encrypt(key));
        String uData = AESEncript.encrypt(user.getUsername() + "/" + key);
        String uncrData = AESEncript.decrypt(uData);

        relacionador.add(uData);
        System.out.println("SOY EL TIPO DE USUARI: " + verificadorAdmin(key) + "          " + uncrData);
    }

    private void logOut(String key) {
        if (verificadorKey(key)) {

        }
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

    private void createEmpresa(String key, String nit, String nombre, String direccion) {
        if (verificadorKey(key)) {
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
            System.out.println("La empresa se ha creado exitosamente");
        } else {
            System.out.println("La sesion no existe, ah caducado.");
        }
    }

    private void createOfertaBase(String key) {
        if (verificadorKey(key)) {
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
            System.out.println(
                    "La oferta base, ah sido creada correctamente. El ID de la oferta es: " + oferta.optionalGetId());
        } else {
            System.out.println("La sesion no existe, ah caducado.");
        }
    }

    private void createOferta(String key, String descipcion, String tipo, String tiempo, String sueldo) {
        if (verificadorKey(key)) {
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
            System.out.println("La oferta base, ah sido creada correctamente. El ID de la oferta es: " + oferta.optionalGetId());
        } else {
            System.out.println("La sesion no existe, ah caducado.");
        }
    }

   

    private boolean verificadorAdmin(String key) {
        boolean sas = false;

        if (verificadorKey(key) && relacionador.contains(AESEncript.encrypt(key))) {
            for (String datos : relacionador) {
                String[] data = separator(AESEncript.decrypt(datos));
                if(data[1].equals(key)){
                     relacionador.set(datos,relacionador.get(AESEncript.decrypt(datos)));
               }
            }
        }
        return sas;
    }
     private void getTipoUsuario(Usuario u) {
        System.out.println(u.getTipoUsuario());
    }

    // Metodos CRUD Ofertas
    private void addPropiedadOferta(String key, String x, String id, String info) {
        if (verificadorKey(key)) {
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
                            System.out.println("Se ha añadido la propiedad correctamente");
                            break;
                        case "2":
                            oferta = new TipoContratoOferta(oferta, info);
                            dataOfertas.set(iD, oferta);
                            System.out.println("Se ha añadido la propiedad correctamente");
                            break;
                        case "3":
                            oferta = new TiempoOferta(oferta, info);
                            dataOfertas.set(iD, oferta);
                            System.out.println("Se ha añadido la propiedad correctamente");
                            break;
                        case "4":
                            oferta = new SueldoMensualOferta(oferta, info);
                            dataOfertas.set(iD, oferta);
                            System.out.println("Se ha añadido la propiedad correctamente");
                            break;
                        default:
                            System.out.println("Escoja una eleccion valida");
                            break;

                    }
                }
            }
        } else {
            System.out.println("La sesion no existe, ah caducado.");
        }
    }
    // Guardar ofertas/empresas

    private void addEmpresa(String key, String nEmpresa1, String nEmpresa2) {
        if (verificadorKey(key)) {
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
                    System.out.println("Se ha añadido la empresa correctamente");
                } else {
                    System.out.println("La informacion de las empresas que ha ingresado no se encuentra registrada. Intente nuevamente");
                }
            } else {
                System.out.println("El usuario no esta registrado en el sistema");
            }

        } else {
            System.out.println("La sesion no existe, ah caducado.");
        }

    }

    private void addOferta(String key, String nEmpresa1, String id) {
        if (verificadorKey(key)) {
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
                    System.out.println("Se ha añadido la oferta correctamente");
                } else {
                    System.out.println(
                            "La informacion de las empresas que ha ingresado no se encuentra registrada. Intente nuevamente");
                }
            } else {
                System.out.println("El usuario no esta registrado en el sistema");
            }
        } else {
            System.out.println("La sesion no existe, ah caducado.");
        }
    }

    // Imprimir Oferta
    private void imprimirOferta(String key, String nEmpresa) {
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
                    System.out.println(empresa1.imprimirOferta());
                } else {
                    System.out.println("La empresa no se encuentra registrada.");
                }
            } else {
                System.out.println("El usuario no esta registrado en el sistema");
            }
        } else {
            System.out.println("La sesion no existe, ah caducado.");
        }
    }
}
