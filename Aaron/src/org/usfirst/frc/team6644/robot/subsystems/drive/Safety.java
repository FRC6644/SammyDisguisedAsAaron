package org.usfirst.frc.team6644.robot.subsystems.drive;

import edu.wpi.first.wpilibj.MotorSafety;

public class Safety {
	private static Safety instance;

	public static Safety getInstance() {
		if (instance == null) {
			instance = new Safety();
		}
		return instance;
	}

	private MotorSafety motor;
	private double timeout = 0;
	private boolean timeoutsEnabled = true;
	private boolean outputsEnabled = true;

	public Safety() {

	}

	/**
	 * Sets the timeout of the motors
	 * 
	 * @param timeout
	 *            the time in milliseconds before the motors shut down
	 */
	public void setTimeout(double timeout) {
		if (motor == null) {
			return;
		}
		motor.setExpiration(timeout);
		this.timeout = timeout;
	}

	/**
	 * Returns the timeout value of the motors.
	 * 
	 * @return the timeout of the motors in milliseconds or 0 if not registered
	 */
	public double getTimeout() {
		if (motor == null) {
			return 0;
		}
		return motor.getExpiration();
	}

	/**
	 * Registers a motors to this safety controller
	 * 
	 * @param motor
	 *            An object implementing MotorSafety
	 */
	public void registerMotor(MotorSafety motor) {
		this.motor = motor;
		motor.setExpiration(timeout);
	}

	/**
	 * Enable timeout functions
	 */
	public void enableTimeouts() {
		timeoutsEnabled = true;
		if (motor != null) {
			motor.setSafetyEnabled(timeoutsEnabled);
		}
	}

	/**
	 * Disable timeout functions
	 */
	public void disableTimeouts() {
		timeoutsEnabled = false;
		if (motor != null) {
			motor.setSafetyEnabled(timeoutsEnabled);
		}
	}

	/**
	 * Modifies motor outputs to be "safe"
	 * 
	 * @param outputs
	 *            A reference to a double array to be modified
	 * @return
	 */
	public void modify(double[] outputs) {
		if (outputsEnabled) {
			for (int i = 0; i != outputs.length; i++) {
				outputs[i] = 0;
			}
		}
	}

	/**
	 * Enables all motor outputs
	 */
	public void enableOutputs() {
		outputsEnabled = true;
	}

	/**
	 * Disables all motor outputs
	 */
	public void disableOutputs() {
		outputsEnabled = false;
	}

	/**
	 * Enables all safety functions
	 */
	public void enableAll() {
		enableTimeouts();
		enableOutputs();
	}

	/**
	 * Disable all safety outputs
	 */
	public void disableAll() {
		disableOutputs();
		disableTimeouts();
	}
}
