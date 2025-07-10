package FTClib.Subsystem;

import static java.lang.Thread.sleep;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FlyWheel extends SubsystemBase {
    private String name;
    private DcMotor fly1,fly2;
    private double power = 0;
    public FlyWheel(HardwareMap hardwareMap, String name){
        fly1 = hardwareMap.get(DcMotor.class,"fly1");
        fly2 = hardwareMap.get(DcMotor.class,"fly2");
    }
    public void changePower(double power){
        this.power += power;
    }
    public void setPower(double power){
        this.power = power;
    }

    public double getPower(){
        return power;
    }

    @Override
    public void periodic(){

         if(power <= 0){
            power = 0;
        } else if(power >= 1){
             power = 1;
         }
        fly1.setPower(power);
        fly2.setPower(power);
    }

}
