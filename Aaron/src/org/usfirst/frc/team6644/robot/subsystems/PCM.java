package org.usfirst.frc.team6644.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * For controlling the pneumatics control module.
 * Currently configured to use a single double solenoid.
 */
public class PCM extends Subsystem {

	private DoubleSolenoid sol = new DoubleSolenoid(0, 1);
	protected Compressor compressor = new Compressor();

	public void clearAllPCMStickyFaults() {
		sol.clearAllPCMStickyFaults();
	}

	public void printCompressorStats() {
		System.out.println("\n\n******\t\tCompressor Stats:\t\t******");
		System.out.println("\t\tGeneral:");
		System.out.println("\t\t\t\tCompressor Enabled: " + compressor.enabled());
		System.out.println("\t\t\t\tCurrent: " + compressor.getCompressorCurrent());
		System.out.println("\t\t\t\tPressure Switch Value: " + compressor.getPressureSwitchValue());
		System.out.println("\t\t\t\tClosed Loop Control: " + compressor.getClosedLoopControl());
		System.out.println("\t\tFaults:");
		System.out.println("\t\t\t\tTooHighFault: " + compressor.getCompressorCurrentTooHighFault());
		System.out.println("\t\t\t\tTooHighStickyFault: " + compressor.getCompressorCurrentTooHighStickyFault());
		System.out.println("\t\t\t\tNotConnectedFault: " + compressor.getCompressorNotConnectedFault());
		System.out.println("\t\t\t\tNotConnectedStickyFault: " + compressor.getCompressorNotConnectedStickyFault());
		System.out.println("\t\t\t\tShortedFault: " + compressor.getCompressorShortedFault());
		System.out.println("\t\t\t\tShortedStickyFault: " + compressor.getCompressorShortedStickyFault());
		System.out.println("--------------------------------------");
	}
	
	/*
	 * Compressor stuff below
	 */
	
	public boolean compressorEnabled() {
		return compressor.enabled();
	}

	public void startCompressor() {
		compressor.start();
	}
	
	/*
	 * Double solenoid stuff below
	 */
	public String getSolenoidValue() {
		return sol.get().toString();
	}
	
	public void setSolenoidForward() {
		sol.set(DoubleSolenoid.Value.valueOf("kForward"));
	}
	
	public void setSolenoidOff() {
		sol.set(DoubleSolenoid.Value.valueOf("kOff"));
	}
	
	public void setSolenoidReverse() {
		sol.set(DoubleSolenoid.Value.valueOf("kReverse"));
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}