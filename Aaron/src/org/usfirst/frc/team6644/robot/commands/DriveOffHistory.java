package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.subsystems.DriveMotors;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveOffHistory extends Command {

    public DriveOffHistory(int n) {
        requires(DriveMotors.getInstance());
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DriveMotors.getInstance().startAutoMode();
    	DriveMotors.getInstance().loadHistory(0);
    	DriveMotors.getInstance().startDrivingFromHistory();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DriveMotors.getInstance().driveWithJoystick();
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
