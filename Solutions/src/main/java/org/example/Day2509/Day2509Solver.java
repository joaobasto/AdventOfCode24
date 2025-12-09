package org.example.Day2509;

import org.example.AbstractSolver;
import org.example.utils.Position2D;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day2509Solver extends AbstractSolver {

    private static final long NUMBER_OF_CONNECTIONS = 1000;

    @Override
    protected long createSolutionExercise1(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        Set<Position2D> coordinates = new HashSet<>();
        while ((line = br.readLine()) != null) {
            String[] coordinatesString = line.split(",");
            coordinates.add(new Position2D(Long.parseLong(coordinatesString[0]), Long.parseLong(coordinatesString[1])));
        }
        long maxArea = 0;
        for (Position2D coordinate1 : coordinates) {
            for (Position2D coordinate2 : coordinates) {
                long area = calculateArea(coordinate1, coordinate2);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }
        return maxArea;
    }

    private long calculateArea(Position2D coordinate1, Position2D coordinate2) {
        return (Math.abs(coordinate1.getX() - coordinate2.getX()) + 1) *
                (Math.abs(coordinate1.getY() - coordinate2.getY()) + 1);
    }

    @Override
    protected long createSolutionExercise2(BufferedReader br) throws IOException {
        long result = 0;
        String line;
        Set<Position2D> coordinates = new HashSet<>();
        Map<Long, List<Long>> coordinatesMap = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] coordinatesString = line.split(",");
            coordinates.add(new Position2D(Long.parseLong(coordinatesString[0]), Long.parseLong(coordinatesString[1])));
            List<Long> currentList = coordinatesMap.getOrDefault(Long.parseLong(coordinatesString[1]), new ArrayList<>());
            currentList.add(Long.parseLong(coordinatesString[0]));
            coordinatesMap.put(Long.parseLong(coordinatesString[1]), currentList);
        }

        // points set of points
        long[][] points = new long[coordinates.size()][2];
        int iter = 0;
        for(Position2D position2D : coordinates) {
            points[iter][0] = position2D.getX();
            points[iter][1] = position2D.getY();
            iter++;
        }

        // Call function to get convex hull
        long[][] hull = findConvexHull(points);


        long maxArea = 0;
        for (Position2D coordinate1 : coordinates) {
            for (Position2D coordinate2 : coordinates) {
                if (!isFeasible(coordinate1, coordinate2, coordinates, points, hull)) {
                    continue;
                }
                long area = calculateArea(coordinate1, coordinate2);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }
        return maxArea;
    }

    private boolean isFeasible(Position2D coordinate1, Position2D coordinate2, Set<Position2D> coordinates, long[][] points, long[][] hull) {
        //if x or y coordinates are equal is feasible
        if (coordinate1.getX() == coordinate2.getX() ||
                coordinate1.getY() == coordinate2.getY()) {
            return true;
        }
        Position2D otherCorner1 = new Position2D(coordinate2.getX(), coordinate1.getY());
        Position2D otherCorner2 = new Position2D(coordinate1.getX(), coordinate2.getY());

        long[][] pointsAltered = new long[points.length + 2][2];
        for(int i = 0; i < points.length; i++) {
            pointsAltered[i][0] = points[i][0];
            pointsAltered[i][1] = points[i][1];
        }
        pointsAltered[points.length][0] = otherCorner1.getX();
        pointsAltered[points.length][1] =otherCorner1.getY();
        pointsAltered[points.length+1][0] = otherCorner2.getX();
        pointsAltered[points.length+1][1] = otherCorner2.getY();
        long[][] hullAltered = findConvexHull(pointsAltered);


        Set<Position2D> positions = new HashSet<>();
        Set<Position2D> positionsAltered = new HashSet<>();

        for(int i = 0; i < hull.length; i++) {
            positions.add(new Position2D(hull[i][0], hull[i][1]));
        }

        for(int i = 0; i < hullAltered.length; i++) {
            positionsAltered.add(new Position2D(hullAltered[i][0], hullAltered[i][1]));
        }

        return positions.equals(positionsAltered);
    }

    // Function to find the convex hull of a set of points
    static long[][] findConvexHull(long[][] points) {
        // Get number of points
        long n = points.length;

        // Convex hull is not possible with less than 3 points
        if (n < 3) return new long[][]{{-1}};

        // Convert int[][] points to list of Point objects
        ArrayList<Point> a = new ArrayList<>();
        for (long[] p : points) {
            a.add(new Point(p[0], p[1]));
        }

        // Find the bottom-most point (and left-most if tie)
        Point p0 = Collections.min(a, (p1, p2) -> {
            if (p1.y != p2.y)
                return Double.compare(p1.y, p2.y);
            return Double.compare(p1.x, p2.x);
        });

        // Sort points based on polar angle with respect to p0
        a.sort((p1, p2) -> {
            long o = orientation(p0, p1, p2);

            // If collinear, sort by distance from p0
            if (o == 0) {
                return Double.compare(distSq(p0, p1), distSq(p0, p2));
            }

            // Otherwise sort by orientation
            return (o < 0) ? -1 : 1;
        });

        // Stack to store the points of convex hull
        Stack<Point> st = new Stack<>();

        // Traverse sorted points
        for (Point p : a) {

            // Remove last point while the angle formed is not counter-clockwise
            while (st.size() > 1 && orientation(st.get(st.size() - 2), st.peek(), p) >= 0)
                st.pop();

            // Add current point to the convex hull
            st.push(p);
        }

        // If convex hull has less than 3 points, it's invalid
        if (st.size() < 3) return new long[][]{{-1}};

        // Convert the convex hull points into int[][]
        long[][] result = new long[st.size()][2];
        int i = 0;
        for (Point p : st) {
            result[i][0] = (long)p.x;
            result[i][1] = (long)p.y;
            i++;
        }

        return result;
    }

    // Class to represent a point with x and y coordinates
    static class Point {
        double x, y;

        // Constructor to initialize point
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        // Override equals to compare two points
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point t = (Point) obj;
            return Double.compare(t.x, x) == 0 && Double.compare(t.y, y) == 0;
        }
    }

    // Function to calculate orientation of ordered triplet (a, b, c)
    static int orientation(Point a, Point b, Point c) {
        // Compute the cross product value
        double v = a.x * (b.y - c.y) +
                b.x * (c.y - a.y) +
                c.x * (a.y - b.y);

        // Return -1 for clockwise, 1 for counter-clockwise, 0 for collinear
        if (v < 0) return -1;
        if (v > 0) return 1;
        return 0;
    }

    // Function to compute square of distance between two points
    static double distSq(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) +
                (a.y - b.y) * (a.y - b.y);
    }
}
