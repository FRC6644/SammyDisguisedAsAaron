package org.usfirst.frc.team6644.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gyro extends Subsystem {

	// could I just get rid of instance and just make gyro static?
	private static Gyro instance;
	private ADXRS450_Gyro gyro;

	private Gyro() {
		gyro = new ADXRS450_Gyro();
		gyro.reset();
	}

	public static Gyro getInstance() {
		if (instance == null) {
			instance = new Gyro();
		}
		return instance;
	}

	public static double getDegrees() {
		return getInstance().gyro.getAngle() % 360;
	}

	public static double getDegreesTotal() {
		return getInstance().gyro.getAngle();
	}

	public static double getRate() {
		return getInstance().gyro.getRate();
	}

	public static void resetGyro() {
		getInstance().gyro.reset();
	}

	public ADXRS450_Gyro getGyro() {
		return this.gyro;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
