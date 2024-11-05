package org.carlmontrobotics;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController.Axis;

public final class Constants {
    public static final class Drivetrainc {
        public static final int left_motor_id = 0;
        public static final int right_motor_id = 1;
        //Reqires tuning and testing
        public static final double kP = 0.1;
        public static final double kI = 0.1;
        public static final double kD = 0.1;
        
        public static final int motor1_rotation_k = 1;
        public static final int motor2_rotation_k = -1;
        //Find out the values
        public static final double wheel_diameter = 0;//inches
        public static final double wheel_radius = 0;//inches
        //d_wheels stands for distance between the wheels(dominant)
        public static final double d_wheels = 0;//inches
    }
    public static final class Dumperc {
        public static final int dumper_id = 2;
        //reqires tuning and testing
        public static final double kP = 0.1;
        public static final double kI = 0.1;
        public static final double kD = 0.1;
        public static final double soft_stop_degrees = 50; //degrees
        public static final double soft_stop_rotations = soft_stop_degrees/360; //rotations
        public static final int rotation_k = 1;
        // Figure this out
        public static final double drop_off_angle = 45;
        public static final double resting_angle = 0;


    }
    public static final class HitAndRunAutonc {
        //from the center of the robot
        //FIND THESE OUT
        public static final double min_d = 54;//inches
        public static final double robot_length = 18;//inches
        public static final double max_d = 96-robot_length; //inches
        public static final double drop_off_wait_time = 2.0; //seconds
        public static final double kP = Dumperc.kP;
        public static final double kI = Dumperc.kI;
        public static final double kD = Dumperc.kD;
    }
    public static final class OI {
        public static final int port = 0;
        public static final Axis dumperTrigger = Axis.kRightTrigger;
        public static final Axis alignTrigger = Axis.kLeftTrigger;
        public static final double MIN_AXIS_TRIGGER_VALUE = 0.2;
    }
    public static final class AutoAlignToShelfc {
        public static final double rotationalSpeed = 0.5;
    }
}
