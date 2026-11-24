package com.unibague.tareas.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Clase C: tarea asignada a un agente inteligente.
 * Se guarda en la coleccion "tareas_agentes" de la base tareas_agentes_db.
 * Un agente inteligente (clase A) puede tener 0..* tareas y cada tarea pertenece a un agente.
 */
@Document(collection = "tareas_agentes")
public class TareaAgente {

    @Id
    @NotNull(message = "El ID de la tarea es obligatorio")
    @Min(value = 1, message = "El ID de la tarea debe ser un entero positivo")
    private Integer idTarea;                    // int           - llave primaria

    @Indexed(unique = true)
    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñÜü _.-]+$",
            message = "El nombre solo admite letras, numeros, espacios, punto, guion y guion bajo")
    private String nombre;                      // String        - obligatorio, unico

    @NotNull(message = "La prioridad es obligatoria")
    @Min(value = 1, message = "La prioridad debe estar entre 1 y 5")
    @Max(value = 5, message = "La prioridad debe estar entre 1 y 5")
    private Integer prioridad;                  // int           - obligatorio (1 a 5)

    @NotNull(message = "El porcentaje de avance es obligatorio")
    @DecimalMin(value = "0.0", message = "El porcentaje de avance debe estar entre 0 y 100")
    @DecimalMax(value = "100.0", message = "El porcentaje de avance debe estar entre 0 y 100")
    private Double porcentajeAvance;            // double        - obligatorio (0 a 100)

    @NotNull(message = "La fecha de asignacion es obligatoria")
    @PastOrPresent(message = "La fecha de asignacion no puede ser futura")
    private LocalDateTime fechaAsignacion;      // LocalDateTime - obligatorio

    @NotNull(message = "Debe indicar si la tarea esta completada")
    private Boolean completada;                 // boolean       - obligatorio

    @DecimalMin(value = "0.0", inclusive = false, message = "Las horas estimadas deben ser mayores que 0")
    @DecimalMax(value = "1000.0", message = "Las horas estimadas no pueden superar 1000")
    private Double horasEstimadas;              // double        - OPCIONAL

    @Size(max = 200, message = "Las observaciones admiten maximo 200 caracteres")
    private String observaciones;               // String        - OPCIONAL

    @Indexed
    @NotNull(message = "El agente inteligente de la tarea es obligatorio")
    @Min(value = 1, message = "El ID del agente inteligente debe ser un entero positivo")
    private Integer idAgenteInteligente;        // referencia a la clase A - obligatorio

    public TareaAgente() {
    }

    public Integer getIdTarea() { return idTarea; }
    public void setIdTarea(Integer idTarea) { this.idTarea = idTarea; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Integer getPrioridad() { return prioridad; }
    public void setPrioridad(Integer prioridad) { this.prioridad = prioridad; }
    public Double getPorcentajeAvance() { return porcentajeAvance; }
    public void setPorcentajeAvance(Double porcentajeAvance) { this.porcentajeAvance = porcentajeAvance; }
    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDateTime fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public Boolean getCompletada() { return completada; }
    public void setCompletada(Boolean completada) { this.completada = completada; }
    public Double getHorasEstimadas() { return horasEstimadas; }
    public void setHorasEstimadas(Double horasEstimadas) { this.horasEstimadas = horasEstimadas; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Integer getIdAgenteInteligente() { return idAgenteInteligente; }
    public void setIdAgenteInteligente(Integer idAgenteInteligente) { this.idAgenteInteligente = idAgenteInteligente; }
}
