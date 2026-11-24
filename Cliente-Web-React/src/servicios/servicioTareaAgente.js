import ClienteGraphQL from './clienteGraphQL.js';
import TareaAgente from '../modelo/TareaAgente.js';
import Notificador, { TAREA_AGENTE } from './notificador.js';

/*
 * Tareas (clase C). El cliente solo habla con el microservicio A-B (GraphQL);
 * ese servidor es el que llama al microservicio de tareas.
 */
const CAMPOS = 'idTarea nombre prioridad porcentajeAvance fechaAsignacion completada horasEstimadas observaciones idAgenteInteligente';
const CAMPOS_LISTA = `${CAMPOS} nombreAgente estadoAgente`;

const CREAR = `mutation Crear($input: TareaAgenteInput!) {
  crearTareaAgente(input: $input) { ${CAMPOS} }
}`;
const ACTUALIZAR = `mutation Actualizar($id: Int!, $input: ActualizarTareaAgenteInput!) {
  actualizarTareaAgente(idTarea: $id, input: $input) { ${CAMPOS} }
}`;
const ELIMINAR = `mutation Eliminar($id: Int!) { eliminarTareaAgente(idTarea: $id) }`;
const POR_ID = `query PorId($id: Int!) { tareaAgentePorId(idTarea: $id) { ${CAMPOS} } }`;
const POR_NOMBRE = `query PorNombre($nombre: String!) { tareaAgentePorNombre(nombre: $nombre) { ${CAMPOS} } }`;
// Listado PAGINADO con filtros opcionales: tarea + nombre y estado de su agente
const LISTAR = `query Listar($idAgente: Int, $prioridad: Int, $pagina: Int, $tamano: Int) {
  tareasConAgente(idAgente: $idAgente, prioridad: $prioridad, pagina: $pagina, tamano: $tamano) {
    elementos { ${CAMPOS_LISTA} } total pagina tamano totalPaginas
  }
}`;

const cliente = () => ClienteGraphQL.obtener();

const servicioTareaAgente = {
  llave: 'idTarea',

  async crear(datos) {
    const r = await cliente().ejecutar(CREAR, { input: datos });
    Notificador.obtener().notificar(TAREA_AGENTE);
    return TareaAgente.desdeDatos(r.crearTareaAgente);
  },
  async actualizar(id, datos) {
    const r = await cliente().ejecutar(ACTUALIZAR, { id, input: datos });
    Notificador.obtener().notificar(TAREA_AGENTE);
    return TareaAgente.desdeDatos(r.actualizarTareaAgente);
  },
  async eliminar(id) {
    const r = await cliente().ejecutar(ELIMINAR, { id });
    Notificador.obtener().notificar(TAREA_AGENTE);
    return r.eliminarTareaAgente;
  },
  async buscarPorId(id) {
    const r = await cliente().ejecutar(POR_ID, { id });
    return TareaAgente.desdeDatos(r.tareaAgentePorId);
  },
  async buscarPorNombre(nombre) {
    const r = await cliente().ejecutar(POR_NOMBRE, { nombre });
    return TareaAgente.desdeDatos(r.tareaAgentePorNombre);
  },
  /** Una página de tareas; idAgente y prioridad son opcionales (null = sin ese filtro). */
  async listar(idAgente = null, prioridad = null, pagina = 0, tamano = 25) {
    const r = await cliente().ejecutar(LISTAR, { idAgente, prioridad, pagina, tamano });
    return r.tareasConAgente;
  },
};

export default servicioTareaAgente;
