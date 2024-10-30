// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics;

//199 files
//ERROR:
// import org.carlmontrobotics.commands.*;
// import org.carlmontrobotics.subsystems.*;
// import static org.carlmontrobotics.Constants.*;

//controllers
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Axis;

//commands
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//control bindings
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import org.carlmontrobotics.Constants.OI;
import org.carlmontrobotics.Subsystems.Drivetrain;
import org.carlmontrobotics.Subsystems.Dumper;

public class RobotContainer {
  //Creating an object of DT Class
  public final Drivetrain drivetrain = new Drivetrain();

  //Creating an object of Dumper Class
  public final Dumper dumper = new Dumper();

  //Creates an object of Xbox controller
  public final GenericHID controller = new GenericHID(OI.port);
  
  public RobotContainer() {
    drivetrain.resetYaw();
    drivetrain.resetEncoders();
    dumper.resetEncoder();
    setBindings();
  }

  private void setBindings() {
    new JoystickButton(controller, OI.dumperTrigger);

  }
  private double getStickyValue(GenericHID hid, Axis axis) {
    return hid.getRawAxis(axis.value);
  }

//Karena said that this is not needed
private void setDefaultCommands() {
    drivetrain.setDefaultCommand(new Drivetrain(
      () -> ProcessedAxisValue(controller, Axis.kLeftY),
      () -> ProcessedAxisValue(controller, Axis.kRightX)));
    //Setting the default command to check inputs of the A button
    //I'm thinking once it's pressed, the load is lifted, but once
    //the button is released, the load is slid down
    dumper.setDefaultCommand(new Dumper(
      () -> getAButtonPressed(),
      () -> getAButtonReleased()));
    drivetrain.setDefaultCommand(new TeleopDrive(
      drivetrain,
      () -> ProcessedAxisValue(driverController, Axis.kLeftY),
      () -> ProcessedAxisValue(driverController, Axis.kLeftX),
      () -> ProcessedAxisValue(driverController, Axis.kRightX),
      () -> driverController.getRawButton(OI.Driver.slowDriveButton)));
}

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  /**
   * Flips an axis' Y coordinates upside down, but only if the select axis is a joystick axis
   * 
   * @param hid The controller/plane joystick the axis is on
   * @param axis The processed axis
   * @return The processed value.
   */
  private double getStickValue(GenericHID hid, Axis axis) {
    return hid.getRawAxis(axis.value) * (axis == Axis.kLeftY || axis == Axis.kRightY ? -1 : 1);
  }
  /**
   * Processes an input from the joystick into a value between -1 and 1, sinusoidally instead of linearly
   * 
   * @param value The value to be processed.
   * @return The processed value.
   */
  private double inputProcessing(double value) {
    double processedInput;
    // processedInput =
    // (((1-Math.cos(value*Math.PI))/2)*((1-Math.cos(value*Math.PI))/2))*(value/Math.abs(value));
    processedInput = Math.copySign(((1 - Math.cos(value * Math.PI)) / 2) * ((1 - Math.cos(value * Math.PI)) / 2),
        value);
    return processedInput;
  }
  /**
   * Combines both getStickValue and inputProcessing into a single function for processing joystick outputs
   * 
   * @param hid The controller/plane joystick the axis is on
   * @param axis The processed axis
   * @return The processed value.
   */
  private double ProcessedAxisValue(GenericHID hid, Axis axis){
    return inputProcessing(getStickValue(hid, axis));
  }

}