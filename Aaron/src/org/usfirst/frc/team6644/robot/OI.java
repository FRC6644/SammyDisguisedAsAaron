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
	// keep all joystick buttons here so that it's easy to keep track of them.
	//public static JoystickButton linearDrive = new JoystickButton(Robot.joystick, 1);
	//public static JoystickButton compensate = new JoystickButton(Robot.joystick, 2);
	public static JoystickButton lowGear = new JoystickButton(Robot.joystick, 3);
	public static JoystickButton highGear = new JoystickButton(Robot.joystick, 5);
	public static JoystickButton elevatorUp = new JoystickButton(Robot.joystick, 10);
	public static JoystickButton elevatorDown = new JoystickButton(Robot.joystick, 9);
	public static JoystickButton grabberIn = new JoystickButton(Robot.joystick, 1);
	public static JoystickButton grabberOut = new JoystickButton(Robot.joystick, 2);
	//public static JoystickButton driveHistory = new JoystickButton(Robot.joystick, 12);

	public OI() {

		// Operate solenoid
		lowGear.whenPressed(new OperateSolenoid(0));
		highGear.whenPressed(new OperateSolenoid(2));
	}
}
