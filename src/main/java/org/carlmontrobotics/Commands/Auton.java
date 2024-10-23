package org.carlmontrobotics.Commands;

import java.util.ResourceBundle.Control;
import java.util.function.DoubleSupplier;

import org.carlmontrobotics.Subsystems.Drivetrain;
import org.mockito.internal.matchers.Null;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj.Timer;


public class Auton extends Command{
    private final Drivetrain drivetrain;
    Timer timer = new Timer();

    public Auton(Drivetrain drivetrain) {
        addRequirements(this.drivetrain = drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()  {
        timer.reset();
        timer.start();
        drivetrain.arcadeDrive(1, 0);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        
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
