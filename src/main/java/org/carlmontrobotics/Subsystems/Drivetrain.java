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

//NavX
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.PubSub;
import edu.wpi.first.wpilibj.SPI;

//Constants
import org.carlmontrobotics.Constants.Drivetrainc;

public class Drivetrain extends SubsystemBase{
    //Defines different variables
    CANSparkMax motor1 = MotorControllerFactory.createSparkMax(Drivetrainc.left_motor_id,MotorConfig.NEO);
    CANSparkMax motor2 = MotorControllerFactory.createSparkMax(Drivetrainc.right_motor_id,MotorConfig.NEO);
    private AHRS navx;
    private final Dumper dumper = new Dumper();
    SparkPIDController pid1 = motor1.getPIDController();
    SparkPIDController pid2 = motor2.getPIDController();
    RelativeEncoder encoder1 = motor1.getEncoder();
    RelativeEncoder encoder2 = motor2.getEncoder();
    double rotationTarget;

    
    public Drivetrain() {
        //Tries connects the Gyro to the drivestation
        try {
            navx = new AHRS(SPI.Port.kMXP);  // creates an object to connect with the navx on the robot
        } catch (RuntimeException ex) {
            System.out.println("Error instantiating navX: " + ex.getMessage());
        }
        //Sets PID for the drivetrain motors
        pid1.setP(Drivetrainc.kP);
        pid1.setI(Drivetrainc.kI);
        pid1.setD(Drivetrainc.kD);
        pid2.setP(Drivetrainc.kP);
        pid2.setI(Drivetrainc.kI);
        pid2.setD(Drivetrainc.kD);
    }

    //The inputs are leftX and rightY
    //First it checksBalance
    //Gets two values for the motrs from the joysticks
    //Sets the motors to the speeds that it got
    public void arcadeDrive(double leftY, double rightX) {
        if (checkBalance()) {
            double[] motorInputs = jIP(leftY, rightX);
            motor1.set(motorInputs[0]*Drivetrainc.motor1_rotation_k);
            motor2.set(motorInputs[1]*Drivetrainc.motor2_rotation_k);
        }
    }
    //Same thing as the arcade drive but the right joystick is for direction and the left rotation
    public void reversedArcadeDrive(double leftX, double rightY) {
        if (checkBalance()) {
            double[] motorInputs = jIP(rightY, leftX);
            motor1.set(motorInputs[0]*Drivetrainc.motor1_rotation_k);
            motor2.set(motorInputs[1]*Drivetrainc.motor2_rotation_k);
        }
    }
    //Right joystick controls right motor and left joystick controls left motor
    public void tankDrive(double leftY, double rightY) {
        if (checkBalance()) {
            motor1.set(leftY);
            motor2.set(rightY);
        }
    }
        
    //Moves the motors by how far the drivetrain is away from the target
    public void pidDrive(double target) {
        rotationTarget = target*Drivetrainc.kDis_Rot;
        pid1.setReference(rotationTarget, ControlType.kPosition);
        pid2.setReference(rotationTarget, ControlType.kPosition);
    }
    //Sets the motors back to zero
    public void brakeMotor() {
        motor1.set(0);
        motor2.set(0);
    }
    

    //Turns joystick inputs into volatage percentage of the motors
    private double [] jIP(double yAxis, double xAxis) {
        double[] posYArr = {yAxis+xAxis, yAxis-xAxis};
        double[] negYArr = {yAxis-xAxis, yAxis+xAxis};
        if (yAxis==0) {
            double[] arr= {xAxis, -xAxis};
            return arr;
        }
        else if (xAxis==0) {
            double [] arr = {yAxis, yAxis};
            return arr;
        }
        else if (yAxis<0) {
            return negYArr;
        }
        else if (yAxis > 0) {
            return posYArr;
        }
        double [] empty = {0.0,0.0};
        return empty;
    }

    //returns a double that represents the number of degrees the drivetrain the turned
    public double getYaw() {
        return navx.getYaw(); // Degrees
    }

    //Sets the current angle the drivetrain is at to 0
    public void resetYaw() {
        navx.reset();
    }

    //Sets the position of the encoders to 0
    public void resetEncoders() {
        encoder1.setPosition(0);
        encoder2.setPosition(0);
    }

    //Finds the average the rotation returns the average distance by the motors
    public double getDistance() {
        double average_rotation = (encoder1.getPosition()+encoder2.getPosition())/2;
        return average_rotation/Drivetrainc.kDis_Rot;
    }
    
    //Checks if the dumper is extended or not
    private boolean checkBalance() {
        if (dumper.softStop()) {
            return true;
        }
        else {
            return false;
        }
    }
}
