package org.carlmontrobotics.Commands;

import java.util.ResourceBundle.Control;
import java.util.function.DoubleSupplier;

import org.carlmontrobotics.Subsystems.Drivetrain;
import org.carlmontrobotics.Subsystems.Dumper;
import org.mockito.internal.matchers.And;
import org.mockito.internal.matchers.Null;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.wpilibj.Timer;

import org.carlmontrobotics.Constants.HitAndRunAutonc;


//Auton that first scores a cube(Assuming that the robot was placed right next to the shelf) 
//and then runs off to where it needs to go on the perimeter to get maximum points
public class HitAndRunAuton extends Command{
    private final Drivetrain drivetrain;
    private final Dumper dumper;
    Timer timer = new Timer();
    boolean cube_scored = false;
    double time_dropped_off;
    double lastTimestamp = 0;
    double accumulatedError = 0; // for the kI term
    double previousError = 0; // for the kD term
    double target = (HitAndRunAutonc.min_d+HitAndRunAutonc.max_d)/2;
    double currentPos;

    public HitAndRunAuton(Drivetrain drivetrain, Dumper dumper) {
        addRequirements(this.drivetrain = drivetrain, this.dumper = dumper);
        drivetrain.resetMotors();
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize()  {
        timer.reset();
        timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (cube_scored) {
            if (time_dropped_off+HitAndRunAutonc.drop_off_wait_time <= timer.get()) {
                dumper.rest();
                currentPos = drivetrain.getDistance();
                if (currentPos >= HitAndRunAutonc.min_d && currentPos <= HitAndRunAutonc.max_d) {
                    drivetrain.brakeMotor();
                }
                else {
                    drivetrain.arcadeDrive(calculate(currentPos, target, HitAndRunAutonc.kP, HitAndRunAutonc.kI, HitAndRunAutonc.kD), 0);
                }
            }
        }
        else {
            if (dumper.achievedDropOff()) {
                cube_scored = true;
                time_dropped_off = timer.get();
                lastTimestamp = time_dropped_off+HitAndRunAutonc.drop_off_wait_time;
            }
            else {
                dumper.dropOff();
            }
        }
    }

    /* calculates the output of the PID controller given the error (input) */
    private double calculate(double currentPosition, double targetPosition, double kP, double kI, double kD) {
        double error = targetPosition - currentPosition;
        double currentTimestamp = timer.get();
        // amount of time that passed since the last time the controller was run
        double deltaTime = currentTimestamp - lastTimestamp;
        accumulatedError += kI * error * deltaTime;
        double derivative = (error - previousError) / deltaTime;

        // update values for next time this method is called
        lastTimestamp = currentTimestamp;
        previousError = error;

        return kP * error + accumulatedError + kD * derivative;
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
