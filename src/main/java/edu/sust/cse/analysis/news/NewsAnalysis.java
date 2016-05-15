package edu.sust.cse.analysis.news;

import com.recognition.software.jdeskew.ImageUtil;

import edu.sust.cse.analysis.util.Conversion;
import edu.sust.cse.analysis.util.PointLengthCalculator;
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
import java.util.*;

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
        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-01-300c.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-02-300.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-03-300.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\sc-04-300.jpg");
//        Mat inputImageMat = Highgui.imread("G:\\Study\\Thesis\\06-12-2015\\sc-01-300.jpg");D:\OpenCV_Library\resources\Scan_Img\image
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\06-12-2015\\etable_sample_1.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-01.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-02.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-03.jpg");
//       Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-04.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-05.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-06.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-07.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-08.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-09.jpg");
 //       Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-10.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Bangla_OCR\\[COLUMN_8][LINE_HEIGHT_32_]_2016-05-14_09-18_PM.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-11.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-t-01.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-t-02.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-t-03.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-t-04.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\Scan_Img\\image\\07-04-16\\sc-t-05.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Biswajit\\4-2\\CSE 425\\Class Notes\\CSE_425_01_1_01.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Bangla_OCR\\Image0.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Bangla_OCR\\Image1.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Bangla_OCR\\Image2.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\table\\t-1.png");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\table\\t-2.png");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\table\\t-3.png");
//        Mat inputImageMat = Highgui.imread("D:\\OpenCV_Library\\resources\\table\\t-4.png");
//        Mat inputImageMat = Highgui.imread("D:\\Bangla_OCR\\[SUB_HEADLINE_9][LINE_HEIGHT_64_64_]_2016-04-11_12-17_AM.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Bangla_OCR\\[COLUMN_7][LINE_HEIGHT_0_]_2016-05-15_01-38_PM.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Bangla_OCR\\[SUB_HEADLINE_19][LINE_HEIGHT_46_]_2016-05-15_01-53_PM.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Bangla_OCR\\[SUB_HEADLINE_9][LINE_HEIGHT_64_64_]_2016-04-11_12-17_AM.jpg");
//        Mat inputImageMat = Highgui.imread("D:\\Bangla_OCR\\[SUB_HEADLINE_19][LINE_HEIGHT_46_]_2016-05-15_01-53_PM.jpg");
        PixelFileWriter pixelFileWriter = new PixelFileWriter();
        ViewerUI.show("ORIGINAL", inputImageMat, ViewableUI.SHOW_ORIGINAL);


        /**
         * We have explained some filters which main goal is to smooth an input
         * image. However, sometimes the filters do not only dissolve the noise,
         * but also smooth away the edges
         */
        Mat filteredImage = new Mat();
        Imgproc.bilateralFilter(inputImageMat, filteredImage, -1, 50, 10);
        ViewerUI.show("NOISE_FILTER", filteredImage, ViewableUI.SHOW_NOISE_FILTER);

        /**
         * Pixel filtered which is lower than 175, it's make the image more clean
         */
        Mat threshedImage = new Mat();
        Imgproc.threshold(filteredImage, threshedImage, 175, 255, Imgproc.THRESH_BINARY);
        filteredImage = threshedImage.clone();

        ViewerUI.show("PIXEL_REPLACED_FILTER", threshedImage, ViewableUI.SHOW_NOISE_FILTER);


        Histogram.showHistogram("FULL_IMAGE_HISTOGRAM", filteredImage);

        /**
         * Canny operation is performed for e.dge detection of an image
         */
        Imgproc.Canny(filteredImage, filteredImage, 10, 150);
        ViewerUI.show("EDGE_DETECTED", filteredImage, ViewableUI.SHOW_EDGE_DETECTION);

        Size filteredImageSize = filteredImage.size();
        int width = (int) filteredImageSize.width;
        int height = (int) filteredImageSize.height;
        PointLengthCalculator pointLenCal = new PointLengthCalculator(filteredImage);
        PointDistance[][] pointDistances = pointLenCal.getPointDistances();

        Conversion conversion = new Conversion(filteredImage, pointDistances);
        int[][] blackWhite = conversion.getBlackWhitePixelInfo();
        Mat convertArea = conversion.getConvertedArea();
        ViewerUI.show("CONVERSION", convertArea, ViewableUI.SHOW_CONVERSION);

        ImageBorderDetectionBFS imgBFS = new ImageBorderDetectionBFS();
//        ArrayList<BorderItem> borderItems = imgBFS.getBorder(blackWhite, width, height, filteredImage, inputImageMat);
        ArrayList<BorderItem> borderItems = imgBFS.getBorder(blackWhite, width, height, filteredImage, threshedImage);
        System.out.println("[BORDER ITEM COUNTS][" + borderItems.size() + "]");

        boolean[] imageIndexer = new boolean[borderItems.size()];
        int[] blockLineHeights =  new int[borderItems.size()];
       // int[] lineHeight = new int[borderItems.size()];
        ContentType[] contentTypes = new ContentType[borderItems.size()];
        int highestLineHeight = -1, lowestLineHeight = 10000000;
        int totalHeight = 0, notImage = 0;

        for (int i = 0; i < borderItems.size(); i++) {
            blockLineHeights[i] = 0;
            BorderItem borderItem = borderItems.get(i);
            if (borderItem.getIsImage()) {
                /*
                    Only Block Histogram Showing
                 */
                double[] linePercetage = Histogram.getLinePercetageFromHistogram(borderItem.getBlock(), 80.0);
                String vPer = String.format("%.2f", linePercetage[0]);
                String hPer = String.format("%.2f", linePercetage[1]);


                if (linePercetage[0] < 4.5 && linePercetage[1] < 4.5) {
                    Histogram.showHistogram("TABLE_" + i + "_HISTOGRAM", borderItem.getBlock());
                    ViewerUI.show("[TABLE_" + i + " ][V][" + vPer + "][H][" + hPer + "]", borderItem.getBlock(), ViewableUI.SHOW_IMAGE);
                    contentTypes[i] = ContentType.TABLE;
                } else {
                    Histogram.showHistogram("IMAGE_" + i + "_HISTOGRAM", borderItem.getBlock());
                    ViewerUI.show("[IMAGE_" + i + " ][V][" + vPer + "][H][" + hPer + "]", borderItem.getBlock(), ViewableUI.SHOW_IMAGE);
                    contentTypes[i] = ContentType.IMAGE;
                }
                imageIndexer[i] = true;
                continue;
            } else {
                notImage++;
                imageIndexer[i] = false;
                contentTypes[i] = ContentType.UNKNOWN;
            }

            Mat subBlock = new Mat();
            Imgproc.cvtColor(borderItem.getBlock(), subBlock, Imgproc.COLOR_RGB2GRAY, 0);
            Imgproc.threshold(subBlock, subBlock, 175, 255, Imgproc.THRESH_BINARY);
            ZLineHeightCalculator heightCalculator = new ZLineHeightCalculator();
//            LineHeightCalculator heightCalculator = new LineHeightCalculator();
            int blockHeight = heightCalculator.getLineHeight(subBlock,i);
            totalHeight += blockLineHeights[i]=blockHeight;
            subBlock.release();

            if (blockLineHeights[i] > highestLineHeight) {
                highestLineHeight = blockLineHeights[i];
            }
            if (blockLineHeights[i] < lowestLineHeight) {
                lowestLineHeight = blockLineHeights[i];
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
        /*for (int i = 0; i < borderItems.size(); i++) {
            if (!imageIndexer[i]) {
                if (lineHeight[i] > lowestLineHeight + 52 && lineHeight[i] >= 160) {
                    System.out.println("HeadLine");
                    ViewerUI.show("Index - "+i+" [LineHeight_" + lineHeight[i] + "]_Headline" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_HEADING);
//                    ViewerUI.show("Headline-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_HEADING);

                } else if (lineHeight[i] > lowestLineHeight + 32 && lineHeight[i] >= 48 && lineHeight[i] < 65) {
                    System.out.println("Sub HeadLine");
                    ViewerUI.show("Index - "+i+" [LineHeight_" + lineHeight[i] + "]_Sub_Headline" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_SUB_HEADING);
//                    ViewerUI.show("Sub Headline-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_SUB_HEADING);

                } else {
                    System.out.println("Column");
                    ViewerUI.show("Index - "+i+" [LineHeight_" + lineHeight[i] + "]_Column" + i, borderItems.get(i).getBlock(), ViewableUI.SHOW_COLUMN);
//                    ViewerUI.show("Column-Histogram" + i, Histogram.getHistogram(borderItems.get(i).getBlock()), ViewableUI.SHOW_HISTOGRAM_COLUMN);

                }
            }
        }*/

//        for (int i = 0; i < borderItems.size(); i++) {
//            if (!imageIndexer[i]) {
//                if (lineHeight[i] >= 145) {
//                    Histogram.showHistogram("[HEADLINE_" + i + "][LH_" + lineHeight[i]+"]_HISTOGRAM", borderItems.get(i).getBlock());
//                    ViewerUI.show("[HEADLINE_" + i + "[LINE_HEIGHT_" + lineHeight[i] + "_"+blockHeights[i]+"_]", borderItems.get(i).getBlock(), ViewableUI.SHOW_HEADING);
//                    contentTypes[i] = ContentType.HEADLINE;
//                } else if (lineHeight[i] >= 48 && lineHeight[i] < 75) {
//                    Histogram.showHistogram("[SUB_HEADLINE_" + i + "][LH_" + lineHeight[i]+"]_HISTOGRAM", borderItems.get(i).getBlock());
//                    ViewerUI.show("[SUB_HEADLINE_" + i + "][LINE_HEIGHT_" + lineHeight[i] + "_"+blockHeights[i]+"_]", borderItems.get(i).getBlock(), ViewableUI.SHOW_SUB_HEADING);
//                    contentTypes[i] = ContentType.SUB_HEADLINE;
//
//                } else {
//
//                    Histogram.showHistogram("[COLUMN_" + i + "][LH_" + lineHeight[i] + "]_HISTOGRAM", borderItems.get(i).getBlock());
//                    ViewerUI.show("[COLUMN_" + i + "][LINE_HEIGHT_" + lineHeight[i] + "_"+blockHeights[i]+"_]", borderItems.get(i).getBlock(), ViewableUI.SHOW_COLUMN);
//                    contentTypes[i] = ContentType.COLUMN;
//
//                }
//            }
//        }

        for (int i = 0; i < borderItems.size(); i++) {
            if (!imageIndexer[i]) {

                if (blockLineHeights[i] >= 100) {
                 //   Histogram.showHistogram("[HEADLINE_" + i + "][LH_" + lineHeight[i]+"]_HISTOGRAM", borderItems.get(i).getBlock());
                    ViewerUI.show("[HEADLINE_" + i + "[LINE_HEIGHT_"+blockLineHeights[i]+"_]", borderItems.get(i).getBlock(), ViewableUI.SHOW_HEADING);
                    contentTypes[i] = ContentType.HEADLINE;
                } else if (blockLineHeights[i] >= 45 && blockLineHeights[i] < 100) {
                   // Histogram.showHistogram("[SUB_HEADLINE_" + i + "][LH_" + lineHeight[i]+"]_HISTOGRAM", borderItems.get(i).getBlock());
                    ViewerUI.show("[SUB_HEADLINE_" + i + "][LINE_HEIGHT_"+blockLineHeights[i]+"_]", borderItems.get(i).getBlock(), ViewableUI.SHOW_SUB_HEADING);
                    contentTypes[i] = ContentType.SUB_HEADLINE;

                } else if(blockLineHeights[i]>-1){
                   // Histogram.showHistogram("[COLUMN_" + i + "][LH_" + lineHeight[i] + "]_HISTOGRAM", borderItems.get(i).getBlock());
                    ViewerUI.show("[COLUMN_" + i + "][LINE_HEIGHT_"+blockLineHeights[i]+"_]", borderItems.get(i).getBlock(), ViewableUI.SHOW_COLUMN);
                    contentTypes[i] = ContentType.COLUMN;

                }else{
                    ViewerUI.show("[GARBAGE_" + i + "]", borderItems.get(i).getBlock(), ViewableUI.SHOW_GARBAGE);
                    contentTypes[i] = ContentType.UNKNOWN;
                }
            }
        }


        for(int i=0;i<blockLineHeights.length;i++){
            if(!imageIndexer[i])
                System.out.print(blockLineHeights[i]+" ");
        }
        System.out.println("");

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
            boolean whiteBit = false;
            for (int j = 0; j < width; j++) {
                if (subMat.get(i, j)[0] <= 180) {
                    whiteBit = true;
                    if (start == -1) {
                        start = i;
                    }
                    break;
                }
            }
            if (!whiteBit && start != -1) {
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
