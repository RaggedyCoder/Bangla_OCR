package edu.sust.cse.analysis.util;

import edu.sust.cse.util.ViewerUI;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Biswajit Debnath on 20-Jun-16.
 */
public class HLDeskew extends SkewManager {
    @Override
    public double computeSkew(Mat img) {
        Mat gray = new Mat();

        Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(gray, gray, new Size(3, 3));

        // detect the edges
        Mat edges = new Mat();
        int lowThreshold = 50;
        int ratio = 3;
//        Imgproc.Canny(gray, edges, lowThreshold, lowThreshold * ratio);
        Imgproc.Canny(gray, edges, 10, 150);

        Mat lines = new Mat();
        Imgproc.HoughLinesP(edges, lines, 1, Math.PI / 180, 50, 50, 10);
        double angleC = 0.0;
        Point p1 = new Point();
        Point p2 = new Point();
        for(int i = 0; i < lines.cols(); i++) {
            double[] val = lines.get(0, i);
             p1.x=val[0];
             p1.y=val[1];
             p2.x=val[2];
             p2.y=val[3];
            Core.line(img, p1, p2, new Scalar(0, 0, 255), 2);
            angleC+= Math.atan2(p2.y-p1.y,p2.x-p1.x);
        }

//        double angleR = 0.0;
//        for(int i = 0; i < lines.rows(); i++) {
//            double[] val = lines.get(i, 0);
//            p1.x=val[0];
//            p1.y=val[1];
//            p2.x=val[2];
//            p2.y=val[3];
//            Core.line(img, p1, p2, new Scalar(0, 0, 255), 2);
//            angleR+= Math.atan2(p2.y-p1.y,p2.x-p1.x);
//        }

        angleC/=lines.cols();
//        angleR/=lines.rows();

        double thetaC =Math.toDegrees(angleC);
//        double thetaR =Math.toDegrees(angleR);

//        if (thetaC < 0.0) {
//            thetaC += 360.0;
//        }
//
//        if (thetaR < 0.0) {
//            thetaR += 360.0;
//        }

        System.out.println("[HLDESKEW][COMPUTE_SKEW][ANGLE-RADIAN-C]["+angleC+"]");
//        System.out.println("[HLDESKEW][COMPUTE_SKEW][ANGLE-RADIAN-R]["+angleR+"]");
        System.out.println("[HLDESKEW][COMPUTE_SKEW][ANGLE-DEGREE-C]["+thetaC+"]");
//        System.out.println("[HLDESKEW][COMPUTE_SKEW][ANGLE-DEGREE-R]["+thetaR+"]");

        ViewerUI.show("[HLDESKEW][EDGES]", edges, false);
        ViewerUI.show("[HLDESKEW][GRAY]", gray, false);
        ViewerUI.show("[HLDESKEW][IMG]["+thetaC+"]", img, true);
        return angleC;
    }

    @Override
    public void deskew(Mat img, double angle) {

    }

    @Override
    public void deskew(Mat img) {
        double angle = computeSkew(img);
        deskew(img,angle);

    }
}
