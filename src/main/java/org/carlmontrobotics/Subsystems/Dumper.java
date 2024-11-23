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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;


import org.carlmontrobotics.Constants.Dumperc;

public class Dumper extends SubsystemBase{
    //Creates a motor for the subsystem
    CANSparkMax dumperMotor = MotorControllerFactory.createSparkMax(Dumperc.dumper_id, MotorConfig.NEO);
    SparkPIDController pid = dumperMotor.getPIDController();
    RelativeEncoder dumperEncoder = dumperMotor.getEncoder();
    double target;

    public Dumper() {
        //Intiallize the subsystem
        //You would want to reset the point where the motor reconginzes 0 degrees/0 rotations so it will be easier for dropOff and the other code
        // pid.setP(Dumperc.kP);
        // pid.setI(Dumperc.kI);
        // pid.setD(Dumperc.kD);
        resetEncoder();
        
    }
    
    public void resetEncoder() {
        dumperEncoder.setPosition(0);
        SmartDashboard.putNumber("StartingEncoderValue", dumperEncoder.getPosition());
    }

    public void dropOff() {
        /* 
        Aproaches a certain angle 
        */
        SmartDashboard.putNumber("EncoderValue", dumperEncoder.getPosition());
        SmartDashboard.putBoolean("Resting", false);
        if (softStop()) {
            if (achievedDropOff()) {
                dumperMotor.set(0);
                dumperMotor.setIdleMode(IdleMode.kBrake);
            }
            else {
                dumperMotor.set(Dumperc.rotation_k);
                //pid.setReference(target, ControlType.kPosition);
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
        double currentAngle = -dumperEncoder.getPosition()*360*Dumperc.gearRatio;
        SmartDashboard.putNumber("CurrentAngle", currentAngle);
        if (currentAngle-Dumperc.angle_off_horizontal >= Dumperc.drop_off_angle) {
            return true;
        }
        else {
            return false;
        }
        
    }

    public void rest() {
        //IF the dumper tips over 70 degrees, rest should cause to go back SLOWLY not agressive
        //IF it is not tipped over then use motor.setIdleMode(IdleMode.kCoast); 
        SmartDashboard.putBoolean("Resting", true);
        double currentAngle = -dumperEncoder.getPosition()*360*Dumperc.gearRatio;
        SmartDashboard.putBoolean("Past80", currentAngle-Dumperc.angle_off_horizontal >= 80);
        if(currentAngle-Dumperc.angle_off_horizontal >= 80) {
            dumperMotor.set(-Dumperc.rotation_k);
        }
       else {
            dumperMotor.setIdleMode(IdleMode.kCoast);;
       }
        
    }

    public double getEncoderPosition() {
        return dumperEncoder.getPosition()*(-1)*Dumperc.gearRatio
    }

    public boolean softStop() {
        /*
         return True if the robot can keep rotating more/move away
         return False if not
         */
        double currentAngle = -dumperEncoder.getPosition()*360*Dumperc.gearRatio;
        if (currentAngle-Dumperc.angle_off_horizontal < Dumperc.soft_stop_degrees) {
            return true;
        }
        else {
            return false; 
        }
    }
}
