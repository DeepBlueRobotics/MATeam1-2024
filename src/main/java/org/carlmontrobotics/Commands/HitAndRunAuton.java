package org.carlmontrobotics.Commands;

import java.util.ResourceBundle.Control;
import java.util.function.DoubleSupplier;

import org.carlmontrobotics.Subsystems.Drivetrain;
import org.carlmontrobotics.Subsystems.Dumper;

import com.revrobotics.SparkPIDController;

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
    double currentPos;
    double driveTime;
    
    

    public HitAndRunAuton(Drivetrain drivetrain, Dumper dumper) {
        addRequirements(this.drivetrain = drivetrain, this.dumper = dumper);
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
        if (cube_scored) {
            if (driveTime <= timer.get()) {
                dumper.rest();
                currentPos = drivetrain.getDistance();
                if (currentPos > HitAndRunAutonc.min_d && currentPos < HitAndRunAutonc.max_d) {
                    drivetrain.brakeMotor();
                }
                else {
                    //We don't have time for pid :(
                    //drivetrain.pidDrive(HitAndRunAutonc.averageDistance);
                    if (currentPos < HitAndRunAutonc.min_d) {
                        drivetrain.drive(HitAndRunAutonc.optimalSpeed1, HitAndRunAutonc.optimalSpeed2);
                    }
                    else {
                        drivetrain.drive(-HitAndRunAutonc.optimalSpeed1, -HitAndRunAutonc.optimalSpeed2);
                    }
                }
            }
        }
        else {
            if (dumper.achievedDropOff()) {
                cube_scored = true;
                time_dropped_off = timer.get();
                driveTime = time_dropped_off+HitAndRunAutonc.drop_off_wait_time;
            }
            else {
                dumper.dropOff();
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
