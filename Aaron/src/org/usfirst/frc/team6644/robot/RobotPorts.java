package org.usfirst.frc.team6644.robot;

public enum RobotPorts {

	// Laptop USB Ports
	JOYSTICK(0),

	// PWM Ports
	RIGHT_DRIVE_PWM_SPLIT(1), LEFT_DRIVE_PWM_SPLIT(0), LEFT_GRABBER_MOTOR(2), RIGHT_GRABBER_MOTOR(3), ELEVATOR_MOTOR(4),

	// Analog Ports
	FORCE_SENSOR_LEFT(0), FORCE_SENSOR_RIGHT(1);
	// Digital Ports

	private final int portNumber;

	private RobotPorts(int portNumber) {
		this.portNumber = portNumber;
	}

	public int get() {
		return portNumber;
	}
}