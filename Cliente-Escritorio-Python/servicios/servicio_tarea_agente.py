"""Consumo de los servicios GraphQL de las tareas (clase C).

El cliente solo habla con el microservicio A-B (GraphQL). Ese servidor es el que
llama al microservicio de tareas, asi que para el cliente es transparente.
"""
from modelo.tarea_agente import TareaAgente
from servicios.cliente_graphql import ClienteGraphQL
from servicios.notificador import TAREA_AGENTE, Notificador

CAMPOS = ("idTarea nombre prioridad porcentajeAvance fechaAsignacion completada "
          "horasEstimadas observaciones idAgenteInteligente")
CAMPOS_LISTA = CAMPOS + " nombreAgente estadoAgente"

CREAR = """
mutation Crear($input: TareaAgenteInput!) {
  crearTareaAgente(input: $input) { %s }
}""" % CAMPOS

ACTUALIZAR = """
mutation Actualizar($id: Int!, $input: ActualizarTareaAgenteInput!) {
  actualizarTareaAgente(idTarea: $id, input: $input) { %s }
}""" % CAMPOS

ELIMINAR = """
mutation Eliminar($id: Int!) {
  eliminarTareaAgente(idTarea: $id)
}"""

POR_ID = """
query PorId($id: Int!) {
  tareaAgentePorId(idTarea: $id) { %s }
}""" % CAMPOS

POR_NOMBRE = """
query PorNombre($nombre: String!) {
  tareaAgentePorNombre(nombre: $nombre) { %s }
}""" % CAMPOS

# Listado PAGINADO: tarea + nombre y estado del agente inteligente. Los filtros son opcionales.
LISTAR = """
query Listar($idAgente: Int, $prioridad: Int, $pagina: Int, $tamano: Int) {
  tareasConAgente(idAgente: $idAgente, prioridad: $prioridad, pagina: $pagina, tamano: $tamano) {
    elementos { %s }
    total pagina tamano totalPaginas
  }
}""" % CAMPOS_LISTA


class ServicioTareaAgente:
    LLAVE = "idTarea"

    def __init__(self):
        self._cliente = ClienteGraphQL()

    def crear(self, datos: dict) -> TareaAgente:
        r = self._cliente.ejecutar(CREAR, {"input": datos})
        Notificador().notificar(TAREA_AGENTE)
        return TareaAgente.desde_dict(r["crearTareaAgente"])

    def actualizar(self, id_tarea: int, datos: dict) -> TareaAgente:
        r = self._cliente.ejecutar(ACTUALIZAR, {"id": id_tarea, "input": datos})
        Notificador().notificar(TAREA_AGENTE)
        return TareaAgente.desde_dict(r["actualizarTareaAgente"])

    def eliminar(self, id_tarea: int) -> str:
        mensaje = self._cliente.ejecutar(ELIMINAR, {"id": id_tarea})["eliminarTareaAgente"]
        Notificador().notificar(TAREA_AGENTE)
        return mensaje

    def buscar_por_id(self, id_tarea: int) -> TareaAgente:
        r = self._cliente.ejecutar(POR_ID, {"id": id_tarea})
        return TareaAgente.desde_dict(r["tareaAgentePorId"])

    def buscar_por_nombre(self, nombre: str) -> TareaAgente:
        r = self._cliente.ejecutar(POR_NOMBRE, {"nombre": nombre})
        return TareaAgente.desde_dict(r["tareaAgentePorNombre"])

    def listar(self, id_agente: int = None, prioridad: int = None, pagina: int = 0, tamano: int = 25) -> dict:
        """Una pagina de tareas (todas si no hay filtro), con nombre y estado de su agente."""
        variables = {"idAgente": id_agente, "prioridad": prioridad, "pagina": pagina, "tamano": tamano}
        return self._cliente.ejecutar(LISTAR, variables)["tareasConAgente"]
