/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sust.cse.util;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * @author Biswajit Debnath
 */
public class Histogram {

    private static Mat horizontalHistogramMatrix;

    private static Mat verticalHistogramMatrix;


    public static void showHistogram(Mat filteredImage) {
        Mat threshedImage = new Mat();
        Imgproc.threshold(filteredImage, threshedImage, 170, 255, Imgproc.THRESH_BINARY);
//        ViewerUI.show("Image-Histogram", threshedImage, ViewableUI.SHOW_HISTOGRAM_IMAGE);
        Imgproc.cvtColor(threshedImage, threshedImage, Imgproc.COLOR_RGB2GRAY);
        verticalHistogram(threshedImage);
        horizontalHistogram(threshedImage);
        bothHistogramWithFilteredImage(threshedImage);

    }

    public static void showHistogram(String title, Mat filteredImage) {
        Mat threshedImage = new Mat();
        Imgproc.threshold(filteredImage, threshedImage, 175, 255, Imgproc.THRESH_BINARY);
//        ViewerUI.show(title, threshedImage, ViewableUI.SHOW_HISTOGRAM_IMAGE);
        Imgproc.cvtColor(threshedImage, threshedImage, Imgproc.COLOR_RGB2GRAY);
        verticalHistogram(threshedImage);
        horizontalHistogram(threshedImage);
        bothHistogramWithFilteredImage(title, threshedImage);

    }

    public static double[] getLinePercetageFromHistogram(Mat filteredImage, double linePercentageThresHold) {
        Mat threshedImage = new Mat();
        Imgproc.threshold(filteredImage, threshedImage, 175, 255, Imgproc.THRESH_BINARY_INV);
        Imgproc.cvtColor(threshedImage, threshedImage, Imgproc.COLOR_RGB2GRAY);
        double vPer = verticalHistogram(threshedImage,linePercentageThresHold);
        double hPer = horizontalHistogram(threshedImage,linePercentageThresHold);
        double[] values = {vPer,hPer};
        return values;
    }


    private static void horizontalHistogram(Mat threshedImage) {
       horizontalHistogram(threshedImage,0.0);
     //   ViewerUI.show("HISTOGRAM_HORIZONTAL", horizontalHistogramMatrix, ViewableUI.SHOW_HISTOGRAM_IMAGE);
    }

    private static double horizontalHistogram(Mat threshedImage, double linePercentageThreshold) {

        Mat rowMatrix;
        int numOfRows = threshedImage.rows();
        int numOfNonZeros;
        int max = Integer.MIN_VALUE;
        int numOfNonZerosPerRows[] = new int[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            rowMatrix = threshedImage.row(i);
            numOfNonZeros = Core.countNonZero(rowMatrix);
            numOfNonZerosPerRows[i] = numOfNonZeros;
            max = Math.max(numOfNonZeros, max);
        }
        int width = max;
        int height = numOfRows;
        horizontalHistogramMatrix = Mat.zeros(new Size(width, height), CvType.CV_8U);
        int count = 0;
        for (int i = 0; i < height; i++) {
            double percentage = (numOfNonZerosPerRows[i] * 100.0) / (max*1.0);
            count = percentage > linePercentageThreshold ? count+1 : count;
            Core.line(horizontalHistogramMatrix, new Point(0, i), new Point(numOfNonZerosPerRows[i], i), new Scalar(255));
        }
        return count*100.0/(height*1.0);
    }

    private static void verticalHistogram(Mat thershedImage) {

        verticalHistogram(thershedImage,0.0);
      //  ViewerUI.show("HISTOGRAM_VERTICAL", verticalHistogramMatrix, ViewableUI.SHOW_HISTOGRAM_IMAGE);
    }

    private static double verticalHistogram(Mat threshedImage, double linePercentageThreshold) {

        Mat columnMatrix;
        int numOfColumns = threshedImage.cols();
        int numOfNonZeros;
        int max = Integer.MIN_VALUE;
        int numOfNonZerosPerColumns[] = new int[numOfColumns];
        for (int i = 0; i < numOfColumns; i++) {
            columnMatrix = threshedImage.col(i);
            numOfNonZeros = Core.countNonZero(columnMatrix);
            numOfNonZerosPerColumns[i] = numOfNonZeros;
            max = Math.max(numOfNonZeros, max);
        }
        int width = numOfColumns;
        int height = max;
        verticalHistogramMatrix = Mat.zeros(new Size(width, height), CvType.CV_8U);
        int count=0;
        for (int i = 0; i < width; i++) {
            double percentage = (numOfNonZerosPerColumns[i] * 100.0) / (max*1.0f);
            count = percentage > linePercentageThreshold ? count+1 : count;
            Core.line(verticalHistogramMatrix, new Point(i, max), new Point(i, max - numOfNonZerosPerColumns[i]), new Scalar(255));
        }
        return count*100.0/(width*1.0);
    }

    private static void bothHistogramWithFilteredImage(Mat filteredImage) {
        bothHistogramWithFilteredImage("IMAGE_WITH_HISTOGRAM",filteredImage);
    }

    private static void bothHistogramWithFilteredImage(String title, Mat filteredImage) {
        int width = horizontalHistogramMatrix.cols() + verticalHistogramMatrix.cols();
        int height = verticalHistogramMatrix.rows() + horizontalHistogramMatrix.rows();
        Mat imageMatrix = Mat.zeros(new Size(width, height), CvType.CV_16UC1);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i < verticalHistogramMatrix.rows() && j < horizontalHistogramMatrix.cols()) {
                    imageMatrix.put(i, j, 255, 255);
                } else if (i >= verticalHistogramMatrix.rows() && j < horizontalHistogramMatrix.cols()) {
                    imageMatrix.put(i, j, horizontalHistogramMatrix.get(i - verticalHistogramMatrix.rows(), j));
                } else if (i < verticalHistogramMatrix.rows() && j >= horizontalHistogramMatrix.cols()) {
                    imageMatrix.put(i, j, verticalHistogramMatrix.get(i, j - horizontalHistogramMatrix.cols()));
                } else {
                    imageMatrix.put(i, j, filteredImage.get(i - verticalHistogramMatrix.rows(), j - horizontalHistogramMatrix.cols()));
                }
            }
        }
        ViewerUI.show(title, imageMatrix, ViewableUI.SHOW_HISTOGRAM_IMAGE);
    }
}
