package org.carlmontrobotics;

public final class Constants {
    //PID for the Drivetrain (DT)
    //Not sure whether we need PID, but it would really help w/ precision
    //Please tune the kP value so it doesn't oscillate too much bc momentu,
    final dtKP = 0.75;
    // public static final class Drivetrain {
    //     public static final double MAX_SPEED_MPS = 2;
    // }
    public static final class OI {
        public static final class Driver {
            public static final int port = 0;
        }
        public static final class Manipulator {
            public static final int port = 1;
        }
    }
}
