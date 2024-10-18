package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "TeleOpDriveTrain")
public class MecanumDriveTrain extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor fl = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor bl = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor fR = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor bR = hardwareMap.dcMotor.get("backRightMotor");

        //Reverse the left side motors because some mecanum wheels are backwards
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        //The code after this is only run after start is pressed
        waitForStart();

        if (isStopRequested()) {
            return;
        }

        //This is loop that checks the gamepad fr inputs every iteration
        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;

            //Denominator is the largest motor power (abs value) or 1
            //This ensures all the powers maintain the same ratio,
            //but only if at least one is out of the range [-1,1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x + rx) / denominator;
            double backRightPower = (y + x + rx) / denominator;

            fl.setPower(frontLeftPower);
            bl.setPower(backLeftPower);
            fR.setPower(frontRightPower);
            bR.setPower(backRightPower);
        }
    }
}
