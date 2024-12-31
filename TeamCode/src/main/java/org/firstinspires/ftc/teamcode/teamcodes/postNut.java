package org.firstinspires.ftc.teamcode.teamcodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "postNut")
public class postNut extends LinearOpMode {

    private static final double TICKS_PER_REVOLUTION = 1538.0;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrain drivetrain = new DriveTrain(this);
        Mechanisms mech = new Mechanisms(this);

        waitForStart();

        drivetrain.initDriveTrain((hardwareMap));
        mech.initViperSlide(hardwareMap);
        mech.initClaw(hardwareMap);
        //mech.initMessumiSlides(hardwareMap);
        mech.initArmMotor(hardwareMap);
        //mech.BlockPickupPosition();


        if (isStopRequested()) {
            return;
        }

        //This is loop that checks the gamepad fr inputs every iteration
        while (opModeIsActive()) {
            //mech.armPreset();
            mech.updateZeroPosition();

            time = runtime.startTime();
            telemetry.addData("RunTime", time);
            telemetry.update();
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x;
            //double speedScale = 0.8;
           // double linearSlideUp = gamepad1.right_trigger;
           // double linearSLideDown = gamepad1.left_trigger;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
            double frontLeftPower = (y + x + rx)/2.0;
            double backLeftPower = (y - x + rx)/2.0;
            double frontRightPower = (y - x - rx)/2.0;
            double backRightPower = (y + x - rx)/2.0;

            drivetrain.frontLeft.setPower(frontLeftPower);
            drivetrain.frontRight.setPower(frontRightPower);
            drivetrain.backLeft.setPower(backLeftPower);
            drivetrain.backRight.setPower(backRightPower);


            //controls
            mech.openClaw(gamepad2.left_trigger);
            mech.closeClaw(gamepad2.right_trigger);

            if(gamepad2.dpad_left){
                mech.extendViperSlide("backward");
            } else if(gamepad2.dpad_right){
                mech.extendViperSlide("forward");
            }

            telemetry.addData("viperPivot", mech.viperPivot.getCurrentPosition());
            if(gamepad2.dpad_down){
                mech.armMotorPivot("down");
            }
            if(gamepad2.dpad_up){
                mech.armMotorPivot("up");
            }



//            if (gamepad2.x) {
//                mech.BasketScorePosition();
//            }
//
//            if (gamepad2.y) {
//                mech.SpecimenPickupPosition();
//            }
//
//            if(gamepad2.b){
//                mech.BlockPickupPosition();
//            }


            telemetry.addData("ClawPivotPosition",mech.pivot.getPosition());
            if(gamepad2.right_bumper){
                mech.setPivot("up");

            }
            if(gamepad2.left_bumper) {
                mech.setPivot("down");

            }

            telemetry.update();

            sleep(100);
        }
    }
}
