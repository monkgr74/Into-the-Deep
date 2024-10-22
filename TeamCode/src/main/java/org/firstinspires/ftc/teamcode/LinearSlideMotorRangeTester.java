package org.firstinspires.ftc.teamcode;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Motor Revolution and Tick Tracker", group="Tests")
public class LinearSlideMotorRangeTester extends LinearOpMode {

    private static final double TICKS_PER_REVOLUTION = 1993.6; // Encoder ticks per revolution for GoBILDA 5203

    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {
        // Initialize the motor
        DcMotor motor = hardwareMap.get(DcMotor.class, "linearSlide");

        // Reset the encoder to start from 0
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set motor to run using encoder (but no power for manual movement)
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPower(0); // Ensure no power is applied to the motor

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            // Get the current encoder position (raw tick data)
            int currentTicks = motor.getCurrentPosition();

            // Calculate the number of revolutions
            double revolutions = currentTicks / TICKS_PER_REVOLUTION;

            //double linearSlide = gamepad1.right_trigger;

            // Display both the raw tick data and the revolutions on the telemetry
            telemetry.addData("Raw Ticks: ", currentTicks);
            telemetry.addData("Revolutions: ", String.format("%.2f", revolutions));
            telemetry.update();

            // Optional sleep to slow down telemetry updates
            sleep(100);
        }
    }
}
