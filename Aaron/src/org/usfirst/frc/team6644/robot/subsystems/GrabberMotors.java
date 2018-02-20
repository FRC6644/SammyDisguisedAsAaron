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

	public static GrabberMotors getInstance() {
		if (instance == null) {
			instance = new GrabberMotors();
		}
		return instance;
	}

	private GrabberMotors() {
		grabber = new SpeedControllerGroup(new Spark(RobotPorts.FORCE_SENSOR_LEFT.get()),
				new Spark(RobotPorts.FORCE_SENSOR_RIGHT.get()));
	}

	public void setSpeed(double speed) {
		grabber.set(speed);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
