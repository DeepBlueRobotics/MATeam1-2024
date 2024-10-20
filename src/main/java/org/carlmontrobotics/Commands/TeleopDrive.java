package org.carlmontrobotics.Commands;

import java.util.ResourceBundle.Control;
import java.util.function.DoubleSupplier;

import org.mockito.internal.matchers.Null;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PS4Controller.Axis;
import edu.wpi.first.wpilibj2.command.Command;

public class TeleopDrive extends Command{
    double leftAxis, rightAxis;
    public TeleopDrive(DoubleSupplier left, DoubleSupplier right) {
        double leftAxis = left.getAsDouble();
        double rightAxis = right.getAsDouble();
        jIP(leftAxis,rightAxis);
    }
}
