package org.carlmontrobotics.Commands;

import java.util.ResourceBundle.Control;
import java.util.function.DoubleSupplier;

import org.carlmontrobotics.Subsystems.Drivetrain;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.Command;


import org.carlmontrobotics.Constants.AutoAlignToShelfc;

public class AutoAlignToShelf extends Command {
    private final Drivetrain drivetrain;

    public AutoAlignToShelf(Drivetrain drivetrain) {
        addRequirements(this.drivetrain = drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double currentAngle = drivetrain.getYaw();
        if (currentAngle > AutoAlignToShelfc.goodAngle) {
            drivetrain.drive(AutoAlignToShelfc.rotationalSpeed, AutoAlignToShelfc.rotationalSpeed, false);
        }
        else if (currentAngle < -AutoAlignToShelfc.goodAngle) {
            drivetrain.drive(-AutoAlignToShelfc.rotationalSpeed, -AutoAlignToShelfc.rotationalSpeed, false);
        }
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