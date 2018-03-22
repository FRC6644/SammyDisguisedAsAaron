package org.usfirst.frc.team6644.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team6644.robot.subsystems.drive.DriveMotors;
import org.usfirst.frc.team6644.robot.subsystems.Gyro;

/**
 * incomplete
 */
public class AutonomousTurn extends PIDCommand {
	private double degree = 0;

	public AutonomousTurn(double degree) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super(0.02, 0.0000102 * Math.pow(360 / degree, 2), 0);
		requires(DriveMotors.getInstance());
		this.degree = degree;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		DriveMotors.getInstance().startAutoMode();
		setSetpoint(degree);
		getPIDController().setPercentTolerance(0.5);// sets to 0.5% tolerance

		// PIDController automatically started when command is initialized, according to
		// class docs

		// test stuff below:
		System.out.println(getPIDController().getSetpoint());

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return getPIDController().onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		// PIDController is automatically stopped when the command is ended/interrupted
		DriveMotors.getInstance().stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {

	}

	@Override
	protected double returnPIDInput() {
		return Gyro.getDegreesTotal();
	}

	@Override
	protected void usePIDOutput(double output) {
		// limit outputs to between -1 and 1, inclusive.
		if (Math.abs(output) <= 1) {
			DriveMotors.getInstance().arcadeDrive(0, -output);
		} else if (output < 0) {
			DriveMotors.getInstance().arcadeDrive(0, 1);
		} else {
			DriveMotors.getInstance().arcadeDrive(0, -1);
		}
	}
}
