package org.usfirst.frc.team6644.robot;

import org.usfirst.frc.team6644.robot.commands.OperateSolenoid;
import org.usfirst.frc.team6644.robot.commands.RecordDriveHistory;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	JoystickButton forward = new JoystickButton(Robot.joystick, 3);
	JoystickButton off = new JoystickButton(Robot.joystick, 9);
	JoystickButton reverse = new JoystickButton(Robot.joystick, 5);
	JoystickButton driveHistory = new JoystickButton(Robot.joystick, 12);

	public OI() {

		// Operate solenoid
		forward.whenPressed(new OperateSolenoid(0));
		off.whenPressed(new OperateSolenoid(1));
		reverse.whenPressed(new OperateSolenoid(2));

		// Record history in TeleOp
		if (DriverStation.getInstance().isOperatorControl() && DriverStation.getInstance().isEnabled()) {
			driveHistory.whenPressed(new RecordDriveHistory(driveHistory));
		}
	}
}
