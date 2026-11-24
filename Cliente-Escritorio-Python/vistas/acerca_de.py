"""Ventana Ayuda > Acerca de..."""
import tkinter as tk
from tkinter import ttk

from config import CURSO, INTEGRANTES, NOMBRE_APP, TECNOLOGIA, VERSION
from vistas.ventana_base import centrar_en_pantalla


class VentanaAcercaDe(tk.Toplevel):

    def __init__(self, master):
        super().__init__(master)
        self.title("Acerca de...")
        self.resizable(False, False)
        self.transient(master)
        marco = ttk.Frame(self, padding=24)
        marco.pack(fill="both", expand=True)
        ttk.Label(marco, text=NOMBRE_APP, style="Portada.TLabel").pack()
        ttk.Label(marco, text=f"Versión {VERSION}").pack(pady=(4, 0))
        ttk.Label(marco, text=TECNOLOGIA, style="Ayuda.TLabel").pack()
        ttk.Label(marco, text=CURSO, style="Ayuda.TLabel").pack(pady=(0, 14))
        ttk.Label(marco, text="Integrantes del equipo", style="Etiqueta.TLabel").pack()
        for nombre in INTEGRANTES:
            ttk.Label(marco, text=nombre).pack()
        ttk.Button(marco, text="Cerrar", command=self.destroy).pack(pady=(18, 0))
        self.bind("<Escape>", lambda _e: self.destroy())
        centrar_en_pantalla(self)
        self.grab_set()
