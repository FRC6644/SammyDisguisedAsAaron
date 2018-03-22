package org.usfirst.frc.team6644.robot;

public enum RobotPorts {

	// Laptop USB Ports
	JOYSTICK(0),

	// PWM Ports
	RIGHT_DRIVE_PWM_SPLIT(0), LEFT_DRIVE_PWM_SPLIT(1), LEFT_GRABBER_MOTOR(4), RIGHT_GRABBER_MOTOR(5), ELEVATOR_MOTOR(
			6), REEL_MOTOR(7),
	// Digital Ports
	LEFT_ENCODER_A(0), LEFT_ENCODER_B(1), RIGHT_ENCODER_A(2), RIGHT_ENCODER_B(3);
	private final int portNumber;

	private RobotPorts(int portNumber) {
		this.portNumber = portNumber;
	}

	public int get() {
		return portNumber;
	}
}