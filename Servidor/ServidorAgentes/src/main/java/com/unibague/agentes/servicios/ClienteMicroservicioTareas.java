package com.unibague.agentes.servicios;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.unibague.agentes.dto.Pagina;
import com.unibague.agentes.excepciones.RecursoNoEncontradoException;
import com.unibague.agentes.excepciones.ReglaNegocioException;
import com.unibague.agentes.model.TareaAgente;

/**
 * Comunicacion con el microservicio C (tareas) por medio de su servicio web REST.
 * Los clientes graficos no conocen este microservicio: siempre pasan por el GraphQL de A-B.
 */
@Component
public class ClienteMicroservicioTareas {

    private final RestTemplate rest;
    private final String url;

    public ClienteMicroservicioTareas(RestTemplateBuilder builder,
                                      @Value("${microservicio.tareas.url}") String url) {
        this.rest = builder.build();
        this.url = url;
    }

    public TareaAgente crear(TareaAgente tarea) {
        try {
            return rest.postForObject(url, tarea, TareaAgente.class);
        } catch (RestClientException e) {
            throw traducirError(e);
        }
    }

    public TareaAgente buscarPorId(int idTarea) {
        try {
            return rest.getForObject(url + "/{id}", TareaAgente.class, idTarea);
        } catch (RestClientException e) {
            throw traducirError(e);
        }
    }

    public TareaAgente buscarPorNombre(String nombre) {
        try {
            return rest.getForObject(url + "/buscar?nombre={nombre}", TareaAgente.class, nombre);
        } catch (RestClientException e) {
            throw traducirError(e);
        }
    }

    public Pagina<TareaAgente> listar(Integer idAgente, Integer prioridad, int pagina, int tamano) {
        String direccion = url + "?pagina=" + pagina + "&tamano=" + tamano;
        if (idAgente != null) {
            direccion += "&idAgente=" + idAgente;
        }
        if (prioridad != null) {
            direccion += "&prioridad=" + prioridad;
        }
        try {
            return rest.exchange(direccion, HttpMethod.GET, null,
                    new ParameterizedTypeReference<Pagina<TareaAgente>>() { }).getBody();
        } catch (RestClientException e) {
            throw traducirError(e);
        }
    }

    public TareaAgente actualizar(int idTarea, TareaAgente tarea) {
        try {
            return rest.exchange(url + "/{id}", HttpMethod.PUT, new HttpEntity<>(tarea),
                    TareaAgente.class, idTarea).getBody();
        } catch (RestClientException e) {
            throw traducirError(e);
        }
    }

    public String eliminar(int idTarea) {
        try {
            return rest.exchange(url + "/{id}", HttpMethod.DELETE, null, String.class, idTarea).getBody();
        } catch (RestClientException e) {
            throw traducirError(e);
        }
    }

    public long contarPorAgente(int idAgente) {
        try {
            Long cantidad = rest.getForObject(url + "/contar?idAgente={id}", Long.class, idAgente);
            return cantidad == null ? 0 : cantidad;
        } catch (RestClientException e) {
            throw traducirError(e);
        }
    }

    /** Convierte la respuesta de error del microservicio C en una excepcion de este servidor. */
    private RuntimeException traducirError(RestClientException e) {
        if (e instanceof HttpStatusCodeException respuesta) {
            String mensaje = "El microservicio de tareas respondio con el error " + respuesta.getStatusCode().value();
            try {
                Map<?, ?> cuerpo = respuesta.getResponseBodyAs(Map.class);
                if (cuerpo != null && cuerpo.get("mensaje") != null) {
                    mensaje = cuerpo.get("mensaje").toString();
                }
            } catch (RuntimeException sinCuerpo) {
                // la respuesta no trae JSON: se deja el mensaje general
            }
            if (respuesta.getStatusCode().value() == 404) {
                return new RecursoNoEncontradoException(mensaje);
            }
            return new ReglaNegocioException(mensaje);
        }
        return new IllegalStateException("No se pudo conectar con el microservicio de tareas en " + url
                + ". Verifique que este en ejecucion");
    }
}
