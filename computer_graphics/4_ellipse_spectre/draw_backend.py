import math

import numpy


def _circleMultiply(points, xCenter, yCenter):
    points += list(map(lambda p: [xCenter + p[1] - yCenter, yCenter + p[0] - xCenter], points))
    points += list(map(lambda p: [p[0], 2 * yCenter - p[1]], points))
    points += list(map(lambda p: [2 * xCenter - p[0], p[1]], points))
    return points


def drawCircleNormal(xCenter, yCenter, radius):
    result = []

    radius2 = radius * radius
    asd = round(xCenter + radius / math.sqrt(2) + 1)
    for x in range(xCenter, round(xCenter + radius / math.sqrt(2) + 1)):
        y = yCenter + math.sqrt(radius2 - (x - xCenter) * (x - xCenter))
        result.append((x, y))

    return _circleMultiply(result, xCenter, yCenter)


def drawCircleParameter(xCenter, yCenter, radius):
    result = []
    step = 1 / radius

    for angle in numpy.arange(0, math.pi / 4 + step, step):
        x = xCenter + radius * math.cos(angle)
        y = yCenter + radius * math.sin(angle)
        result.append((x, y))

    return _circleMultiply(result, xCenter, yCenter)


def drawCircleBresenham(xCenter, yCenter, radius):
    result = []
    x = 0
    y = radius
    result.append((x + xCenter, y + yCenter))
    delta = 2 * (1 - radius)

    while x < y:
        if delta <= 0:
            d1 = 2 * (delta + y) - 1
            x += 1
            if d1 >= 0:
                y -= 1
                delta += 2 * (x - y + 1)
            else:
                delta += x + x + 1

        else:
            d2 = 2 * (delta - x) - 1
            y -= 1
            if d2 < 0:
                x += 1
                delta += 2 * (x - y + 1)
            else:
                delta -= 2 * y - 1

        result.append((x + xCenter, y + yCenter))

    return _circleMultiply(result, xCenter, yCenter)


def drawCircleMiddlePoint(xCenter, yCenter, radius):
    result = []
    x = radius
    y = 0
    result.append((x + xCenter, y + yCenter))
    p = 1 - radius

    while x > y:
        y += 1
        if p >= 0:
            x -= 1
            p -= x + x
        p += 2 * y + 1
        result.append((x + xCenter, y + yCenter))

    return _circleMultiply(result, xCenter, yCenter)


def _ellipseMultiply(points, x, y):
    points += list(map(lambda p: [p[0], 2 * y - p[1]], points))
    points += list(map(lambda p: [2 * x - p[0], p[1]], points))
    return points


def drawEllipseNormal(xCenter, yCenter, wRadius, hRadius):
    result = []
    wRadius2 = wRadius * wRadius
    hRadius2 = hRadius * hRadius
    ab2 = wRadius2 * hRadius2

    for x in range(xCenter, round(xCenter + wRadius / math.sqrt(1 + hRadius2 / wRadius2))):
        y = yCenter + math.sqrt(ab2 - (x - xCenter) * (x - xCenter) * hRadius2) / wRadius
        x += 1
        result.append((x, y))

    for y in range(yCenter, round(yCenter + hRadius / math.sqrt(1 + wRadius2 / hRadius2))):
        x = xCenter + math.sqrt(ab2 - (y - yCenter) * (y - yCenter) * wRadius2) / hRadius
        y += 1
        result.append((x, y))

    return _ellipseMultiply(result, xCenter, yCenter)


def drawEllipseParameter(xCenter, yCenter, wRadius, hRadius):
    result = []
    step = 1 / wRadius if wRadius > hRadius else 1 / hRadius

    for angle in numpy.arange(0, math.pi / 2 + step, step):
        x = xCenter + wRadius * math.cos(angle)
        y = yCenter + hRadius * math.sin(angle)
        result.append((x, y))

    return _ellipseMultiply(result, xCenter, yCenter)


def drawEllipseBresenham(xCenter, yCenter, wRadius, hRadius):
    result = []
    x = 0
    y = hRadius
    wRadius2 = wRadius * wRadius
    hRadius2 = hRadius * hRadius
    result.append((x + xCenter, y + yCenter))
    delta = hRadius2 - wRadius2 * (2 * hRadius + 1)

    while y > 0:
        if delta <= 0:
            d1 = 2 * delta + wRadius2 * (2 * y - 1)
            x += 1
            delta += hRadius2 * (2 * x + 1)
            if d1 >= 0:
                y -= 1
                delta += wRadius2 * (-2 * y + 1)

        else:
            d2 = 2 * delta + hRadius2 * (-2 * x - 1)
            y -= 1
            delta += wRadius2 * (-2 * y + 1)
            if d2 < 0:
                x += 1
                delta += hRadius2 * (2 * x + 1)

        result.append((x + xCenter, y + yCenter))

    return _ellipseMultiply(result, xCenter, yCenter)


def drawEllipseMiddlePoint(xCenter, yCenter, wRadius, hRadius):
    result = []
    wRadius2 = wRadius * wRadius
    hRadius2 = hRadius * hRadius

    limit = round(wRadius / math.sqrt(1 + hRadius2 / wRadius2))

    x = 0
    y = hRadius
    result.append([x + xCenter, y + yCenter])

    fu = hRadius2 - round(wRadius2 * (hRadius - 1 / 4))
    while x < limit:
        if fu > 0:
            y -= 1
            fu -= 2 * wRadius2 * y

        x += 1
        fu += hRadius2 * (2 * x + 1)
        result.append([x + xCenter, y + yCenter])

    limit = round(hRadius / math.sqrt(1 + wRadius2 / hRadius2))

    y = 0
    x = wRadius
    result.append([x + xCenter, y + yCenter])

    fu = wRadius2 - round(hRadius2 * (wRadius - 1 / 4))
    while y < limit:
        if fu > 0:
            x -= 1
            fu -= 2 * hRadius2 * x

        y += 1
        fu += wRadius2 * (2 * y + 1)
        result.append([x + xCenter, y + yCenter])

    return _ellipseMultiply(result, xCenter, yCenter)
