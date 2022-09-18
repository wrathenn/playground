from tkinter import *

class Drawer(Canvas):

    def drawLine(self, points, color="black"):
        self.offsetX = 15
        self.offsetY = 15
        self.offsetAxes = 15
        width, height = self.getCanvasSize()
        for (x, y) in points:
            if not(x < 0 or x > width or y < 0 or y > height):
                self.create_oval(x + self.offsetX, y + self.offsetY, x + self.offsetX, y + self.offsetY, outline=color, width=1)
        # print(points)

    def drawLineWithColor(self, pointsWithColor):
        self.offsetX = 15
        self.offsetY = 15
        self.offsetAxes = 15
        width, height = self.getCanvasSize()
        for (x, y, color) in pointsWithColor:
            if not(x < 0 or x > width or y < 0 or y > height):
                self.create_oval(x + self.offsetX, y + self.offsetY, x + self.offsetX, y + self.offsetY, outline=color, width=1)
        # print(pointsWithColor)


    def clear(self):
        self.offsetX = 15
        self.offsetY = 15
        self.offsetAxes = 15
        self.delete("all")
        self.drawAxes()

    def getCanvasSize(self):
        self.offsetX = 15
        self.offsetY = 15
        self.offsetAxes = 15
        return self.winfo_width(), self.winfo_height()

    def drawAxes(self):
        self.offsetX = 15
        self.offsetY = 15
        self.offsetAxes = 15
        width = self.winfo_width()
        height = self.winfo_height()
        width = 1350 if width == 1 else width
        height = 1000

        self.create_line(self.offsetX, self.offsetY,
                                width - self.offsetX, self.offsetY,
                                arrow=LAST)

        self.create_line(self.offsetX, self.offsetY,
                                self.offsetX, height - self.offsetY,
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
