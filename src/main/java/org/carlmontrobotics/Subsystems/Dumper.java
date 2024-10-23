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

import org.carlmontrobotics.Constants.Dumperc;

public class Dumper extends SubsystemBase{
    //Creates a motor for the subsystem
    CANSparkMax dumperMotor = MotorControllerFactory.createSparkMax(Dumperc.dumper_id, MotorConfig.NEO);

    public Dumper() {
        //Intiallize the subsystem
    }
    public void Dropoff() {
        //Using PID or some of it make a code that will go toward the goal of staying at the dropoff angle
        //While the specfic button is pressed, robot container will continously call this causing the slide to be at the angle
        //Leave break mode on so it will not osilate too much
    }
    public void Rest() {
        //SLOWLY lower the arm down(coast mode maybe?)
        //When the specific button is not pressed this method will be called.
    }
    private double PID() {
        //Using the PID constants in Constants.Dumperc that will be picked up later
        //Return a motor input ranging from 0-1 not negative!
    }
}
