package org.usfirst.frc.team6644.robot.subsystems.drive;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team6644.robot.Robot;
import org.usfirst.frc.team6644.robot.RobotPorts;
import org.usfirst.frc.team6644.robot.libraryAdditions.DifferentialDrivePID;
import org.usfirst.frc.team6644.robot.libraryAdditions.DriveEncodersPID;

public class DriveMotors extends Subsystem {
	// History
	private static History history = History.getInstance();

	// Drivebase stuff
	private static DriveMotors instance;
	private static DifferentialDrivePID drive;
	private static final double motorSafteyExpireTime = 0.3;// sets the PWM to expire in 0.3 seconds after the last call
															// of .Feed()
	private boolean disableMotors;
	protected double left = 0;
	protected double right = 0;

	// Autonomous Stuff
	private static boolean drivingFromHistory;
	private static PIDController StraighteningPID;

	// Encoder stuff
	private static DriveEncodersPID encoders;

	// Testing Stuff (please clear when done using these)
	// TODO: DELETE THIS WHEN DONE
	private static int rateCounter = 0;
	private static boolean calculateScale = false;
	private static boolean pressed = false;
	private static JoystickButton searchForScale = new JoystickButton(Robot.joystick, 11);

	public static DriveMotors getInstance() {
		if (instance == null) {
			instance = new DriveMotors();
		}
		return instance;
	}

	private DriveMotors() {
		// create a DifferentialDrive
		drive = new DifferentialDrivePID(new Spark(RobotPorts.LEFT_DRIVE_PWM_SPLIT.get()),
				new Spark(RobotPorts.RIGHT_DRIVE_PWM_SPLIT.get()), true);
		disableMotors = false;

		// do encoder stuff
		encoders = new DriveEncodersPID(new Encoder(RobotPorts.LEFT_ENCODER_A.get(), RobotPorts.LEFT_ENCODER_B.get()),
				new Encoder(RobotPorts.RIGHT_ENCODER_A.get(), RobotPorts.RIGHT_ENCODER_B.get()), true, true);
		encoders.encoderReset();
		encoders.setReverseDirection(true, false);
		encoders.encoderSetSamplesToAverage(4);
		encoders.encoderSetDistancePerPulse(0.0020454076); // this is in ft/pulse
	}

	public DriveEncodersPID getEncoders() {
		return encoders;
	}

	/*
	 * methods for drive motors
	 */
	public void enableSaftey() {
		drive.setSafetyEnabled(true);
		drive.setExpiration(motorSafteyExpireTime);
	}

	public void disableSafety() {
		drive.setSafetyEnabled(false);
		drive.setExpiration(motorSafteyExpireTime);
	}

	public void arcadeDrive(double linear, double turn) {
		drive.arcadeDrive(linear, turn);
	}

	public void arcadeDrive(GenericHID stick) {
		drive.arcadeDrive(stick.getY(), stick.getX());
	}

	public void tankDrive(double left, double right, boolean squaredInputs) {
		drive.tankDrive(left, right, squaredInputs);
	}

	public void stop() {
		disableSafety();
		drive.stopMotor();
		if (StraighteningPID != null && StraighteningPID.isEnabled()) {
			StraighteningPID.disable();
		}
	}

	public void startAutoMode() {
		disableSafety();
		// set setpoint
		drive.free();
		history.clear();
		history.resetCount();
		drivingFromHistory = false;
		encoders.setPIDSourceType(PIDSourceType.kDisplacement);
		StraighteningPID = new PIDController(0, 0, 0, encoders, drive);
		StraighteningPID.setOutputRange(-1, 1);
		StraighteningPID.enable();
	}

	public void stopAutoMode() {
		StraighteningPID.disable();
		/*
		 * pidLoop.free(); drive = new DifferentialDrive(new
		 * Spark(RobotPorts.LEFT_DRIVE_PWM_SPLIT.get()), new
		 * Spark(RobotPorts.RIGHT_DRIVE_PWM_SPLIT.get()));
		 */
	}

	public void startTeleopMode() {
		enableSaftey();
	}

	/*
	 * 
	 * methods for driving in Teleop
	 */

	public void driveWithJoystick(boolean squared, boolean compensate) {
		double forwardModifier = 1 - Math.abs(Robot.joystick.getY());
		double sensitivity = (-Robot.joystick.getRawAxis(3) + 1) / 2;
		if (disableMotors) {
			sensitivity = 0;
		}
		left = (forwardModifier * Robot.joystick.getX() - Robot.joystick.getY()) * sensitivity;
		right = (-forwardModifier * Robot.joystick.getX() - Robot.joystick.getY()) * -sensitivity;
		if (squared) {
			left = Math.copySign(left * left, left);
			right = Math.copySign(right * right, right);
		}

		tankDrive(left, right, false);
		// -----------------------------------------------------------------
		// TODO: DELETE THIS WHEN DONE

		if (!pressed) {
			calculateScale = searchForScale.get();
		}

		if (calculateScale) {
			findScaleFactor();
		}
		// ------------------------------------------------------------------
	}

	public void toggleMotorDisableState() {
		disableMotors = !disableMotors;
	}

	public void disableMotors() {
		disableMotors = true;
	}

	public void enableMotors() {
		disableMotors = false;
	}

	public History getHistory() {
		return history;
	}

	public void startDrivingFromHistory() {
		drivingFromHistory = true;
	}

	public boolean checkDrivingFromHistory() {
		return !drivingFromHistory;
	}

	public void abortDrivingFromHistory() {
		history.clear();
		history.resetCount();
		drivingFromHistory = false;
	}

	/*
	 * stuff for SmartDashboard
	 */

	/**
	 * Returns the current output of the drive motors as an array [left, right]
	 * 
	 * @return Current outputs of motors from -1 to 1, left to right
	 */
	public double[] getDriveOutputs() {
		// returns an array [left,right]
		return new double[] { left, right };
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/*
	 * Testing stuff
	 */

	/**
	 * A test drive method for straightening and controlling when to drive with
	 * squared inputs. Driving with squared inputs is good for fine control, while
	 * without squared inputs is better for higher speeds.
	 * 
	 * Straightening drive PID loop is still untested and unreviewed.
	 * 
	 * @param squared
	 * @param compensate
	 */
	public void testDrive(boolean squared, boolean compensate) {// TODO:DELETE THIS WHEN DONE
		double forwardModifier = 1 - Math.abs(Robot.joystick.getY());
		double sensitivity = (-Robot.joystick.getRawAxis(3) + 1) / 2;
		if (disableMotors) {
			sensitivity = 0;
		}
		left = (forwardModifier * Robot.joystick.getX() - Robot.joystick.getY()) * sensitivity;
		right = (-forwardModifier * Robot.joystick.getX() - Robot.joystick.getY()) * -sensitivity;
		if (squared) {
			left = Math.copySign(left * left, left);
			right = Math.copySign(right * right, right);
		}
		if (compensate) {
			StraighteningPID.enable();
		} else {
			try {
				if (StraighteningPID.isEnabled()) {
					StraighteningPID.disable();
				}
			} catch (NullPointerException e) {
				System.out.println("Straightening PIDController not initialized");
			}
			drive.tankDrive(left, right, false);
		}
	}

	public void findScaleFactor() {
		double[] rateSamples = new double[2];
		double[] driveInputs = new double[2];
		double scaleFactorLeft = 0;
		double scaleFactorRight = 0;
		double leftIn = 0;
		double rightIn = 0;
		if (rateCounter < 50) {
			calculateScale = true;
			pressed = true;
			rateSamples = encoders.encoderRate();
			driveInputs = getDriveOutputs();
			scaleFactorLeft += rateSamples[0] / driveInputs[0];
			scaleFactorRight += rateSamples[1] / driveInputs[1];
			leftIn += driveInputs[0];
			rightIn += driveInputs[1];
			rateCounter++;
		} else {
			calculateScale = false;
			pressed = false;
			scaleFactorLeft /= 50;
			scaleFactorRight /= 50;
			leftIn /= 50;
			rightIn /= 50;
			System.out.println("\n\n\n\n-----------------------------------------------");
			System.out.println("Input: " + leftIn + "; Scale Factor: " + scaleFactorLeft + ";");
			System.out.println("Input: " + rightIn + "; Scale Factor: " + scaleFactorRight + ";");
		}
	}
}