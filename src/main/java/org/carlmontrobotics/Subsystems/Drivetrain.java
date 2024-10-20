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

public class Drivetrain {
    CANSparkMax motor1 = MotorControllerFactory.createSparkMax(0,MotorConfig.NEO);
    CANSparkMax motor2 = MotorControllerFactory.createSparkMax(1,MotorConfig.NEO);
    double YAxis, XAxis;
    public Drivetrain(DoubleSupplier left, DoubleSupplier right) {
        double YAxis = left.getAsDouble();
        double XAxis = right.getAsDouble();
        double[] motorInputs = jIP(YAxis, XAxis);
        motor1.set(motorInputs[0]);
        motor2.set(motorInputs[1]);
    }
    
    private double [] jIP(double left, double right) {
        double yAxis = left;
        double xAxis = right;
        double[] posYArr = {yAxis+xAxis, yAxis-xAxis};
        double[] negYArr = {yAxis-xAxis, yAxis+xAxis};
        if (yAxis==0) {
            double[] arr= {xAxis, -xAxis}
            return arr;
        }
        else if (xAxis==0) {
            double [] arr = {yAxis, yAxis}
            return arr;
        }
        else if (yAxis<0) {
            return negYArr;
        }
        else if (yAxis > 0) {
            return posYArr;
        }
    }
}
