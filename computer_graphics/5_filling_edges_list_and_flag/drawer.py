import math
import tkinter.messagebox
from tkinter import *
from typing import List, Tuple
import time

from draw_backend import ddaSegment

class Drawer(Canvas):
    def __init__(self, master, bg):
        super().__init__(master, bg=bg)
        self.offsetX = 15
        self.offsetY = 15
        self.offsetAxes = 15
        self.img = PhotoImage(width=1344, height=976)

    def drawLine(self, points, color="black"):
        for (x, y) in points:
            self.img.put(color, (x + self.offsetX, y + self.offsetY))
            # self.create_oval(x + self.offsetX, y + self.offsetY, x + self.offsetX, y + self.offsetY, outline=color,
            #                  width=1)
        # 688, 503)
        self.create_image((0, 0), image=self.img, state="normal", anchor="nw")

    def imgDrawLine(self, x1, y1, x2, y2, color):
        lineDots = ddaSegment(x1, y1, x2, y2)
        for dot in lineDots:
            self.img.put(color, dot)

    def redraw(self):
        self.delete("all")
        self.create_image((0, 0), image=self.img, state="normal", anchor="nw")
        self.drawAxes()

    def clear(self):
        self.delete("all")
        self.img = PhotoImage(width=1344, height=976)
        self.drawAxes()

    def getCanvasSize(self):
        return self.winfo_width(), self.winfo_height()

    def drawAxes(self):
        width, height = self.getCanvasSize()
        width = 1350 if width == 1 else width
        height = 1000 if height == 1 else height

        self.create_line(self.offsetX, self.offsetY,
                         width - self.offsetX, self.offsetY,
                         arrow=LAST)

        self.create_line(self.offsetX, self.offsetY,
                         self.offsetX, height - self.offsetY - 30,
                         arrow=LAST)

        for x_text in range(100, width - 2 * self.offsetX, 100):
            self.create_line(x_text + self.offsetX, self.offsetY - 3,
                             x_text + self.offsetX, self.offsetY + 3)
            self.create_text(x_text + self.offsetX, self.offsetY + self.offsetAxes,
                             text=f"{x_text}")

        for y_text in range(100, height - 2 * self.offsetY, 100):
            self.create_line(self.offsetX - 3, y_text + self.offsetY,
                             self.offsetX + 3, y_text + self.offsetY)
            self.create_text(self.offsetX + self.offsetAxes, y_text + self.offsetY,
                             text=f"{y_text}")

        self.create_text(self.offsetX + self.offsetAxes, self.offsetY + self.offsetAxes,
                         text="0")
        self.create_text(width - self.offsetX, self.offsetY + self.offsetAxes, text="X")
        self.create_text(self.offsetX + self.offsetAxes, height - self.offsetY, text="Y")

    def getColorOfPixel(self, x: int, y: int) -> str:
        colorTuple = self.img.get(x, y)
        color = "#" + f"{colorTuple[0]:02X}" + f"{colorTuple[1]:02X}" + f"{colorTuple[2]:02X}"
        return color

    def findMaxesMins(self, figureList: List[List[Tuple[int, int]]]):
        xMax: int = figureList[0][0][0]
        xMin: int = figureList[0][0][0]
        yMax: int = figureList[0][0][0]
        yMin: int = figureList[0][0][0]

        figure: List[Tuple[int, int]]
        for figure in figureList:
            for dot in figure:
                xMax = max(dot[0], xMax)
                xMin = min(dot[0], xMin)
                yMax = max(dot[1], yMax)
                yMin = min(dot[1], yMin)

        return xMin, xMax, yMin, yMax

    def fillFigure(self, figureList: List[List[Tuple[int, int]]], fillColor="#F0F0F0", isDelayed: bool = False) -> None:
        uniqueColor = "#123456"
        bgColor = "#FFFFFF"

        # Найти максимумы
        xMin, xMax, yMin, yMax = self.findMaxesMins(figureList)
        timeStart, timeEnd = 0, 0

        if not isDelayed:
            timeStart = time.time()

        # Расставить пиксели у отрезков
        for figure in figureList:
            for i in range(-1, len(figure) - 1):
                dot1: Tuple[int, int] = figure[i]
                dot2: Tuple[int, int] = figure[i + 1]
                # print(f"\n\n\nПрямая - {dot1, dot2}")

                dx: int = dot2[0] - dot1[0]
                dy: int = dot2[1] - dot1[1]
                bx: float = dx / abs(dy) if dy != 0 else dx
                by = dy / abs(dy) if dy != 0 else dy

                # print(f"dx = {dx}\ndy = {dy}\nbx = {bx}\nby = {by}\n\n")

                xCur: float = dot1[0] + bx / 2
                yCur: float = dot1[1] + by / 2

                # print(f"xCur = {xCur}\nyCur = {yCur}\n\n")

                def centerOfCurrentPixel():
                    return math.trunc(xCur) + 0.5, math.trunc(yCur) + 0.5

                def currentPixel():
                    return math.trunc(xCur), math.trunc(yCur)

                for _ in range(abs(dy)):
                    xCenterOfPixel, yCenterOfPixel = centerOfCurrentPixel()
                    xPixel, yPixel = currentPixel()
                    # print(f"Рассматриваю пиксель {xPixel, yPixel}")
                    # print(f"Центр пикселя - {xCenterOfPixel, yCenterOfPixel}")
                    # print(f"Прямая {dot1, dot2} пересекает y = {yCenterOfPixel} в точке ({xCur, yCur})")
                    if xCenterOfPixel < xCur:
                        if self.getColorOfPixel(xPixel + 1, yPixel) == uniqueColor:
                            self.img.put(bgColor, (xPixel + 1, yPixel))
                        else:
                            # print(f"\tЭто правее центра пикселя, помечаю - ({xPixel + 1, yPixel})")
                            self.img.put(uniqueColor, (xPixel + 1, yPixel))
                    else:
                        if self.getColorOfPixel(xPixel, yPixel) == uniqueColor:
                            self.img.put(bgColor, (xPixel, yPixel))
                        else:
                            # print(f"\tЭто левее центра пикселя, помечаю - ({xPixel, yPixel})")
                            self.img.put(uniqueColor, (xPixel, yPixel))
                    xCur += bx
                    yCur += by
                    # print("_____________________________")
                # if isDelayed:
                #     self.redraw()
                #     self.update()
                #     time.sleep(0.5)

        # Закрасить все, что надо закрасить
        # print(xMin, yMin, xMax, yMax)
        for y in range(yMin, yMax + 1):
            curColor = bgColor
            for x in range(xMin, xMax + 1):
                pixelColor = self.getColorOfPixel(x, y)
                # if (pixelColor != "#000"):
                # print(pixelColor, end=" ")
                if pixelColor == uniqueColor:
                    # print("Смена цвета")
                    curColor = bgColor if curColor == fillColor else fillColor

                self.img.put(curColor, (x, y))

            if isDelayed:
                self.redraw()
                self.update()
                time.sleep(0.025)

        self.redraw()

        if not isDelayed:
            timeEnd = time.time()
            timeEnd -= timeStart
            tkinter.messagebox.showinfo("Время выполнения", f"Выполнено за {timeEnd:.4f} секунд")
