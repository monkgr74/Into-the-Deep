package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


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
            if(gamepad1.dpad_right) {
                mech.extendSlide("up");
            }
            if (gamepad1.dpad_left) {
                mech.extendSlide("down");
            }
            /*
            if(gamepad1.right_bumper) {
                mech.openClaw();
            } else if(gamepad1.left_bumper) {
                mech.closeClaw();;
            }
            telemetry.update();

             */

            sleep(100);


        }
    }
}
