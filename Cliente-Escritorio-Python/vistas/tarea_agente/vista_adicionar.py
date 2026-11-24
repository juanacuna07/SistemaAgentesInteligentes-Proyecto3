"""Ventana: Adicionar tarea (un solo caso de uso)."""
from vistas.casos_base import VentanaAdicionar
from vistas.tarea_agente.comun import ConfigTareaAgente


class VistaAdicionarTareaAgente(ConfigTareaAgente, VentanaAdicionar):
    pass
