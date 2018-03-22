package org.usfirst.frc.team6644.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team6644.robot.RobotPorts;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 *
 */
public class GrabberMotors extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private static GrabberMotors instance;
	private SpeedControllerGroup grabber;
	public Spark reel;
	public static GrabberMotors getInstance() {
		if (instance == null) {
			instance = new GrabberMotors();
		}
		return instance;
	}
	
	private GrabberMotors() {
		Spark left = new Spark(RobotPorts.LEFT_GRABBER_MOTOR.get());
		left.setInverted(false);
		Spark right = new Spark(RobotPorts.RIGHT_GRABBER_MOTOR.get());
		right.setInverted(true);
		reel = new Spark(RobotPorts.REEL_MOTOR.get());
		grabber = new SpeedControllerGroup(left, right);
	}

	public void setSpeed(double speed) {
		grabber.set(speed);
	}
	public void release(double speed) {
		reel.set(speed);
	}
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
