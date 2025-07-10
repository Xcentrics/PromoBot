package FTClib.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import FTClib.Subsystem.Shooter;

public class shoot extends CommandBase {
    private final Shooter shooter;

    public shoot(Shooter shooter) {
        this.shooter = shooter;
        addRequirements(shooter);
    }
    @Override
    public void initialize(){
        shooter.shoot();
    }
    @Override
    public boolean isFinished(){
        return true;
    }
}
