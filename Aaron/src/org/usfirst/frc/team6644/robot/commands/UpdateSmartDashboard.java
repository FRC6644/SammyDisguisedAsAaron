package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.Robot;
import org.usfirst.frc.team6644.robot.subsystems.DriveMotors;
import org.usfirst.frc.team6644.robot.subsystems.ForceSensor;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateSmartDashboard extends Command {
	double[] encoderValues;
	double[] driveOutputs;
	JoystickButton linearDrive;
	JoystickButton compensate;

	// ForceSensor test;
	public UpdateSmartDashboard() {
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		DriveMotors.getInstance();
		// test=new ForceSensor(0);
		linearDrive = new JoystickButton(Robot.joystick, 1);
		compensate = new JoystickButton(Robot.joystick, 2);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (DriverStation.getInstance().isDisabled()) {
			DriveMotors.getInstance().testDrive(linearDrive.get(), compensate.get());
		}

		encoderValues = DriveMotors.getInstance().encoderDistance();
		driveOutputs = DriveMotors.getInstance().getDriveOutputs();
		SmartDashboard.putNumber("Encoder left value: ", encoderValues[0]);
		SmartDashboard.putNumber("Encoder right value: ", encoderValues[1]);
		SmartDashboard.putNumber("Drive left value: ", driveOutputs[0]);
		SmartDashboard.putNumber("Drive right value: ", driveOutputs[1]);
		SmartDashboard.putBoolean("linearDrive: ", linearDrive.get());
		SmartDashboard.putBoolean("compensate: ", compensate.get());

		// SmartDashboard.putNumber("ForceSensor Voltage: ", test.getVoltage());
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
