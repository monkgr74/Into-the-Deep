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

         // Ensure no power is applied to the motor

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {
            ;
        }
    }
}
