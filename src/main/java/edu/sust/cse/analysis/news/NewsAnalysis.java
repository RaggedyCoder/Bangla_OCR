package edu.sust.cse.analysis.news;

import com.recognition.software.jdeskew.ImageUtil;

import edu.sust.cse.analysis.util.PointLengthCalculator;
import edu.sust.cse.analysis.util.Convertion;
import edu.sust.cse.detection.algorithm.ImageBorderDetectionBFS;
import edu.sust.cse.item.BorderItem;

import edu.sust.cse.item.PointDistance;
import edu.sust.cse.util.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author fahad_000
 */
public class NewsAnalysis {

    private static BufferedWriter bw;

    static {
        /**
         * OpenCV DLL Library Included
         */
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws IOException {
        /*
        * For Biswa
       * D:\OpenCV_Library\resources\Scan_Img\image\06-12-2015*/

        /*
        * For Tuman
       * C:\Users\sajid\Desktop\ScanImage\06-12-2015*/
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-01-145c.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-01-300c.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-02-145c.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-02-300.jpg");
        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-03-145.jpg");
//        Mat inputImageMat = Highgui.imread("G:\\Study\\Thesis\\06-12-2015\\sc-01-300.jpg");
        PixelFileWriter pixelFileWriter = new PixelFileWriter();
        double ratio = 150 / 72.0;  // 4.167
        int inputWidth = (int) (inputImageMat.width() * ratio);
        int inputHeight = (int) (inputImageMat.height() * ratio);
        Debug.debugLog("[Image [Cols, Rows]: [" + inputImageMat.cols() + ", " + inputImageMat.rows() + "]]");
        ViewerUI.show("Original", inputImageMat, ViewableUI.SHOW_ORIGINAL);

        // Do some image processing on the image and display in another window.

        /**
         * We have explained some filters which main goal is to smooth an input
         * image. However, sometimes the filters do not only dissolve the noise,
         * but also smooth away the edges
         */
        Mat filteredImage = new Mat();
        Imgproc.bilateralFilter(inputImageMat, filteredImage, -1, 50, 10);

        ViewerUI.show("Noise Filter", filteredImage, ViewableUI.SHOW_NOISE_FILTER);
//        pixelFileWriter.writePixelInFile("NoiseFilter-3-300c.txt",filteredImage);
        Histogram.showHistogram(filteredImage);
//        ViewerUI.show("Noise Filter-Histogram", Histogram.getHistogram(filteredImage), ViewableUI.SHOW_HISTOGRAM_NOISE_FILTER);
        Imgproc.Canny(filteredImage, filteredImage, 10, 150);
//        Imgproc.bilateralFilter(filteredImage, filteredImage, -1, 50, 10);
//        Imgproc.threshold(filteredImage, filteredImage, 250, 300,Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C );
        //Imgproc.cvtColor(m1, m1, Imgproc.COLOR_RGB2GRAY, 0);
//        imshow("Edge Detected", m2);
        ViewerUI.show("Edge Detected", filteredImage, ViewableUI.SHOW_EDGE_DETECTION);
//        pixelFileWriter.writePixelInFile("EdgeDetectedPixels-3-300c.txt",filteredImage);
//        pixelFileWriter.writeInFile("EdgeDetected-01-200c.txt"," ","0",filteredImage);
//        ViewerUI.show("Edge Detected-Histogram", Histogram.getHistogram(filteredImage), ViewableUI.SHOW_HISTOGRAM_EDGE_DETECTION);

        Size filteredImageSize = filteredImage.size();
        System.out.println("Width: " + filteredImageSize.width + " Height: " + filteredImageSize.height);
        int width = (int) filteredImageSize.width;
        int height = (int) filteredImageSize.height;
        PointLengthCalculator pointLenCal = new PointLengthCalculator(filteredImage);
        PointDistance[][] pointDistances = pointLenCal.getPointDistances();
        // int[][][] pointLength = pointLenCal.getPointLength();
        // Convertion convertion = new Convertion(filteredImage,pointLength);
        Convertion convertion = new Convertion(filteredImage, pointDistances);
        int[][] blackWhite = convertion.getBlackWhitePixelInfo();
        Mat convertArea = convertion.getConvertedArea();
        ViewerUI.show("Convertion", convertArea, ViewableUI.SHOW_CONVERSION);

        ImageBorderDetectionBFS imgBFS = new ImageBorderDetectionBFS();
        ArrayList<BorderItem> borderItems = imgBFS.getBorder(blackWhite, width, height, filteredImage, inputImageMat);
        System.out.println("Border Item Founds: " + borderItems.size());

        boolean[] imageIndexer = new boolean[borderItems.size()];
        int[] lineHeight = new int[borderItems.size()];
        int highestLineHeight = -1, lowestLineHeight = 100000;
        int totalHeight = 0, notImage = 0;

        for (int i = 0; i < borderItems.size(); i++) {
            lineHeight[i] = 0;
            BorderItem borderItem = borderItems.get(i);
            if (borderItem.getIsImage()) {
                //   System.out.println("subMat" + i + " is an image");
//                imshow("Image" + i, borderItem.getBlock());
                ViewerUI.show("Image" + i, borderItem.getBlock(), ViewableUI.SHOW_IMAGE);
                Mat thersoldImage = new Mat();

//                ViewerUI.show("Image-Histogram" + i, Histogram.getHistogram(borderItem.getBlock()), ViewableUI.SHOW_HISTOGRAM_IMAGE);
                imageIndexer[i] = true;
                continue;
            } else {
                notImage++;
                imageIndexer[i] = false;
            }

            Mat fake = new Mat();
            Imgproc.cvtColor(borderItem.getBlock(), fake, Imgproc.COLOR_RGB2GRAY, 0);
            //  lineHeight[i]=getLineHeight(fake);
            //  totalHeight+=lineHeight[i]+getLineHeight(fake);
            totalHeight += lineHeight[i] = getLineHeight(fake);
            //  System.out.print(totalHeight+" ");

            fake.release();
            // System.out.println("line height " + i + ": " + lineHeight[i]);
            if (lineHeight[i] > highestLineHeight) {
                highestLineHeight = lineHeight[i];
            }
            if (lineHeight[i] < lowestLineHeight) {
                lowestLineHeight = lineHeight[i];
            }
        }

//        int avgLineHeight = totalHeight / notImage;
//        System.out.println("Not Image: " + notImage);
        //      System.out.println("Highest Line Hight: " + highestLineHeight);
        //    System.out.println("Lowest Line Hight: " + lowestLineHeight);
        //  System.out.println("Average Line Hight: " + avgLineHeight);
/**
 * Previous Code for HeadLine, Sub HeadLine, Column Detection 96 dpi eprothom alo
 */
//        for (int i = 0; i < borderItems.size(); i++) {
//            if (!imageIndexer[i]) {
//                if (lineHeight[i] - lowestLineHeight > 13 && lineHeight[i] >= 45) {
//                    ViewerUI.show("Headline" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_HEADING);
//                    System.out.println("HeadLine Height: "+ lineHeight[i]);
////                    ViewerUI.show("Headline-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_HEADING);
//
//                } else if (lineHeight[i] - lowestLineHeight > 8 && lineHeight[i] >= 21 && lineHeight[i] < 45) {
//                    ViewerUI.show("Sub Headline" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_SUB_HEADING);
//                    System.out.println("Sub HeadLine Height: "+ lineHeight[i]);
////                    ViewerUI.show("Sub Headline-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_SUB_HEADING);
//
//                } else {
//                    ViewerUI.show("Column" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_COLUMN);
//                    System.out.println("Column: "+ lineHeight[i]);
////                    ViewerUI.show("Column-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_COLUMN);
//
//                }
//            }
//        }

        /**
         * Biswajit Inserted code for HeadLine, Sub HeadLine Detection 145 dpi scan image
         */

//           for (int i = 0; i < borderItems.size(); i++) {
//            if (!imageIndexer[i]) {
//                if (lineHeight[i] > lowestLineHeight + 26 && lineHeight[i] >= 90) {
//                    System.out.println("HeadLine");
//                    ViewerUI.show("[LineHeight_"+lineHeight[i]+"]_Headline" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_HEADING);
////                    ViewerUI.show("Headline-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_HEADING);
//
//                } else if (lineHeight[i] > lowestLineHeight + 16 && lineHeight[i] >= 42 && lineHeight[i] < 90) {
//                    System.out.println("Sub HeadLine");
//                    ViewerUI.show("[LineHeight_"+lineHeight[i]+"]_Sub_Headline" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_SUB_HEADING);
////                    ViewerUI.show("Sub Headline-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_SUB_HEADING);
//
//                } else {
//                    System.out.println("Column");
//                    ViewerUI.show("[LineHeight_"+lineHeight[i]+"]_Column" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_COLUMN);
////                    ViewerUI.show("Column-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_COLUMN);
//
//                }
//            }
//        }


        /**
         * Biswajit Inserted code for HeadLine, Sub HeadLine Detection 300dpi
         */
        for (int i = 0; i < borderItems.size(); i++) {
            if (!imageIndexer[i]) {
                if (lineHeight[i] > lowestLineHeight + 52 && lineHeight[i] >= 160) {
                    System.out.println("HeadLine");
                    ViewerUI.show("[LineHeight_" + lineHeight[i] + "]_Headline" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_HEADING);
//                    ViewerUI.show("Headline-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_HEADING);

                } else if (lineHeight[i] > lowestLineHeight + 32 && lineHeight[i] >= 48 && lineHeight[i] < 65) {
                    System.out.println("Sub HeadLine");
                    ViewerUI.show("[LineHeight_" + lineHeight[i] + "]_Sub_Headline" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_SUB_HEADING);
//                    ViewerUI.show("Sub Headline-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_SUB_HEADING);

                } else {
                    System.out.println("Column");
                    ViewerUI.show("[LineHeight_" + lineHeight[i] + "]_Column" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_COLUMN);
//                    ViewerUI.show("Column-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_COLUMN);

                }
            }
        }

    }

    public static void fileWrite(String content) {
        try {
            bw.write(content);
            bw.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void imshow(String title, Mat img) {

        // Convert image Mat to a jpeg
        MatOfByte imageBytes = new MatOfByte();
        Highgui.imencode(".jpg", img, imageBytes);

        try {
            // Put the jpeg bytes into a JFrame window and show.
//            JFrame frame = new JFrame(title);
//            frame.getContentPane().add(new JLabel(new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes.toArray())))));
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.pack();
//            frame.setVisible(true);
            JFrame frame = new JFrame(title);
//            frame.setLayout(null);
            Icon image = new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes.toArray())));

            frame.setPreferredSize(new Dimension(450, 450));
//            frame.getContentPane().add(new JLabel(new ImageIcon(ImageIO.read(new ByteArrayInputStream(imageBytes.toArray())))));
            frame.getContentPane().add(new JScrollPane(new JLabel(image)));
            frame.setDefaultCloseOperation(JFrame.HEIGHT);
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getLineHeight(Mat subMat) {
        int lineHeight = 0;
        float width = subMat.width();
        float height = subMat.height();

        if (height < 5 || width < 5) {
            return lineHeight;
        }

        int start = -1, end = -1;
        for (int i = 0; i < height; i++) {
            int white = 0;
            for (int j = 0; j < width; j++) {
                if (subMat.get(i, j)[0] <= 140) {
                    white++;
                    if (start == -1) {
                        start = i;
                    }
                } else {
                }
            }
            if (white == 0 && start != -1) {
                if ((i - 1 - start) < 5) {
                    lineHeight = i - 1 - start;
                    start = -1;
                    continue;
                }
                if (end == -1) {
                    end = i - 1;
                }
                lineHeight = end - start;
                break;
            }
            if (i == height - 1 && end == -1) {
                end = i;
                lineHeight = end - start;
            }
        }
        return lineHeight;

    }

    public static int[] largestBlackBatch1(BufferedImage cImage) {
        int hMin = 0;//(int) ((this.cImage.getHeight()) / 4.0);
        int hMax = cImage.getHeight(); //(int) ((this.cImage.getHeight()) * 3.0 / 4.0);
//        List<Integer> data = new ArrayList<>();
        String blacks = "";
        System.out.println("height: " + hMax);
        int lineIndex = 0, startIndex = 0, endIndex = 0, preVal = 0;
//        boolean isAnyWhitespace = false;

        boolean start = true, end = false, diter = false;

        for (int y = hMin; y < hMax; y++) {
            String black = "";
            int blc = 0, wht = 0;
            diter = false;
            for (int x = 1; x < (cImage.getWidth()); x++) {
                if (ImageUtil.isBlack(cImage, x, y, 200)) {
                    black += "1";
                    blc++;

                    if (start) {
                        startIndex = y;
                    }

                    start = false;
                    diter = true;
                } else {
                    if (!start) {
//                        black+="0";
                    }
                }
            }

            if (!start && !diter) {
                if (y - startIndex < 10) {
                    start = true;
                    diter = false;
                    continue;
                }
                endIndex = y - 1;
                end = true;
            }
//            blacks += black+"\n";
//            System.out.println("balck: "+blc);

//            if(!data.isEmpty()){
            if (blc > preVal) {
                preVal = blc;
                lineIndex = y;
            }
//            }
//            data.add(blc);
//
//            totalBlack += blc;

            if (!start && end) {
                System.out.println(blacks);
//                System.out.println("\n\n");
//                System.out.println("start Index: "+startIndex);
//                System.out.println("line index: "+lineIndex);
//                System.out.println("preVal: "+preVal);
//                System.out.println("end index: "+endIndex);
//                System.out.println("isAnyWhitespace: "+isAnyWhitespace);
//                System.out.println("\n\n");
//                System.out.println("\n\n");
//                System.out.println("\n\n");
//                System.out.println("\n\n");
                break;
            }
//            System.out.println("white: "+wht);
        }
//        System.out.println(blacks);
        if (endIndex == 0) {
            endIndex = hMax;
        }
        int[] resilt = {0, endIndex - startIndex, lineIndex - startIndex, preVal};
        return resilt;
    }
}
