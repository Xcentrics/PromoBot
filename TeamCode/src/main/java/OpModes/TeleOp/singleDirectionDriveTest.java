package OpModes.TeleOp;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Localizer;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
@TeleOp(name = "test")
public class singleDirectionDriveTest  extends OpMode {
    private Follower follower;
    private Pose startPose = new Pose(8.8,9,Math.toRadians(0));
    private double target;



    @Override
    public void init() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
    }

    @Override
    public void loop() {
                if(gamepad1.dpad_left){
                    target -= 0.1;
                } else if(gamepad1.dpad_right){
                    target += 0.1;
                }
                Pose targetPose = new Pose(8.8,target,Math.toRadians(0));
               PathChain scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(follower.getPose()), new Point(targetPose)))
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(0))
                .build();
               follower.followPath(scorePickup2);
               follower.update();
               telemetry.addData("current pose:",follower.getPose());
               telemetry.addData("target pose",targetPose);
               telemetry.addData("target:",target);
               telemetry.update();

    }
}
