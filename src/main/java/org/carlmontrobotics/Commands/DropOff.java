// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.Commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

import org.carlmontrobotics.Subsystems.Dumper;

public class DropOff extends Command {
  private final Dumper dumper;
  
  public DropOff(Dumper dumper) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.dumper = dumper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // dumper.dropOff();
    dumper.moveMotor(-0.1);
   
  }
//Il
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
