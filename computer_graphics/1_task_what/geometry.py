from math import sqrt
from typing import List, Tuple

FLOAT_ERROR_RATE: float = 1e-6


def floatCompare(a: float, b:float):
    if abs(a - b) < FLOAT_ERROR_RATE:
        return 1;
    else:
        return 0;


class Dot:
    def __init__(self, x: float = 0, y: float = 0):
        self.x: float = x
        self.y: float = y

    def setCords(self, x: float, y: float):
        self.x = x
        self.y = y

    def getTupleCords(dot):
        return dot.x, dot.y


def dotDistance(dot1: Dot, dot2: Dot) -> float:
    return sqrt((dot1.x - dot2.x) ** 2 + (dot1.y - dot2.y) ** 2)

# For tuple/list dotTypes
def dotDistance2(dot1, dot2) -> float:
    return sqrt((dot1[0] - dot2[0]) ** 2 + (dot1[1] - dot2[1]) ** 2)


class GeometryObject:
    def __init__(self, dotList: List[Dot]):
        self.cords: List[Dot] = dotList

    def getCords(self) -> List[Dot]:
        return self.cords


class Rectangle(GeometryObject):
    # Проверить по массиву из 4 точек, что они образуют прямоугольник
    def check(dotList: List[Dot]):
        # Проверка - попарно равные отрезки
        distList: List[float] = list(map(dotDistance, dotList, [*dotList[1::], dotList[0]]))
        if not floatCompare(distList[0], distList[2]) or not floatCompare(distList[1], distList[3]):
            return False

        # Проверка - ни один отрезок не должен быть = 0
        for length in distList:
            if floatCompare(length, 0.0):
                return False

        # Проверка - угол 90 градусов
        hypotenuse: float = dotDistance(dotList[0], dotList[2])
        if abs(distList[0] ** 2 + distList[1] ** 2 - hypotenuse ** 2) > FLOAT_ERROR_RATE:
            return False

        return True

    def __init__(self, *dots):
        if Rectangle.check([*dots]):
            super().__init__([*dots])
            # print("Корректный прямоугольник")
        else:
            raise Exception("Некорректные точки")
