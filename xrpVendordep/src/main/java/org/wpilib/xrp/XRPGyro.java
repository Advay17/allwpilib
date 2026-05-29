// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.wpilib.xrp;

import static org.wpilib.units.Units.Degrees;
import static org.wpilib.units.Units.DegreesPerSecond;

import org.wpilib.hardware.hal.SimDevice;
import org.wpilib.hardware.hal.SimDevice.Direction;
import org.wpilib.hardware.hal.SimDouble;
import org.wpilib.math.geometry.Rotation2d;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.AngularVelocity;

/**
 * Use a rate gyro to return the robots heading relative to a starting position.
 *
 * <p>This class is for the XRP onboard gyro, and will only work in simulation/XRP mode. Only one
 * instance of a XRPGyro is supported.
 */
public class XRPGyro {
  private final SimDevice m_simDevice;
  private final SimDouble m_simRateX;
  private final SimDouble m_simRateY;
  private final SimDouble m_simRateZ;
  private final SimDouble m_simAngleX;
  private final SimDouble m_simAngleY;
  private final SimDouble m_simAngleZ;

  private double m_angleXOffset;
  private double m_angleYOffset;
  private double m_angleZOffset;

  /**
   * Constructs an XRPGyro.
   *
   * <p>Only one instance of a XRPGyro is supported.
   */
  public XRPGyro() {
    m_simDevice = SimDevice.create("Gyro:XRPGyro");
    if (m_simDevice != null) {
      m_simDevice.createBoolean("init", Direction.OUTPUT, true);
      m_simRateX = m_simDevice.createDouble("rate_x", Direction.INPUT, 0.0);
      m_simRateY = m_simDevice.createDouble("rate_y", Direction.INPUT, 0.0);
      m_simRateZ = m_simDevice.createDouble("rate_z", Direction.INPUT, 0.0);

      m_simAngleX = m_simDevice.createDouble("angle_x", Direction.INPUT, 0.0);
      m_simAngleY = m_simDevice.createDouble("angle_y", Direction.INPUT, 0.0);
      m_simAngleZ = m_simDevice.createDouble("angle_z", Direction.INPUT, 0.0);
    } else {
      m_simRateX = null;
      m_simRateY = null;
      m_simRateZ = null;
      m_simAngleX = null;
      m_simAngleY = null;
      m_simAngleZ = null;
    }
  }

  /**
   * Get the rate of turn around the X-axis.
   *
   * @return rate of turn as a measure.
   */
  public AngularVelocity getRateX() {
    if (m_simRateX != null) {
      return DegreesPerSecond.of(m_simRateX.get());
    }

    return DegreesPerSecond.of(0.0);
  }

  /**
   * Get the rate of turn around the Y-axis.
   *
   * @return rate of turn as a measure.
   */
  public AngularVelocity getRateY() {
    if (m_simRateY != null) {
      return DegreesPerSecond.of(m_simRateY.get());
    }

    return DegreesPerSecond.of(0.0);
  }

  /**
   * Get the rate of turn around the Z-axis.
   *
   * @return rate of turn as a measure.
   */
  public AngularVelocity getRateZ() {
    if (m_simRateZ != null) {
      return DegreesPerSecond.of(m_simRateZ.get());
    }

    return DegreesPerSecond.of(0.0);
  }

  /**
   * Get the currently reported angle around the X-axis.
   *
   * @return current angle around X-axis as a measure.
   */
  public Angle getAngleX() {
    if (m_simAngleX != null) {
      return Degrees.of(m_simAngleX.get() - m_angleXOffset);
    }

    return Degrees.of(0.0);
  }

  /**
   * Get the currently reported angle around the X-axis.
   *
   * @return current angle around Y-axis as a measure.
   */
  public Angle getAngleY() {
    if (m_simAngleY != null) {
      return Degrees.of(m_simAngleY.get() - m_angleYOffset);
    }

    return Degrees.of(0.0);
  }

  /**
   * Get the currently reported angle around the Z-axis.
   *
   * @return current angle around Z-axis as a measure.
   */
  public Angle getAngleZ() {
    if (m_simAngleZ != null) {
      return Degrees.of(m_simAngleZ.get() - m_angleZOffset);
    }

    return Degrees.of(0.0);
  }

  /** Reset the gyro angles to 0. */
  public void reset() {
    if (m_simAngleX != null) {
      m_angleXOffset = m_simAngleX.get();
      m_angleYOffset = m_simAngleY.get();
      m_angleZOffset = m_simAngleZ.get();
    }
  }

  /**
   * Return the actual angle in degrees that the robot is currently facing.
   *
   * <p>The angle is based on integration of the returned rate form the gyro. The angle is
   * continuous, that is, it will continue from 360->361 degrees. This allows algorithms that
   * wouldn't want to see a discontinuity in the gyro output as it sweeps from 360 to 0 on the
   * second time around.
   *
   * @return the current heading of the robot as a measure.
   */
  public Angle getAngle() {
    return getAngleZ();
  }

  /**
   * Gets the angle the robot is facing.
   *
   * @return A Rotation2d with the current heading.
   */
  public Rotation2d getRotation2d() {
    return new Rotation2d(getAngle());
  }

  /**
   * Return the rate of rotation of the gyro.
   *
   * <p>The rate is based on the most recent reading of the gyro.
   *
   * @return the current rate as a measure.
   */
  public AngularVelocity getRate() {
    return getRateZ();
  }

  /** Close out the SimDevice. */
  public void close() {
    if (m_simDevice != null) {
      m_simDevice.close();
    }
  }
}
