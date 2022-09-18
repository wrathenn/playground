import matplotlib
import tkinter as tk
import tkinter.messagebox as tkmsg
import math

from geometry import Dot, Rectangle, floatCompare

from graph import Graph
from table import Table
from store import rectangleStore, dotM2Store

'''
На плоскости задан прямоугольник координатами (по порядку обхода вершин) и множество точек.

Найти такой треугольник с вершинами в точках множества, для которого прямая, 
соединяющая центр прямоугольника и центр тяжести треугольника (точка пересечения медиан) 
образует минимальный угол с осью ординат.
'''

# Приготовления
matplotlib.use('TkAgg')

# Создать окно
root = tk.Tk()
root.title("ЛР №1 Шацкий Р.Е. ИУ7-45Б")
root.geometry('1920x1080')

# Создать фреймы
# M1Frame = tk.Frame(root, bg="#2b2b2b")
# M1Frame.place(relx=0.0, rely=0.0, relwidth=0.2, relheight=1, anchor="nw")
M2Frame = tk.Frame(root, bg="#2b2b2b")
M2Frame.place(relx=0.8, rely=0.0, relwidth=0.2, relheight=1, anchor="nw")

mainFrame = tk.Frame(root, bg="#acacac")
mainFrame.place(relx=0.0, rely=0.0, relwidth=0.8, relheight=0.75, anchor="nw")

inputFrame = tk.Frame(root)
rectangleFrame = tk.Frame(inputFrame)
dotFrame = tk.Frame(inputFrame)
solveFrame = tk.Frame(inputFrame)
inputFrame.place(relx=0.0, rely=0.75, relwidth=0.8, relheight=0.25, anchor="nw")
rectangleFrame.place(relx=0.0, rely=0.0, relwidth=0.2, relheight=1, anchor="nw")
dotFrame.place(relx=0.8, rely=0.0, relwidth=0.2, relheight=1, anchor="nw")
solveFrame.place(relx=0.2, rely=0.0, relwidth=0.6, relheight=1, anchor="nw")

graph = Graph(mainFrame)

solveText = tk.Label(solveFrame, justify=tk.LEFT, state=tk.NORMAL, font="ubuntu 14", fg="black", bg="white",
                     anchor="nw", text="Введите координаты вершин прямоугольника!")
solveText.place(relx=0.0, rely=0.25, relwidth=1, relheight=0.75)


def findSolution() -> None:
    new_text = graph.draw()
    solveText.configure(text=new_text)


solveButton = tk.Button(solveFrame, justify="center", bg="green", text="Решить",
                        command=lambda: findSolution())
solveButton.place(relx=0.0, rely=0.0, relwidth=1, relheight=0.25)

M2Table = Table(M2Frame, ("№", "x", "y"), [])

# Кнопки и инпуты прямоугольника
rectangleLabel = tk.Label(rectangleFrame, text="Прямоугольник")
rectangleLabel.place(relx=0.0, rely=0.0, relwidth=1, relheight=0.125)
rectangleXLabel = tk.Label(rectangleFrame, text="x", justify="center")
rectangleXLabel.place(relx=0.0, rely=0.125, relwidth=0.5, relheight=0.125)
rectangleYLabel = tk.Label(rectangleFrame, text="y", justify="center")
rectangleYLabel.place(relx=0.5, rely=0.125, relwidth=0.5, relheight=0.125)

rectangleDotXInput = tk.Entry(rectangleFrame, justify="center")
rectangleDotXInput.place(relx=0.0, rely=0.25, relwidth=0.5, relheight=0.125)
rectangleDotYInput = tk.Entry(rectangleFrame, justify="center")
rectangleDotYInput.place(relx=0.5, rely=0.25, relwidth=0.5, relheight=0.125)
rectangleHeightLabel = tk.Label(rectangleFrame, text="Высота")
rectangleHeightLabel.place(relx=0.0, rely=0.375, relwidth=0.5, relheight=0.125)
rectangleHeightInput = tk.Entry(rectangleFrame, justify="center")
rectangleHeightInput.place(relx=0.5, rely=0.375, relwidth=0.5, relheight=0.125)
rectangleWidthLabel = tk.Label(rectangleFrame, text="Ширина")
rectangleWidthLabel.place(relx=0.0, rely=0.5, relwidth=0.5, relheight=0.125)
rectangleWidthInput = tk.Entry(rectangleFrame, justify="center")
rectangleWidthInput.place(relx=0.5, rely=0.5, relwidth=0.5, relheight=0.125)
rectangleAngleLabel = tk.Label(rectangleFrame, text="Угол наклона")
rectangleAngleLabel.place(relx=0.0, rely=0.625, relwidth=0.5, relheight=0.125)
rectangleAngleInput = tk.Entry(rectangleFrame, justify="center")
rectangleAngleInput.place(relx=0.5, rely=0.625, relwidth=0.5, relheight=0.125)


def rectangleGet():
    try:
        dot1 = Dot(float(rectangleDotXInput.get()), float(rectangleDotYInput.get()))
        h = float(rectangleHeightInput.get())
        l = float(rectangleWidthInput.get())
        angle = float(rectangleAngleInput.get())
    except Exception:
        tkmsg.showwarning("Ошибка!", "Некорректные данные")
        return

    angle_l = math.pi / 180 * angle
    # Второй угол - "пи/2 - первый", так что можно заменить его косинус и синус по формулам приведения
    l_x_cos = l * math.cos(angle_l)
    l_x_sin = l * math.sin(angle_l)
    h_x_cos = h * math.sin(angle_l)
    h_x_sin = h * math.cos(angle_l)
    l_x_cos = 0 if floatCompare(l_x_cos, 0) else l_x_cos
    l_x_sin = 0 if floatCompare(l_x_sin, 0) else l_x_sin
    h_x_cos = 0 if floatCompare(h_x_cos, 0) else h_x_cos
    h_x_sin = 0 if floatCompare(h_x_sin, 0) else h_x_sin

    dot2 = Dot(dot1.x - h_x_cos, dot1.y + h_x_sin)
    dot3 = Dot(dot2.x + l_x_cos, dot2.y + l_x_sin)
    dot4 = Dot(dot1.x + l_x_cos, dot1.y + l_x_sin)

    rectangle = Rectangle(dot1, dot2, dot3, dot4)
    rectangleStore.change(rectangle)

    graph.drawOnlyRectangle()
    solveText.configure(text="Координаты текущего прямоугольника:" +
                             "\nx1 - " + str(round(rectangle.cords[0].x, 3)) + ", y1 - " + str(round(rectangle.cords[0].y, 3)) +
                             "\nx2 - " + str(round(rectangle.cords[1].x, 3)) + ", y2 - " + str(round(rectangle.cords[1].y, 3)) +
                             "\nx3 - " + str(round(rectangle.cords[2].x, 3)) + ", y3 - " + str(round(rectangle.cords[2].y, 3)) +
                             "\nx4 - " + str(round(rectangle.cords[3].x, 3)) + ", y4 - " + str(round(rectangle.cords[3].y, 3)))


def rectangleDelete():
    rectangleStore.clear()
    graph.clearData()
    solveText.configure(text="Введите координаты вершин прямоугольника!")


rectangleCreateButton = tk.Button(rectangleFrame, justify="center", bg="green", text="Построить/Изменить",
                                  command=lambda: rectangleGet(), fg="white")
rectangleCreateButton.place(relx=0.0, rely=0.75, relwidth=1, relheight=0.125)
rectangleDeleteButton = tk.Button(rectangleFrame, justify="center", bg="red", text="Удалить", fg="white",
                                  command=lambda: rectangleDelete())
rectangleDeleteButton.place(relx=0.0, rely=0.875, relwidth=1, relheight=0.125)

# Кнопки и инпуты точки
dotLabel = tk.Label(dotFrame, text="Точка")
dotLabel.place(relx=0.0, rely=0.0, relwidth=1, relheight=0.125)
dotXLabel = tk.Label(dotFrame, text="x", justify="center")
dotXLabel.place(relx=0.0, rely=0.125, relwidth=0.5, relheight=0.125)
dotYLabel = tk.Label(dotFrame, text="y", justify="center")
dotYLabel.place(relx=0.5, rely=0.125, relwidth=0.5, relheight=0.125)

dotXInput = tk.Entry(dotFrame, justify="center")
dotXInput.place(relx=0.0, rely=0.25, relwidth=0.5, relheight=0.125)
dotYInput = tk.Entry(dotFrame, justify="center")
dotYInput.place(relx=0.5, rely=0.25, relwidth=0.5, relheight=0.125)


def dotGet():
    try:
        dot = Dot(float(dotXInput.get()), float(dotYInput.get()))
    except Exception:
        tkmsg.showwarning("Ошибка!", "Некорректная первая точка")
        return

    try:
        dotM2Store.add(dot)
    except Exception:
        tkmsg.showwarning("Ошибка!", "Такая точка уже есть")
        return

    M2Table.render(dotM2Store)


def dotDelete():
    id = M2Table.table.focus()
    if not id:
        return
    data = M2Table.table.item(id)["values"]
    dotData = list(map(float, data[1:2]))
    dotIndex = int(round(data[0]))
    dotM2Store.delete(dotIndex, dotData)
    M2Table.render(dotM2Store)


def dotDeleteAll():
    dotM2Store.data = []
    M2Table.render(dotM2Store)


def dotChange():
    id = M2Table.table.focus()
    if id == "":
        return
    data = M2Table.table.item(id)["values"]
    dotData = list(map(float, data[1:2]))
    dotIndex = int(round(data[0]))

    changeWindow: tk.Tk = tk.Tk()
    changeWindow.geometry("300x300")
    changeWindow.title("Изменить точку")

    changeWindowFrame = tk.Frame(changeWindow)
    changeWindowFrame.place(relx=0.0, rely=0.0, relwidth=1, relheight=1)
    changeLabel = tk.Label(changeWindowFrame, justify="center",
                           text="Введите новые координаты\n"
                                "Предыдущие - " + str(data[1]) + ", " + str(data[2]))
    changeLabel.place(relx=0.0, rely=0.0, relwidth=1, relheight=0.25)
    changeXLabel = tk.Label(changeWindowFrame, justify="center", text="X:")
    changeXLabel.place(relx=0.0, rely=0.25, relwidth=0.3, relheight=0.25)
    changeXInput = tk.Entry(changeWindowFrame, justify="center")
    changeXInput.place(relx=0.3, rely=0.25 + 0.0625, relwidth=0.5, relheight=0.125)
    changeYLabel = tk.Label(changeWindowFrame, justify="center", text="Y:")
    changeYLabel.place(relx=0.0, rely=0.50, relwidth=0.3, relheight=0.25)
    changeYInput = tk.Entry(changeWindowFrame, justify="center")
    changeYInput.place(relx=0.3, rely=0.50 + 0.0625, relwidth=0.5, relheight=0.125)

    def localChange():
        try:
            dot = Dot(float(changeXInput.get()), float(changeYInput.get()))
        except Exception:
            tkmsg.showwarning("Ошибка!", "Некорректные данные")
            return
        dotM2Store.data[dotIndex - 1][1] = dot
        M2Table.render(dotM2Store)
        changeWindow.destroy()

    changeButton = tk.Button(changeWindowFrame, justify="center", bg="green", text="Изменить",
                             command=lambda: localChange())
    changeButton.place(relx=0.0, rely=0.75, relwidth=1, relheight=0.25)
    changeWindow.mainloop()


dotCreateButton = tk.Button(dotFrame, justify="center", bg="green", text="Построить",
                            command=lambda: dotGet(), fg="white")
dotCreateButton.place(relx=0.0, rely=0.375, relwidth=0.5, relheight=0.125)
dotDeleteButton = tk.Button(dotFrame, justify="center", bg="red", text="Удалить", fg="white",
                            command=lambda: dotDelete())
dotDeleteButton.place(relx=0.5, rely=0.375, relwidth=0.5, relheight=0.125)
dotChangeButton = tk.Button(dotFrame, justify="center", bg="orange", text="изменить",
                            command=lambda: dotChange())
dotChangeButton.place(relx=0.0, rely=0.5, relwidth=1, relheight=0.125)
dotDeleteAllButton = tk.Button(dotFrame, justify="center", bg="darkred", text="УДАЛИТЬ ВСЕ",
                               command=lambda: dotDeleteAll(), fg="white")
dotDeleteAllButton.place(relx=0.0, rely=0.625, relwidth=1, relheight=0.125)

root.mainloop()
