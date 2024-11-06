package org.carlmontrobotics.Subsystems;

import java.util.function.DoubleSupplier;

import org.carlmontrobotics.lib199.MotorConfig;
import org.carlmontrobotics.lib199.MotorControllerFactory;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.ResourceBundle.Control;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis; 
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;


import org.carlmontrobotics.Constants.Dumperc;

public class Dumper extends SubsystemBase{
    //Creates a motor for the subsystem
    CANSparkMax dumperMotor = MotorControllerFactory.createSparkMax(Dumperc.dumper_id, MotorConfig.NEO);
    SparkPIDController pid = dumperMotor.getPIDController();
    RelativeEncoder dumperEncoder = dumperMotor.getEncoder();
    double rest_position = dumperEncoder.getPosition();
    double horizontal_angle = rest_position*360+Dumperc.angle_off_horizontal;
    double target = Dumperc.drop_off_angle/360 - rest_position;

    public Dumper() {
        //Intiallize the subsystem
        //You would want to reset the point where the motor reconginzes 0 degrees/0 rotations so it will be easier for dropOff and the other code
        pid.setP(Dumperc.kP);
        pid.setI(Dumperc.kI);
        pid.setD(Dumperc.kD);
        
    }
    
    public void resetEncoder() {
        dumperMotor.getEncoder().setPosition(0);
    }

    public void dropOff() {
        /* 
        Make the motor go the speed of pID(currrent angle, target angle, kP, kI, KD) that will go toward the goal of staying at the dropoff angle
        Make sure you know what units you are using for the angles, motor.getEncoder().getPosition() gets the answer in rotations 
        but if your constant for target angle is in degrees that will be a problem
        While the specfic button is pressed, robot container will continously call this causing the slide to be at the angle
        Leave break mode on so it will not osilate too much
        motor.setIdleMode(IdleMode.kBrake);
        Warning! brake mode does not fully stop the motor from moving! The slide is heavy and so gravity will pull it downs
        You can use motor.getEncoder().getPosition() to get the exact amount of rotations, like 2.43 rotations
        Make sure to call in softStop to make sure that you are not going to much accidently
        */
        if (softStop()) {
            if (achievedDropOff()) {
                dumperMotor.set(0);
                dumperMotor.setIdleMode(IdleMode.kBrake);
            }
            else {
                
                pid.setReference(target, ControlType.kPosition);
            }
        }
        else {
            dumperMotor.set(0);
            dumperMotor.setIdleMode(IdleMode.kBrake);
        }
    }

    public boolean achievedDropOff() {
        /*
         return True if the current position of the slide is at the drop off angle or greater
         */
        //The postition is in degrees
        double currentAngle = dumperEncoder.getPosition()*360;
        if (currentAngle-horizontal_angle >= Dumperc.drop_off_angle) {
            return true;
        }
        else {
            return false;
        }
        
    }

    public void rest() {
        //When the specific button is not pressed this method will be called.
        //IF the dumper tips over 90 degrees, rest should cause to go back SLOWLY not agressive
        //IF it is not tipped over then use motor.setIdleMode(IdleMode.kCoast); 
        //this will cause the motor to stop preventing itself from moving and will easily go down

        //have this at the end to reset pID
        //Holy shit please don't do that with locals ever again
        double currentAngle = dumperEncoder.getPosition()*360;
        if(currentAngle-horizontal_angle >= 90) {
            dumperMotor.set(-0.1*Dumperc.rotation_k);
        }
       else {
            dumperMotor.setIdleMode(IdleMode.kCoast);;
       }
        
    }

    public boolean softStop() {
        /*
         Prevent the robot to go try to flippen flip himself over
         use Dumperc.soft_stop_degrees or Dumperc.soft_stop_rotations. Make sure you know which one you are using
         return True if the robot can keep rotating more/move away
         return False if not
         don't worry about it being very close, that is why it is a soft stop
         */
        double currentAngle = dumperEncoder.getPosition()*360;
        if (currentAngle-horizontal_angle < Dumperc.soft_stop_degrees) {
            return true;
        }
        else {
            return false; 
        }
    }
}
