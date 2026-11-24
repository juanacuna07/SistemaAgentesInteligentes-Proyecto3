"""Ventana: Consultar tarea (un solo caso de uso)."""
from vistas.casos_base import VentanaConsultar
from vistas.tarea_agente.comun import ConfigTareaAgente


class VistaConsultarTareaAgente(ConfigTareaAgente, VentanaConsultar):
    pass
