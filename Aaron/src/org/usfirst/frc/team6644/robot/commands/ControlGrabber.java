package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.OI;
import org.usfirst.frc.team6644.robot.subsystems.GrabberMotors;

import edu.wpi.first.wpilibj.command.Command;

public class ControlGrabber extends Command {

	public ControlGrabber() {

	}

	public void initialize() {
		requires(GrabberMotors.getInstance());
	}

	public void execute() {
		if (OI.grabberIn.get()) {
			GrabberMotors.getInstance().setSpeed(1);
		} else if (OI.grabberOut.get()) {
			GrabberMotors.getInstance().setSpeed(-1);
		} else {
			GrabberMotors.getInstance().setSpeed(0);
		}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
