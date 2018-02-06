package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TankDriveTest extends Command {

    public TankDriveTest() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivemotors);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivemotors.disableSafety();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivemotors.tankDrive(0.55, 0.55);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivemotors.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
