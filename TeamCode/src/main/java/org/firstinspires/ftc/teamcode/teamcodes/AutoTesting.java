package org.firstinspires.ftc.teamcode.teamcodes;

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
            /*
            drivetrain.moveForward(0.5,1000);
            drivetrain.rotateRight(0.5, 2000);
            drivetrain.moveForward(0.5,1000);
            mech.BasketScorePosition();
           // mech.moveArmMotor(Value);
            drivetrain.moveForward(0.5,1000);
            mech.toggleServoDirection("backward");
            drivetrain.moveBackwards(0.5, 1000);
            mech.zeroPosition();
            drivetrain.rotateRight(0.5,2000);
            drivetrain.moveForward(0.5,1000);
            drivetrain.rotateRight(0.5,2000);
           // mech.extendViperSlide(Value);
            mech.toggleServoDirection("forward");
            drivetrain.rotateRight(0.5,1000);
            drivetrain.moveForward(0.5,1000);

             */

            //closer to basket
/*
            drivetrain.strafeRight(0.5,1000); //Change the stuff
            drivetrain.rotateRight(0.5,1000);
            drivetrain.moveForward(0.5,1000);
            mech.BasketScorePosition();
            mech.inTakeServoAuto("Forward", 1);

 */

            //pushbot

            //drivetrain.strafeRight(0.5,1000);

            //Field Measurement Testing
            //FieldMeasuring fieldMeasuring




        }




    }
}
