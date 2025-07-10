package OpModes.TeleOp;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import FTClib.Commands.powerDown;
import FTClib.Commands.powerUp;
import FTClib.Commands.setPower;
import FTClib.Commands.shoot;
import FTClib.Subsystem.FlyWheel;
import FTClib.Subsystem.Shooter;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
@TeleOp(name = "TeleopLive")
public class TeleOpLive extends OpMode {
    private Follower follower;
    private static int pathState = -1;
    private static Pose homePose = new Pose(8.8,9,Math.toRadians(0));
    private FlyWheel flyWheel;
    private Shooter shooter;
    public static PathBuilder builder = new PathBuilder();
    private ArrayList<Pose> poses = new ArrayList<>();
    private ArrayList<Path> lines = new ArrayList<>();
    private ArrayList<Integer> sP = new ArrayList<>();
    private boolean canDrive = false,canShoot = false,autoDrive = false;
    private GamepadEx driver;
    private boolean auto;

    //init
    @Override
    public void init() {
        flyWheel = new FlyWheel(hardwareMap,"flyWheel");
        shooter = new Shooter(hardwareMap, "shooter");
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        driver = new GamepadEx(gamepad1);

    }
    @Override
    public void start(){
        follower.startTeleopDrive();
    }
    //main loop
    @Override
    public void loop() {

        if(!canDrive) {
            follower.setTeleOpMovementVectors(-driver.getLeftY(), -driver.getRightX(),
                    -driver.getLeftX(), true);
            follower.setMaxPower(0.7);
        } else {
            follower.setTeleOpMovementVectors(gamepad2.left_stick_y, -gamepad2.right_stick_x,
                    -gamepad2.left_stick_x, true);
            follower.setMaxPower(0.7);
        }

        if(!canShoot) {
            if(driver.gamepad.x){
                shooter.shoot();
            }
        } else {
            if(gamepad2.x){
                shooter.shoot();
            }
        }


        if(true){
            if(driver.gamepad.dpad_up){
                flyWheel.changePower(0.1);
                halt(0.5);
            } else if(driver.gamepad.dpad_down){
                flyWheel.changePower(-0.1);
                halt(0.5);
            }
        }
        if(driver.gamepad.dpad_left){
            flyWheel.setPower(0.5);
        }

        if(driver.gamepad.y){
            auto = true;
        } else {
            auto = false;
        }
        if(auto){
            shooter.shoot();
            halt(1);
        }



        if(driver.gamepad.options){
            canShoot = true;
            canDrive = true;
        }
        if(driver.gamepad.ps){
            canShoot = false;
            canDrive = false;
        }

        if(driver.gamepad.a){
            addPoint();
            halt(0.5);
        } else if(driver.gamepad.b){
            addShootPoint();
            halt(0.5);
        }

        showTelemetry();
        follower.update();
        CommandScheduler.getInstance().run();

    }
    //line builder
    private void buildLines(){
        lines.add(new Path(
                new BezierLine(
                        new Point(follower.getPose()),
                        new Point(poses.get(0)))
        ));
        for(int i = 1; i < poses.size(); i++){
            lines.add(new Path(
                    new BezierLine(
                            new Point(poses.get(i - 1)),
                            new Point(poses.get(i)))
            ));
        }
    }
    //add points
    private void addPoint(){
        poses.add(follower.getPose());
    }
    private void addShootPoint(){
        addPoint();
        sP.add(poses.size()-1);
    }
    //follow path and timer
    private double time;
    private void followPath(){
        buildLines();
        resetRuntime();
        for(int i = 0; i < lines.size(); i++){
            if(!follower.isBusy()) {
                follower.followPath(lines.get(i));
                if(i == sP.get(0)){
                    new shoot(shooter).schedule();
                    halt(1);
                    sP.remove(0);
                }
               time = getRuntime();
            }
        }

        autoDrive = false;
    }
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
    private void showTelemetry(){
        telemetry.addLine("[RBT PromoBot] 2 components");
        if(autoDrive || time + 10 > getRuntime()){
            telemetry.addData("Time:", time);
        }
        telemetry.addData("Points: ", poses.size());
        telemetry.addData("Shoot Points",sP.size());
        telemetry.addData("Can Drive:", canDrive);
        telemetry.addData("Can Shoot: ", canShoot);
        telemetry.addData("Points", poses.size());
        telemetry.addData("Power:", flyWheel.getPower());
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));
        telemetry.update();


    }

}
