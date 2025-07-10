package FTClib.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import FTClib.Subsystem.FlyWheel;

public class setPower extends CommandBase {
    private final FlyWheel flyWheel;
    double power;

    public setPower(FlyWheel flyWheel,double power){
        this.flyWheel = flyWheel;
        this.power = power;
        addRequirements(flyWheel);
    }

    @Override
    public void initialize(){
        flyWheel.setPower(power);
    }
    @Override
    public boolean isFinished(){
        return true;
    }
}
