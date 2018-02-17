package org.usfirst.frc.team6644.robot.subsystems;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team6644.robot.Robot;
import org.usfirst.frc.team6644.robot.RobotPorts;

public class DriveMotors extends Subsystem {
	// Drivebase stuff
	private static DriveMotors instance;
	private static DifferentialDrive drive;
	private static final double motorSafteyExpireTime = 0.3;// sets the PWM to expire in 0.3 seconds after the last call
															// of .Feed()
	private boolean disableMotors;
	private double left = 0;
	private double right = 0;

	// Autonomous Stuff
	private static boolean drivingFromHistory;
	private static ArrayList<double[]> driveHistory;
	private static int histories;
	private static int historyCounter;
	private static PIDController pidLoopLeft;
	private static PIDController pidLoopRight;

	// Encoder stuff
	private static Encoder leftEncoder;
	private static Encoder rightEncoder;

	// Testing Stuff (please clear when done using these)
	//TODO: DELETE THIS WHEN DONE
	private static int rateCounter = 0;
	private static boolean calculateScale=false;
	private static boolean pressed=false;
	private static JoystickButton searchForScale=new JoystickButton(Robot.joystick,11);
	
	public static DriveMotors getInstance() {
		if (instance == null) {
			instance = new DriveMotors();
		}
		return instance;
	}

	private DriveMotors() {
		// create a DifferentialDrive
		drive = new DifferentialDrive(new Spark(RobotPorts.LEFT_DRIVE_PWM_SPLIT.get()),
				new Spark(RobotPorts.RIGHT_DRIVE_PWM_SPLIT.get()));
		disableMotors = false;

		// do encoder stuff
		leftEncoder = new Encoder(0, 1);
		rightEncoder = new Encoder(2, 3);
		leftEncoder.reset();
		rightEncoder.reset();
		leftEncoder.setReverseDirection(true);
		leftEncoder.setSamplesToAverage(2);
		rightEncoder.setSamplesToAverage(2);
		encoderSetDistancePerPulse(0.0020454076);

		// set autonnomous stuff
		driveHistory = null;
		histories = 0;
		drivingFromHistory = false;
		historyCounter = 0;
		pidLoopLeft = new PIDController(0, 0, 0, leftEncoder, new Spark(RobotPorts.LEFT_DRIVE_PWM_SPLIT.get()));
		pidLoopRight = new PIDController(0, 0, 0, leftEncoder, new Spark(RobotPorts.RIGHT_DRIVE_PWM_SPLIT.get()));
		pidLoopLeft.disable();
		pidLoopRight.disable();
		pidLoopLeft.setOutputRange(-1, 1);
		pidLoopRight.setOutputRange(-1, 1);
	}

	/*
	 * methods for drive motors
	 */
	public void enableSaftey() {
		drive.setSafetyEnabled(true);
		drive.setExpiration(motorSafteyExpireTime);
	}

	public void disableSafety() {
		drive.setSafetyEnabled(false);
		drive.setExpiration(motorSafteyExpireTime);
	}

	public void arcadeDrive(double linear, double turn) {
		drive.arcadeDrive(linear, turn);
	}

	public void arcadeDrive(GenericHID stick) {
		drive.arcadeDrive(stick.getY(), stick.getX());
	}

	public void tankDrive(double left, double right, boolean squaredInputs) {
		// left and right should be double values at/between -1 and 1.

		// Use enableSaftey for turning on drive motor safety. Not much sense in turning
		// safety on in one motor but not the other.
		// DO NOT HAVE MOTOR INPUTS GREATER IN MAGNITUDE THAN 1
		if (Math.abs(left) > 1 || Math.abs(right) > 1) {
			System.out.println("ERROR: MOTOR OUTPUTS ARE GREATER IN MAGNITUDE THAN 1");
		}
		drive.tankDrive(left, right, squaredInputs);
	}

	public void stop() {
		disableSafety();
		drive.stopMotor();
		if (pidLoopLeft.isEnabled()) {
			pidLoopLeft.disable();
		}
		if (pidLoopRight.isEnabled()) {
			pidLoopRight.disable();
		}
	}

	public void startAutoMode() {
		disableSafety();
		// set setpoint
		pidLoopLeft.enable();
		pidLoopRight.enable();
	}

	public void startTeleopMode() {
		enableSaftey();
	}

	/*
	 * 
	 * methods for driving in Teleop
	 */

	public void driveWithJoystick(boolean squared, boolean compensate) {
		double forwardModifier = 1 - Math.abs(Robot.joystick.getY());
		double sensitivity = (-Robot.joystick.getRawAxis(3) + 1) / 2;
		if (disableMotors) {
			sensitivity = 0;
		}
		left = (forwardModifier * Robot.joystick.getX() - Robot.joystick.getY()) * sensitivity;
		right = (-forwardModifier * Robot.joystick.getX() - Robot.joystick.getY()) * -sensitivity;
		if (squared) {
			left = Math.copySign(left * left, left);
			right = Math.copySign(right * right, right);
		}
		if (compensate) {
			compensateDrive();
		}
		tankDrive(left, right, false);
		//-----------------------------------------------------------------
		//TODO: DELETE THIS WHEN DONE
		
		if(!pressed) {
			calculateScale=searchForScale.get();
		}
		
		if(calculateScale) {
			findScaleFactor();
		}
		//------------------------------------------------------------------
	}

	public void compensateDrive() {
		double[] rate = encoderRate();
		double difference = rate[0] - rate[1];
		double expectedDifference = left - right;
		// TODO: find scale factor between rate and tankDrive inputs.
	}

	public void toggleMotorDisableState() {
		disableMotors = !disableMotors;
	}

	public void disableMotors() {
		disableMotors = true;
	}

	public void enableMotors() {
		disableMotors = false;
	}

	public void returnPIDInput() {

	}

	public void usePIDOutput() {

	}

	/*
	 * Encoder thingies
	 */
	public void freeEncoders() {
		System.out.println("\n\n\nFREED\n\n\n");
		leftEncoder.free();
		rightEncoder.free();
	}

	public void encoderSetDistancePerPulse(double r) {
		leftEncoder.setDistancePerPulse(r);
		rightEncoder.setDistancePerPulse(r);
	}

	public int[] encoderGet() {
		int[] thing = new int[2];
		thing[0] = leftEncoder.get();
		thing[1] = rightEncoder.get();
		return thing;
	}

	public int[] encoderRaw() {
		int[] thing = new int[2];
		thing[0] = leftEncoder.getRaw();
		thing[1] = rightEncoder.getRaw();
		return thing;
	}

	public boolean[] encoderDirection() {
		boolean[] thing = new boolean[2];
		thing[0] = leftEncoder.getDirection();
		thing[1] = rightEncoder.getDirection();
		return thing;
	}

	public double[] encoderDistance() {
		double[] thing = new double[2];
		thing[0] = leftEncoder.getDistance();
		thing[1] = rightEncoder.getDistance();
		return thing;
	}

	public double[] encoderRate() {
		double[] thing = new double[2];
		thing[0] = leftEncoder.getRate();
		thing[1] = rightEncoder.getRate();
		return thing;
	}

	public void encoderReset() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	/*
	 * Stuff for drive histories
	 * 
	 * Hopefully this is a really quick way of getting some sort of autonomous mode
	 * up if sensors end up taking too long.
	 */

	public void startHistory() {
		driveHistory = new ArrayList<double[]>();
	}

	public void updateHistory() {
		// Please don't leave this method running for so long that the RoboRio runs out
		// of RAM... That will make all of the programmers sad.
		driveHistory.add(getDriveOutputs());
	}

	public void endHistory() {
		endHistory(histories);
		histories++;
	}

	public void endHistory(int n) {
		File dh = new File("custom" + File.separator + "driveHistory" + n + ".txt");

		// Check if file already exists. If so, delete it.
		if (dh.exists()) {
			dh.delete();
		}

		// Create new file
		try {
			dh.createNewFile();
		} catch (IOException e) {
			System.out.println("Hey, look at DriveMotors.endHistory(int n)... Something's wrong 1.\n" + e);
		}

		// load ArrayList into a StringBuilder
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < driveHistory.size(); i++) {
			output.append(driveHistory.get(i)[0]);
			output.append(";");
			output.append(driveHistory.get(i)[1]);
			output.append(";");
		}

		// delete array
		driveHistory = null;

		// write StringBuilder output to File dh
		try {
			FileWriter scribble = new FileWriter(dh);
			scribble.write(output.toString());
			scribble.close();
		} catch (IOException e) {
			System.out.println("Hey, look at DriveMotors.endHistory(int n)... Something's wrong 2.\n" + e);
		}
	}

	public void countHistories() {
		File dh = new File("custom" + File.separator + "driveHistory0.txt");
		int counter = 0;
		while (dh.exists()) {
			counter++;
			dh = new File("custom" + File.separator + "driveHistory" + counter + ".txt");
		}
		histories = counter;
	}

	public void loadHistory(int n) {
		File dh = new File("custom" + File.separator + "driveHistory" + n + ".txt");
		try {
			// scan through driveHistory text file for left and right motor outputs and add
			// those to driveHistory.
			Scanner scan = new Scanner(dh);
			scan.useDelimiter(";");
			while (scan.hasNext()) {
				driveHistory.add(new double[] { Double.parseDouble(scan.next()), Double.parseDouble(scan.next()) });
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(
					"Look in DriveMotors, no such driveHistory" + n + " file found... Something's wrong 0.\n" + e);
		}
	}

	public void startDrivingFromHistory() {
		drivingFromHistory = true;
		historyCounter = 0;
	}

	public boolean checkDrivingFromHistory() {
		return !drivingFromHistory;
	}

	public void abortDrivingFromHistory() {
		driveHistory = null;
		histories = 0;
		drivingFromHistory = false;
		historyCounter = 0;
	}

	/*
	 * stuff for SmartDashboard
	 */

	public double[] getDriveOutputs() {
		// returns an array [left,right]
		double[] driveOutputs = new double[2];
		driveOutputs[0] = left;
		driveOutputs[1] = right;
		return driveOutputs;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/*
	 * Testing stuff
	 */

	public void testDrive(boolean squared, boolean compensate) {// TODO:DELETE THIS WHEN DONE
		double forwardModifier = 1 - Math.abs(Robot.joystick.getY());
		double sensitivity = (-Robot.joystick.getRawAxis(3) + 1) / 2;
		if (disableMotors) {
			sensitivity = 0;
		}
		left = (forwardModifier * Robot.joystick.getX() - Robot.joystick.getY()) * sensitivity;
		right = (-forwardModifier * Robot.joystick.getX() - Robot.joystick.getY()) * -sensitivity;
		if (squared) {
			left = Math.copySign(left * left, left);
			right = Math.copySign(right * right, right);
		}
		if (compensate) {
			compensateDrive();
		}
	}

	public void findScaleFactor() {
		double[] rateSamples = new double[2];
		double[] driveInputs = new double[2];
		double scaleFactorLeft = 0;
		double scaleFactorRight = 0;
		double leftIn = 0;
		double rightIn = 0;
		if (rateCounter < 50) {
			calculateScale=true;
			pressed=true;
			rateSamples = encoderRate();
			driveInputs = getDriveOutputs();
			scaleFactorLeft += rateSamples[0] / driveInputs[0];
			scaleFactorRight += rateSamples[1] / driveInputs[1];
			leftIn += driveInputs[0];
			rightIn += driveInputs[1];
			rateCounter++;
		} else {
			calculateScale=false;
			pressed=false;
			scaleFactorLeft/=50;
			scaleFactorRight/=50;
			leftIn/=50;
			rightIn/=50;
			System.out.println("\n\n\n\n-----------------------------------------------");
			System.out.println("Input: "+leftIn+"; Scale Factor: "+scaleFactorLeft+";");
			System.out.println("Input: "+rightIn+"; Scale Factor: "+scaleFactorRight+";");
		}
	}
}