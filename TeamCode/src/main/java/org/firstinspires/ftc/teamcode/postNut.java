package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


@TeleOp(name = "postNut")
public class postNut extends LinearOpMode {

    private static final double TICKS_PER_REVOLUTION = 1993.6;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrain drivetrain = new DriveTrain(this);
        Mechanisms mech = new Mechanisms(this);

        waitForStart();

        drivetrain.initDriveTrain((hardwareMap));
        mech.initViperSlide(hardwareMap);
        //mech.initClaw(hardwareMap);




        if (isStopRequested()) {
            return;
        }

        //This is loop that checks the gamepad fr inputs every iteration
        while (opModeIsActive()) {
            time = runtime.startTime();
            telemetry.addData("RunTime", time);
            telemetry.update();
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;
            //double speedScale = 0.8;
            // double linearSlideUp = gamepad1.right_trigger;
            // double linearSLideDown = gamepad1.left_trigger;

            //Denominator is the largest motor power (abs value) or 1
            //This ensures all the powers maintain the same ratio,
            //but only if at least one is out of the range [-1,1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx)/denominator;
            double backLeftPower = (y - x + rx)/denominator;
            double frontRightPower = (y - x - rx)/denominator;
            double backRightPower = (y + x - rx)/denominator;

            drivetrain.frontLeft.setPower(frontLeftPower);
            drivetrain.frontRight.setPower(frontRightPower);
            drivetrain.backLeft.setPower(backLeftPower);
            drivetrain.backRight.setPower(backRightPower);

            telemetry.addData("Front left Encoder", drivetrain.frontLeft.getCurrentPosition());
            telemetry.addData("Back Left Encoder", drivetrain.backLeft.getCurrentPosition());
            telemetry.addData("Front Right Encoder", drivetrain.frontRight.getCurrentPosition());
            telemetry.addData("Back Right Encoder", drivetrain.backRight.getCurrentPosition());
            telemetry.update();

            telemetry.addData("Viper current", mech.viperSlide.getCurrentPosition());
            if(gamepad2.dpad_up) {
                mech.extendSlide("up");
            }
            if (gamepad2.dpad_down) {
                mech.extendSlide("down");
            }

            if(gamepad2.right_bumper) {
                mech.openClaw();
            } else if(gamepad1.left_bumper) {
                mech.closeClaw();;
            }

            if(gamepad2.dpad_right) {
                mech.adjustClawMesh("extend");
            }
            if (gamepad2.dpad_left) {
                mech.adjustClawMesh("retract");
            }

            // movement presets
            if (gamepad1.a) {
                // pivot robot 180 degrees from starting angle (counterclockwise if pivoted left, clockwise if pivoted right)
                drivetrain.rotateToAngle(180,0.3);
            }

            if (gamepad1.b) {
                drivetrain.rotateToAngle(90,0.3);
            }

            if (gamepad1.x) {
                // pivot robot 90 degrees counter clockwise from starting angle
                drivetrain.rotateToAngle(270,0.3);
            }

            if (gamepad1.y) {
                // pivot robot to starting position (0 degrees from initialization)
                drivetrain.rotateToAngle(0,0.3);
            }

            if (gamepad1.left_bumper) {
                // shift to regular driving mode. normal teleop driving mode
                drivetrain.presetL2();
            }

            if (gamepad1.right_bumper) {
                /*
                Shift to inverse driving mode. Inverts driving directions.
                What would be forward is now backwards.
                Left would be right.
                Right would be left.
                Strafing is also inversed.

                Handy when the front of the robot is facing towards you when driving it,
                to avoid confusion since you must tilt the joystick to the right for the
                robot to go left. when using inverse, whichever way you tilt the joystick
                the robot will move that way without the need to counter steer.
                */
                drivetrain.presetR2();
            }


            telemetry.update();



            sleep(100);


        }
    }
}
