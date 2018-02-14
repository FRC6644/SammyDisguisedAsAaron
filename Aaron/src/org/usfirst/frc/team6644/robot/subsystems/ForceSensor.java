package org.usfirst.frc.team6644.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ForceSensor extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private AnalogInput in;
	
	public ForceSensor(int channel) {
		in=new AnalogInput(channel);
	}
	
	public double getVoltage() {
		return in.getAverageVoltage();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

