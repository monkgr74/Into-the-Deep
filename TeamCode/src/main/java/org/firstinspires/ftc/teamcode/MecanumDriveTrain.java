package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "TeleOpDriveTrain")
public class MecanumDriveTrain extends LinearOpMode {

    private static final double TICKS_PER_REVOLUTION = 1993.6;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor fl = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor bl = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor fR = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor bR = hardwareMap.dcMotor.get("backRightMotor");

        //Reverse the left side motors because some mecanum wheels are backwards
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        DcMotor motor = hardwareMap.get(DcMotor.class, "linearSlide");

        // Reset the encoder to start from 0
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motor to run using encoder (but no power for manual movement)
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(0); // Ensure no power is applied to the motor

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
            double linearSlideUp = gamepad1.right_trigger;
            double linearSLideDown = gamepad1.left_trigger;

            //Denominator is the largest motor power (abs value) or 1
            //This ensures all the powers maintain the same ratio,
            //but only if at least one is out of the range [-1,1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;
            double linearSlideMotorPower = linearSlideUp - linearSLideDown;


            int currentTicks = motor.getCurrentPosition();
            int currentPosition = motor.getCurrentPosition();
            // Calculate the number of revolutions
            double revolutions = currentTicks / TICKS_PER_REVOLUTION;

            //After the reaches max, the linearslide



            telemetry.addData("LF Power", frontLeftPower);
            telemetry.addData("RF Power", frontRightPower);
            telemetry.addData("LB Power", backLeftPower);
            telemetry.addData("RB Power", backRightPower);
            telemetry.addData("Raw Ticks: ", currentTicks);
            telemetry.addData("Revolutions: ", String.format("%.2f", revolutions));
            telemetry.addData("CurrentPosition", currentPosition);
            telemetry.update();

            // Optional sleep to slow down telemetry updates
            sleep(100);

            fl.setPower(frontLeftPower);
            bl.setPower(backLeftPower);
            fR.setPower(frontRightPower);
            bR.setPower(backRightPower);
        }
        motor.setPower(0);
    }
}
