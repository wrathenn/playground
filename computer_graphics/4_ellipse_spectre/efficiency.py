from drawer import Drawer
from draw_backend import *
import time
import matplotlib.pyplot as plt
import numpy as np


#                 "Каноническое уравнение",
#                 "Параметрическое уравнение",
#                 "Алгоритм Брезенхема",
#                 "Алгоритм средней точки",
#                 "Библиотечная функция"

def compareCircleTime():
    rList = [10, 100, 200, 300, 400, 500]
    xStart, yStart = 1, 1

    normalStore = list()
    parameterStore = list()
    bresStore = list()
    middleStore = list()
    libStore = list()

    drawer = Drawer(None, "white")
    modifier = 1000
    repeats = 5

    def testNormal(x0, y0, r, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(drawCircleNormal(x0, y0, r), color)
        timeEnd = time.time()
        normalStore.append(int((timeEnd - timeBegin) * modifier))

    def testParameter(x0, y0, r, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(drawCircleParameter(x0, y0, r), color)
        timeEnd = time.time()
        parameterStore.append(int((timeEnd - timeBegin) * modifier))

    def testBres(x0, y0, r, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(drawCircleBresenham(x0, y0, r), color)
        timeEnd = time.time()
        bresStore.append(int((timeEnd - timeBegin) * modifier))

    def testMiddle(x0, y0, r, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(drawCircleMiddlePoint(x0, y0, r), color)
        timeEnd = time.time()
        middleStore.append(int((timeEnd - timeBegin) * modifier))

    def testLib(x0, y0, r, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.create_oval(x0 - r, y0 - r, x0 + r, y0 + r, outline=color)
        timeEnd = time.time()
        libStore.append(int((timeEnd - timeBegin) * modifier))

    for r in rList:
        testNormal(xStart, yStart, r)
        testParameter(xStart, yStart, r)
        testBres(xStart, yStart, r)
        testMiddle(xStart, yStart, r)
        testLib(xStart, yStart, r)

    x = [0, 1, 2, 3, 4, 5]
    y1 = normalStore
    y2 = parameterStore
    y3 = bresStore
    y4 = middleStore
    y5 = libStore

    figure = plt.figure()
    ax = figure.add_subplot(111)
    ax.set_title(f"Сравнение времени на окружностях радиуса {rList} (в с * {modifier})")

    ax.plot(x, y1, c='blue', label="Каноническое уравнение")
    ax.plot(x, y2, c='orange', label="Параметрическое уравнение")
    ax.plot(x, y3, c='green', label="Алгоритм Брезенхема")
    ax.plot(x, y4, c='red', label="Алгоритм средней точки")
    ax.plot(x, y5, c='purple', label="Библиотечная функция")

    ax.legend()
    ax.set_facecolor('seashell')
    # fig.set_figwidth(12)  # ширина Figure
    # fig.set_figheight(6)  # высота Figure
    # fig.set_facecolor('floralwhite')

    print(f"Каноническое уравнение", normalStore)
    print(f"Параметрическое уравнение", parameterStore)
    print(f"Алгоритм Брезенхема", bresStore)
    print(f"Алгоритм средней точки", middleStore)
    print(f"Библиотечная функция", libStore)

    plt.show()


def compareEllipseTime():
    rhList = [10, 100, 200, 300, 400, 500]
    rwList = [30, 300, 600, 900, 1200, 1500]
    xStart, yStart = 1, 1

    normalStore = list()
    parameterStore = list()
    bresStore = list()
    middleStore = list()
    libStore = list()

    drawer = Drawer(None, "white")
    modifier = 1000
    repeats = 5

    def testNormal(x0, y0, rw, rh, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(drawEllipseNormal(x0, y0, rw, rh), color)
        timeEnd = time.time()
        normalStore.append(int((timeEnd - timeBegin) * modifier))

    def testParameter(x0, y0, rw, rh, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(drawEllipseParameter(x0, y0, rw, rh), color)
        timeEnd = time.time()
        parameterStore.append(int((timeEnd - timeBegin) * modifier))

    def testBres(x0, y0, rw, rh, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(drawEllipseBresenham(x0, y0, rw, rh), color)
        timeEnd = time.time()
        bresStore.append(int((timeEnd - timeBegin) * modifier))

    def testMiddle(x0, y0, rw, rh, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(drawEllipseMiddlePoint(x0, y0, rw, rh), color)
        timeEnd = time.time()
        middleStore.append(int((timeEnd - timeBegin) * modifier))

    def testLib(x0, y0, rw, rh, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.create_oval(x0 - rw, y0 - rh, x0 + rw, y0 + rh, outline=color)
        timeEnd = time.time()
        libStore.append(int((timeEnd - timeBegin) * modifier))

    for rW, rH in zip(rwList, rhList):
        testNormal(xStart, yStart, rW, rH)
        testParameter(xStart, yStart, rW, rH)
        testBres(xStart, yStart, rW, rH)
        testMiddle(xStart, yStart, rW, rH)
        testLib(xStart, yStart, rW, rH)

    x = [0, 1, 2, 3, 4, 5]
    y1 = normalStore
    y2 = parameterStore
    y3 = bresStore
    y4 = middleStore
    y5 = libStore

    figure = plt.figure()
    ax = figure.add_subplot(111)
    ax.set_title(f"Сравнение времени на эллипсах радиусами {rwList}, {rhList} (в с * {modifier})")
    ax.plot(x, y1, c='blue', label="Каноническое уравнение")
    ax.plot(x, y2, c='orange', label="Параметрическое уравнение")
    ax.plot(x, y3, c='green', label="Алгоритм Брезенхема")
    ax.plot(x, y4, c='red', label="Алгоритм средней точки")
    ax.plot(x, y5, c='purple', label="Библиотечная функция")

    ax.legend()
    ax.set_facecolor('seashell')

    print(f"Каноническое уравнение", normalStore)
    print(f"Параметрическое уравнение", parameterStore)
    print(f"Алгоритм Брезенхема", bresStore)
    print(f"Алгоритм средней точки", middleStore)
    print(f"Библиотечная функция", libStore)

    plt.show()
