from math import acos, pi

import matplotlib.pyplot as plt
import tkinter as tk
import tkinter.messagebox as tkmsg
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg

from geometry import Rectangle, Dot, dotDistance2, floatCompare
from store import rectangleStore, dotM2Store


def localGetCenter(dot1, dot2):
    return [(dot1[0] + dot2[0]) / 2, (dot1[1] + dot2[1]) / 2]


class Graph:
    def __init__(self, parent: tk.Frame):
        self.parent = parent
        self.figure: plt.Figure = plt.figure()
        self.data = {'rectangle': None,
                     'triangle': None,
                     'rectangleDiagonals': None,
                     'triangleMedians': None,
                     'connector': None,
                     'extraY': None,
                     'axesX': None,
                     'axesY': None
                     }
        axes = self.figure.gca()
        self.figure.gca().set_aspect("equal")
        axes.plot(1, 0, ">k", transform=axes.get_yaxis_transform(), clip_on=False)
        axes.plot(0, 1, "^k", transform=axes.get_xaxis_transform(), clip_on=False)
        axes.set_xlabel("X", loc="right")
        axes.set_ylabel("Y", loc="top")

        # Move the left and bottom spines to x = 0 and y = 0, respectively.
        axes.spines["left"].set_position(("data", 0))
        axes.spines["bottom"].set_position(("data", 0))

        # Hide the top and right spines.
        axes.spines["top"].set_visible(False)
        axes.spines["right"].set_visible(False)

        self.canvas = FigureCanvasTkAgg(self.figure, master=self.parent)
        self.canvas.get_tk_widget().pack(side=tk.BOTTOM, fill=tk.BOTH, expand=True)
        # self.canvas.tkcanvas.pack(side=tk.BOTTOM, fill=tk.BOTH, expand=True)

    def clearData(self):
        if self.data['rectangle']:
            self.data['rectangle'].remove()
            self.data['rectangle'] = None
        if self.data['triangle']:
            self.data['triangle'].remove()
            self.data['triangle'] = None
        if self.data['connector']:
            self.data['connector'].remove()
            self.data['connector'] = None
        if self.data['extraY']:
            self.data['extraY'].remove()
            self.data['extraY'] = None
        if self.data['axesX']:
            self.data['axesX'].remove()
            self.data['axesX'] = None
        if self.data['axesY']:
            self.data['axesY'].remove()
            self.data['axesY'] = None
        if self.data['rectangleDiagonals']:
            if len(self.data['rectangleDiagonals']):
                for dataPart in self.data['rectangleDiagonals']:
                    dataPart.remove()
            self.data['rectangleDiagonals'] = None
        if self.data['triangleMedians']:
            if len(self.data['triangleMedians']):
                for dataPart in self.data['triangleMedians']:
                    dataPart.remove()
            self.data['triangleMedians'] = None

        self.canvas.draw()

    def countAxes(self, triangle):
        localPadding = 2

        if len(rectangleStore.data) == 0:
            return 0

        rectangle: Rectangle = rectangleStore.data[0]
        xMax: float = rectangle.cords[0].x
        xMin: float = rectangle.cords[0].x
        yMax: float = rectangle.cords[0].y
        yMin: float = rectangle.cords[0].y

        for dot in rectangleStore.data[0].getCords():
            xMax = dot.x if dot.x > xMax else xMax
            yMax = dot.y if dot.y > yMax else yMax
            xMin = dot.x if dot.x < xMin else xMin
            yMin = dot.y if dot.y < yMin else yMin

        if triangle:
            for dot in triangle:
                xMax = dot[0] if dot[0] > xMax else xMax
                yMax = dot[1] if dot[1] > yMax else yMax
                xMin = dot[0] if dot[0] < xMin else xMin
                yMin = dot[1] if dot[1] < yMin else yMin

        self.data['axisX'] = [xMin - localPadding, xMax + localPadding]
        self.data['axisY'] = [yMin - localPadding, yMax + localPadding]
        return (xMin, xMax), (yMin, yMax)

    def drawOnlyRectangle(self):
        self.clearData()

        # Координаты прямоугольника в виде [(x1, y1), (x2, y2), (x3, y3), (x4, y4)]
        rectangleCords = list(map(Dot.getTupleCords, rectangleStore.data[0].cords))

        # Построить сам прямоугольник
        self.data['rectangle'] = plt.Polygon(rectangleCords, fill=False, ec="blue", lw=3)
        self.figure.gca().add_patch(self.data['rectangle'])

        # Подвинуть оси
        axesX, axesY = self.countAxes([[0, 0], [0, 0], [0, 0]])
        self.figure.gca().set_xlim(self.data['axisX'])
        self.figure.gca().set_ylim(self.data['axisY'])
        self.data['axesX'] = plt.Polygon([(axesX[0] - 2, 0), (axesX[1] + 2, 0)], ec="black", lw=1)
        self.data['axesY'] = plt.Polygon([(0, axesY[0] - 2), (0, axesY[1] + 2)], ec="black", lw=1)
        self.figure.gca().add_patch(self.data['axesX'])
        self.figure.gca().add_patch(self.data['axesY'])

        self.canvas.draw()

    def draw(self) -> str:
        result: str = ""

        allDots = dotM2Store.getDataList()
        if len(allDots) < 3:
            tkmsg.showwarning("Ошибка!", "Слишком мало точек")
            return "Ошибка! Слишком мало точек"

        if len(rectangleStore.data) == 0:
            tkmsg.showwarning("Ошибка!", "Не задан прямоугольник")
            return "Ошибка! Не задан прямоугольник"

        # Координаты прямоугольника в виде [(x1, y1), (x2, y2), (x3, y3), (x4, y4)]
        rectangleCords = list(map(Dot.getTupleCords, rectangleStore.data[0].cords))

        # Нахождение центра прямоугольника
        rectangleCenter = ((rectangleCords[0][0] + rectangleCords[2][0]) / 2,
                           (rectangleCords[0][1] + rectangleCords[2][1]) / 2)
        extraDot = (rectangleCenter[0], rectangleCenter[1] + 2)

        resultTriangle = []
        resultCenterDot = []
        resultTriangleMedians = []
        resultConnector = []
        normalTrianglesAmount = 0
        badTrianglesAmount = 0
        maxCos = 0  # Угол 90 градусов - косинус = 0
        # dotPart = [index, x, y]
        for dotPart1 in allDots:
            for dotPart2 in allDots[dotPart1[0]:]:
                for dotPart3 in allDots[dotPart2[0]:]:
                    # print(dotPart1, dotPart2, dotPart3)
                    dot1 = dotPart1[1::]
                    dot2 = dotPart2[1::]
                    dot3 = dotPart3[1::]

                    dist1 = dotDistance2(dot1, dot2)
                    dist2 = dotDistance2(dot2, dot3)
                    dist3 = dotDistance2(dot1, dot3)

                    if floatCompare(dist1, dist2 + dist3) or \
                            floatCompare(dist2, dist1 + dist3) or \
                            floatCompare(dist3, dist1 + dist2):
                        badTrianglesAmount += 1
                    else:
                        # Нахождение центра тяжести треугольника
                        triangleCenter = ((dot1[0] + dot2[0] + dot3[0]) / 3, (dot1[1] + dot2[1] + dot3[1]) / 3)
                        dist1 = dotDistance2(rectangleCenter, triangleCenter)
                        dist2 = dotDistance2(rectangleCenter, extraDot)
                        dist3 = dotDistance2(triangleCenter, extraDot)

                        if floatCompare(dist1, 0) or floatCompare(dist2, 0) or floatCompare(dist3, 0):
                            badTrianglesAmount += 1
                            continue

                        # Нахождение косинуса по теореме косинусов
                        cos = (dist1 ** 2 + dist2 ** 2 - dist3 ** 2) / dist1 / dist2 / 2

                        if abs(cos) > maxCos:
                            maxCos = abs(cos)
                            resultTriangle = [dot1, dot2, dot3]
                            resultCenterDot = triangleCenter

                            resultTriangleMedians = [[dot1, localGetCenter(dot2, dot3)],
                                                     [dot2, localGetCenter(dot1, dot3)],
                                                     [dot3, localGetCenter(dot1, dot2)]]
                            resultConnector = [resultCenterDot, rectangleCenter]

                        normalTrianglesAmount += 1

        if normalTrianglesAmount == 0:
            tkmsg.showwarning("Ошибка!", "Все треугольники оказались вырожденными")
            return "Ошибка! Все треугольники оказались вырожденными"

        result += f"Прямоугольник: {list(map(lambda x: [round(x[0], 3), round(x[1], 3)], rectangleCords))}\n"
        result += f"Найденный треугольник: {resultTriangle}\n"
        result += f"Центр прямоугольника: {rectangleCenter[0]:.3f}, {rectangleCenter[1]:.3f}\n"
        result += f"Центр тяжести треугольника: {resultCenterDot[0]:.3f}, {resultCenterDot[1]:.3f}\n"
        result += f"Косинус угла: {maxCos:.3f}\n"
        result += f"Угол: {acos(maxCos) / pi * 180:.3f}\n"

        self.clearData()

        # Построить сам прямоугольник
        self.data['rectangle'] = plt.Polygon(rectangleCords, fill=False, ec="blue", lw=3)
        self.figure.gca().add_patch(self.data['rectangle'])

        # Добавить диагонали прямоугольника
        self.data['rectangleDiagonals'] = [plt.Polygon([rectangleCords[0], rectangleCords[2]], ec="blue", lw=1),
                                           plt.Polygon([rectangleCords[1], rectangleCords[3]], ec="blue", lw=1)]
        self.figure.gca().add_patch(self.data['rectangleDiagonals'][0])
        self.figure.gca().add_patch(self.data['rectangleDiagonals'][1])

        # Добавить треугольник сам
        self.data['triangle'] = plt.Polygon(resultTriangle, fill=False, ec="red", lw=3)
        self.figure.gca().add_patch(self.data['triangle'])

        # Добавить медианы этого треугольника
        self.data['triangleMedians'] = [plt.Polygon(resultTriangleMedians[0], ec="red", lw=1),
                                        plt.Polygon(resultTriangleMedians[1], ec="red", lw=1),
                                        plt.Polygon(resultTriangleMedians[2], ec="red", lw=1)
                                        ]
        self.figure.gca().add_patch(self.data['triangleMedians'][0])
        self.figure.gca().add_patch(self.data['triangleMedians'][1])
        self.figure.gca().add_patch(self.data['triangleMedians'][2])

        # Добавить соединяющую линию
        self.data['connector'] = plt.Polygon(resultConnector, ec="green", lw=3)
        self.figure.gca().add_patch(self.data['connector'])

        # Добавить дополнительную линию
        self.data['extraY'] = plt.Polygon([[rectangleCenter[0], rectangleCenter[1] - 5],
                                           rectangleCenter,
                                           [rectangleCenter[0], rectangleCenter[1] + 5]],
                                          ec="green", lw=3)
        self.figure.gca().add_patch(self.data['extraY'])

        # Подвинуть оси
        axesX, axesY = self.countAxes(resultTriangle)
        self.figure.gca().set_xlim(self.data['axisX'])
        self.figure.gca().set_ylim(self.data['axisY'])
        self.data['axesX'] = plt.Polygon([(axesX[0] - 2, 0), (axesX[1] + 2, 0)], ec="black", lw=1)
        self.data['axesY'] = plt.Polygon([(0, axesY[0] - 2), (0, axesY[1] + 2)], ec="black", lw=1)
        self.figure.gca().add_patch(self.data['axesX'])
        self.figure.gca().add_patch(self.data['axesY'])

        self.canvas.draw()

        return result
