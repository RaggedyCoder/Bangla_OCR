package edu.sust.cse.analysis.util;

import edu.sust.cse.util.ViewableUI;
import edu.sust.cse.util.ViewerUI;
import org.omg.CORBA.IMP_LIMIT;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Biswajit Debnath on 18-Jun-16.
 */
public class BDeSkew extends SkewManager {

    @Override
    public double computeSkew(Mat image) {
        Mat grayImage=new Mat();
        //Convert the image to gray level image
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_RGB2GRAY);
        ViewerUI.show("[BDESKEW][GRAY IMAGE]", grayImage, true);
        // Binarize
        Imgproc.threshold(grayImage, grayImage, 225, 255, Imgproc.THRESH_BINARY);
        ViewerUI.show("[BDESKEW][BINARY IMAGE]", grayImage, true);
        Mat bitWiseNotImage = new Mat();
        //Invert the image
        Core.bitwise_not(grayImage, bitWiseNotImage);
        ViewerUI.show("[BDESKEW][BIT WISE NOT IMAGE]", bitWiseNotImage, true);
        Mat element  = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,3));
        Imgproc.erode(bitWiseNotImage,bitWiseNotImage,element);

        ViewerUI.show("[BDESKEW][MORPH]", bitWiseNotImage, true);

        List<Point> points = new ArrayList<Point>();
       for( int r=0;r<bitWiseNotImage.rows();r++){
           for (int c=0;c<bitWiseNotImage.cols();c++){
               double[] pixel = bitWiseNotImage.get(r,c);
               points.add(new Point(pixel));
           }
       }
       MatOfPoint2f matOfPoint2f = new MatOfPoint2f();
       matOfPoint2f.fromList(points);
       RotatedRect rectBox = Imgproc.minAreaRect(matOfPoint2f);
        double angle = rectBox.angle;
//        if (angle < -45.)
//            angle += 90.;

        System.out.println("[BDESKEW][ANGLE][" + angle +"]");
        return angle;
    }

    @Override
    public void deskew(Mat img, double angle) {

    }

    @Override
    public void deskew(Mat img) {
        double angle = computeSkew(img);
        deskew(img, angle);
    }
}
