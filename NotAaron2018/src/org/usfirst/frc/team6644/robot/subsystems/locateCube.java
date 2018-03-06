package org.usfirst.frc.team6644.robot.subsystems;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionPipeline;

public class locateCube extends Subsystem implements VisionPipeline{
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.vision.VisionPipeline#process(org.opencv.core.Mat)
	 */
	/**
	 * @param image
	 * Image to process
	 *
	 *	@Steps
	 * Input ->
	 * Resize ->
	 * Blur0 ->
	 * HSV Threshold ->
	 * Blur1 ->
	 * Finds Blobs 
	 * 
	 *<p>Blur1 stored as Input</p>
	 */

	private MatOfKeyPoint findBlobsOutput = new MatOfKeyPoint();
	private List<KeyPoint> ListOfPoints = new ArrayList<KeyPoint>(); 
	private double picWidth = 160.0;
	private double picHeight = 120.0;
	@Override
	public void process(Mat image) {
		if(image != null) {
			Imgproc.resize(image, image, new Size(picWidth, picHeight), 0, 0, Imgproc.INTER_CUBIC);
			Imgproc.medianBlur(image, image, 25);
			Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2HSV);
			Core.inRange(image, new Scalar(0.0, 115, 65), new Scalar(44, 214, 255), image);
			Imgproc.medianBlur(image, image, 33);
			double[] blobCircularity = {0.0 , 1.0};
			findBlobs(image, 1.0, blobCircularity , false, findBlobsOutput);
		
		}
	}
	/**
	 * 
	 * @param A MatOfKeyPoints from a findblobOutput
	 * @return
	 * 	The coordinates of the center of the largest blob [0] = x, [1] = y so that ([0], [1]) is the center of the blob
	 */
	private int[] getLocationOfLargestBlob(MatOfKeyPoint keyPointsInput) {
		int largestBlob = 0;
		int amountOfBlobs = 0;
		int[] blobLoc = {0 , 0};
		if(!keyPointsInput.toList().isEmpty()) {
			ListOfPoints = findBlobsOutput.toList();
			for (int index = 0; index < ListOfPoints.size() - 1; index++) {
				amountOfBlobs = ListOfPoints.size();
				if (ListOfPoints.get(index + 1).size > ListOfPoints.get(index).size) {
					largestBlob = index + 1;
				} // end if
				if (!(ListOfPoints.get(index + 1).size > ListOfPoints.get(index).size)) {
					largestBlob = index;
					
				} // end else if
			} // end for
			
			blobLoc[0] = (int) ListOfPoints.get(largestBlob).pt.x;
			blobLoc[1] = (int) ListOfPoints.get(largestBlob).pt.y;
		}//end if
		else if(keyPointsInput.toList().isEmpty()) {
			blobLoc[0] = -1;
			blobLoc[1] = -1;
		}
		
		return blobLoc;//if no blobs found, then blob location : (-1, -1)
	}
	/**
	 * runs getLocationOfLargestBlob, then does some math-a-magic
	 * @return
	 * Distance to the central vertical Axis
	 */
	private double centerrobotXAxis() {
		double midxaxis = picWidth/2;
		if (getLocationOfLargestBlob(findBlobsOutput)[0] <= midxaxis) {
			return midxaxis - getLocationOfLargestBlob(findBlobsOutput)[0];
		} else {
			return getLocationOfLargestBlob(findBlobsOutput)[0] - midxaxis;
		}
	}// returns distance on the x axis from the center
	/**
	 * runs getLocationOfLargestBlob(findBlobsOutput), then puts the result of centerrobotXAxis onto the smart dashboard with a key of: "Distance from center: " 
	 */
	public void centerX() {
		getLocationOfLargestBlob(findBlobsOutput);
		SmartDashboard.putNumber("Distance from center: ", centerrobotXAxis());
		
		
	}
	@Override
	protected void initDefaultCommand() {
		
	}
	private void findBlobs(Mat input, double minArea, double[] circularity, Boolean darkBlobs, MatOfKeyPoint blobList) {
		FeatureDetector blobDet = FeatureDetector.create(FeatureDetector.SIMPLEBLOB);
		try {
			File tempFile = File.createTempFile("config", ".xml");

			StringBuilder config = new StringBuilder();

			config.append("<?xml version=\"1.0\"?>\n");
			config.append("<opencv_storage>\n");
			config.append("<thresholdStep>10.</thresholdStep>\n");
			config.append("<minThreshold>50.</minThreshold>\n");
			config.append("<maxThreshold>220.</maxThreshold>\n");
			config.append("<minRepeatability>2</minRepeatability>\n");
			config.append("<minDistBetweenBlobs>10.</minDistBetweenBlobs>\n");
			config.append("<filterByColor>1</filterByColor>\n");
			config.append("<blobColor>");
			config.append((darkBlobs ? 0 : 255));
			config.append("</blobColor>\n");
			config.append("<filterByArea>1</filterByArea>\n");
			config.append("<minArea>");
			config.append(minArea);
			config.append("</minArea>\n");
			config.append("<maxArea>");
			config.append(Integer.MAX_VALUE);
			config.append("</maxArea>\n");
			config.append("<filterByCircularity>1</filterByCircularity>\n");
			config.append("<minCircularity>");
			config.append(circularity[0]);
			config.append("</minCircularity>\n");
			config.append("<maxCircularity>");
			config.append(circularity[1]);
			config.append("</maxCircularity>\n");
			config.append("<filterByInertia>1</filterByInertia>\n");
			config.append("<minInertiaRatio>0.1</minInertiaRatio>\n");
			config.append("<maxInertiaRatio>" + Integer.MAX_VALUE + "</maxInertiaRatio>\n");
			config.append("<filterByConvexity>1</filterByConvexity>\n");
			config.append("<minConvexity>0.95</minConvexity>\n");
			config.append("<maxConvexity>" + Integer.MAX_VALUE + "</maxConvexity>\n");
			config.append("</opencv_storage>\n");
			FileWriter writer;
			writer = new FileWriter(tempFile, false);
			writer.write(config.toString());
			writer.close();
			blobDet.read(tempFile.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		blobDet.detect(input, blobList);
	}

}
	/**
	 * 
	 */


