package edu.sust.cse.util;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Created by Biswajit Debnath on 10-Apr-16.
 */
public class LineHeightCalculatorCopy {

    private  int height = 0;
    private  int width = 0;
    private  boolean[][] VISITED;
    private int blockId=0;

    /**
     *
     * @param subMat
     * @param blockId
     * @return Calculate all connected component line height using bfs algorithm
     * then normalize the line height and return the max line height from the list
     */
    public int getLineHeight(Mat subMat, int blockId) {

        this.height = subMat.height();
        this.width = subMat.width();
        this.VISITED = new boolean[height][width];
        this.blockId=blockId;


        int maxLineHeight = -1;
        ArrayList<Integer> lineHeights = new ArrayList<Integer>();

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                double[] d = subMat.get(h, w);

                if (VISITED[h][w] == false && d[0] == 0.0) {
                    maxLineHeight = bfs(subMat, h, w);
                    lineHeights.add(maxLineHeight);
                }
            }
        }

        maxLineHeight = normalizeLineHeight(lineHeights);

        return maxLineHeight;
    }


    private  int bfs(Mat mat, int h, int w) {
        int[] offsetH = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] offsetW = {-1, 0, 1, -1, 1, 0, 0, 1};

        int upperHeight = Integer.MAX_VALUE;
        int lowerHeight = Integer.MIN_VALUE;
        VISITED[h][w] = true;
        Queue<Integer> Q = new LinkedList<Integer>();
        Q.add(h);
        Q.add(w);

        while (!Q.isEmpty()) {
            h = Q.poll();
            w = Q.poll();
            for (int i = 0; i < 8; i++) {
                int newH = h + offsetH[i];
                int newW = w + offsetW[i];
                if (isInBoundary(newH, newW) && VISITED[newH][newW] == false) {
                    double[] d = mat.get(newH, newW);
                    if (d[0] == 0.0) {
                        VISITED[newH][newW] = true;
                        Q.add(newH);
                        Q.add(newW);
                        upperHeight = Math.min(upperHeight, newH);
                        lowerHeight = Math.max(lowerHeight, newH);
                    }
                }

            }

        }
        return (lowerHeight - upperHeight);

    }

    private  boolean isInBoundary(int h, int w) {
        return (h >= 0 && h < height) && (w >= 0 && w < width);
    }


    /**
     *
     * @param heights
     * @return first remove the line garbage line height from the list using a threshold
     * second, calculating the standard deviation set the height range and filter the all height from it
     * then return the max height from the list
     */
    private int normalizeLineHeight(ArrayList<Integer> heights){
        System.out.println("[NORMALIZE BLOCK ID : " + blockId+"]");
        ArrayList<Integer>  normalizeHeights = new ArrayList<Integer>();
        System.out.print("ALL HEIGHT: ");
        for(Integer height:heights){
            System.out.print(height+" ");
        }
        System.out.println();
        heights = getHeightsAfterGarbageFilter(heights,18);
        double average;
        double sum=0.0f;
        System.out.print("GARBAGE FILTER HEIGHT: ");
        for(Integer height:heights){
            System.out.print(height+" ");
            sum+=height;
        }
        System.out.println();
        average = sum/heights.size();

        sum=0.0;
        for(Integer height:heights){
            sum+=Math.pow(height-average, 2);
        }
        double variance = sum / heights.size();
        double standardDeviation = Math.sqrt(variance);

        int maxLimit = (int)(average+standardDeviation);
        int minLimit = (int)(average-standardDeviation);

        normalizeHeights.addAll(heights.stream().filter(height -> minLimit <= height && height <= maxLimit).collect(Collectors.toList()));

        int lineHeight=-1;
        if(normalizeHeights.size()>0) {
/*
            Collections.sort(normalizeHeights);
            lineHeight= normalizeHeights.get(normalizeHeights.size() - 1);
*/
            int avarage=0;
            int sum2=0;
            for(int height:normalizeHeights){
                sum2+=height;
            }
            lineHeight=sum2/normalizeHeights.size();
        }else if(heights.size()>0){
            Collections.sort(heights);
            lineHeight= heights.get(heights.size() - 1);
        }
        System.out.println("LINE HEIGHT: " + lineHeight);
        System.out.println();
        return lineHeight;
    }

    /**
     *
     * @param heights
     * @param garbageHeight
     * @return all the heights which is greater than garbageHeight
     */
    private ArrayList<Integer> getHeightsAfterGarbageFilter(ArrayList<Integer> heights, int garbageHeight){
        return heights.stream().filter(height -> height > garbageHeight).collect(Collectors.toCollection(ArrayList::new));
    }


}
