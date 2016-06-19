package edu.sust.cse.analysis.util;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Biswajit Debnath on 18-Jun-16.
 */
public class SDeskew extends SkewManager {
    @Override
    public double computeSkew(Mat image) {
        Mat bitWiseNotImage = new Mat();
        Core.bitwise_not(image, bitWiseNotImage);
        int height = bitWiseNotImage.height();
        int width = bitWiseNotImage.width();

        Mat imageEdges = new Mat();
        Imgproc.Canny(bitWiseNotImage, imageEdges, 150, 200);

        //    lines = cv2.HoughLinesP(edges, 1, cv2.cv.CV_PI/180, 100, minLineLength=width / 2.0, maxLineGap=20)
        Mat imageLines = new Mat();
        Imgproc.HoughLinesP(imageEdges, imageLines, 1, Math.PI / 180, 100);

        double angle = 0.0;

        Size lines = imageLines.size();
        System.out.println(lines);

        return 0;
    }

    @Override
    public void deskew(Mat img, double angle) {

    }

    @Override
    public void deskew(Mat img) {

    }
}
