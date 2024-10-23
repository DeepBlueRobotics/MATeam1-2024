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
        //public static final double wheel_diameter = 
        //public static final double wheel_radius = 
        //d_wheels stands for distance between the wheels(dominant)
        //public static final double d_wheels = 
        //public static final double rotation_lenght = 3.141529*wheel_diameter;
    }
    public static final class Dumperc {
        public static final int dumper_id = 2;
        //reqires tuning and testing
        public static final double kP = 0.1;
        public static final double kI = 0.1;
        public static final double kD = 0.1;
        public static final double soft_stop = 50; //degrees
        // Figure this out
        //public static final double drop_off_angle = 
        public static final double resting_angle = 0;


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
