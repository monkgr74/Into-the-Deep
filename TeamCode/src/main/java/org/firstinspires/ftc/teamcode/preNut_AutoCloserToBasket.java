package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "preNut_BasketCloser")
public class preNut_AutoCloserToBasket extends LinearOpMode {
    @Override
    public void runOpMode() {
        DriveTrain drivetrain = new DriveTrain(this);
        drivetrain.initDriveTrain(hardwareMap);
        Mechanisms mech = new Mechanisms(this);

        waitForStart();

        if(isStopRequested()) {
            return;
        }

        drivetrain.moveForward(0.5,1000);
        drivetrain.rotate(0.5, 2000);
        drivetrain.moveForward(0.5,1000);
       // mech.BasketScorePosition();

    }
}
