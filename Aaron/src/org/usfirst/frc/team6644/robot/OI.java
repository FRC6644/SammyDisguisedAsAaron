package org.usfirst.frc.team6644.robot;

import org.usfirst.frc.team6644.robot.commands.ForFunsies;
import org.usfirst.frc.team6644.robot.commands.OperateSolenoid;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	JoystickButton forward = new JoystickButton(Robot.joystick, 8);
	JoystickButton off = new JoystickButton(Robot.joystick, 9);
	JoystickButton reverse = new JoystickButton(Robot.joystick, 10);
	JoystickButton control = new JoystickButton(Robot.joystick, 1);

	public OI() {
		forward.whenPressed(new OperateSolenoid(0));
		off.whenPressed(new OperateSolenoid(1));
		reverse.whenPressed(new OperateSolenoid(2));
		control.whileHeld(new ForFunsies());
		control.whenReleased(new OperateSolenoid(1));
	}
}
