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
        self.window.attributes("-zoomed", True)

        style = Style()  # If you dont have a class, put your root in the()
        style.configure('TCombobox', arrowsize=30)
        style.configure('Vertical.TScrollbar', arrowsize=28)

        self.initGUI()
        self.canvas.clear()
        self.window.mainloop()

    def initGUICurve(self):
        self.drawCurveFrame = LabelFrame(self.drawParamsFrame, text="Построение кривой")
        self.drawCurveFrame.place(relx=0, rely=0, relwidth=1, relheight=0.5)

        self.drawCurveAlgLabel = Label(self.drawCurveFrame, text="Алгоритм:")
        self.drawCurveAlgLabel.place(relx=0, rely=0, relwidth=0.15, relheight=0.05)
        self.drawCurveAlgComboBox = Combobox(
            self.drawCurveFrame,
            values=[
                "Каноническое уравнение",
                "Параметрическое уравнение",
                "Алгоритм Брезенхема",
                "Алгоритм средней точки",
                "Библиотечная функция"
            ],
            state="readonly"
        )
        self.drawCurveAlgComboBox.current(0)
        self.drawCurveAlgComboBox.place(relx=0.17, rely=0, relwidth=0.8, relheight=0.05)

        self.colors = {
            "Черный": "#000000",
            "Белый": "#FFFFFF",
            "Красный": "#FF0000",
            "Зеленый": "#00FF00",
            "Синий": "#0000FF"
        }
        self.drawCurveColorComboBox = Combobox(
            self.drawCurveFrame,
            values=list(self.colors.keys()),
            state="readonly"
        )
        self.drawCurveColorComboBox.bind("<<ComboboxSelected>>", lambda evt: self.changeCurveColor())
        self.drawCurveColorComboBox.current(0)
        self.drawCurveColorComboBox.place(relx=0.05, rely=0.07, relwidth=0.5)
        self.colorOfCurve = Label(self.drawCurveFrame, bg="black")
        self.colorOfCurve.place(relx=0.60, rely=0.07, relwidth=0.3)

    def initGUICircle(self):
        self.drawCircleFrame = LabelFrame(self.drawCurveFrame, text="Окружность")
        self.drawCircleFrame.place(relx=0, rely=0.14, relwidth=1, relheight=0.38)

        self.drawCircleXLabel = Label(self.drawCircleFrame, text="X центр:")
        self.drawCircleXEntry = Entry(self.drawCircleFrame)
        self.drawCircleXLabel.place(relx=0.02, rely=0.05, relwidth=0.1, relheight=0.15)
        self.drawCircleXEntry.place(relx=0.14, rely=0.05, relwidth=0.15, relheight=0.15)

        self.drawCircleYLabel = Label(self.drawCircleFrame, text="Y центр:")
        self.drawCircleYEntry = Entry(self.drawCircleFrame)
        self.drawCircleYLabel.place(relx=0.02, rely=0.22, relwidth=0.1, relheight=0.15)
        self.drawCircleYEntry.place(relx=0.14, rely=0.22, relwidth=0.15, relheight=0.15)

        self.drawCircleRLabel = Label(self.drawCircleFrame, text="Радиус:")
        self.drawCircleREntry = Entry(self.drawCircleFrame)
        self.drawCircleRLabel.place(relx=0.31, rely=0.05, relwidth=0.1, relheight=0.15)
        self.drawCircleREntry.place(relx=0.43, rely=0.05, relwidth=0.15, relheight=0.15)

        self.drawCircleDrawButton = Button(self.drawCircleFrame, text="Построить", bg="green", fg="white",
                                           command=self.drawCircle)
        self.drawCircleDrawButton.place(relx=0.02, rely=0.5, relwidth=0.4, relheight=0.4)

        self.drawCircleClearButton = Button(self.drawCircleFrame, text="Очистить", bg="orange",
                                            command=self.canvas.clear)
        self.drawCircleClearButton.place(relx=0.5, rely=0.5, relwidth=0.4, relheight=0.4)

    def initGUIEllipse(self):
        self.drawEllipseFrame = LabelFrame(self.drawCurveFrame, text="Эллипс")
        self.drawEllipseFrame.place(relx=0, rely=0.55, relwidth=1, relheight=0.38)

        self.drawEllipseXLabel = Label(self.drawEllipseFrame, text="X центр:")
        self.drawEllipseXEntry = Entry(self.drawEllipseFrame)
        self.drawEllipseXLabel.place(relx=0.02, rely=0.05, relwidth=0.1, relheight=0.15)
        self.drawEllipseXEntry.place(relx=0.14, rely=0.05, relwidth=0.15, relheight=0.15)

        self.drawEllipseYLabel = Label(self.drawEllipseFrame, text="Y центр:")
        self.drawEllipseYEntry = Entry(self.drawEllipseFrame)
        self.drawEllipseYLabel.place(relx=0.02, rely=0.22, relwidth=0.1, relheight=0.15)
        self.drawEllipseYEntry.place(relx=0.14, rely=0.22, relwidth=0.15, relheight=0.15)

        self.drawEllipseRWLabel = Label(self.drawEllipseFrame, text="Гориз. R:")
        self.drawEllipseRWEntry = Entry(self.drawEllipseFrame)
        self.drawEllipseRWLabel.place(relx=0.31, rely=0.05, relwidth=0.1, relheight=0.15)
        self.drawEllipseRWEntry.place(relx=0.43, rely=0.05, relwidth=0.15, relheight=0.15)

        self.drawEllipseRHLabel = Label(self.drawEllipseFrame, text="Верт. R:")
        self.drawEllipseRHEntry = Entry(self.drawEllipseFrame)
        self.drawEllipseRHLabel.place(relx=0.31, rely=0.22, relwidth=0.1, relheight=0.15)
        self.drawEllipseRHEntry.place(relx=0.43, rely=0.22, relwidth=0.15, relheight=0.15)

        self.drawEllipseDrawButton = Button(self.drawEllipseFrame, text="Построить", bg="green", fg="white",
                                            command=self.drawEllipse)
        self.drawEllipseDrawButton.place(relx=0.02, rely=0.5, relwidth=0.4, relheight=0.4)

        self.drawEllipseClearButton = Button(self.drawEllipseFrame, text="Очистить", bg="orange",
                                             command=self.canvas.clear)
        self.drawEllipseClearButton.place(relx=0.5, rely=0.5, relwidth=0.4, relheight=0.4)

    def initGUISpectrum(self):
        self.drawSpectrumFrame = LabelFrame(self.drawParamsFrame, text="Построение спектра")
        self.drawSpectrumFrame.place(relx=0, rely=0.5, relwidth=1, relheight=0.5)

        self.drawSpectrumAlgLabel = Label(self.drawSpectrumFrame, text="Алгоритм:")
        self.drawSpectrumAlgLabel.place(relx=0, rely=0, relwidth=0.15, relheight=0.05)
        self.drawSpectrumAlgComboBox = Combobox(
            self.drawSpectrumFrame,
            values=[
                "Каноническое уравнение",
                "Параметрическое уравнение",
                "Алгоритм Брезенхема",
                "Алгоритм средней точки",
                "Библиотечная функция"
            ],
            state="readonly"
        )
        self.drawSpectrumAlgComboBox.current(0)
        self.drawSpectrumAlgComboBox.place(relx=0.17, rely=0, relwidth=0.8, relheight=0.05)

        self.colors = {
            "Черный": "#000000",
            "Белый": "#FFFFFF",
            "Красный": "#FF0000",
            "Зеленый": "#00FF00",
            "Синий": "#0000FF"
        }
        self.drawSpectrumColorComboBox = Combobox(
            self.drawSpectrumFrame,
            values=list(self.colors.keys()),
            state="readonly"
        )
        self.drawSpectrumColorComboBox.bind("<<ComboboxSelected>>", lambda evt: self.changeSpectrumColor())
        self.drawSpectrumColorComboBox.current(0)
        self.drawSpectrumColorComboBox.place(relx=0.05, rely=0.07, relwidth=0.5)
        self.colorOfSpectrum = Label(self.drawSpectrumFrame, bg="black")
        self.colorOfSpectrum.place(relx=0.60, rely=0.07, relwidth=0.3)

    def initGUISpectrumCircle(self):
        self.drawSpectrumCircleFrame = LabelFrame(self.drawSpectrumFrame, text="Окружность")
        self.drawSpectrumCircleFrame.place(relx=0, rely=0.14, relwidth=1, relheight=0.38)

        self.drawSpectrumCircleRminLabel = Label(self.drawSpectrumCircleFrame, text="R мин:")
        self.drawSpectrumCircleRminEntry = Entry(self.drawSpectrumCircleFrame)
        self.drawSpectrumCircleRminLabel.place(relx=0.02, rely=0.05, relwidth=0.1, relheight=0.15)
        self.drawSpectrumCircleRminEntry.place(relx=0.14, rely=0.05, relwidth=0.15, relheight=0.15)

        self.drawSpectrumCircleRmaxLabel = Label(self.drawSpectrumCircleFrame, text="R макс:")
        self.drawSpectrumCircleRmaxEntry = Entry(self.drawSpectrumCircleFrame)
        self.drawSpectrumCircleRmaxLabel.place(relx=0.02, rely=0.22, relwidth=0.1, relheight=0.15)
        self.drawSpectrumCircleRmaxEntry.place(relx=0.14, rely=0.22, relwidth=0.15, relheight=0.15)

        self.drawSpectrumCircleStepLabel = Label(self.drawSpectrumCircleFrame, text="Шаг:")
        self.drawSpectrumCircleStepEntry = Entry(self.drawSpectrumCircleFrame)
        self.drawSpectrumCircleStepLabel.place(relx=0.31, rely=0.05, relwidth=0.1, relheight=0.15)
        self.drawSpectrumCircleStepEntry.place(relx=0.43, rely=0.05, relwidth=0.15, relheight=0.15)

        self.drawSpectrumCircleAmountLabel = Label(self.drawSpectrumCircleFrame, text="Кол-во:")
        self.drawSpectrumCircleAmountEntry = Entry(self.drawSpectrumCircleFrame)
        self.drawSpectrumCircleAmountLabel.place(relx=0.31, rely=0.22, relwidth=0.1, relheight=0.15)
        self.drawSpectrumCircleAmountEntry.place(relx=0.43, rely=0.22, relwidth=0.15, relheight=0.15)

        self.drawSpectrumCircleButton = Button(self.drawSpectrumCircleFrame, text="Построить", bg="green", fg="white",
                                               command=self.drawCircleSpectrum)
        self.drawSpectrumCircleButton.place(relx=0.02, rely=0.5, relwidth=0.4, relheight=0.4)

        self.drawSpectrumCircleClearButton = Button(self.drawSpectrumCircleFrame, text="Очистить", bg="orange",
                                                    command=self.canvas.clear)
        self.drawSpectrumCircleClearButton.place(relx=0.5, rely=0.5, relwidth=0.4, relheight=0.4)

    def initGUISpectrumEllipse(self):
        self.drawSpectrumEllipseFrame = LabelFrame(self.drawSpectrumFrame, text="Эллипс")
        self.drawSpectrumEllipseFrame.place(relx=0, rely=0.55, relwidth=1, relheight=0.38)

        self.drawSpectrumEllipseRWminLabel = Label(self.drawSpectrumEllipseFrame, text="R_ мин.:")
        self.drawSpectrumEllipseRWminEntry = Entry(self.drawSpectrumEllipseFrame)
        self.drawSpectrumEllipseRWminLabel.place(relx=0.02, rely=0.05, relwidth=0.11, relheight=0.15)
        self.drawSpectrumEllipseRWminEntry.place(relx=0.14, rely=0.05, relwidth=0.15, relheight=0.15)

        self.drawSpectrumEllipseRWmaxLabel = Label(self.drawSpectrumEllipseFrame, text="R_ макс.:")
        self.drawSpectrumEllipseRWmaxEntry = Entry(self.drawSpectrumEllipseFrame)
        self.drawSpectrumEllipseRWmaxLabel.place(relx=0.31, rely=0.05, relwidth=0.11, relheight=0.15)
        self.drawSpectrumEllipseRWmaxEntry.place(relx=0.43, rely=0.05, relwidth=0.15, relheight=0.15)

        self.drawSpectrumEllipseRHmaxLabel = Label(self.drawSpectrumEllipseFrame, text="R | макс.:")
        self.drawSpectrumEllipseRHmaxEntry = Entry(self.drawSpectrumEllipseFrame)
        self.drawSpectrumEllipseRHmaxLabel.place(relx=0.60, rely=0.05, relwidth=0.11, relheight=0.15)
        self.drawSpectrumEllipseRHmaxEntry.place(relx=0.72, rely=0.05, relwidth=0.15, relheight=0.15)

        self.drawSpectrumEllipseAmountLabel = Label(self.drawSpectrumEllipseFrame, text="Кол-во:")
        self.drawSpectrumEllipseAmountEntry = Entry(self.drawSpectrumEllipseFrame)
        self.drawSpectrumEllipseAmountLabel.place(relx=0.31, rely=0.22, relwidth=0.11, relheight=0.15)
        self.drawSpectrumEllipseAmountEntry.place(relx=0.43, rely=0.22, relwidth=0.15, relheight=0.15)

        self.drawSpectrumEllipseButton = Button(self.drawSpectrumEllipseFrame, text="Построить", bg="green", fg="white",
                                                command=self.drawEllipseSpectrum)
        self.drawSpectrumEllipseButton.place(relx=0.02, rely=0.5, relwidth=0.4, relheight=0.4)

        self.drawSpectrumEllipseClearButton = Button(self.drawSpectrumEllipseFrame, text="Очистить", bg="orange",
                                                     command=self.canvas.clear)
        self.drawSpectrumEllipseClearButton.place(relx=0.5, rely=0.5, relwidth=0.4, relheight=0.4)

    def initGUIEfficiency(self):
        self.effFrame = LabelFrame(self.window, text="Эффективность по времени")
        self.effFrame.place(relx=0, rely=0.875, relwidth=0.3, relheight=0.125)

        self.effCircleButton = Button(self.effFrame, text="Окружности", bg="green", fg="white",
                                      command=compareCircleTime)
        self.effCircleButton.place(relx=0.1, rely=0.1, relwidth=0.3, relheight=0.8)
        self.effCircleButton = Button(self.effFrame, text="Эллипсы", bg="green", fg="white", command=compareEllipseTime)
        self.effCircleButton.place(relx=0.6, rely=0.1, relwidth=0.3, relheight=0.8)

    def initGUI(self):
        self.plotFrame = Frame(self.window)
        self.canvas = Drawer(self.plotFrame, bg="white")
        self.canvas.pack(fill=BOTH, expand=True)
        self.plotFrame.place(relx=0.3, rely=0, relwidth=0.7, relheight=1)

        self.paramsFrame = Frame(self.window)
        self.paramsFrame.place(relx=0, rely=0, relwidth=0.3, relheight=1)

        self.drawParamsFrame = LabelFrame(self.paramsFrame, text="Параметры")
        self.drawParamsFrame.place(relx=0, rely=0, relwidth=1, relheight=0.875)

        self.initGUICurve()
        self.initGUICircle()
        self.initGUIEllipse()
        self.initGUISpectrum()
        self.initGUISpectrumCircle()
        self.initGUISpectrumEllipse()
        self.initGUIEfficiency()

    def drawCircle(self):
        xCenter: int
        yCenter: int
        radius: int
        try:
            xCenter = int(self.drawCircleXEntry.get())
            if xCenter <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный X центра")
            return
        try:
            yCenter = int(self.drawCircleYEntry.get())
            if yCenter <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный Y центра")
            return
        try:
            radius = int(self.drawCircleREntry.get())
            if radius <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный радиус окружности")
            return

        method = self.drawCurveAlgComboBox.get()
        color = self.getCurveColor()

        if method == "Каноническое уравнение":
            self.canvas.drawLine(drawCircleNormal(xCenter, yCenter, radius), color)
        elif method == "Параметрическое уравнение":
            self.canvas.drawLine(drawCircleParameter(xCenter, yCenter, radius), color)
        elif method == "Алгоритм Брезенхема":
            self.canvas.drawLine(drawCircleBresenham(xCenter, yCenter, radius), color)
        elif method == "Алгоритм средней точки":
            self.canvas.drawLine(drawCircleMiddlePoint(xCenter, yCenter, radius), color)
        elif method == "Библиотечная функция":
            self.canvas.create_oval(xCenter - radius + self.canvas.offsetX, yCenter - radius + self.canvas.offsetY,
                                    xCenter + radius + self.canvas.offsetX, yCenter + radius + self.canvas.offsetY,
                                    outline=color)

    def drawEllipse(self):
        xCenter: int
        yCenter: int
        wRadius: int
        hRadius: int
        try:
            xCenter = int(self.drawEllipseXEntry.get())
            if xCenter <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный X центра")
            return
        try:
            yCenter = int(self.drawEllipseYEntry.get())
            if yCenter <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный Y центра")
            return
        try:
            hRadius = int(self.drawEllipseRHEntry.get())
            if hRadius <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный вертикальный радиус")
            return
        try:
            wRadius = int(self.drawEllipseRWEntry.get())
            if wRadius <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный горизонтальный радиус")
            return

        method = self.drawCurveAlgComboBox.get()
        color = self.getCurveColor()

        if method == "Каноническое уравнение":
            self.canvas.drawLine(drawEllipseNormal(xCenter, yCenter, wRadius, hRadius), color)
        elif method == "Параметрическое уравнение":
            self.canvas.drawLine(drawEllipseParameter(xCenter, yCenter, wRadius, hRadius), color)
        elif method == "Алгоритм Брезенхема":
            self.canvas.drawLine(drawEllipseBresenham(xCenter, yCenter, wRadius, hRadius), color)
        elif method == "Алгоритм средней точки":
            self.canvas.drawLine(drawEllipseMiddlePoint(xCenter, yCenter, wRadius, hRadius), color)
        elif method == "Библиотечная функция":
            self.canvas.create_oval(xCenter - hRadius + self.canvas.offsetX, yCenter - wRadius + self.canvas.offsetY,
                                    xCenter + hRadius + self.canvas.offsetX, yCenter + wRadius + self.canvas.offsetY,
                                    outline=color)

    def drawCircleSpectrum(self):
        xCenter, yCenter = self.getCanvasSize()
        xCenter = round(xCenter / 2)
        yCenter = round(yCenter / 2)
        rMin: int
        rMax: int
        step: int
        amount: int
        try:
            rMin = int(self.drawSpectrumCircleRminEntry.get())
            if rMin <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный минимальный радиус")
            return
        try:
            rMax = int(self.drawSpectrumCircleRmaxEntry.get())
            if rMax <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный максимальный радиус")
            return
        try:
            step = self.drawSpectrumCircleStepEntry.get()
            step = 0 if step == '' else int(step)
            if step < 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный шаг")
            return
        try:
            amount = self.drawSpectrumCircleAmountEntry.get()
            amount = 0 if amount == '' else int(amount)
            if amount < 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректное количество окружностей")
            return

        if amount != 0 and step != 0:
            showerror("Ошибка!", "Либо шаг, либо кол-во должно быть 0 (или не заполнено)")
            return

        if step == 0:
            step = round((rMax - rMin) / amount)

        method = self.drawSpectrumAlgComboBox.get()
        color = self.getSpectrumColor()

        methodFunction = drawCircleNormal
        if method == "Каноническое уравнение":
            methodFunction = drawCircleNormal
        elif method == "Параметрическое уравнение":
            methodFunction = drawCircleParameter
        elif method == "Алгоритм Брезенхема":
            methodFunction = drawCircleBresenham
        elif method == "Алгоритм средней точки":
            methodFunction = drawCircleMiddlePoint
        elif method == "Библиотечная функция":
            for radius in range(rMin, rMax, step):
                self.canvas.create_oval(xCenter - radius + self.canvas.offsetX, yCenter - radius + self.canvas.offsetY,
                                        xCenter + radius + self.canvas.offsetX, yCenter + radius + self.canvas.offsetY,
                                        outline=color)

        if method != "Библиотечная функция":
            for radius in range(rMin, rMax, step):
                self.canvas.drawLine(methodFunction(xCenter, yCenter, radius), color)

    def drawEllipseSpectrum(self):
        xCenter, yCenter = self.getCanvasSize()
        xCenter = round(xCenter / 2)
        yCenter = round(yCenter / 2)
        rwMin: int
        rwMax: int
        rhMin: int
        rhMax: int
        step: int
        amount: int
        try:
            rwMin = int(self.drawSpectrumEllipseRWminEntry.get())
            if rwMin <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный минимальный радиус по горизонтали")
            return
        try:
            rwMax = int(self.drawSpectrumEllipseRWmaxEntry.get())
            if rwMax <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный максимальный радиус по горизонтали")
            return
        try:
            rhMax = int(self.drawSpectrumEllipseRHmaxEntry.get())
            if rhMax <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректный максимальный радиус по вертикали")
            return
        try:
            amount = int(self.drawSpectrumEllipseAmountEntry.get())
            if amount <= 0:
                raise ValueError
        except ValueError:
            showerror("Ошибка!", "Некорректное количество эллипсов")
            return

        rhMin = round(rhMax * rwMin / rwMax)

        method = self.drawSpectrumAlgComboBox.get()
        color = self.getSpectrumColor()
        rhStep = round((rhMax - rhMin) / amount)
        rwStep = round((rwMax - rwMin) / amount)

        methodFunction = drawEllipseNormal
        if method == "Каноническое уравнение":
            methodFunction = drawEllipseNormal
        elif method == "Параметрическое уравнение":
            methodFunction = drawEllipseParameter
        elif method == "Алгоритм Брезенхема":
            methodFunction = drawEllipseBresenham
        elif method == "Алгоритм средней точки":
            methodFunction = drawEllipseMiddlePoint
        elif method == "Библиотечная функция":
            for _ in range(amount):
                self.canvas.create_oval(xCenter - rwMin + self.canvas.offsetX, yCenter - rhMin + self.canvas.offsetY,
                                        xCenter + rwMin + self.canvas.offsetX, yCenter + rhMin + self.canvas.offsetY,
                                        outline=color)
                rhMin += rhStep
                rwMin += rwStep

        if method != "Библиотечная функция":
            for _ in range(amount):
                self.canvas.drawLine(methodFunction(xCenter, yCenter, rwMin, rhMin), color)
                rhMin += rhStep
                rwMin += rwStep

    @staticmethod
    def decardToScreenCoordinates(x, y, width, height):
        return int(x + width / 2), int(height / 2 - y)

    def changeCurveColor(self):
        color = self.drawCurveColorComboBox.get()
        if not color:
            return
        self.colorOfCurve.configure(bg=self.colors[color])

    def changeSpectrumColor(self):
        color = self.drawSpectrumColorComboBox.get()
        if not color:
            return
        self.colorOfSpectrum.configure(bg=self.colors[color])

    def getCanvasSize(self):
        return self.canvas.winfo_width(), self.canvas.winfo_height()

    def getCurveColor(self):
        return self.colors[self.drawCurveColorComboBox.get()]

    def getSpectrumColor(self):
        return self.colors[self.drawSpectrumColorComboBox.get()]


app = App()
