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
    CANSparkMax dumperMotor = MotorControllerFactory.createSparkMax(2, MotorConfig.NEO);

    public Dumper() {

    }
}
