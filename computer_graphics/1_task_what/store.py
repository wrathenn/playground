from typing import List

from geometry import Rectangle, Dot


class Store:
    def __init__(self, data=None):
        if data is None:
            data = []
        self.data = data

    def delete(self, index, data) -> any:
        return

    def getDataList(self):
        return


# List[Rectangle] ~ [Rectangle]
class RectangleStore(Store):
    def __init__(self, data=None):
        super().__init__(data)

    def change(self, rectangle: Rectangle) -> None:
        self.data = [rectangle]

    def getDataList(self):
        result = []
        for i in self.data:
            i: Rectangle
            temp = []

            for k in i.cords:
                k: Dot
                temp.append(k.x)
                temp.append(k.y)

            result.append(temp)
        return result

    def clear(self):
        self.data = []


# Будет такой: [[index=0, dot], [index=1, dot]...]
class DotStore(Store):
    def __init__(self, data=None):
        super().__init__(data)

    def add(self, new: Dot):
        self.data.append([self.getNewIndex(), new])

    def find(self, index, data: List[float]) -> int:
        # for dataPart in self.data:
        #     dot: Dot = dataPart[1:2]
        #     dotIndex = dataPart[0]
        #     if data == [dot.x, dot.y] and index == dotIndex:
        #         return index - 1
        return -1

    def getDataList(self):
        result = []
        for dot, i in zip(self.data, range(len(self.data))):
            trueDot = dot[1]
            result.append([i + 1, trueDot.x, trueDot.y])

        return result

    def delete(self, index, data: List[float]):
        self.data.pop(index - 1)
        for dataPart, i in zip(self.data, range(len(self.data))):
            dataPart[0] = i + 1

    def getNewIndex(self):
        return len(self.data) + 1


rectangleStore = RectangleStore()
dotM2Store = DotStore()
