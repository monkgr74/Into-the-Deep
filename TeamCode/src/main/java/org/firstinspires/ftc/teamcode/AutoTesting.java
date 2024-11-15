package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "preNut_BasketCloser")
public class AutoTesting extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        DriveTrain drivetrain = new DriveTrain(this);
        drivetrain.initDriveTrain(hardwareMap);
        Mechanisms mech = new Mechanisms(this);
        mech.initViperSlide(hardwareMap);
        mech.initClaw(hardwareMap);


        waitForStart();

        if(isStopRequested()) {
            return;
        }

        runtime.startTime();
        while(runtime.milliseconds() <= 30000) {
            drivetrain.moveForward(0.5,1000);
            drivetrain.rotateRight(0.5, 2000);
            drivetrain.moveForward(0.5,1000);
            mech.BasketScorePosition();
            drivetrain.moveForward(0.5,1000);
            mech.toggleServoDirection("backward");
            drivetrain.rotateRight(0.5,2000);
            mech.zeroPosition();
            drivetrain.moveForward(0.5,1000);
            drivetrain.rotateRight(0.5,2000);
            mech.toggleServoDirection("forward");
            drivetrain.rotateRight(0.5,1000);
            drivetrain.moveForward(0.5,1000);


        }




    }
}
