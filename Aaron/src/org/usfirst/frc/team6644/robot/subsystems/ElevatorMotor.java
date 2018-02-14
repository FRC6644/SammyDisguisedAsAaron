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
	private ElevatorMotor instance;
	private Spark elevator;

	public ElevatorMotor getInstance() {
		if (instance == null) {
			instance = new ElevatorMotor();
		}
		return instance;
	}
	
	private ElevatorMotor() {
		elevator=new Spark(RobotPorts.ELEVATOR_MOTOR.get());
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

