import time
from tkinter import *
from tkinter.ttk import Combobox, Style
from tkinter.messagebox import showerror
from drawer import Drawer
from draw_backend import *
from typing import List, Tuple


class App:
    def __init__(self):
        self.window = Tk()
        self.window.geometry("1920x1080")
        self.window.title("Построчный алгоритм заполнения с затравкой")
        self.window.attributes("-zoomed", True)

        style = Style()  # If you dont have a class, put your root in the()
        style.configure('TCombobox', arrowsize=30)
        style.configure('Vertical.TScrollbar', arrowsize=28)

        self.initGUI()
        self.canvas.clear()
        self.window.mainloop()

    def initGUICurve(self):
        self.drawCurveFrame = LabelFrame(self.drawParamsFrame, text="Заполнение")
        self.drawCurveFrame.place(relx=0, rely=0, relwidth=1, relheight=0.5)

        self.colors = {
            "Черный": "#000001",
            "Белый": "#FFFFFF",
            "Красный": "#FF0000",
            "Зеленый": "#00FF00",
            "Синий": "#0000FF"
        }

        self.drawBorderColorLabel = Label(self.drawCurveFrame, text="Границы:")
        self.drawBorderColorLabel.place(relx=0.05, rely=0.01, relwidth=0.2)
        self.drawBorderColorComboBox = Combobox(
            self.drawCurveFrame,
            values=list(self.colors.keys()),
            state="readonly"
        )
        self.drawBorderColorComboBox.bind("<<ComboboxSelected>>", lambda event: self.changeBorderColor())
        self.drawBorderColorComboBox.current(0)
        self.drawBorderColorComboBox.place(relx=0.30, rely=0.01, relwidth=0.3)
        self.colorOfBorder = Label(self.drawCurveFrame, bg="black")
        self.colorOfBorder.place(relx=0.65, rely=0.01, relwidth=0.3)

        self.drawCurveColorLabel = Label(self.drawCurveFrame, text="Заполнение:")
        self.drawCurveColorLabel.place(relx=0.05, rely=0.07, relwidth=0.2)
        self.drawCurveColorComboBox = Combobox(
            self.drawCurveFrame,
            values=list(self.colors.keys()),
            state="readonly"
        )
        self.drawCurveColorComboBox.bind("<<ComboboxSelected>>", lambda evt: self.changeCurveColor())
        self.drawCurveColorComboBox.current(0)
        self.drawCurveColorComboBox.place(relx=0.30, rely=0.07, relwidth=0.3)
        self.colorOfCurve = Label(self.drawCurveFrame, bg="black")
        self.colorOfCurve.place(relx=0.65, rely=0.07, relwidth=0.3)

        self.delayBool = BooleanVar()
        self.delayCheckButton = Checkbutton(self.drawCurveFrame, text="Включить задержку", variable=self.delayBool,
                                            onvalue=True, offvalue=False)
        self.delayCheckButton.place(relx=0.05, rely=0.15)

        self.dotLabel = Label(self.drawCurveFrame, text="Построить точку:")
        self.dotLabel.place(relx=0.05, rely=0.25)
        self.xLabel = Label(self.drawCurveFrame, text="X:")
        self.xLabel.place(relx=0.05, rely=0.35, relwidth=0.05)
        self.xEntry = Entry(self.drawCurveFrame)
        self.xEntry.place(relx=0.12, rely=0.35, relwidth=0.2)
        self.yLabel = Label(self.drawCurveFrame, text="Y:")
        self.yLabel.place(relx=0.35, rely=0.35, relwidth=0.05)
        self.yEntry = Entry(self.drawCurveFrame)
        self.yEntry.place(relx=0.42, rely=0.35, relwidth=0.2)

        self.dotButton = Button(self.drawCurveFrame, text="Добавить точку", bg="green", command=self.figureDotAdd)
        self.dotButton.place(relx=0.02, rely=0.42, relwidth=0.3, relheight=0.12)
        self.stopButton = Button(self.drawCurveFrame, text="Отменить фигуру", bg="orange", command=self.figureStopInput)
        self.stopButton.place(relx=0.35, rely=0.42, relwidth=0.3, relheight=0.12)
        self.endButton = Button(self.drawCurveFrame, text="Готово", bg="yellow", command=self.figureEndInput)
        self.endButton.place(relx=0.68, rely=0.42, relwidth=0.3, relheight=0.12)

        self.clearButton = Button(self.drawCurveFrame, text="Очистить", bg="red", command=self.clear)
        self.clearButton.place(relx=0.02, rely=0.60, relwidth=0.3, relheight=0.12)

        self.manualFrame = LabelFrame(self.drawParamsFrame, text="Управление")
        self.manualFrame.place(relx=0, rely=0.55, relwidth=1, relheight=0.4)
        self.manualLKMLabel = Label(self.manualFrame, text="ЛКМ - добавление новой точки")
        self.manualCtrlLKMLabel = Label(self.manualFrame, text="ctrl + ЛКМ - добавление новой точки (по горизонтали)")
        self.manualShiftLKMLabel = Label(self.manualFrame, text="ctrl + ЛКМ - добавление новой точки (по вертикали)")
        self.manualPKMLabel = Label(self.manualFrame, text="ПКМ - замкнуть фигуру")
        self.manualWheelLabel = Label(self.manualFrame, text="Колесико мыши - прервать построение фигуры")
        self.manualShiftPKMLabel = Label(self.manualFrame, text="shift + ПКМ - выполнить заливку в выбранной точке")
        self.manualLKMLabel.place(rely=0.05, relx=0.05, relwidth=0.9, relheight=0.07)
        self.manualCtrlLKMLabel.place(rely=0.15, relx=0.05, relwidth=0.9, relheight=0.07)
        self.manualShiftLKMLabel.place(rely=0.25, relx=0.05, relwidth=0.9, relheight=0.07)
        self.manualPKMLabel.place(rely=0.35, relx=0.05, relwidth=0.9, relheight=0.07)
        self.manualWheelLabel.place(rely=0.45, relx=0.05, relwidth=0.9, relheight=0.07)
        self.manualShiftPKMLabel.place(rely=0.55, relx=0.05, relwidth=0.9, relheight=0.07)

    def initGUI(self):
        self.plotFrame = Frame(self.window)
        self.canvas = Drawer(self.plotFrame, bg="white")
        self.canvas.pack(fill=BOTH, expand=True)
        self.plotFrame.place(x=500, y=0, width=1420, height=1080)

        self.paramsFrame = Frame(self.window)
        self.paramsFrame.place(relx=0, rely=0, width=500, height=1080)

        self.drawParamsFrame = LabelFrame(self.paramsFrame, text="Параметры")
        self.drawParamsFrame.place(relx=0, rely=0, relwidth=1, relheight=0.875)

        self.initGUICurve()
        self.initCanvasFunctions()

    def initCanvasFunctions(self):
        self.figureList: List[List[Tuple[int, int]]] = [[]]
        self.cur: int = 0

        self.canvas.bind("<Button-1>", lambda event: self.figureDotAdd(event))
        self.canvas.bind("<Button-3>", lambda event: self.figureEndInput())
        self.canvas.bind("<Button-2>", lambda event: self.figureStopInput())
        self.canvas.bind("<Control-Button-1>", lambda event: self.figureDotAdd(event, mode=1))
        self.canvas.bind("<Shift-Button-1>", lambda event: self.figureDotAdd(event, mode=2))
        self.canvas.bind("<Shift-Button-3>",
                         lambda event: self.canvas.fillFigure(event.x, event.y,
                                                              self.canvas.findMaxesMins(self.figureList),
                                                              fillColor=self.getCurveColor(),
                                                              borderColor=self.getBorderColor(),
                                                              isDelayed=self.delayBool.get()))

    # mode = 1 => horizontal(ctrl), mode = 2 => vertical(shift)
    def figureDotAdd(self, event=0, mode=0):
        if event:
            _x = event.x
            _y = event.y
        else:
            try:
                _x = int(self.xEntry.get())
            except ValueError:
                showerror("Ошибка!", "Некорректный X")
                return
            try:
                _y = int(self.yEntry.get())
            except ValueError:
                showerror("Ошибка!", "Некорректный X")
                return

        tmpLen: int = len(self.figureList[self.cur])
        if tmpLen != 0:
            if mode == 1:
                _y = self.figureList[self.cur][tmpLen - 1][1]
            elif mode == 2:
                _x = self.figureList[self.cur][tmpLen - 1][0]

        self.figureList[self.cur].append((_x, _y))
        self.canvas.img.put(self.getBorderColor(), (_x, _y))

        tmpLen = len(self.figureList[self.cur])
        if tmpLen > 1:
            self.canvas.imgDrawLine(self.figureList[self.cur][tmpLen - 2][0],
                                    self.figureList[self.cur][tmpLen - 2][1],
                                    self.figureList[self.cur][tmpLen - 1][0],
                                    self.figureList[self.cur][tmpLen - 1][1],
                                    self.getBorderColor())

        self.canvas.redraw()

    def figureEndInput(self):
        tmpLen: int = len(self.figureList[self.cur])
        if tmpLen < 3:
            showerror("Ошибка!", "Нужно отметить как минимум 3 точки")
            return

        self.canvas.imgDrawLine(self.figureList[self.cur][0][0],
                                self.figureList[self.cur][0][1],
                                self.figureList[self.cur][tmpLen - 1][0],
                                self.figureList[self.cur][tmpLen - 1][1],
                                self.getBorderColor())

        self.cur += 1
        self.figureList.append([])
        self.canvas.redraw()

    def figureStopInput(self):
        self.figureList.pop()

        def redrawFigures():
            self.canvas.clear()
            figure: List[Tuple[int, int]]
            for figure in self.figureList:
                for i in range(len(figure) - 1):
                    self.canvas.imgDrawLine(figure[i][0],
                                            figure[i][1],
                                            figure[i + 1][0],
                                            figure[i + 1][1],
                                            self.getBorderColor())
                self.canvas.imgDrawLine(figure[0][0],
                                        figure[0][1],
                                        figure[len(figure) - 1][0],
                                        figure[len(figure) - 1][1],
                                        self.getBorderColor())

        redrawFigures()
        self.figureList.append([])
        self.canvas.redraw()

    def clear(self):
        self.figureList = [[]]
        self.cur = 0
        self.canvas.clear()

    @staticmethod
    def decardToScreenCoordinates(x, y, width, height):
        return int(x + width / 2), int(height / 2 - y)

    def changeCurveColor(self):
        color = self.drawCurveColorComboBox.get()
        if not color:
            return
        self.colorOfCurve.configure(bg=self.colors[color])

    def changeBorderColor(self):
        color = self.drawBorderColorComboBox.get()
        if not color:
            return
        self.colorOfBorder.configure(bg=self.colors[color])

    def getCanvasSize(self):
        return self.canvas.winfo_width(), self.canvas.winfo_height()

    def getCurveColor(self):
        return self.colors[self.drawCurveColorComboBox.get()]

    def getBorderColor(self):
        return self.colors[self.drawBorderColorComboBox.get()]


app = App()
