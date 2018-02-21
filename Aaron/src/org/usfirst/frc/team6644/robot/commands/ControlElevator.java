package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.OI;
import org.usfirst.frc.team6644.robot.Robot;
import org.usfirst.frc.team6644.robot.subsystems.ElevatorMotor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ControlElevator extends Command {
	public double speed = 0;
	public ControlElevator() {
		requires(ElevatorMotor.getInstance());
		speed = .6;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		int POV = Robot.joystick.getPOV();
		boolean goUp = POV == 0;// OI.elevatorUp.get();
		boolean goDown = POV == 180;// OI.elevatorDown.get();
		
		
		if (goUp) {
			ElevatorMotor.getInstance().setElevatorSpeed(speed);
		} else if (goDown) {
			ElevatorMotor.getInstance().setElevatorSpeed(-speed/2);
		} else {
			ElevatorMotor.getInstance().setElevatorSpeed(0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		ElevatorMotor.getInstance().stop();
		System.out.println("Stopped");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
