package org.usfirst.frc.team6644.robot.libraryAdditions;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * A class for putting two encoders into one object and controlling the robot
 * drivebase with one PIDController.
 *
 */
public class DriveEncodersPID implements PIDSource {
	PIDSourceType pidSource;
	Encoder left;
	Encoder right;
	boolean targetRates;
	boolean compare;

	public DriveEncodersPID(Encoder leftEncoder, Encoder rightEncoder) {
		left = leftEncoder;
		right = rightEncoder;
		targetRates = false;
		compare = false;
	}

	public DriveEncodersPID(Encoder leftEncoder, Encoder rightEncoder, boolean compare) {
		left = leftEncoder;
		right = rightEncoder;
		this.compare = compare;
		targetRates = false;
	}

	public DriveEncodersPID(Encoder leftEncoder, Encoder rightEncoder, boolean compare, boolean targetRates) {
		left = leftEncoder;
		right = rightEncoder;
		this.compare = compare;
		this.targetRates = targetRates;
	}

	/**
	 * Compare rates is for setting the input to the PIDController as the difference
	 * between the left and right encoders, rather than the average of the two
	 * encoder values.
	 * 
	 * @param state
	 */
	public void compareEncoders(boolean state) {
		compare = state;
	}

	/**
	 * For if the setpoint is a target rate rather than a distance.
	 * 
	 * @param state
	 */
	public void targetRates(boolean state) {
		targetRates = state;
	}

	public void setPIDSourceType(PIDSourceType pidSource) {
		this.pidSource = pidSource;
	}

	public PIDSourceType getPIDSourceType() {
		return pidSource;
	}

	public double pidGet() {
		if (targetRates) {
			switch (pidSource) {
			case kDisplacement:
				return getRate();
			case kRate:
				System.out.println(
						"ERROR: Using a PIDSourceType kRate when comparing the displacement between rates.\n\t\t\tSent from libraryAdditions.DriveEncodersPID");
				return 0.0;
			default:
				return 0.0;
			}
		} else {
			switch (pidSource) {
			case kDisplacement:
				return getDisplacement();
			case kRate:
				return getRate();
			default:
				return 0.0;
			}
		}
	}

	/**
	 * Returns the difference (left encoder distance) - (right encoder distance)
	 */
	public double getDisplacement() {
		double[] distance = encoderDistance();
		if (compare) {
			return distance[0] - distance[1];
		} else {
			return (distance[0] + distance[1]) / 2;
		}
	}

	/**
	 * Returns the difference (left encoder rate) - (right encoder rate)
	 */
	public double getRate() {
		double[] rate = encoderRate();
		if (compare) {
			return rate[0] - rate[1];
		} else {
			return (rate[0] + rate[1]) / 2;
		}
	}

	public void freeEncoders() {
		left.free();
		right.free();
	}

	public void encoderSetDistancePerPulse(double r) {
		left.setDistancePerPulse(r);
		right.setDistancePerPulse(r);
	}

	public void encoderSetSamplesToAverage(int n) {
		left.setSamplesToAverage(n);
		right.setSamplesToAverage(n);
	}

	public int[] encoderGet() {
		int[] thing = new int[2];
		thing[0] = left.get();
		thing[1] = right.get();
		return thing;
	}

	public int[] encoderRaw() {
		int[] thing = new int[2];
		thing[0] = left.getRaw();
		thing[1] = right.getRaw();
		return thing;
	}

	public boolean[] encoderDirection() {
		boolean[] thing = new boolean[2];
		thing[0] = left.getDirection();
		thing[1] = right.getDirection();
		return thing;
	}

	public double[] encoderDistance() {
		return new double[] {left.getDistance(), right.getDistance()};
	}

	public double[] encoderRate() {
		double[] thing = new double[2];
		thing[0] = left.getRate();
		thing[1] = right.getRate();
		return thing;
	}

	public void encoderReset() {
		left.reset();
		right.reset();
	}

	/**
	 * @param left
	 *            setReverseDirection() value
	 * @param right
	 *            setReverseDirection() value
	 */
	public void setReverseDirection(boolean left, boolean right) {
		this.left.setReverseDirection(left);
		this.right.setReverseDirection(right);
	}
	public Encoder[] getEncoder() {
		Encoder[] encodersList = {left, right};
		return encodersList;
	}

}
