package com.unibague.agentes.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/** Datos para CREAR una tarea (incluye la llave primaria). */
public record TareaAgenteInput(

        @NotNull(message = "El ID de la tarea es obligatorio")
        @Positive(message = "El ID de la tarea debe ser un entero positivo")
        Integer idTarea,

        @NotBlank(message = "El nombre de la tarea es obligatorio")
        @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
        @Pattern(regexp = Validaciones.PATRON_NOMBRE, message = Validaciones.MENSAJE_NOMBRE)
        String nombre,

        @NotNull(message = "La prioridad es obligatoria")
        @Min(value = 1, message = "La prioridad debe estar entre 1 y 5")
        @Max(value = 5, message = "La prioridad debe estar entre 1 y 5")
        Integer prioridad,

        @NotNull(message = "El porcentaje de avance es obligatorio")
        @DecimalMin(value = "0.0", message = "El porcentaje de avance debe estar entre 0 y 100")
        @DecimalMax(value = "100.0", message = "El porcentaje de avance debe estar entre 0 y 100")
        Double porcentajeAvance,

        @NotNull(message = "La fecha de asignacion es obligatoria")
        @PastOrPresent(message = "La fecha de asignacion no puede ser futura")
        LocalDateTime fechaAsignacion,

        @NotNull(message = "Debe indicar si la tarea esta completada")
        Boolean completada,

        @DecimalMin(value = "0.0", inclusive = false, message = "Las horas estimadas deben ser mayores que 0")
        @DecimalMax(value = "1000.0", message = "Las horas estimadas no pueden superar 1000")
        Double horasEstimadas,

        @Size(max = 200, message = "Las observaciones admiten maximo 200 caracteres")
        String observaciones,

        @NotNull(message = "Debe elegir el agente inteligente de la tarea")
        @Positive(message = "El ID del agente inteligente debe ser un entero positivo")
        Integer idAgenteInteligente
) {
}
