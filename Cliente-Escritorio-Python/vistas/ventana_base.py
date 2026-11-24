"""Ventana base: encabezado comun y manejo uniforme de excepciones."""
import tkinter as tk
from tkinter import messagebox, ttk

from config import NOMBRE_APP
from modelo.validador import ErrorValidacion
from servicios.excepciones import ErrorConexion, ErrorServidor


def centrar_en_pantalla(ventana, ancho=None, alto=None):
    """Ubica la ventana en la mitad de la pantalla.

    Se centra cuando la ventana ya aparece en pantalla (evento <Map>), porque en Windows
    la posicion que se pone antes de mostrarla se cambia por la posicion por defecto.
    """
    if ancho and alto:
        ventana.geometry(f"{ancho}x{alto}")

    def ubicar():
        ventana.update_idletasks()
        w = ancho or ventana.winfo_reqwidth()
        h = alto or ventana.winfo_reqheight()
        x = max((ventana.winfo_screenwidth() - w) // 2, 0)
        y = max((ventana.winfo_screenheight() - h) // 2, 0)
        ventana.geometry(f"+{x}+{y}")

    def al_aparecer(evento):
        if evento.widget is ventana and not getattr(ventana, "_ya_centrada", False):
            ventana._ya_centrada = True   # solo la primera vez que aparece
            ventana.after(10, ubicar)

    ventana.bind("<Map>", al_aparecer, add="+")
    ubicar()


class VentanaBase(tk.Toplevel):

    def __init__(self, master, titulo, subtitulo="", ancho=780, alto=560):
        super().__init__(master)
        self.title(f"{titulo} · {NOMBRE_APP}")
        centrar_en_pantalla(self, ancho, alto)
        self.minsize(560, 380)
        self.transient(master)

        barra = ttk.Frame(self, style="Barra.TFrame", padding=(16, 10))
        barra.pack(fill="x")
        ttk.Label(barra, text=titulo, style="TituloVentana.TLabel").pack()
        if subtitulo:
            ttk.Label(barra, text=subtitulo, style="SubtituloVentana.TLabel").pack()

        self.cuerpo = ttk.Frame(self, padding=16)
        self.cuerpo.pack(fill="both", expand=True)
        self.bind("<Escape>", lambda _e: self.destroy())

    def ejecutar(self, accion):
        """Ejecuta una accion y muestra cualquier excepcion con un mensaje claro."""
        try:
            return accion()
        except ErrorValidacion as e:
            messagebox.showwarning("Revise los datos", "\n".join("• " + x for x in e.errores), parent=self)
        except ErrorServidor as e:
            messagebox.showerror("Operación no realizada", str(e), parent=self)
        except ErrorConexion as e:
            messagebox.showerror("Sin conexión con el servidor", str(e), parent=self)
        except Exception as e:  # noqa: BLE001 - ultimo recurso para no cerrar la app
            messagebox.showerror("Error inesperado", str(e), parent=self)
        return None
