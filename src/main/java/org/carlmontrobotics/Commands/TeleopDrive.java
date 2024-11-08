// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.carlmontrobotics.Commands;

import java.util.function.DoubleSupplier;

import org.carlmontrobotics.Constants.TeleopC;
import org.carlmontrobotics.Subsystems.Drivetrain;

import com.ctre.phoenix6.mechanisms.swerve.SwerveModule.DriveRequestType;

import edu.wpi.first.wpilibj2.command.Command;

public class TeleopDrive extends Command {
    public final Drivetrain drivetrain;
    private DoubleSupplier forwardArcade;
    private DoubleSupplier rotateArcade;
    private DoubleSupplier forwardReversedArcade;
    private DoubleSupplier rotateReversedArcade;
    private DoubleSupplier leftTank;
    private DoubleSupplier rightTank;

    /** Creates a new AimOuttakeSpeaker. */
    public TeleopDrive(Drivetrain drivetrain, 
    DoubleSupplier forwardArcade, DoubleSupplier rotateArcade, 
    DoubleSupplier forwardReversedArcade, DoubleSupplier rotateReversedArcade,
    DoubleSupplier leftTank, DoubleSupplier rightTank) {
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(this.drivetrain = drivetrain);
        this.forwardArcade = forwardArcade;
        this.rotateArcade = rotateArcade;
        this.forwardReversedArcade = forwardReversedArcade;
        this.rotateReversedArcade = rotateReversedArcade;
        this.leftTank = leftTank;
        this.rightTank = rightTank;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        //ignore the dead code, it is not dead as it is designed for easy change in the way how to operate the drivetrain
        if (TeleopC.driveType == 0) {
            Arcade();
        }
        else if (TeleopC.driveType == 1) {
            ReversedArcade();
        }
        else if (TeleopC.driveType == 2) {
            Tank();
        }
    }

    //Moves drivetrain using Arcade
    private void Arcade() {
        double yAxis = forwardArcade.getAsDouble();
        double xAxis = rotateArcade.getAsDouble();
        drivetrain.arcadeDrive(yAxis, xAxis);
    }
    //Moves drivetrain using Reverse arcade
    private void ReversedArcade() {
        double yAxis = forwardReversedArcade.getAsDouble();
        double xAxis = rotateReversedArcade.getAsDouble();
        drivetrain.reversedArcadeDrive(xAxis, yAxis);
    }
    //Moves drivetrain using tank drive
    private void Tank() {
        double left = leftTank.getAsDouble();
        double right = rightTank.getAsDouble();
        drivetrain.tankDrive(left, right);
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