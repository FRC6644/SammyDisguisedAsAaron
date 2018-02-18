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
	private boolean rate;

	public DifferentialDrivePID(SpeedController leftMotor, SpeedController rightMotor) {
		super(leftMotor, rightMotor);
		compare = false;
		rate = false;
	}

	public DifferentialDrivePID(SpeedController leftMotor, SpeedController rightMotor, boolean rate) {
		super(leftMotor, rightMotor);
		compare = false;
		this.rate = rate;
	}

	public DifferentialDrivePID(SpeedController leftMotor, SpeedController rightMotor, boolean rate, boolean compare) {
		super(leftMotor, rightMotor);
		this.compare = compare;
		this.rate = compare;
	}

	public void setCompare(boolean state) {
		compare = state;
	}

	public void setRate(boolean state) {
		rate = state;
	}

	/**
	 * If (compare==true){ Attempts to drive straight by preferentially increasing
	 * the speed of the lagging side (the side with lowest speed, irrespective of
	 * direction), then decreasing the speed of the faster side. } else {
	 * 
	 */
	public void pidWrite(double output) {
		double[] outputs = DriveMotors.getInstance().getDriveOutputs();
		if (compare) {
			boolean[] directions = DriveMotors.getInstance().getEncoders().encoderDirection();
			if (directions[0] == directions[1]) {
				int sign = (directions[0] ? 1 : 0) * 2 - 1;
				if (rate) {
					if (Math.abs(outputs[0]) > Math.abs(outputs[1])/*Is this necessary?*/) {
						
					}
				} else {
				}
			}
		} else {
			if (rate) {

			} else {

			}
		}
	}
}
