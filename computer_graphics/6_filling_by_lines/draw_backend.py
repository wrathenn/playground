def ddaSegment(xStart, yStart, xEnd, yEnd):
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

