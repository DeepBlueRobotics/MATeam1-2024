package org.carlmontrobotics.Subsystems;

import java.util.function.DoubleSupplier;

import org.carlmontrobotics.lib199.MotorConfig;
import org.carlmontrobotics.lib199.MotorControllerFactory;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.ResourceBundle.Control;

import org.mockito.internal.matchers.Null;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.Command;

import org.carlmontrobotics.Constants.Dumperc;

public class Dumper extends SubsystemBase{
    //Creates a motor for the subsystem
    CANSparkMax dumperMotor = MotorControllerFactory.createSparkMax(Dumperc.dumper_id, MotorConfig.NEO);

    public Dumper() {
        //Intiallize the subsystem
        //You would want to reset the point where the motor reconginzes 0 degrees/0 rotations so it will be easier for dropOff and the other code
    }
    public void dropOff() {
        /* 
        Using PID or some of it make a code that will go toward the goal of staying at the dropoff angle
        While the specfic button is pressed, robot container will continously call this causing the slide to be at the angle
        Leave break mode on so it will not osilate too much
        You can use motor.getPosition() to get the exact amount of rotations, like 2.43 rotations
        Make sure to call in softStop to make sure that you are not going to much accidently
        */
    }

    public boolean achievedDropOff() {
        /*
         return if the current position of the slide is at the drop off angle or greater
         */
    }
    public void rest() {
        //SLOWLY lower the arm down(coast mode maybe?)
        //When the specific button is not pressed this method will be called.
    }

    private double pID() {
        /*Using the PID constants in Dumperc that will be picked up later
        They are called Dumperc.kP, Dumperc.kI, Dumperc.kD
        Return a motor input ranging from 0-1 not negative!
        Btw there is already code for pID that you can find in MatterMost Programming training website
        */
    }

    private void softStop() {
        /*
         Prevent the robot to go try to flippen flip himself over
         use Dumperc.soft_stop_degrees or Dumperc.soft_stop_rotations. Make sure you know which one you are using
         return True if the robot can keep rotating more
         return False if not
         don't worry about it being very close, that is why it is a soft stop
         */
    }
}
