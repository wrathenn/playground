import math


def angleToRadians(angle: int) -> float:
    return angle / 180 * math.pi


def _intPart(a):
    return int(a)


def _floatPart(a):
    return a - int(a)


def _reverseFloatPart(a):
    return 1 - _floatPart(a)


def _applyIntensity(color: str, intensity: float) -> str:
    red: float = 255 - (255 - int(color[1:3], 16)) * intensity
    green: float = 255 - (255 - int(color[3:5], 16)) * intensity
    blue: float = 255 - (255 - int(color[5:7], 16)) * intensity
    red: str = f"{int(abs(red)):02X}"
    green: str = f"{int(abs(green)):02X}"
    blue: str = f"{int(abs(blue)):02X}"
    return "#" + red + green + blue


def _sign(value):
    EPS = 1e-10

    if value > 0:
        return 1
    elif abs(value) < EPS:
        return 0
    else:
        return -1


def digitalDiffAnalyzer(xStart, yStart, xEnd, yEnd):
    if xStart == xEnd and yStart == yEnd:
        return [(xStart, yStart)]

    result = list()

    length = abs(xEnd - xStart) if abs(xEnd - xStart) > abs(yEnd - yStart) else abs(yEnd - yStart)
    dx, dy = (xEnd - xStart) / length, (yEnd - yStart) / length
    x, y = xStart, yStart
    for i in range(length + 1):
        result.append((round(x), round(y)))
        x += dx
        y += dy

    return result


def bresenhamDouble(xStart, yStart, xEnd, yEnd):
    if xStart == xEnd and yStart == yEnd:
        return [(xStart, yStart)]

    result = list()

    dx, dy = xEnd - xStart, yEnd - yStart
    stepX, stepY = _sign(dx), _sign(dy)
    dx, dy = abs(dx), abs(dy)

    flag = False
    if dy > dx:
        dx, dy = dy, dx
        flag = True

    tangens = dy / dx
    error = tangens - 0.5

    x, y = xStart, yStart
    for i in range(dx + 1):
        result.append((x, y))
        if error > -1e-6:
            if flag:
                x += stepX
            else:
                y += stepY
            error -= 1
        if error < 1e-6:
            if flag:
                y += stepY
            else:
                x += stepX
            error += tangens

    return result


def bresenhamInt(xStart, yStart, xEnd, yEnd):
    if xStart == xEnd and yStart == yEnd:
        return [(xStart, yStart)]

    result = list()

    dx, dy = xEnd - xStart, yEnd - yStart
    stepX, stepY = _sign(dx), _sign(dy)
    dx, dy = abs(dx), abs(dy)

    flag = False
    if dx < dy:
        dx, dy = dy, dx
        flag = True

    error = 2 * dy - dx

    x, y = xStart, yStart
    for i in range(dx + 1):
        result.append((x, y))
        if error >= 0:
            if flag:
                x += stepX
            else:
                y += stepY
            error -= 2 * dx
        if error <= 0:
            if flag:
                y += stepY
            else:
                x += stepX
            error += 2 * dy

    return result


def bresenhamStairsReduce(xStart, yStart, xEnd, yEnd, color):
    if xStart == xEnd and yStart == yEnd:
        return [(xStart, yStart, color)]

    result = list()

    dx, dy = xEnd - xStart, yEnd - yStart
    stepX, stepY = _sign(dx), _sign(dy)
    dx, dy = abs(dx), abs(dy)

    flag = False
    if dy > dx:
        dx, dy = dy, dx
        flag = True

    tangens = dy / dx

    intensityMax = 1
    x, y = xStart, yStart
    error = intensityMax / 2
    w = intensityMax - tangens

    for i in range(dx + 1):
        newColor = _applyIntensity(color, error)
        result.append((x, y, newColor))
        if error < w:
            if flag:
                y += stepY
            else:
                x += stepX

            error += tangens
        else:  # ступенька
            x += stepX
            y += stepY
            error -= w

    return result


def wu(xStart, yStart, xEnd, yEnd, color):
    x0, y0, x1, y1 = xStart, yStart, xEnd, yEnd
    dx = xEnd - xStart
    dy = yEnd - yStart
    steep = abs(dx) < abs(dy)
    choosePoint = lambda px, py: ((px, py), (py, px))[steep]
    choosePointColor = lambda px, py, color: ((px, py, color), (py, px, color))[steep]

    if steep:
        xStart, yStart, xEnd, yEnd, dx, dy = yStart, xStart, yEnd, xEnd, dy, dx
    if xEnd < xStart:
        xStart, xEnd, yStart, yEnd = xEnd, xStart, yEnd, yStart

    result = list()
    grad = dy / dx
    intery = yStart + _reverseFloatPart(xStart) * grad

    def getEndPoint(point):
        x, y = point
        xend = round(x)
        yend = y + grad * (xend - x)
        xgap = _reverseFloatPart(x + 0.5)
        px, py = int(xend), int(yend)
        result.append(choosePointColor(px, py, _applyIntensity(color, _reverseFloatPart(yend) * xgap)))
        result.append(choosePointColor(px, py + 1, _applyIntensity(color, _floatPart(yend) * xgap)))
        return px

    xBegin = getEndPoint(choosePoint(x0, y0)) + 1
    xEnd = getEndPoint(choosePoint(x1, y1))

    for x in range(min(xEnd, xBegin), max(xEnd, xBegin)):
        y = int(intery)
        result.append(choosePointColor(x, y, _applyIntensity(color, _reverseFloatPart(intery))))
        result.append(choosePointColor(x, y + 1, _applyIntensity(color, _floatPart(intery))))
        intery += grad

    return result

