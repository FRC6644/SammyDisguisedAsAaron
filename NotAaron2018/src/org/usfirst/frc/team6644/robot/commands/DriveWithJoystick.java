package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.OI;
import org.usfirst.frc.team6644.robot.Robot;
import org.usfirst.frc.team6644.robot.subsystems.drive.DriveMotors;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithJoystick extends Command {

	public DriveWithJoystick() {
		requires(DriveMotors.getInstance());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		DriveMotors.getInstance().startTeleopMode();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		DriveMotors.getInstance().driveWithJoystick(false, false);
		//DriveMotors.getInstance().arcadeDrive(Robot.joystick);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		DriveMotors.getInstance().stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
