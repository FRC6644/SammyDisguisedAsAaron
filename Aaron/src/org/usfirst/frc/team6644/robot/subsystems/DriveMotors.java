package org.usfirst.frc.team6644.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import org.usfirst.frc.team6644.robot.Robot;
import org.usfirst.frc.team6644.robot.RobotPorts;

public class DriveMotors extends Subsystem {
	private static DifferentialDrive drive;
	private final double motorSafteyExpireTime = 0.3;// sets the PWM to expire in 0.3 seconds after the last call of .Feed()
	private boolean disableMotors;
	private double left = 0;
	private double right = 0;

	public DriveMotors() {
		SpeedControllerGroup leftDriveGroup = new SpeedControllerGroup(new Spark(RobotPorts.LEFT_DRIVE_PWM_LONE.get()),
				new Spark(RobotPorts.LEFT_DRIVE_PWM_SPLIT.get()));
		SpeedControllerGroup rightDriveGroup = new SpeedControllerGroup(
				new Spark(RobotPorts.RIGHT_DRIVE_PWM_LONE.get()), new Spark(RobotPorts.RIGHT_DRIVE_PWM_SPLIT.get()));
		drive = new DifferentialDrive(leftDriveGroup, rightDriveGroup);
		disableMotors = false;
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
		drive.arcadeDrive(stick.getX(), stick.getY());
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
	}

	public void startAutoMode() {
		disableSafety();
	}

	public void startTeleopMode() {
		enableSaftey();
	}

	/*
	 * 
	 * methods for driving in Teleop
	 */

	public void driveWithJoystick() {
		double forwardModifier = 1 - Math.abs(Robot.joystick.getY());
		double sensitivity = (-Robot.joystick.getRawAxis(3) + 1) / 2;
		if (disableMotors) {
			sensitivity = 0;
		}
		left = (forwardModifier * Robot.joystick.getX() - Robot.joystick.getY()) * sensitivity;
		right = (-forwardModifier * Robot.joystick.getX() - Robot.joystick.getY()) * sensitivity;
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
