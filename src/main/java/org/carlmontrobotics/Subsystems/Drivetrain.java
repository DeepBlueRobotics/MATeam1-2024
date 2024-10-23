package org.carlmontrobotics.Subsystems;

import java.util.function.DoubleSupplier;

import org.carlmontrobotics.lib199.MotorConfig;
import org.carlmontrobotics.lib199.MotorControllerFactory;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.ResourceBundle.Control;

import org.mockito.internal.matchers.Null;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.Command;

//NavX
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

//Constants
import org.carlmontrobotics.Constants.Drivetrainc;

public class Drivetrain extends SubsystemBase{
    //new Drivetrainc constants = Drivetrainc
    CANSparkMax motor1 = MotorControllerFactory.createSparkMax(Drivetrainc.left_motor_id,MotorConfig.NEO);
    CANSparkMax motor2 = MotorControllerFactory.createSparkMax(Drivetrainc.right_motor_id,MotorConfig.NEO);
    double YAxis, XAxis;
    private AHRS navx;
    //_init_ navx/gyro
    public Drivetrain() {
        try {
            navx = new AHRS(SPI.Port.kMXP);  // Initialize NavX
        } catch (RuntimeException ex) {
            System.out.println("Error instantiating navX: " + ex.getMessage());
        }
    }

    //Arcade Drive method, inputs are left y axis for forward and backward and a right x axis for rotional movement
    public void arcadeDrive(double left, double right) {
        // double YAxis = left.getAsDouble();
        // double XAxis = right.getAsDouble();
        double[] motorInputs = jIP(YAxis, XAxis);
        motor1.set(motorInputs[0]);
        motor2.set(motorInputs[1]);
    }
    

    //Turns inputs from the joysticks into inputs for motors, 
    //might have 1.5 and even 2 but aaron said that is still a one and the result will still be relaible
    private double [] jIP(double left, double right) {
        double yAxis = left;
        double xAxis = right;
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
    //Method to get current rotation angle
    public double getYaw() {
        return navx.getYaw(); // Yaw angle in degrees [-180,180]
    }
    //Method to reset gyro
    public void resetYaw() {
        navx.reset();
    }
}
