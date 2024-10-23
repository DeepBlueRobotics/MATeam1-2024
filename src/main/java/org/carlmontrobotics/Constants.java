package org.carlmontrobotics;

public final class Constants {
    //PID for the Drivetrain (DT)
    //Not sure whether we need PID, but it would really help w/ precision
    //Please tune the kP value so it doesn't oscillate too much bc momentum
    final dtKP = 0.75;
    // public static final class Drivetrain {
    //     public static final double MAX_SPEED_MPS = 2;
    // }{
    public static final class Drivetrainc {
        public static final int left_motor_id = 0;
        public static final int right_motor_id = 1;
        //Find out the values
        //public static final double wheel_diameter = 
        //public static final double wheel_radius = 
        //d_wheels stands for distance between the wheels(dominant)
        //public static final double d_wheels = 
    }
    public static final class Dumperc {
        public static final int dumper_id = 2;
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
