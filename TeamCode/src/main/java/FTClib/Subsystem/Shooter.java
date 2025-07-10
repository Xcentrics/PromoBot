package FTClib.Subsystem;


import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;

import java.util.concurrent.TimeUnit;

public class Shooter extends SubsystemBase {
    private Servo servo;
    String name;

    public Shooter(HardwareMap hardwareMap, String name){
        this.name = name;
        servo = hardwareMap.get(Servo.class,"servo");
    }
    public void shoot(){
        servo.setPosition(0);
        halt(0.5);
        servo.setPosition(0.5);
    }
    public volatile double time = 0.0;
    // time is volatile because LinearOpMode updates this on the main event loop thread instead of
    // the OpMode thread

    // internal time tracking
    private volatile long startTime = 0; // in nanoseconds
    protected void halt(double seconds) {
        resetRuntime();
        while (getRuntime() < seconds) {}
    }
    public double getRuntime() {
        final double NANOSECONDS_PER_SECOND = TimeUnit.SECONDS.toNanos(1);
        return (System.nanoTime() - startTime) / NANOSECONDS_PER_SECOND;
    }
    public void resetRuntime() {
        startTime = System.nanoTime();
    }
}
