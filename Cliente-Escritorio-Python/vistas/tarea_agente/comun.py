"""Configuracion compartida por las ventanas de las tareas (clase C)."""
from modelo.campos import CAMPOS_TAREA_AGENTE
from servicios.notificador import AGENTE_INTELIGENTE, TAREA_AGENTE
from servicios.servicio_agente_inteligente import ServicioAgenteInteligente
from servicios.servicio_tarea_agente import ServicioTareaAgente


class ConfigTareaAgente:
    nombre_entidad = "tarea"
    nombre_plural = "tareas de los agentes"
    campos = CAMPOS_TAREA_AGENTE
    servicio_cls = ServicioTareaAgente
    # la grilla muestra datos del agente inteligente, asi que tambien observa sus cambios
    eventos_observados = [TAREA_AGENTE, AGENTE_INTELIGENTE]

    def proveedor_referencias(self):
        return ServicioAgenteInteligente().opciones
