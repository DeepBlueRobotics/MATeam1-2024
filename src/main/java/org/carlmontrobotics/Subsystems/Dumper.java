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
    //Define this section
    CANSparkMax dumperMotor = MotorControllerFactory.createSparkMax(Dumperc.dumper_id, MotorConfig.NEO);
    SparkPIDController pid = dumperMotor.getPIDController();
    RelativeEncoder dumperEncoder = dumperMotor.getEncoder();
    double target;

    public Dumper() {
        //Define this section
        pid.setP(Dumperc.kP);
        pid.setI(Dumperc.kI);
        pid.setD(Dumperc.kD);
        resetEncoder();
        
    }
    //Define this method
    public void resetEncoder() {
        dumperMotor.getEncoder().setPosition(0);
    }
    //Define this method
    public void dropOff() {
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
    ////Define this method
    public boolean achievedDropOff() {
        double currentAngle = dumperEncoder.getPosition()*360;
        if (currentAngle-Dumperc.angle_off_horizontal >= Dumperc.drop_off_angle) {
            return true;
        }
        else {
            return false;
        }
        
    }
    ////Define this method
    public void rest() {
        double currentAngle = dumperEncoder.getPosition()*360;
        if(currentAngle-Dumperc.angle_off_horizontal >= 90) {
            dumperMotor.set(-0.1*Dumperc.rotation_k);
        }
       else {
            dumperMotor.setIdleMode(IdleMode.kCoast);;
       }
        
    }
    //Define this method
    public boolean softStop() {
        double currentAngle = dumperEncoder.getPosition()*360;
        if (currentAngle-Dumperc.angle_off_horizontal < Dumperc.soft_stop_degrees) {
            return true;
        }
        else {
            return false; 
        }
    }
}
