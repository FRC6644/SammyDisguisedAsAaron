package org.usfirst.frc.team6644.robot.commands;

import org.usfirst.frc.team6644.robot.OI;
import org.usfirst.frc.team6644.robot.subsystems.GrabberMotors;

import edu.wpi.first.wpilibj.command.Command;

public class ControlGrabber extends Command {
	private double speed = 0;

	public ControlGrabber() {
		speed = .3;
	}

	public void initialize() {
		requires(GrabberMotors.getInstance());
	}

	public void execute() {
		if (OI.grabberIn.get()) {
			GrabberMotors.getInstance().setSpeed(speed);
		} else if (OI.grabberOut.get()) {
			GrabberMotors.getInstance().setSpeed(-speed);
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
