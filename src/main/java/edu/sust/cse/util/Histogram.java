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

    /*public static Mat getHistogram(Mat image) {

        try {
            Mat src = new Mat(image.height(), image.width(), CvType.CV_8UC2);
            Imgproc.cvtColor(image, src, Imgproc.COLOR_RGB2GRAY);
            ArrayList<Mat> bgr_planes = new ArrayList<Mat>();
            Core.split(src, bgr_planes);

            MatOfInt histSize = new MatOfInt(256);

            final MatOfFloat histRange = new MatOfFloat(0f, 256f);

            boolean accumulate = false;

            Mat b_hist = new Mat();

            Imgproc.calcHist(bgr_planes, new MatOfInt(0), new Mat(), b_hist, histSize, histRange, accumulate);

            int hist_w = 512;
            int hist_h = 600;
            long bin_w;
            bin_w = Math.round((double) (hist_w / 256));

            Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC1);

            Core.normalize(b_hist, b_hist, 3, histImage.rows(), Core.NORM_MINMAX);

            for (int i = 1; i < 256; i++) {

                Core.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(b_hist.get(i - 1, 0)[0])),
                        new Point(bin_w * (i), hist_h - Math.round(Math.round(b_hist.get(i, 0)[0]))),
                        new Scalar(255, 0, 0), 2, 8, 0);

            }

            return histImage;
        } catch (Exception ex) {
            System.out.println("[HISTOGRAM][ERROR][" + ex.getMessage() + "]");
            return null;
        }
    }*/


    public static void showHistogram(Mat filteredImage) {
        Mat thershedImage = new Mat();
        Imgproc.threshold(filteredImage, thershedImage, 180, 255, Imgproc.THRESH_BINARY_INV);
        ViewerUI.show("Image-Histogram", thershedImage, ViewableUI.SHOW_HISTOGRAM_IMAGE);

        Imgproc.cvtColor(thershedImage, thershedImage, Imgproc.COLOR_RGB2GRAY);
        verticalHistogram(thershedImage);
        horizontalHistogram(thershedImage);
        bothHistogramWithFilteredImage(filteredImage);

    }


    private static void horizontalHistogram(Mat threshedImage) {

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
        for (int i = 0; i < height; i++) {
            Core.line(horizontalHistogramMatrix, new Point(0, i), new Point(numOfNonZerosPerRows[i], i), new Scalar(255));
        }
        ViewerUI.show("Histogram-Horizontal", horizontalHistogramMatrix, ViewableUI.SHOW_HISTOGRAM_IMAGE);
    }

    private static void verticalHistogram(Mat thershedImage) {

        Mat columnMatrix;

        int numOfColumns = thershedImage.cols();
        int numOfNonZeros;
        int max = Integer.MIN_VALUE;
        int numOfNonZerosPerColumns[] = new int[numOfColumns];
        for (int i = 0; i < numOfColumns; i++) {
            columnMatrix = thershedImage.col(i);
            numOfNonZeros = Core.countNonZero(columnMatrix);
            numOfNonZerosPerColumns[i] = numOfNonZeros;
            max = Math.max(numOfNonZeros, max);
        }
        int width = numOfColumns;
        int height = max;
        verticalHistogramMatrix = Mat.zeros(new Size(width, height), CvType.CV_8U);
        for (int i = 0; i < width; i++) {
            Core.line(verticalHistogramMatrix, new Point(i, max), new Point(i, max - numOfNonZerosPerColumns[i]), new Scalar(255));
        }
        ViewerUI.show("Histogram-Vertical", verticalHistogramMatrix, ViewableUI.SHOW_HISTOGRAM_IMAGE);
    }

    private static void bothHistogramWithFilteredImage(Mat filteredImage) {
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
        ViewerUI.show("Image-WITH HISTOGRAM", imageMatrix, ViewableUI.SHOW_HISTOGRAM_IMAGE);
    }
}
