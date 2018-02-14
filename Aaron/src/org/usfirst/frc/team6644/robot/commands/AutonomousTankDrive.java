package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.Robot;
import org.usfirst.frc.team6644.robot.subsystems.DriveMotors;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutonomousTankDrive extends Command {
	double v = 0.2;
	double i=-1;
	public AutonomousTankDrive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(DriveMotors.getInstance());
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		DriveMotors.getInstance().disableSafety();
		// Robot.pcm.printCompressorStats();
		System.out.println("\n\n\n\n\n_________________________________\n\n\t\t\tPWM Loop Sweep Starting...");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// double sensitivity = -Robot.joystick.getRawAxis(3);
		//DriveMotors.getInstance().tankDrive(v, -v, false);
		// System.out.println(sensitivity);
		DriveMotors.getInstance().tankDrive(i, i, false);
		i+=0.001;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return i>1;
	}

	// Called once after isFinished returns true
	protected void end() {
		DriveMotors.getInstance().tankDrive(0, 0, false);
		System.out.println("\n\t\t\tPWM Loop Sweep Finished.\n_________________________________");
		//DriveMotors.getInstance().stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
