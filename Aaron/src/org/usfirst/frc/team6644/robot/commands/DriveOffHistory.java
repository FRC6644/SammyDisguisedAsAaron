package org.usfirst.frc.team6644.robot.commands;

import java.io.FileNotFoundException;

import org.usfirst.frc.team6644.robot.subsystems.drive.DriveMotors;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveOffHistory extends Command {
	private int historyNumber = 0;

	public DriveOffHistory(int n) {
		historyNumber = n;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		requires(DriveMotors.getInstance());
		DriveMotors.getInstance().startAutoMode();
		try {
			DriveMotors.getInstance().getHistory().load(historyNumber);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DriveMotors.getInstance().startDrivingFromHistory();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return DriveMotors.getInstance().checkDrivingFromHistory();
	}

	// Called once after isFinished returns true
	protected void end() {
		DriveMotors.getInstance().stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		DriveMotors.getInstance().abortDrivingFromHistory();
		end();
	}
}
