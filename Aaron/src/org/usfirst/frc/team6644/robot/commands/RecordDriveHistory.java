package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.subsystems.DriveMotors;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RecordDriveHistory extends Command {

	JoystickButton monitor;

	public RecordDriveHistory(JoystickButton m) {
		monitor = m;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		DriveMotors.getInstance().startHistory();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		DriveMotors.getInstance().updateHistory();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return !monitor.get();
		// TODO: Assumes that joystick buttons return true when held down. Check this
		// assumption.
	}

	// Called once after isFinished returns true
	protected void end() {
		DriveMotors.getInstance().endHistory(0);
		// TODO: Check to make sure end() isn't called repeatedly by commands called by
		// JoystickButton.whileHeld(Commad c) after scheduler loops around. That would
		// really mess this up badly.
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
