package org.carlmontrobotics.Commands;

import java.util.ResourceBundle.Control;
import java.util.function.DoubleSupplier;

import org.carlmontrobotics.Constants.CrazyAutoc;
import org.carlmontrobotics.Subsystems.Drivetrain;
import org.carlmontrobotics.Subsystems.Dumper;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj.Timer;


public class CrazyAuto extends Command{
    Timer timer = new Timer();
    private final Drivetrain drivetrain;
    double currentPos;
    boolean crash = false;
    public CrazyAuto(Drivetrain drivetrain) {
        addRequirements(this.drivetrain = drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()  {
        timer.reset();
        timer.start();
        drivetrain.resetEncoders();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (crash) {
            currentPos = drivetrain.getDistance();
            if (currentPos > CrazyAutoc.min_d2) {
                if (currentPos < CrazyAutoc.max_d2) {
                    drivetrain.brakeMotor();
                }
                else {
                    drivetrain.drive(-CrazyAutoc.optimalSpeed1, -CrazyAutoc.optimalSpeed2);
                }
            }
            else {
                drivetrain.drive(CrazyAutoc.optimalSpeed1, CrazyAutoc.optimalSpeed2);
            }
        }
        else {
            currentPos = drivetrain.getDistance();
            if (currentPos > CrazyAutoc.min_d1) {
                crash = true;
                drivetrain.brakeMotor();
            }
            else {
                drivetrain.drive(CrazyAutoc.crashOptimalSpeed1, CrazyAutoc.crashOptimalSpeed2);
            }
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return timer.get() > 15;
    }
}
