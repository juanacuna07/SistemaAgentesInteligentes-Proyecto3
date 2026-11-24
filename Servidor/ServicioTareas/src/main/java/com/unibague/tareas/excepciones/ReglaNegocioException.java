package com.unibague.tareas.excepciones;

/** Se lanza cuando los datos no cumplen una regla del negocio (ID repetido, nombre repetido...). */
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
