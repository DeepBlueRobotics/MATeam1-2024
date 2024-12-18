package org.carlmontrobotics.Commands;

import java.util.ResourceBundle.Control;
import java.util.function.DoubleSupplier;

import org.carlmontrobotics.Constants.HitAndRunAutonc;
import org.carlmontrobotics.Constants.SimpleAutoc;
import org.carlmontrobotics.Subsystems.Drivetrain;
import org.carlmontrobotics.Subsystems.Dumper;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj.Timer;


public class SimpleAuto extends Command{
    Timer timer = new Timer();
    private final Drivetrain drivetrain;
    double currentPos;

    public SimpleAuto(Drivetrain drivetrain) {
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
        currentPos = drivetrain.getDistance();
        if (currentPos > SimpleAutoc.min_d && currentPos < SimpleAutoc.max_d) {
            drivetrain.brakeMotor();
        }
        else {
            if (currentPos < SimpleAutoc.min_d) {
                drivetrain.drive(SimpleAutoc.optimalSpeed1, SimpleAutoc.optimalSpeed2);
            }
            else {
                drivetrain.drive(-SimpleAutoc.optimalSpeed1, -SimpleAutoc.optimalSpeed2);
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
