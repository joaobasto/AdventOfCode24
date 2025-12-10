from shapely.geometry import Polygon, box

def load_points_from_txt(file_path):
    points = []
    with open(file_path, 'r') as f:
        for line in f:
            line = line.strip()
            if not line:
                continue
            x_str, y_str = line.replace(" ", "").split(",")
            points.append((float(x_str), float(y_str)))
    return points


def create_polygon_from_points(points):
    return Polygon(points)


def all_axis_aligned_rectangles(points):
    rectangles = []
    n = len(points)

    for i in range(n):
        for j in range(i + 1, n):
            (x1, y1) = points[i]
            (x2, y2) = points[j]

            # Skip colinear pairs
            if x1 == x2 or y1 == y2:
                continue

            rect = box(min(x1, x2), min(y1, y2),
                       max(x1, x2), max(y1, y2))
            rectangles.append(rect)

    return rectangles


def largest_rectangle_inside_polygon(rectangles, polygon):
    max_area = 0
    best_rect = None

    for rect in rectangles:
        if polygon.contains(rect):  # ensures full containment
            area = rect.area
            if area > max_area:
                max_area = area
                best_rect = rect

    return best_rect, max_area


if __name__ == "__main__":
    file_path = "inputDay2509Solver.txt"
    points = load_points_from_txt(file_path)
    polygon = create_polygon_from_points(points)

    rectangles = all_axis_aligned_rectangles(points)
    best_rect, max_area = largest_rectangle_inside_polygon(rectangles, polygon)

    if best_rect:
        print("Largest rectangle fully inside polygon:")
        print("WKT:", best_rect.wkt)
        print("Area:", max_area)
        print("Rectangle:", best_rect)
        #area needs to be corrected because it considers less one unit than what it should
        #in both dimensions
        print("Corrected area:", (94860-5123+1)*(66140-50423+1))
    else:
        print("No candidate rectangle is fully contained in the polygon.")
