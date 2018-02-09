package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ForFunsies extends Command {
	OperateSolenoid s;
    public ForFunsies() {
        requires(Robot.pcm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.joystick.getX()<0) {
    		s=new OperateSolenoid(0);
    		s.execute();
    	}else {
    		s=new OperateSolenoid(2);
    		s.execute();
    	}
    	Robot.pdm.printPDMStats();
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
