/** Atributos de cada clase: tipo, obligatoriedad y reglas de validación. */
export const ESTADOS_OPERATIVOS = ['ACTIVO', 'INACTIVO', 'MANTENIMIENTO'];
export const PRIORIDADES = ['1', '2', '3', '4', '5'];
const PATRON_NOMBRE = /^[A-Za-z0-9ÁÉÍÓÚáéíóúÑñÜü _.-]+$/;

export const CAMPOS_AGENTE_INTELIGENTE = [
  { clave: 'idAgente', etiqueta: 'ID del agente', tipo: 'entero', obligatorio: true, minimo: 1, esLlave: true, ayuda: 'Entero positivo, único' },
  { clave: 'nombre', etiqueta: 'Nombre', tipo: 'texto', obligatorio: true, minLargo: 3, maxLargo: 50, patron: PATRON_NOMBRE, ayuda: '3 a 50 caracteres, único' },
  { clave: 'nivelAutonomia', etiqueta: 'Nivel de autonomía (%)', tipo: 'decimal', obligatorio: true, minimo: 0, maximo: 100, ayuda: '0 a 100' },
  { clave: 'estadoOperativo', etiqueta: 'Estado operativo', tipo: 'opcion', obligatorio: true, opciones: ESTADOS_OPERATIVOS },
  { clave: 'nivelConfianza', etiqueta: 'Nivel de confianza (%)', tipo: 'decimal', obligatorio: false, minimo: 0, maximo: 100, ayuda: 'Opcional, 0 a 100' },
  { clave: 'fechaRegistro', etiqueta: 'Fecha de registro', tipo: 'fecha', obligatorio: true, ayuda: 'No puede ser futura' },
  { clave: 'aprendizajeContinuo', etiqueta: 'Aprendizaje continuo', tipo: 'booleano', obligatorio: true },
  { clave: 'descripcion', etiqueta: 'Descripción', tipo: 'texto', obligatorio: false, maxLargo: 200, ayuda: 'Opcional, máximo 200 caracteres' },
];

export const CAMPOS_AGENTE_NAVEGACION = [
  { clave: 'idNavegacion', etiqueta: 'ID de navegación', tipo: 'entero', obligatorio: true, minimo: 1, esLlave: true, ayuda: 'Entero positivo, único' },
  { clave: 'nombre', etiqueta: 'Nombre', tipo: 'texto', obligatorio: true, minLargo: 3, maxLargo: 50, patron: PATRON_NOMBRE, ayuda: '3 a 50 caracteres, único' },
  { clave: 'origen', etiqueta: 'Origen', tipo: 'texto', obligatorio: true, minLargo: 2, maxLargo: 60 },
  { clave: 'destino', etiqueta: 'Destino', tipo: 'texto', obligatorio: true, minLargo: 2, maxLargo: 60, ayuda: 'Diferente del origen' },
  { clave: 'precisionGPS', etiqueta: 'Precisión GPS (%)', tipo: 'decimal', obligatorio: true, minimo: 0, maximo: 100, ayuda: '0 a 100' },
  { clave: 'velocidadMaxima', etiqueta: 'Velocidad máxima (km/h)', tipo: 'decimal', obligatorio: false, minimo: 0, minimoExclusivo: true, maximo: 500, ayuda: 'Opcional, mayor que 0 y hasta 500' },
  { clave: 'fechaUltimaCalibracion', etiqueta: 'Última calibración', tipo: 'fecha', obligatorio: true, ayuda: 'No puede ser futura' },
  { clave: 'rutaOptimizada', etiqueta: 'Ruta optimizada', tipo: 'booleano', obligatorio: true },
  { clave: 'idAgenteInteligente', etiqueta: 'Agente inteligente asignado', tipo: 'referencia', obligatorio: false, ayuda: 'Opcional. Escriba nombre o ID y elija de la lista' },
];

export const CAMPOS_TAREA_AGENTE = [
  { clave: 'idTarea', etiqueta: 'ID de la tarea', tipo: 'entero', obligatorio: true, minimo: 1, esLlave: true, ayuda: 'Entero positivo, único' },
  { clave: 'nombre', etiqueta: 'Nombre de la tarea', tipo: 'texto', obligatorio: true, minLargo: 3, maxLargo: 50, patron: PATRON_NOMBRE, ayuda: '3 a 50 caracteres, único' },
  { clave: 'prioridad', etiqueta: 'Prioridad', tipo: 'entero', obligatorio: true, minimo: 1, maximo: 5, ayuda: '1 (baja) a 5 (alta)' },
  { clave: 'porcentajeAvance', etiqueta: 'Avance (%)', tipo: 'decimal', obligatorio: true, minimo: 0, maximo: 100, ayuda: '0 a 100. Si está completada: 100' },
  { clave: 'fechaAsignacion', etiqueta: 'Fecha de asignación', tipo: 'fecha', obligatorio: true, ayuda: 'No puede ser futura' },
  { clave: 'completada', etiqueta: 'Completada', tipo: 'booleano', obligatorio: true },
  { clave: 'horasEstimadas', etiqueta: 'Horas estimadas', tipo: 'decimal', obligatorio: false, minimo: 0, minimoExclusivo: true, maximo: 1000, ayuda: 'Opcional, mayor que 0 y hasta 1000' },
  { clave: 'observaciones', etiqueta: 'Observaciones', tipo: 'texto', obligatorio: false, maxLargo: 200, ayuda: 'Opcional, máximo 200 caracteres' },
  { clave: 'idAgenteInteligente', etiqueta: 'Agente inteligente', tipo: 'referencia', obligatorio: true, ayuda: 'Obligatorio. Escriba nombre o ID y elija de la lista' },
];
