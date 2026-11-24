"""Ventana: Eliminar tarea (un solo caso de uso)."""
from vistas.casos_base import VentanaEliminar
from vistas.tarea_agente.comun import ConfigTareaAgente


class VistaEliminarTareaAgente(ConfigTareaAgente, VentanaEliminar):
    pass
