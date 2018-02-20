package org.usfirst.frc.team6644.robot.subsystems;

import org.usfirst.frc.team6644.robot.RobotPorts;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ElevatorMotor extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private static ElevatorMotor instance;
	private static Spark elevatorController;

	public static ElevatorMotor getInstance() {
		if (instance == null) {
			instance = new ElevatorMotor();
		}
		return instance;
	}

	private ElevatorMotor() {
		elevatorController = new Spark(RobotPorts.ELEVATOR_MOTOR.get());
	}

	public void setElevatorSpeed(double speed) {
		elevatorController.set(speed);
	}

	public void stop() {
		elevatorController.stopMotor();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
