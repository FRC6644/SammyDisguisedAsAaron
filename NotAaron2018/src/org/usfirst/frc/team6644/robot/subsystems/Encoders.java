package org.usfirst.frc.team6644.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.Encoder;

/**
 *
 */
public class Encoders extends Subsystem {
	Encoder left;
	Encoder right;
	
	public Encoders(int leftPortA, int leftPortB, int rightPortA, int rightPortB) {
		left = new Encoder(leftPortA, leftPortB);
		left.setReverseDirection(true);
		right = new Encoder(rightPortA, rightPortB);
	}
	
	public double[] getDistance() {
		return new double[] {left.getDistance(), right.getDistance()};
	}
	public double[] getRate() {
		return new double[] {left.getRate(), right.getRate()};
	}
	public void reset() {
		left.reset();
		right.reset();
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

