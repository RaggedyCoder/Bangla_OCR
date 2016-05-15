package edu.sust.cse.util;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Created by Biswajit Debnath on 10-Apr-16.
 */
public class ZLineHeightCalculator {

    private int OVERLAPPED_PIXEL=18;
    private int CONTINOUS_ROW_FOR_LINE_MAKING=10;

    public int getLineHeight(Mat subMat, int blockId) {
        System.out.println("BLOCK ID >>> " + blockId);
        Mat rowMatrix;
        PixelFileWriter  pixelFileWriter = new PixelFileWriter();
        pixelFileWriter.writeInFile("Pixel.txt","o",".",subMat);
        int numOfRows = subMat.rows();
        int max = Integer.MIN_VALUE;
        int blackPixels;
        int blackBits[] = new int[numOfRows];
        for (int i = 0; i < numOfRows; i++) {
            rowMatrix = subMat.row(i);
            blackPixels = rowMatrix.cols()-Core.countNonZero(rowMatrix);
            blackBits[i] = blackPixels>OVERLAPPED_PIXEL?blackPixels:0;
            max = Math.max(blackPixels, max);
            System.out.println(i+">>>"+blackBits[i]);
        }


        int lineCount=0;
        int maxBlackBitInRow=Integer.MIN_VALUE;
        int continousRowFound=0;
        int continousRowMax=Integer.MIN_VALUE;
        int startMatraLineIndex=-1;
        int endMatraLineIndex=-1;
        int index=0;


        for(int i=0;i<numOfRows;i++){
            if(blackBits[i]!=0){
                continousRowFound++;
                if(maxBlackBitInRow<blackBits[i]){
                    maxBlackBitInRow=blackBits[i];
                    index=i;
                }
            }else{
                if(continousRowFound>CONTINOUS_ROW_FOR_LINE_MAKING){
                    System.out.println("MATRA LINE INDEX: " + index);
                    if(startMatraLineIndex==-1){
                        startMatraLineIndex=index;
                    }
                    endMatraLineIndex=index;
                    lineCount++;
                    continousRowMax=Math.max(continousRowMax,continousRowFound);

                }
                maxBlackBitInRow=Integer.MIN_VALUE;
                continousRowFound=0;
            }
        }



        System.out.println("START MATRA LINE INDEX : " + startMatraLineIndex);
        System.out.println("END MATRA LINE INDEX : " + endMatraLineIndex);
        System.out.println("LINE COUNT : " + lineCount);
        System.out.println("CONTINOUS ROW MAX : " + continousRowMax);

        if(endMatraLineIndex==startMatraLineIndex) return continousRowMax;

        return (endMatraLineIndex-startMatraLineIndex)/lineCount;

    }


}
