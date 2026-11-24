"""Ventana: Actualizar tarea (un solo caso de uso)."""
from vistas.casos_base import VentanaActualizar
from vistas.tarea_agente.comun import ConfigTareaAgente


class VistaActualizarTareaAgente(ConfigTareaAgente, VentanaActualizar):
    pass
