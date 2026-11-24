"""Clase estructural TareaAgente (clase C) del lado del cliente."""
from dataclasses import dataclass, asdict
from typing import Optional


@dataclass
class TareaAgente:
    idTarea: int
    nombre: str
    prioridad: int
    porcentajeAvance: float
    fechaAsignacion: str
    completada: bool
    horasEstimadas: Optional[float]
    observaciones: Optional[str]
    idAgenteInteligente: int

    @classmethod
    def desde_dict(cls, datos: dict) -> "TareaAgente":
        return cls(
            idTarea=datos["idTarea"],
            nombre=datos["nombre"],
            prioridad=datos["prioridad"],
            porcentajeAvance=datos["porcentajeAvance"],
            fechaAsignacion=datos["fechaAsignacion"],
            completada=datos["completada"],
            horasEstimadas=datos.get("horasEstimadas"),
            observaciones=datos.get("observaciones"),
            idAgenteInteligente=datos["idAgenteInteligente"],
        )

    def como_dict(self) -> dict:
        return asdict(self)

    def __str__(self):
        return f"{self.idTarea} - {self.nombre}"
