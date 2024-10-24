package org.carlmontrobotics;

public final class Constants {
    public static final class Drivetrainc {
        public static final int left_motor_id = 0;
        public static final int right_motor_id = 1;
        //Reqires tuning and testing
        public static final double kP = 0.1;
        public static final double kI = 0.1;
        public static final double kD = 0.1;
        
        //Find out the values
        //public static final double wheel_diameter = //inches
        //public static final double wheel_radius = //inches
        //d_wheels stands for distance between the wheels(dominant)
        //public static final double d_wheels = //inches
        //public static final double rotation_lenght = 3.141529*wheel_diameter;
    }
    public static final class Dumperc {
        public static final int dumper_id = 2;
        //reqires tuning and testing
        public static final double kP = 0.1;
        public static final double kI = 0.1;
        public static final double kD = 0.1;
        public static final double soft_stop_degrees = 50; //degrees
        public static final double soft_stop_rotations = soft_stop_degrees/360; //rotations
        // Figure this out
        public static final double drop_off_angle = 45;
        public static final double resting_angle = 0;


    }
    public static final class HitAndRunAutonc {
        //from the center of the robot
        //public static final double min_d_perimeter = //inches
        //public static final double max_d_perimeter = //inches
        //public static final double robot_length = //inches
        public static final double drop_off_wait_time = 2.0; //seconds
    }
    public static final class OI {
        public static final class Driver {
            public static final int port = 0;
        }
        public static final class Manipulator {
            public static final int port = 1;
        }
    }
}
