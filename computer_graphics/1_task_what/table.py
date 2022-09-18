import tkinter as tk
import tkinter.ttk as ttk
import tkinter.messagebox as tkmsg
from typing import Tuple, List

from store import Store


class Table:
    def __init__(self, parent: tk.Frame,
                 headings: Tuple,
                 rows: List):
        self.table: ttk.Treeview = ttk.Treeview(parent, show="headings", selectmode="browse")
        self.table["columns"] = headings
        self.table["displaycolumns"] = headings

        for head in headings:  # Вставить колонки
            self.table.heading(head, text=head, anchor=tk.CENTER)
            self.table.column(head, anchor=tk.CENTER, width=0)

        for row in rows:  # Вставить строки
            self.table.insert('', tk.END, values=row)

        # Добавить скролл
        scrolltable = tk.Scrollbar(master=parent, command=self.table.yview)
        self.table.configure(yscrollcommand=scrolltable.set)
        scrolltable.pack(side=tk.RIGHT, fill=tk.Y)

        self.table.pack(expand=tk.YES, fill=tk.BOTH)

    def delRow(self):
        id = self.table.focus()
        if id:
            self.table.delete(id)
        else:
            tkmsg.showwarning("Ошибка!", "Перед удалением выберите нужную точку!")

    def delAll(self):
        for i in self.table.get_children():
            self.table.delete(i)

    def render(self, store: Store):
        self.delAll()
        a = store.getDataList()
        for row in store.getDataList():  # Вставить строки
            self.table.insert('', tk.END, values=row)
