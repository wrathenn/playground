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

    def fillFigure(self, x: int, y: int, maxes: Tuple[int, int, int, int],
                   fillColor="#F0F0F0", borderColor="#000000", isDelayed: bool = False) -> None:
        xMin, xMax, yMin, yMax = maxes
        xMin = x if x < xMin else xMin
        xMax = x if x > xMax else xMax
        yMin = y if y < yMin else yMin
        yMax = y if y > xMax else yMax

        timeEnd: float
        timeStart: float

        stack = [(x, y)]

        if not isDelayed:
            timeStart = time.time()

        def _fillRight(_x, _y):
            _xr: int = _x
            for _xr in range(_x, xMax):
                _clr = self.getColorOfPixel(_xr, _y)
                if _clr == borderColor or _clr == fillColor:
                    return _xr - 1
                self.img.put(fillColor, (_xr, _y))

            return _xr

        def _fillLeft(_x, _y):
            _xl: int = _x
            for _xl in range(_x, xMin, -1):
                _clr = self.getColorOfPixel(_xl, _y)
                if _clr == borderColor or _clr == fillColor:
                    return _xl + 1
                self.img.put(fillColor, (_xl, _y))

            return _xl

        def _findNewDots(_x, _y, _xl, _xr):
            _intervalFlag = False
            _xCur = _xl
            while _xCur <= _xr:
                while self.getColorOfPixel(_xCur, _y) != borderColor and self.getColorOfPixel(_xCur,
                                                                                              _y) != fillColor and _xCur <= _xr:
                    if not _intervalFlag:
                        _intervalFlag = True
                    _xCur += 1

                if _intervalFlag:
                    stack.append((_xCur - 1, _y))
                    _intervalFlag = False

                _xTemp = _xCur
                while not self.getColorOfPixel(_xCur, _y) in (borderColor, fillColor) and _xCur <= _xr:
                    _xCur += 1
                if _xCur == _xTemp:
                    _xCur += 1

        while stack:
            x, y = stack.pop()
            self.img.put(fillColor, (x, y))
            xl, xr = _fillLeft(x - 1, y), _fillRight(x + 1, y)
            _findNewDots(x, y + 1, xl, xr)
            _findNewDots(x, y - 1, xl, xr)

            if isDelayed:
                self.redraw()
                self.update()
                time.sleep(0.0025)

        self.redraw()

        if not isDelayed:
            timeEnd = time.time()
            timeEnd -= timeStart
            tkinter.messagebox.showinfo("Время выполнения", f"Выполнено за {timeEnd:.4f} секунд")
