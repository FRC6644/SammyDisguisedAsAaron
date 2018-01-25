package org.usfirst.frc.team6644.robot;

public enum RobotPorts {

	// Laptop USB Ports
	JOYSTICK(0),

	// PWM Ports
	RIGHT_DRIVE_PWM_SPLIT(3), RIGHT_DRIVE_PWM_LONE(2), LEFT_DRIVE_PWM_LONE(1), LEFT_DRIVE_PWM_SPLIT(0);

	// Analog Ports

	// Digital Ports

	private final int portNumber;

	private RobotPorts(int portNumber) {
		this.portNumber = portNumber;
	}

	public int get() {
		return portNumber;
	}
}