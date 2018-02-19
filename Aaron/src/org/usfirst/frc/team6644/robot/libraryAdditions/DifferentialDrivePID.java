package org.usfirst.frc.team6644.robot.libraryAdditions;

import org.usfirst.frc.team6644.robot.subsystems.DriveMotors;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * A class for controlling a DifferentialDrive with a PIDController. Class is
 * written primarily for the purpose of maintaining a straight course.
 *
 */
public class DifferentialDrivePID extends DifferentialDrive implements PIDOutput {
	private boolean compare;

	public DifferentialDrivePID(SpeedController leftMotor, SpeedController rightMotor) {
		super(leftMotor, rightMotor);
		compare = false;
	}

	public DifferentialDrivePID(SpeedController leftMotor, SpeedController rightMotor, boolean compare) {
		super(leftMotor, rightMotor);
		this.compare = compare;
	}

	public void setCompare(boolean state) {
		compare = state;
	}

	/**
	 * If (compare==true){ Attempts to drive straight by preferentially increasing
	 * the speed of the lagging side (the side with lowest speed, irrespective of
	 * direction), then decreasing the speed of the faster side.} else {
	 * 
	 */
	public void pidWrite(double output) {
		double[] outputs = DriveMotors.getInstance().getDriveOutputs();
		if (compare) {
			boolean[] directions = DriveMotors.getInstance().getEncoders().encoderDirection();
			if (directions[0] == directions[1]) {
				double compensate = output * ((directions[0] ? 1 : 0) * 2 - 1); // TODO:ASSUMES true is forward. Check
																				// this assumption.
				if (compensate > 0) {
					outputs[1] = limit(outputs[1] + output);
					outputs[0] = limit(outputs[0]
							- ((1 - Math.abs(outputs[1]) < compensate) ? (output + outputs[1] - 1) : 0));
				} else {
					outputs[0] = limit(outputs[0] + compensate);
					outputs[1] = limit(outputs[1]
							- ((1 - Math.abs(outputs[0]) < output) ? (output + outputs[0] - 1) : 0));
				}
				this.tankDrive(outputs[0], outputs[1], false);
			}
		} else {
			//do stuff for driving a set distance
		}
	}
}
