package org.firstinspires.ftc.teamcode.teamcodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "preNut_BasketCloser")
public class preNut extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() {
        DriveTrain drivetrain = new DriveTrain(this);
        drivetrain.initDriveTrain(hardwareMap);
        //Mechanisms mech = new Mechanisms(this);
        //mech.initViperSlide(hardwareMap);

        waitForStart();

        if(isStopRequested()) {
            return;
        }

        runtime.startTime();
        /*
        while(runtime.milliseconds()<=30000){
            drivetrain.rotateToAngle(270,0.3);
            drivetrain.moveForward(0.4,1000);
            //mech.basketScorePosition();
            mech.zeroPosition();
            drivetrain.rotateToAngle(90,0.3);
            drivetrain.moveForward(0.4,1000);
        }

         */

        drivetrain.strafeLeft(0.5,2260);
        drivetrain.moveForward(0.5,800);
        drivetrain.strafeRight(0.5,2260);
        drivetrain.strafeLeft(0.5,2260);
        drivetrain.moveForward(0.5,2260);
        drivetrain.strafeRight(0.5,2260);
        drivetrain.strafeLeft(0.5,2260);
        drivetrain.moveForward(0.5,800);
        drivetrain.strafeRight(0.5,2260);
        drivetrain.strafeLeft(0.5,2260);

    }
}
