package com.unibague.tareas.dto;

import java.util.List;

import org.springframework.data.domain.Page;

/** Una pagina de resultados con el mismo formato que usa el microservicio A-B. */
public record Pagina<T>(List<T> elementos, int total, int pagina, int tamano, int totalPaginas) {

    public static <T> Pagina<T> de(Page<T> resultado) {
        return new Pagina<>(resultado.getContent(), (int) resultado.getTotalElements(),
                resultado.getNumber(), resultado.getSize(), Math.max(1, resultado.getTotalPages()));
    }
}
