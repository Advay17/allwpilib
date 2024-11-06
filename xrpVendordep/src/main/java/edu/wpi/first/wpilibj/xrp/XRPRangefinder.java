// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.wpilibj.xrp;

import static edu.wpi.first.units.Units.Meters;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AnalogInput;

/** This class represents the ultrasonic rangefinder on an XRP robot. */
public class XRPRangefinder {
  private final AnalogInput m_rangefinder = new AnalogInput(2);

  /**
   * Constructs an XRPRangefinder.
   *
   * <p>Only one instance of a XRPRangefinder is supported.
   */
  public XRPRangefinder() {}

  /**
   * Get the measured distance in meters. Distance further than 4m will be reported as 4m.
   *
   * @return distance in meters
   * @deprecated use getDistance instead
   */
  @Deprecated(forRemoval = true, since = "2025")
  public double getDistanceMeters() {
    return (m_rangefinder.getVoltage() / 5.0) * 4.0;
  }

  /**
   * Get the measured distance in inches.
   *
   * @return distance in inches
   * @deprecated use getDistance instead
   */
  @Deprecated(forRemoval = true, since = "2025")
  public double getDistanceInches() {
    return Units.metersToInches(getDistanceMeters());
  }

  /**
   * Get the measured distance in meters. Distance further than 4m will be reported as 4m.
   *
   * @return distance in meters
   */
  public Distance getDistance() {
    return Meters.of((m_rangefinder.getVoltage() / 5.0) * 4.0);
  }
}
