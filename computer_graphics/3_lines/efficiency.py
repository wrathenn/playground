from drawer import Drawer
from draw_backend import *
import time
import matplotlib.pyplot as plt
import numpy as np


def compareTime():
    angle = angleToRadians(30)
    lenList = [10, 100, 200, 300, 400, 500]
    xStart, yStart = 1, 1

    ddaStore = list()
    bresIntStore = list()
    bresDoubleStore = list()
    bresReduceStore = list()
    wuStore = list()
    libStore = list()

    drawer = Drawer()
    modifier = 1000
    repeats = 100

    def testDDA(x0, y0, x1, y1, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(digitalDiffAnalyzer(x0, y0, x1, y1), color)
        timeEnd = time.time()
        ddaStore.append(int((timeEnd - timeBegin) * modifier))

    def testBresInt(x0, y0, x1, y1, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(bresenhamInt(x0, y0, x1, y1), color)
        timeEnd = time.time()
        bresIntStore.append(int((timeEnd - timeBegin) * modifier))

    def testBresDouble(x0, y0, x1, y1, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLine(bresenhamDouble(x0, y0, x1, y1), color)
        timeEnd = time.time()
        bresDoubleStore.append(int((timeEnd - timeBegin) * modifier))

    def testBresReduce(x0, y0, x1, y1, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLineWithColor(bresenhamStairsReduce(x0, y0, x1, y1, color))
        timeEnd = time.time()
        bresReduceStore.append(int((timeEnd - timeBegin) * modifier))

    def testBresWu(x0, y0, x1, y1, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.drawLineWithColor(wu(x0, y0, x1, y1, color))
        timeEnd = time.time()
        wuStore.append(int((timeEnd - timeBegin) * modifier))

    def testLib(x0, y0, x1, y1, color="#FF0000"):
        timeBegin = time.time()
        for i in range(repeats):
            drawer.create_line(x0, y0, x1, y1, fill=color)
        timeEnd = time.time()
        libStore.append(int((timeEnd - timeBegin) * modifier))

    for len in lenList:
        xEnd, yEnd = int(xStart + len * math.cos(angle)), int(yStart + len * math.sin(angle))
        testDDA(xStart, yStart, xEnd, yEnd)
        testBresInt(xStart, yStart, xEnd, yEnd)
        testBresDouble(xStart, yStart, xEnd, yEnd)
        testBresReduce(xStart, yStart, xEnd, yEnd)
        testBresWu(xStart, yStart, xEnd, yEnd)
        testLib(xStart, yStart, xEnd, yEnd)

    barWidth = 0.4 / 3
    x1 = np.arange(1, 7) - barWidth * 3
    x2 = np.arange(1, 7) - barWidth * 2
    x3 = np.arange(1, 7) - barWidth
    x4 = np.arange(1, 7)
    x5 = np.arange(1, 7) + barWidth
    x6 = np.arange(1, 7) + barWidth * 2
    y1 = ddaStore
    y2 = bresIntStore
    y3 = bresDoubleStore
    y4 = bresReduceStore
    y5 = wuStore
    y6 = libStore

    fig, ax = plt.subplots()
    ax.set_title(f"Сравнение времени на отрезках длиной {lenList} (в с * {modifier})")
    ax.bar(x1, y1, width=barWidth, label="Цифровой дифференциальный анализатор")
    ax.bar(x2, y2, width=barWidth, label="Целочисленный алгоритм Брезенхема")
    ax.bar(x3, y3, width=barWidth, label="Вещественный алгоритм Брезенхема")
    ax.bar(x4, y4, width=barWidth, label="Алгоритм Брезенхема с устранением ступенчатости")
    ax.bar(x5, y5, width=barWidth, label="Алгоритм Ву")
    ax.bar(x6, y6, width=barWidth, label="Библиотечная функция")

    ax.legend()
    ax.set_facecolor('seashell')
    fig.set_figwidth(12)  # ширина Figure
    fig.set_figheight(6)  # высота Figure
    fig.set_facecolor('floralwhite')

    print(f"Время в с*{modifier}:")
    print(f"Цифровой дифференциальный анализатор:", ddaStore)
    print(f"Целочисленный алгоритм Брезенхема:", bresIntStore)
    print(f"Вещественный алгоритм Брезенхема:", bresDoubleStore)
    print(f"Алгоритм Брезенхема с устранением ступенчатости:", bresReduceStore)
    print(f"Алгоритм Ву:", wuStore)

    plt.show()


def countStairs(dotList, isWu: bool = False):
    dot0 = dotList[0]
    step = 2 if isWu else 1
    stairs = 0
    for i in range(step, len(dotList), step):
        dot1 = dotList[i]
        if (abs(dot0[0] - dot1[0]) != 0 and abs(dot0[1] - dot1[1]) != 0):
            stairs += 1
        dot0 = dot1

    return stairs


def compareStairs():
    angleListDegrees = (5, 10, 30, 45, 70, 90)
    angleList = list(map(angleToRadians, angleListDegrees))
    len = 100
    xStart, yStart = 0, 0

    ddaStore = list()
    bresIntStore = list()
    bresDoubleStore = list()
    bresReduceStore = list()
    wuStore = list()

    def testDDA(x0, y0, x1, y1, color="#FF0000"):
        ddaStore.append(countStairs(digitalDiffAnalyzer(x0, y0, x1, y1)))

    def testBresInt(x0, y0, x1, y1, color="#FF0000"):
        bresIntStore.append(countStairs(bresenhamInt(x0, y0, x1, y1)))

    def testBresDouble(x0, y0, x1, y1, color="#FF0000"):
        bresDoubleStore.append(countStairs(bresenhamDouble(x0, y0, x1, y1)))

    def testBresReduce(x0, y0, x1, y1, color="#FF0000"):
        bresReduceStore.append(countStairs(bresenhamStairsReduce(x0, y0, x1, y1, color)))

    def testBresWu(x0, y0, x1, y1, color="#FF0000"):
        wuStore.append(countStairs(wu(x0, y0, x1, y1, color), isWu=True))

    for angle in angleList:
        xEnd, yEnd = int(len * math.cos(angle)), int(len * math.sin(angle))
        testDDA(xStart, yStart, xEnd, yEnd)
        testBresInt(xStart, yStart, xEnd, yEnd)
        testBresDouble(xStart, yStart, xEnd, yEnd)
        testBresReduce(xStart, yStart, xEnd, yEnd)
        testBresWu(xStart, yStart, xEnd, yEnd)

    x = angleListDegrees
    y1 = ddaStore
    y2 = bresIntStore
    y3 = bresDoubleStore
    y4 = bresReduceStore
    y5 = wuStore

    fig, ax = plt.subplots()
    ax.set_title(f"Сравнение времени при углах {x}")
    ax.plot(x, y1, label="Цифровой дифференциальный анализатор")
    ax.plot(x, y2, label="Целочисленный алгоритм Брезенхема")
    ax.plot(x, y3, label="Вещественный алгоритм Брезенхема")
    ax.plot(x, y4, label="Алгоритм Брезенхема с устранением ступенчатости")
    ax.plot(x, y5, label="Алгоритм Ву")

    ax.legend()
    ax.set_facecolor('seashell')
    fig.set_figwidth(12)  # ширина Figure
    fig.set_figheight(6)  # высота Figure
    fig.set_facecolor('floralwhite')

    print("Количество ступенек:")
    print(f"Цифровой дифференциальный анализатор:", ddaStore)
    print(f"Целочисленный алгоритм Брезенхема:", bresIntStore)
    print(f"Вещественный алгоритм Брезенхема:", bresDoubleStore)
    print(f"Алгоритм Брезенхема с устранением ступенчатости:", bresReduceStore)
    print(f"Алгоритм Ву:", wuStore)

    plt.show()
