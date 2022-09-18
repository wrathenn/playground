import time
from tkinter import *
from tkinter.ttk import Combobox, Style
from tkinter.messagebox import showerror
from drawer import Drawer
from draw_backend import *
from efficiency import *


class App:
    def __init__(self):
        self.window = Tk()
        self.window.title("Алгоритмы построения отрезков")
        self.window.attributes("-zoomed", True)

        style = Style()  # If you dont have a class, put your root in the()
        style.configure('TCombobox', arrowsize=30)
        style.configure('Vertical.TScrollbar', arrowsize=28)

        self.initGUI()
        self.canvas.clear()
        self.window.mainloop()

    def initGUI(self):
        self.plotFrame = Frame(self.window)
        self.canvas = Drawer(self.plotFrame, bg="white")
        self.canvas.pack(fill=BOTH, expand=True)
        self.plotFrame.place(relx=0.3, rely=0, relwidth=0.7, relheight=1)

        self.paramsFrame = Frame(self.window)
        self.paramsFrame.place(relx=0, rely=0, relwidth=0.3, relheight=1)

        self.drawParamsFrame = LabelFrame(self.paramsFrame, text="Параметры")
        self.drawParamsFrame.place(relx=0, rely=0, relwidth=1, relheight=0.875)

        self.drawLinesFrame = LabelFrame(self.drawParamsFrame, text="Построение отрезков")
        self.drawLinesFrame.place(relx=0, rely=0, relwidth=1, relheight=0.3)

        self.drawLineAlgLabel = Label(self.drawLinesFrame, text="Алгоритм:")
        self.drawLineAlgLabel.place(relx=0, rely=0, relwidth=0.15)
        self.drawLineAlgComboBox = Combobox(
            self.drawLinesFrame,
            values=[
                "Цифровой дифференциальный анализатор",
                "Целочисленный алгоритм Брезенхема",
                "Вещественный алгоритм Брезенхема",
                "Алгоритм Брезенхема с устранением ступенчатости",
                "Алгоритм Ву",
                "Библиотечная функция"
            ],
            state="readonly"
        )
        self.drawLineAlgComboBox.current(0)
        self.drawLineAlgComboBox.place(relx=0.17, rely=0, relwidth=0.8)

        self.drawLineX1Label = Label(self.drawLinesFrame, text="X1:")
        self.drawLineY1Label = Label(self.drawLinesFrame, text="Y1:")
        self.drawLineX2Label = Label(self.drawLinesFrame, text="X2:")
        self.drawLineY2Label = Label(self.drawLinesFrame, text="Y2:")

        self.drawLineX1Entry = Entry(self.drawLinesFrame)
        self.drawLineY1Entry = Entry(self.drawLinesFrame)
        self.drawLineX2Entry = Entry(self.drawLinesFrame)
        self.drawLineY2Entry = Entry(self.drawLinesFrame)

        self.drawLineX1Label.place(relx=0, rely=0.2, relwidth=0.1)
        self.drawLineX1Entry.place(relx=0.1, rely=0.2, relwidth=0.15)

        self.drawLineY1Label.place(relx=0.25, rely=0.2, relwidth=0.1)
        self.drawLineY1Entry.place(relx=0.35, rely=0.2, relwidth=0.15)

        self.drawLineX2Label.place(relx=0.5, rely=0.2, relwidth=0.1)
        self.drawLineX2Entry.place(relx=0.6, rely=0.2, relwidth=0.15)

        self.drawLineY2Label.place(relx=0.75, rely=0.2, relwidth=0.1)
        self.drawLineY2Entry.place(relx=0.85, rely=0.2, relwidth=0.15)

        self.colors = {
            "Черный": "#000000",
            "Белый": "#FFFFFF",
            "Красный": "#FF0000",
            "Зеленый": "#00FF00",
            "Синий": "#0000FF"
        }
        self.drawLineColorComboBox = Combobox(
            self.drawLinesFrame,
            values=list(self.colors.keys()),
            state="readonly"
        )
        self.drawLineColorComboBox.bind("<<ComboboxSelected>>", lambda evt: self.changeLineColor())
        self.drawLineColorComboBox.current(0)
        self.drawLineColorComboBox.place(relx=0.05, rely=0.35, relwidth=0.5)
        self.colorOfLine = Label(self.drawLinesFrame, bg="black")
        self.colorOfLine.place(relx=0.60, rely=0.35, relwidth=0.3)

        self.clearLineButton = Button(
            self.drawLinesFrame,
            text="Стереть",
            command=self.canvas.clear)
        self.clearLineButton.place(relx=0.02, rely=0.67, relwidth=0.45, relheight=0.28)

        self.drawLineDrawButton = Button(
            self.drawLinesFrame,
            text="Построить",
            command=self.drawLine)

        self.drawLineDrawButton.place(relx=0.52, rely=0.67, relwidth=0.45, relheight=0.28)

        self.profilingParams = LabelFrame(self.paramsFrame, text="Исследование характеристик")
        self.profilingParams.place(relx=0, rely=0.875, relwidth=1, relheight=0.125)

        self.compareTimeButton = Button(self.profilingParams, text="Сравнить эффективность",
                                        command=compareTime)
        self.compareTimeButton.place(relx=0.02, rely=0.1, relwidth=0.45, relheight=0.8)
        self.compareStairsButton = Button(self.profilingParams, text="Сравнить ступенчатость",
                                          command=compareStairs)
        self.compareStairsButton.place(relx=0.52, rely=0.1, relwidth=0.45, relheight=0.8)

        ### ffffffffffffffffffffffffffffffffffffff ###

        self.drawSpectrumFrame = LabelFrame(self.drawParamsFrame, text="Построение спектра")
        self.drawSpectrumFrame.place(relx=0, rely=0.35, relwidth=1, relheight=0.3)

        self.drawSpectrumAlgLabel = Label(self.drawSpectrumFrame, text="Алгоритм:")
        self.drawSpectrumAlgLabel.place(relx=0, rely=0, relwidth=0.15)
        self.drawSpectrumAlgComboBox = Combobox(
            self.drawSpectrumFrame,
            values=[
                "Цифровой дифференциальный анализатор",
                "Целочисленный алгоритм Брезенхема",
                "Вещественный алгоритм Брезенхема",
                "Алгоритм Брезенхема с устранением ступенчатости",
                "Алгоритм Ву",
                "Библиотечная функция"
            ],
            state="readonly"
        )
        self.drawSpectrumAlgComboBox.current(0)
        self.drawSpectrumAlgComboBox.place(relx=0.17, rely=0, relwidth=0.8)

        self.drawSpectrumAngleLabel = Label(self.drawSpectrumFrame, text="Угол:")
        self.drawSpectrumLengthLabel = Label(self.drawSpectrumFrame, text="Длина:")

        self.drawSpectrumAngleEntry = Entry(self.drawSpectrumFrame)
        self.drawSpectrumLengthEntry = Entry(self.drawSpectrumFrame)

        self.drawSpectrumAngleLabel.place(relx=0, rely=0.2, relwidth=0.1)
        self.drawSpectrumAngleEntry.place(relx=0.1, rely=0.2, relwidth=0.15)

        self.drawSpectrumLengthLabel.place(relx=0.28, rely=0.2, relwidth=0.1)
        self.drawSpectrumLengthEntry.place(relx=0.38, rely=0.2, relwidth=0.15)

        self.drawSpectrumColorComboBox = Combobox(
            self.drawSpectrumFrame,
            values=list(self.colors.keys()),
            state="readonly"
        )
        self.drawSpectrumColorComboBox.bind("<<ComboboxSelected>>", lambda evt: self.changeSpectrumColor())
        self.drawSpectrumColorComboBox.current(0)
        self.drawSpectrumColorComboBox.place(relx=0.05, rely=0.35, relwidth=0.5)
        self.colorOfSpectrum = Label(self.drawSpectrumFrame, bg="black")
        self.colorOfSpectrum.place(relx=0.60, rely=0.35, relwidth=0.3)

        self.clearSpectrumButton = Button(
            self.drawSpectrumFrame,
            text="Стереть",
            command=self.canvas.clear)
        self.clearSpectrumButton.place(relx=0.02, rely=0.67, relwidth=0.45, relheight=0.28)

        self.drawSpectrumDrawButton = Button(
            self.drawSpectrumFrame,
            text="Построить",
            command=lambda: self.drawSpectrum())

        self.drawSpectrumDrawButton.place(relx=0.52, rely=0.67, relwidth=0.45, relheight=0.28)

    def drawLine(self):
        xStart: int
        xEnd: int
        yStart: int
        yEnd: int
        try:
            xStart = int(self.drawLineX1Entry.get())
        except ValueError:
            showerror("Ошибка!", "Некорректный x1")
            return
        try:
            xEnd = int(self.drawLineX2Entry.get())
        except ValueError:
            showerror("Ошибка!", "Некорректный x2")
            return
        try:
            yStart = int(self.drawLineY1Entry.get())
        except ValueError:
            showerror("Ошибка!", "Некорректный y1")
            return
        try:
            yEnd = int(self.drawLineY2Entry.get())
        except ValueError:
            showerror("Ошибка!", "Некорректный y2")
            return

        method = self.drawLineAlgComboBox.get()
        color = self.getLineColor()

        if method == "Цифровой дифференциальный анализатор":
            self.canvas.drawLine(digitalDiffAnalyzer(xStart, yStart, xEnd, yEnd), color)
        elif method == "Целочисленный алгоритм Брезенхема":
            self.canvas.drawLine(bresenhamInt(xStart, yStart, xEnd, yEnd), color)
        elif method == "Вещественный алгоритм Брезенхема":
            self.canvas.drawLine(bresenhamDouble(xStart, yStart, xEnd, yEnd), color)
        elif method == "Алгоритм Брезенхема с устранением ступенчатости":
            self.canvas.drawLineWithColor(bresenhamStairsReduce(xStart, yStart, xEnd, yEnd, color))
        elif method == "Алгоритм Ву":
            self.canvas.drawLineWithColor(wu(xStart, yStart, xEnd, yEnd, color))
        elif method == "Библиотечная функция":
            self.canvas.create_line(xStart, yStart, xEnd, yEnd, fill=color)

    def drawSpectrum(self):
        lineLen = 0
        angleStep = 0
        try:
            lineLen = int(self.drawSpectrumLengthEntry.get())
        except ValueError:
            showerror("Ошибка!", "Некорректная длина спектра")
            return
        if lineLen < 0:
            showerror("Ошибка!", "Некорректная длина спектра")
            return

        try:
            angleStep = int(self.drawSpectrumAngleEntry.get())
        except ValueError:
            showerror("Ошибка!", "Некорректный угол")
            return
        if angleStep > 360 or angleStep < 0:
            showerror("Ошибка!", "Некорректный угол")
            return

        width, height = self.getCanvasSize()
        xStart, yStart = self.decardToScreenCoordinates(0, 0, width, height)

        result = list()
        angle = 0
        angleStep = angleToRadians(angleStep)
        while angle < 2 * math.pi:
            xEnd, yEnd = lineLen * math.cos(angle), lineLen * math.sin(angle)
            xEnd, yEnd = self.decardToScreenCoordinates(xEnd, yEnd, width, height)

            result.append((xEnd, yEnd))
            angle += angleStep

        method = self.drawSpectrumAlgComboBox.get()
        color = self.getSpectrumColor()
        for xEnd, yEnd in result:
            if method == "Цифровой дифференциальный анализатор":
                self.canvas.drawLine(digitalDiffAnalyzer(xStart, yStart, xEnd, yEnd), color)
            elif method == "Целочисленный алгоритм Брезенхема":
                self.canvas.drawLine(bresenhamInt(xStart, yStart, xEnd, yEnd), color)
            elif method == "Вещественный алгоритм Брезенхема":
                self.canvas.drawLine(bresenhamDouble(xStart, yStart, xEnd, yEnd), color)
            elif method == "Алгоритм Брезенхема с устранением ступенчатости":
                self.canvas.drawLineWithColor(bresenhamStairsReduce(xStart, yStart, xEnd, yEnd, color))
            elif method == "Алгоритм Ву":
                self.canvas.drawLineWithColor(wu(xStart, yStart, xEnd, yEnd, color))
            elif method == "Библиотечная функция":
                self.canvas.create_line(xStart, yStart, xEnd, yEnd, fill=color)

    @staticmethod
    def decardToScreenCoordinates(x, y, width, height):
        return int(x + width / 2), int(height / 2 - y)

    def getCanvasSize(self):
        return self.canvas.winfo_width(), self.canvas.winfo_height()

    def changeLineColor(self):
        color = self.drawLineColorComboBox.get()
        if not color:
            return
        self.colorOfLine.configure(bg=self.colors[color])

    def getLineColor(self):
        return self.colors[self.drawLineColorComboBox.get()]

    def changeSpectrumColor(self):
        color = self.drawSpectrumColorComboBox.get()
        if not color:
            return
        self.colorOfSpectrum.configure(bg=self.colors[color])

    def getSpectrumColor(self):
        return self.colors[self.drawSpectrumColorComboBox.get()]

app = App()
