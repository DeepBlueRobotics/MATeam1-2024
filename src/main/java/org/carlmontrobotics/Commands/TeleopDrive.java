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

    ////Define this method
    public TeleopDrive(Drivetrain drivetrain, 
    DoubleSupplier forwardArcade, DoubleSupplier rotateArcade, 
    DoubleSupplier forwardReversedArcade, DoubleSupplier rotateReversedArcade,
    DoubleSupplier leftTank, DoubleSupplier rightTank) {
        // What does this part do?
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
        //Why are there conditional statements? What do they do?
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

    //Define this method
    private void Arcade() {
        double yAxis = forwardArcade.getAsDouble();
        double xAxis = rotateArcade.getAsDouble();
        drivetrain.arcadeDrive(yAxis, xAxis);
    }
    //Define this method
    private void ReversedArcade() {
        double yAxis = forwardReversedArcade.getAsDouble();
        double xAxis = rotateReversedArcade.getAsDouble();
        drivetrain.reversedArcadeDrive(xAxis, yAxis);
    }
    //Define this method
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