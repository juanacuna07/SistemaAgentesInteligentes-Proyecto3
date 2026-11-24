"""Ventana: Listar tareas (grilla PAGINADA).

Muestra TODOS los atributos de la tarea + 2 atributos del agente inteligente
(nombre y estado). Se puede filtrar por agente inteligente o por prioridad.
"""
from modelo.campos import PRIORIDADES
from vistas.casos_base import VentanaListar
from vistas.tarea_agente.comun import ConfigTareaAgente


class VistaListarTareaAgente(ConfigTareaAgente, VentanaListar):
    columnas = [
        ("idTarea", "ID", 55, "entero"),
        ("nombre", "Nombre", 160, "texto"),
        ("prioridad", "Prioridad", 70, "entero"),
        ("porcentajeAvance", "Avance %", 80, "decimal"),
        ("fechaAsignacion", "Asignación", 130, "fecha"),
        ("completada", "Completada", 85, "booleano"),
        ("horasEstimadas", "Horas est.", 80, "decimal"),
        ("observaciones", "Observaciones", 170, "texto"),
        ("idAgenteInteligente", "ID agente", 75, "entero"),
        ("nombreAgente", "Agente inteligente", 130, "texto"),
        ("estadoAgente", "Estado del agente", 125, "texto"),
    ]
    filtros = [
        {"clave": None, "etiqueta": "Todos", "tipo": None},
        {"clave": "agente", "etiqueta": "Agente inteligente", "tipo": "referencia"},
        {"clave": "prioridad", "etiqueta": "Prioridad", "tipo": "opcion", "opciones": PRIORIDADES},
    ]

    def consultar(self, clave_filtro, valor, pagina, tamano):
        if clave_filtro == "agente":
            return self.servicio.listar(id_agente=valor, pagina=pagina, tamano=tamano)
        if clave_filtro == "prioridad":
            return self.servicio.listar(prioridad=int(valor), pagina=pagina, tamano=tamano)
        return self.servicio.listar(pagina=pagina, tamano=tamano)
