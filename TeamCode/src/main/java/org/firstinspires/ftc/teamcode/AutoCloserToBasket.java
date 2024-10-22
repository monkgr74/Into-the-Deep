package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "preNut_BasketCloser")
public class AutoCloserToBasket extends LinearOpMode {
    @Override
    public void runOpMode() {
        DriveTrain drivetrain = new DriveTrain(this);
        drivetrain.initDriveTrain(hardwareMap);
        Mechanisms mech = new Mechanisms(this);

        waitForStart();


        drivetrain.moveForward(0.5,1000);
        drivetrain.strafeLeft(0.5, 1000);

    }
}
