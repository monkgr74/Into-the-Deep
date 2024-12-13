package org.firstinspires.ftc.teamcode.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class strafeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrain drivetrain = new DriveTrain(this);

        waitForStart();

        if(isStopRequested()) {
            return;
        }

        while(opModeIsActive()) {
            double x = gamepad1.left_stick_x;

            double frontLeftPower = (-x);
            double backLeftPower = (x);
            double frontRightPower = (x);
            double backRightPower = (-x);

            drivetrain.frontLeft.setPower(frontLeftPower);
            drivetrain.frontRight.setPower(frontRightPower);
            drivetrain.backLeft.setPower(backLeftPower);
            drivetrain.backRight.setPower(backRightPower);
            telemetry.addData("Front Left Power", frontLeftPower);
            telemetry.addData("Front right power", frontRightPower);
            telemetry.addData("back Left Power", backLeftPower);
            telemetry.addData("back right Power", backRightPower);
            telemetry.update();

        }
    }

}
