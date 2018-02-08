package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OperateSolenoid extends Command {
	private int mode = 0;

	public OperateSolenoid(int mode) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.mode = mode;
		requires(Robot.pcm);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		switch (mode) {
		case 0:
			Robot.pcm.setSolenoidForward();
			break;
		case 1:
			Robot.pcm.setSolenoidOff();
			break;
		case 2:
			Robot.pcm.setSolenoidReverse();
			break;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
