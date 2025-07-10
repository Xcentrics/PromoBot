package FTClib.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import java.util.concurrent.TimeUnit;

import FTClib.Subsystem.FlyWheel;


public class powerUp extends CommandBase {
    private FlyWheel flyWheel;
    public powerUp(FlyWheel flyWheel){
        this.flyWheel = flyWheel;
        addRequirements(flyWheel);
    }
    public void initialize(){
        flyWheel.changePower(-0.1);
    }
    public boolean isFinished(){
        halt(0.5);
        return true;
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
