package org.usfirst.frc.team6644.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
//import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import org.usfirst.frc.team6644.robot.Robot;
import org.usfirst.frc.team6644.robot.RobotPorts;

public class DriveMotors extends Subsystem {
	//private static SpeedControllerGroup leftDriveLonePWM;
	private static Spark leftDriveSplitPWM;
	//private static SpeedControllerGroup rightDriveLonePWM;
	private static Spark rightDriveSplitPWM;
	private static RobotDrive drive;
	private double motorSafteyExpireTime = 0.3;// sets the PWM to expire in 0.3 seconds after the last call of .Feed()
	private boolean disableMotors;
	/*
	 * 
	 * class variables for driving
	 */
	private boolean isRunning = false;
	// joystick stuff
	private Joystick joystick = new Joystick(RobotPorts.JOYSTICK.get());
	private double left = 0;
	private double right = 0;

	public DriveMotors() {
		//leftDriveGroup = new Spark(RobotPorts.LEFT_DRIVE_PWM_LONE.get());
		leftDriveSplitPWM=new Spark(RobotPorts.LEFT_DRIVE_PWM_SPLIT.get());
		//rightDriveLonePWM = new Spark(RobotPorts.RIGHT_DRIVE_PWM_LONE.get());
		rightDriveSplitPWM=new Spark(RobotPorts.RIGHT_DRIVE_PWM_SPLIT.get());

		//drive = new RobotDrive(leftDrivePWM, rightDrivePWM);
		disableMotors=false;

	}

	/*
	 * 
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
		drive.arcadeDrive(stick);
	}

	public void tankDrive(double left, double right) {
		// left and right should be double values at/between -1 and 1.

		// Use enableSaftey for turning on drive motor safety. Not much sense in turning
		// safety on in one motor but not the other.
		// DO NOT HAVE MOTOR INPUTS GREATER IN MAGNITUDE THAN 1
		if (Math.abs(left) > 1 || Math.abs(right) > 1) {
			if (left > 1) {
				if (right > 1) {
					left = 1;
					right = 1;
				} else if (right < -1) {
					left = 1;
					right = -1;
				} else {
					left = 1;
				}
			} else if (left < -1) {
				if (right > 1) {
					left = -1;
					right = 1;
				} else if (right < -1) {
					left = -1;
					right = -1;
				} else {
					left = -1;
				}
			} else {
				if (right > 1) {
					right = 1;
				} else {
					right = -1;
				}
			}
			drive.tankDrive(left, right);
			System.out.println("DANGER: MOTOR OUTPUTS ARE GREATER IN MAGNITUDE THAN 1");
		} else {
			drive.tankDrive(left, right);
		}
	}

	public void stop() {
		disableSafety();
		drive.tankDrive(0, 0);
		Robot.drivemotors.setIsRunning(false);
	}

	public void startAutoMode() {
		disableSafety();
		setIsRunning(true);
	}

	public void startTeleopMode() {
		enableSaftey();
		setIsRunning(true);
	}

	/*
	 * 
	 * methods for driving in Teleop
	 */
	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public void driveWithJoystick() {
		double forwardModifier = 1 - Math.abs(joystick.getY());
		double sensitivity = (-joystick.getRawAxis(3) + 1) / 2;
		if (disableMotors) {
			sensitivity = 0;
		}
		left = (forwardModifier * joystick.getX() - joystick.getY()) * sensitivity;
		right = (-forwardModifier * joystick.getX() - joystick.getY()) * sensitivity;
		tankDrive(left, right);
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

	/*
	 * stuff for SmartDashboard
	 */
	public boolean isRunning() {
		return isRunning;
	}

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
}
