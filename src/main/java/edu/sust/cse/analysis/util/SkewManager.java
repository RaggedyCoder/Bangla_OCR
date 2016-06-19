package edu.sust.cse.analysis.util;

import edu.sust.cse.util.ViewableUI;
import edu.sust.cse.util.ViewerUI;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by Md. Sajid Shahriar on 07-Jun-16.
 */
public abstract class SkewManager {

    public abstract double computeSkew(Mat image);

    public abstract  void deskew(Mat img, double angle);

    public abstract void deskew(Mat img);
}
