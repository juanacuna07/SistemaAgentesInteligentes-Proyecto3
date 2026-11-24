package com.unibague.agentes.servicios;

import com.unibague.agentes.dto.ActualizarTareaAgenteInput;
import com.unibague.agentes.dto.Pagina;
import com.unibague.agentes.dto.TareaAgenteInput;
import com.unibague.agentes.dto.TareaConAgente;
import com.unibague.agentes.model.TareaAgente;

/** Logica de las tareas (clase C). Los datos se guardan en el microservicio de tareas. */
public interface IServicioTareaAgente {

    Pagina<TareaConAgente> listarConAgente(Integer idAgente, Integer prioridad, Integer pagina, Integer tamano);

    TareaAgente buscarPorId(int idTarea);

    TareaAgente buscarPorNombre(String nombre);

    TareaAgente crear(TareaAgenteInput input);

    TareaAgente actualizar(int idTarea, ActualizarTareaAgenteInput input);

    String eliminar(int idTarea);
}
