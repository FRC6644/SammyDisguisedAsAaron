package org.usfirst.frc.team6644.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6644.robot.Robot;
import org.usfirst.frc.team6644.robot.subsystems.drive.DriveMotors;

/**
 *
 */
public class DriveStraight extends Command {
   	private double leftSpeed = .5;
	private double rightSpeed = .5;
	public double speed;
	private double minSpeed = .8;
	public double mod = 0.05;
    public DriveStraight(double speed) {
    	this.speed = speed;
        // Use requires() here to declare subsystem dependencies
        requires(DriveMotors.getInstance());
        leftSpeed = speed;
        rightSpeed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.encoders.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double[] rates = Robot.encoders.getRate();
 
    	if(rates[0] > rates[1]) {
    		if(leftSpeed < speed * minSpeed) {
    			rightSpeed += mod;
    		}else {
    			leftSpeed -= mod;
    		}
    	} else if(rates[1] > rates[0]) {
    		if(rightSpeed < speed * minSpeed) {
    			leftSpeed += mod;
    		}else {
    			rightSpeed -= mod;
    		}
    	}
    	DriveMotors.getInstance().tankDrive(leftSpeed, rightSpeed, false);
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
