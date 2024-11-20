package org.carlmontrobotics;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController.Axis;

public final class Constants {
    public static final class Drivetrainc {
        private final static double pi = Math.PI;
        public static final int left_motor_id = 18;
        public static final int right_motor_id = 20;
        //Reqires tuning and testing
        public static final double kP = 0.1;
        public static final double kI = 0.1;
        public static final double kD = 0.1;
        
        public static final double motor1_rotation_k_slow = -0.3;
        public static final double motor2_rotation_k_slow = 0.3;
        public static final double motor1_rotation_k_turbo = -0.5;
        public static final double motor2_rotation_k_turbo = 0.5;
        //Find out the values
        public static final double wheel_diameter = 4;//inches
        public static final double wheel_radius = 0;//inches
        //d_wheels stands for distance between the wheels(dominant)
        public static final double d_wheels = 0;//inches
        //multiplier to turn distance in inches into rotations
        public static final double kDis_Rot = 1/(wheel_diameter*pi);
        //For accelerating drive
        public static final boolean accelerating_drive = false;
        public static final double acceleratingK = 0.1;
    }
    public static final class Dumperc {
        public static final int dumper_id = 19;
        //reqires tuning and testing
        // public static final double kP = 0.1;
        // public static final double kI = 0.1;
        // public static final double kD = 0.1;
        public static final double angle_off_horizontal = 16; // degres
        public static final double soft_stop_degrees = 85; //degrees
        public static final double soft_stop_rotations = soft_stop_degrees/360; //rotations
        public static final double rotation_k = 0.1;
        // Figure this out
        public static final double drop_off_angle = 80;


    }
    public static final class HitAndRunAutonc {
        //from the center of the robot
        //FIND THESE OUT
        public static final double min_d = 54;//inches
        public static final double robot_length = 18;//inches
        public static final double max_d = 96-robot_length; //inches
        public static final double averageDistance = (max_d+min_d)/2;
        public static final double drop_off_wait_time = 2.0; //seconds
        //no pid situation
        public static final double optimalSpeed1 = -0.3; //input speed for motor1
        public static final double optimalSpeed2 = 0.3; //input speed for motor2
    }
    public static final class SimpleAutoc {
        public static final double robot_length = 18;
        public static final double min_d = 0;
        public static final double max_d = 0;
        public static final double averageDistance = (max_d+min_d)/2;
        public static final double optimalSpeed1 = -0.3; //input speed for motor1
        public static final double optimalSpeed2 = 0.3; //input speed for motor2
    }
    public static final class CrazyAutoc {
        public static final double robot_length = 18;
        public static final double min_d1 = 0;
        public static final double min_d2 = 0;
        public static final double max_d2 = 0;
        
        public static final double crashOptimalSpeed1 = -0.6; //input speed for motor1
        public static final double crashOptimalSpeed2 = 0.6; //input speed for motor2
        public static final double optimalSpeed1 = -0.3; //input speed for motor1
        public static final double optimalSpeed2 = 0.3; //input speed for motor2
    }
    public static final class OI {
        public static final int port = 0;
        public static final Axis dumperTrigger = Axis.kRightTrigger;
        public static final Axis alignTrigger = Axis.kLeftTrigger;
        public static final double MIN_AXIS_TRIGGER_VALUE = 0.2;
        public static final int A = 1;
	    public static final int B = 2;
	    public static final int X = 3;
	    public static final int Y = 4;
        public static final int leftBumper = 5;
        public static final int rightBumper = 6;
    }
    public static final class AutoAlignToShelfc {
        public static final double rotationalSpeed = 0.5;
        public static final double goodAngle = 5; //degees
    }
    public static final class TeleopC {
        // 0->ARCADE 1->REVERSEDARCADE 2-> TANK
        public static final int driveType = 2;
    }
}

