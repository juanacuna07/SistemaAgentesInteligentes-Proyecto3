package com.unibague.tareas.excepciones;

/** Se lanza cuando la tarea buscada no existe en la base de datos. */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
