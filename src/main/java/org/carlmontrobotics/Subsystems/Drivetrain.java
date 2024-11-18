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
    //new Drivetrainc constants = Drivetrainc
    CANSparkMax motor1 = MotorControllerFactory.createSparkMax(Drivetrainc.left_motor_id,MotorConfig.NEO);
    CANSparkMax motor2 = MotorControllerFactory.createSparkMax(Drivetrainc.right_motor_id,MotorConfig.NEO);
    private AHRS navx;
    private final Dumper dumper;
    SparkPIDController pid1 = motor1.getPIDController();
    SparkPIDController pid2 = motor2.getPIDController();
    RelativeEncoder encoder1 = motor1.getEncoder();
    RelativeEncoder encoder2 = motor2.getEncoder();
    double rotationTarget;
    double motor1_rotation_k = Drivetrainc.motor1_rotation_k_slow;
    double motor2_rotation_k = Drivetrainc.motor2_rotation_k_slow;
    double pastMotor1Speed;
    double pastMotor2Speed;

    //_init_ navx/gyro
    public Drivetrain(Dumper dumper) {
        try {
            navx = new AHRS(SPI.Port.kMXP);  // Initialize NavX
        } catch (RuntimeException ex) {
            System.out.println("Error instantiating navX: " + ex.getMessage());
        }
        //set up PID
        pid1.setP(Drivetrainc.kP);
        pid1.setI(Drivetrainc.kI);
        pid1.setD(Drivetrainc.kD);
        pid2.setP(Drivetrainc.kP);
        pid2.setI(Drivetrainc.kI);
        pid2.setD(Drivetrainc.kD);
        this.dumper = dumper;
    }
    //WHY NO PREDEFINED INPUTS 😭
    public void drive(double motor1_input, double motor2_input, boolean acceleration) {
        if (acceleration) {
            motor1.set(motor1_input + pastMotor1Speed * Drivetrainc.acceleratingK);
            motor2.set(motor2_input + pastMotor2Speed * Drivetrainc.acceleratingK);
            pastMotor1Speed = motor1_input;
            pastMotor2Speed = motor2_input;
        }
        else {
            motor1.set(motor1_input);
            motor2.set(motor2_input);
        }
    }
    // overloaded method
    public void drive(double motor1_input, double motor2_input) {
        // if (Drivetrainc.accelerating_drive) {
        //     motor1.set(motor1_input + pastMotor1Speed * Drivetrainc.acceleratingK);
        //     motor2.set(motor2_input + pastMotor2Speed * Drivetrainc.acceleratingK);
        //     pastMotor1Speed = motor1_input;
        //     pastMotor2Speed = motor2_input;
        // }
        // else {
        //     motor1.set(motor1_input);
        //     motor2.set(motor2_input);
        // }
        //My mom taught me this life hack
        drive(motor1_input, motor2_input, Drivetrainc.accelerating_drive);
    }

    //Arcade Drive method, inputs are left y axis for forward and backward and a right x axis for rotional movement
    public void arcadeDrive(double leftY, double rightX) {
        if (checkBalance()) {
            double[] motorInputs = jIP(leftY, rightX);
            drive(motorInputs[0]*motor1_rotation_k, motorInputs[1]*motor2_rotation_k);
            // motor1.set(motorInputs[0]*motor1_rotation_k);
            // motor2.set(motorInputs[1]*motor2_rotation_k);
        }
    }
    //Reversed arcade method, inputs are left x axis for rotation, right y axis for movement
    public void reversedArcadeDrive(double leftX, double rightY) {
        if (checkBalance()) {
            double[] motorInputs = jIP(rightY, leftX);
            drive(motorInputs[0]*motor1_rotation_k, motorInputs[1]*motor2_rotation_k);
            // motor1.set(motorInputs[0]*motor1_rotation_k);
            // motor2.set(motorInputs[1]*motor2_rotation_k);
        }
    }
    //Tank drive method left joystick moves left motor, right joystick moves right motor
    public void tankDrive(double leftY, double rightY) {
        if (checkBalance()) {
            drive(leftY*motor1_rotation_k, rightY*motor2_rotation_k);
            // motor1.set(leftY*motor1_rotation_k);
            // motor2.set(rightY*motor2_rotation_k);
        }
    }
        
    //PID drive
    public void pidDrive(double target) {
        rotationTarget = target*Drivetrainc.kDis_Rot;
        pid1.setReference(rotationTarget, ControlType.kPosition);
        pid2.setReference(rotationTarget, ControlType.kPosition);
    }
    //it stops the drivetrain (aka a brake)
    public void brakeMotor() {
        motor1.set(0);
        motor2.set(0);
    }
    

    //Turns inputs from the joysticks into inputs for motors, 
    //might have 1.5 and even 2 but aaron said that is still a one and the result will still be relaible
    private double [] jIP(double yAxis, double xAxis) {
        double[] posYArr = {yAxis+xAxis/2, yAxis-xAxis/2};
        double[] negYArr = {yAxis-xAxis/2, yAxis+xAxis/2};
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

    //Method to get current rotation angle
    public double getYaw() {
        return navx.getYaw(); // Yaw angle in degrees [-180,180]
    }

    //Method to reset gyro
    public void resetYaw() {
        navx.reset();
    }

    //resets the encoders
    public void resetEncoders() {
        //set both encoders to 0;
        encoder1.setPosition(0);
        encoder2.setPosition(0);
    }

    //finds distance that drivetrain/robot has traveled
    public double getDistance() {
        double average_rotation = (encoder1.getPosition()+encoder2.getPosition())/2;
        return average_rotation/Drivetrainc.kDis_Rot;
    }
    //makes sure that the robot won't run away while the dumper is off balance
    private boolean checkBalance() {
        if (dumper.softStop()) {
            return true;
        }
        else {
            return false;
        }
    }
    public void madness() {
        motor1_rotation_k = -1;
        motor2_rotation_k = 1;
    }

    public void turbo() {
        motor1_rotation_k = Drivetrainc.motor1_rotation_k_turbo;
        motor2_rotation_k = Drivetrainc.motor2_rotation_k_turbo;
    }

    public void slow() {
        motor1_rotation_k = Drivetrainc.motor1_rotation_k_slow;
        motor2_rotation_k = Drivetrainc.motor2_rotation_k_slow;
    }


}
