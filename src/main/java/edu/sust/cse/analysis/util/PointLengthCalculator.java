package edu.sust.cse.analysis.util;

import edu.sust.cse.item.PointDistance;
import org.opencv.core.Mat;

/**
 * Created by Biswajit Debnath on 26-Dec-15.
 */
public class PointLengthCalculator {

    private PointDistance[][] pointDistances;

    private Mat filterImage;

    private int width;
    private int height;

    public PointLengthCalculator(Mat filterImage) {
        width = (int) filterImage.size().width;
        height = (int) filterImage.size().height;
        this.filterImage = filterImage;
        pointDistances = new PointDistance[height][width];
        doCalculate();
    }

    private void doCalculate() {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pointDistances[i][j] = new PointDistance();

                if (filterImage.get(i, j)[0] != 0) {
                    pointDistances[i][j].setHorizontalValue(0);
                    pointDistances[i][j].setVerticalValue(0);
                    continue;
                }
                /*axisWiseCalculation(i, j, Axis.HORIZONTAL);
                axisWiseCalculation(i, j, Axis.VERTICAL);*/
                if (j != 0 && filterImage.get(i, j - 1)[0] == 0) {
                    pointDistances[i][j].
                            setHorizontalValue(pointDistances[i][j - 1].getHorizontalValue());
                } else {
                    int count = 0;
                    for (int k = j + 1; k < width; k++) {
                        if (filterImage.get(i, k)[0] == 0) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    pointDistances[i][j].setHorizontalValue(count);
                }

                if (i != 0 && filterImage.get(i - 1, j)[0] == 0) {
                    pointDistances[i][j].setVerticalValue(pointDistances[i - 1][j].getVerticalValue());
                } else {
                    int count = 0;
                    for (int k = i + 1; k < height; k++) {
                        if (filterImage.get(k, j)[0] == 0) {
                            count++;
                        } else {
                            break;
                        }
                    }
                    pointDistances[i][j].setVerticalValue(count);
                }
            }
        }
    }

    private void axisWiseCalculation(int column, int row, Axis axis) {
        int columnPoint = axis.equals(Axis.VERTICAL) ? column - 1 : column;
        int rowPoint = axis.equals(Axis.HORIZONTAL) ? row - 1 : row;
        int analysisPoint = axis.equals(Axis.VERTICAL) ? column : row;
        if (analysisPoint != 0 && filterImage.get(columnPoint, rowPoint)[0] == 0) {
            switch (axis) {
                case HORIZONTAL:
                    pointDistances[column][row].setHorizontalValue(pointDistances[columnPoint][rowPoint].getHorizontalValue());
                    break;
                case VERTICAL:
                    pointDistances[column][row].setVerticalValue(pointDistances[columnPoint][rowPoint].getVerticalValue());
                    break;
            }
        } else {
            int count = 0;
            for (int i = (axis.equals(Axis.VERTICAL) ? column : row) + 1; i < (axis.equals(Axis.VERTICAL) ? height : width); i++) {
                if (filterImage.get((axis.equals(Axis.VERTICAL) ? i : column), (axis.equals(Axis.HORIZONTAL) ? i : row))[0] == 0) {
                    count++;
                } else {
                    break;
                }
            }
            switch (axis) {
                case HORIZONTAL:
                    pointDistances[column][row].setHorizontalValue(count);
                    break;
                case VERTICAL:
                    pointDistances[column][row].setVerticalValue(count);
                    break;
            }
        }
    }

    public PointDistance[][] getPointDistances() {
        return this.pointDistances;
    }

}
