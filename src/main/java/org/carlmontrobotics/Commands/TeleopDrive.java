// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.Commands;

import java.util.function.DoubleSupplier;

import org.carlmontrobotics.Subsystems.Drivetrain;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.wpilibj2.command.Command;

public class TeleopDrive extends Command {
    public final Drivetrain drivetrain;
    private DoubleSupplier forward;
    private DoubleSupplier rotate;

    /** Creates a new AimOuttakeSpeaker. */
    public TeleopDrive(Drivetrain drivetrain, DoubleSupplier forward, DoubleSupplier rotate) {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(this.drivetrain = drivetrain);
        this.forward = forward;
        this.rotate = rotate;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double yAxis = forward.getAsDouble();
        double xAxis = rotate.getAsDouble();
        drivetrain.arcadeDrive(yAxis, xAxis);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}