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

    // Constructor
    private Facade() {
        dataUsers = new UsuarioFactory();
        dataEmpresas = new EmpresaFactory();
        dataOfertas = new OfertaFactory();
        dataKeys = new ArrayList<String>();
        relacionador = new RelacionadorFactory();
    }
    // Metodo Main: Único metodo que se comunica con FrontEnd

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
    // Separador de String
    private String[] separator(String data) {
        // Crea la variable donde se separará la información recibida
        String[] separatedData = null;
        // Verifica que la data no sea null
        if (data != null) {
            // Separa y guarda la informacion recibida
            separatedData = data.split("/");
        }
        // Regresa la informacion separada
        return separatedData;
    }

    // Verificador de existencia de key (Sesión Activa)
    private boolean verificadorKey(String key) {
        // Crea la variable de verificación
        boolean existe = false;
        // Recorre el array en busca de la key ingresada para verificar una sesion
        // activa
        for (String localKey : dataKeys) {
            if (localKey.equals(key)) {
                existe = true;
                break;
            }
        }
        // Regresa la variable de verificacion
        return existe;
    }

    // Verifica el tipo de usuario que intenta acceder a una accion
    private boolean veriTipoUsuario(String key, String type) {
        // Trae el username relacionado a la key y se crea la variable de verificacion
        String username = relacionador.getKeyUsuario(key);
        boolean response = false;
        // Si el username existe (diferente de null), traer el objeto usuario
        // correspondiente y verifica su tipo segun sea el caso
        if (username != null) {
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
        // Devuelve la variable de verificacion
        return response;
    }

    // Verifica la existencia de la relacion existente (si la empresa/oferta si fue
    // creada por el usuario)
    private boolean existeData(String[] data, String search) {
        // Crea la variable de verificacion
        boolean response = false;
        // Verifica que la informacion recivida no este vacia.
        if (data != null) {
            // Recorre el array de informacion buscando una coincidencia con el item a
            // buscar.
            for (String d : data) {
                if (d.equals(search)) {
                    response = true;
                    break;
                }
            }
        }
        // Retorna la variable de verificacion
        return response;
    }

    // Metodos LogIn y LogOut
    public void login(Usuario user, String key) {
        // Recibe la informacion del proxy (objeto usuario y key generada) y la guarda
        // en sus respectivos array y factory
        dataUsers.saveUsuario(user.getUsername(), user);
        dataKeys.add(key);
        // Guarda la relacion de key y username en la factory de relacion
        relacionador.saveKeyUsuario(key, user.getUsername());
    }

    private String logOut(String key) {
        // Recibe la key a de la sesion a cerrar y crea la variable de verificacion
        String response = "";
        // Verifica que la key este en registrada en el sistema
        if (verificadorKey(key)) {
            // Realiza el proceso de eliminacion de la factory, si es exitoso elimina la key
            // del array local.
            if (relacionador.deleteKeyUsuario(key)) {
                int index = dataKeys.indexOf(key);
                dataKeys.remove(index);
                response = "Su sesion ha sido cerrada correctamente, vuelva pronto";
            } else {
                response = "No se ha podido cerrar la sesion. Intente nuevamente";
            }
        }
        // Retorna el resultado de la operacion
        return response;
    }

    // Metodos Create
    // Metodo de creacion de usuario (se puede mover a proxy)
    public Usuario createUser(String x, String user, String password) {
        // Recibe la informacion para la creacion del usuario (username y password), y
        // segun el caso crea el tipo de usuario correspondiente
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

    // Metodo creacion de empresa
    private String createEmpresa(String key, String nit, String nombre, String direccion) {
        // Recibe la informacion necesaria para crear una empresa, la key del usuario
        // que la quiere crear y se crea la varible de respuesta
        String response = "";
        // Se verifica que la key este registrada en el sistema
        if (verificadorKey(key)) {
            // Verifica si el usuario tiene los permisos necesarios para realizar la accion
            if (veriTipoUsuario(key, "1")) {
                // Se obtiene el nombre de usuario relacionado a la key recibida y se crea la
                // empresa
                String username = relacionador.getKeyUsuario(key);
                Empresa empresa = new Empresa(nit, nombre, direccion);
                // Se guarda la empresa en su respectiva factory, se maneja como key el nit de
                // la empresa y como value el obejto empresa
                dataEmpresas.saveEmpresa(nit, empresa);
                // Se guarda la relacion entre el usuario y la empresa en su respectivo factory,
                // se maneja como key el username del usuario y el nit de la empresa(se van
                // juntando todas las empresas creadas en un solo dato separadas mediante un /
                // para poder ser separadas en un futuro).
                if (relacionador.getUsuarioEmpresa(username) == null) {
                    // En el caso de que sea la primera empresa creada
                    relacionador.saveUsuarioEmpresa(username, (nit + "/"));
                } else {
                    // En el caso de que no sea la primera empresa creada
                    relacionador.saveUsuarioEmpresa(username, (relacionador.getUsuarioEmpresa(username) + nit + "/"));
                }
                response = "La empresa se ha creado exitosamente";
            } else {
                response = "Usted no tiene acceso para realizar esta acción!!!";
            }
        } else {
            response = "La sesion no existe, ah caducado.";
        }
        // Se retorna el resultado de la operacion
        return response;
    }

    // Metodo de creacion de oferta base (sin propiedades definidas)
    private String createOfertaBase(String key) {
        // Se recibe la key del usuario y se crea la variable de resultado
        String response = "";
        // Se verifica la existencia de la key dentro del sistema
        if (verificadorKey(key)) {
            // Se verifica si el usuario tiene los permisos necesarios para realizar la
            // accion
            if (veriTipoUsuario(key, "1")) {
                // Se obtiene el nombre de usuario relacionado a la key y se genera el id de la
                // oferta (Cantidad de ofertas creadas + 1)
                String username = relacionador.getKeyUsuario(key);
                int id = dataOfertas.getSize() + 1;
                // Se crea la oferta base y se guarda en su respectiva factory, se toma como key
                // el id de la oferta y como value el objeto oferta creado
                Componente oferta = new OfertaBase(String.valueOf(id));
                dataOfertas.saveOferta(String.valueOf(id), oferta);
                // Se guarda la relacion entre el usuario y la oferta en su respectivo factory,
                // se maneja como key el username del usuario y el id de la oferta(se van
                // juntando todas las ofertas creadas en un solo dato separadas mediante un /
                // para poder ser separadas en un futuro).
                if (relacionador.getUsuarioOferta(username) == null) {
                    // En el caso de que sea la primera oferta creada
                    relacionador.saveUsuarioOferta(username, (id + "/"));
                } else {
                    // En el caso de que no sea la primera oferta creada
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
        // Se retorna el resultado de la operacion
        return response;
    }

    // Metodo de creacion de oferta con todas sus propiedades
    private String createOferta(String key, String descipcion, String tipo, String tiempo, String sueldo) {
        // Se recibe la key del usuario y todos los datos necesarios para la creacion de
        // la oferta, tambien se crea la variable de resultado
        String response = "";
        // Se verifica la existencia de la key dentro del sistema
        if (verificadorKey(key)) {
            // Se verifica si el usuario tiene los permisos necesarios para realizar la
            // accion
            if (veriTipoUsuario(key, "1")) {
                // Se obtiene el nombre de usuario relacionado a la key y se genera el id de la
                // oferta (Cantidad de ofertas creadas + 1)
                String username = relacionador.getKeyUsuario(key);
                int id = dataOfertas.getSize() + 1;
                // Se crea la oferta y se guarda en su respectiva factory, se toma como key el
                // id de la oferta y como value el objeto oferta creado
                Componente oferta = new SueldoMensualOferta(
                        new TiempoOferta(
                                new TipoContratoOferta(
                                        new DescripcionOferta(new OfertaBase(String.valueOf(id)), descipcion), tipo),
                                tiempo),
                        sueldo);
                dataOfertas.saveOferta(String.valueOf(id), oferta);
                // Se guarda la relacion entre el usuario y la oferta en su respectivo factory,
                // se maneja como key el username del usuario y el id de la oferta(se van
                // juntando todas las ofertas creadas en un solo dato separadas mediante un /
                // para poder ser separadas en un futuro).
                if (relacionador.getUsuarioOferta(username) == null) {
                    // En el caso de que sea la primera oferta creada
                    relacionador.saveUsuarioOferta(username, (id + "/"));
                } else {
                    // En el caso de que no sea la primera oferta creada
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
        // Se retorna el resultado de la operacion
        return response;
    }

    // Metodos de edicion de ofertas
    // Metodo de adicion de propiedad de oferta
    private String addPropiedadOferta(String key, String x, String id, String info) {
        // Se recibe la key del usuario, el caso a añadir, le id de la oferta a
        // modificar y la infomacion a añadir, tambien se crea la variable de respuesta
        String response = "";
        // Se verifica la existencia de la key dentro del sistema
        if (verificadorKey(key)) {
            // Se verifica si el usuario tiene los permisos necesarios para realizar la
            // accion
            if (veriTipoUsuario(key, "1")) {
                // Se obtiene el nombre de usuario relacionado
                String username = relacionador.getKeyUsuario(key);
                // Se verifica que exista relacion entre el username y el id de la oferta a
                // modificar (que la oferta a modificar si sea creada pro el usuario)
                if (existeData(separator(relacionador.getUsuarioOferta(username)), id)) {
                    // Se trae la oferta, se aplica la propiedad segun sea el caso y se guarda en su
                    // factory bajo el mismo id
                    Componente oferta = dataOfertas.getOferta(id);
                    switch (x) {
                    case "1":
                        // Agregar descipcion a la oferta
                        oferta = new DescripcionOferta(oferta, info);
                        dataOfertas.saveOferta(id, oferta);
                        response = "Se ha añadido la propiedad correctamente";
                        break;
                    case "2":
                        // Agregar tipo de contrato a la oferta
                        oferta = new TipoContratoOferta(oferta, info);
                        dataOfertas.saveOferta(id, oferta);
                        response = "Se ha añadido la propiedad correctamente";
                        break;
                    case "3":
                        // Agregar tiempo a la oferta
                        oferta = new TiempoOferta(oferta, info);
                        dataOfertas.saveOferta(id, oferta);
                        response = "Se ha añadido la propiedad correctamente";
                        break;
                    case "4":
                        // Agregar sueldo mensual a la oferta
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
        // Se retorna el resultado de la operacion
        return response;
    }

    // Metodos de guardado de componentes
    // Metodo de guardado de empresa dentro de empresa
    private String addEmpresa(String key, String nEmpresa1, String nEmpresa2) {
        // Se recibe la key del usuario y los nit de las empresas, se crea la variable
        // de respuesta
        String response = "";
        // Se verifica la existencia de la key dentro del sistema
        if (verificadorKey(key)) {
            // Se verifica si el usuario tiene los permisos necesarios para realizar la
            // accion
            if (veriTipoUsuario(key, "1")) {
                // Se obtiene el nombre de usuario relacionado
                String username = relacionador.getKeyUsuario(key);
                // Se verifica que el username exista (diferente de null)
                if (username != null) {
                    // Se verifica que las empresas a guardar hayan sido creadas por el usuario (Se
                    // trae la relacion de usuario-empresas, se separa esta informacion y se manda
                    // al metodo de existeData en busca de respuesta booleana)
                    if (existeData(separator(relacionador.getUsuarioEmpresa(username)), nEmpresa1)
                        && existeData(separator(relacionador.getUsuarioEmpresa(username)), nEmpresa2)) {
                        // Se llaman las empresas desde su factory y se realiza el proceso de guardado 
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
        // Se retorna el resultado de la operacion
        return response;
    }

    // Metodo de guardado de oferta dentro de empresa
    private String addOferta(String key, String nEmpresa1, String id) {
        // Se recibe la key del usuario, el nit de la empresa y el id de la oferta, se crea la variable
        // de respuesta
        String response = "";
        // Se verifica la existencia de la key dentro del sistema
        if (verificadorKey(key)) {
            // Se verifica si el usuario tiene los permisos necesarios para realizar la
            // accion
            if (veriTipoUsuario(key, "1")) {
                // Se trae el username relacionado con la key
                String username = relacionador.getKeyUsuario(key);
                // Se verifica que el username exista (diferente de null)
                if (username != null) {
                    // Se verifica que la empresa y la oferta a guardar hayan sido creadas por el usuario (Se
                    // trae la relacion de usuario-empresas y usuario-ofertas, se separa esta informacion y se manda
                    // al metodo de existeData en busca de respuesta booleana)
                    if (existeData(separator(relacionador.getUsuarioEmpresa(username)), nEmpresa1)
                            && existeData(separator(relacionador.getUsuarioOferta(username)), id)) {
                        // Se llama la empresa y la oferta desde su respectivo factory y se realiza el proceso de guardado 
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
        // Se retorna el resultado de la operacion
        return response;
    }

// Metodos de impresion
    // Metodo imprimir oferta
    private String imprimirOferta(String key, String nEmpresa) {
        // Se recive la key de usuario y el nit de la empresa, se crea la variable de respuesta
        String response = "";
        // Se verifica la existencia de la key dentro del sistema
        if (verificadorKey(key)) {
            // Se llama la empresa desde su factory
            Empresa empresa = dataEmpresas.getEmpresa(nEmpresa);
            // Se verifica que la empresa exista (diferente de null)
            if (empresa != null) {
                // Se guarda el metodo de oferta en la variable de respuesta
                response = empresa.imprimirOferta();
            } else {
                response = "La empresa no se encuentra registrada.";
            }
        } else {
            response = "La sesion no existe, ah caducado.";
        }
        // Se retorna el resultado de la operacion
        return response;
    }
}
