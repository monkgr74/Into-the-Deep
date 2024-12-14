package org.firstinspires.ftc.teamcode.teamcode;

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
        mech.BlockPickupPosition();


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

            telemetry.addData("Front left Encoder", drivetrain.frontLeft.getCurrentPosition());
            telemetry.addData("Back Left Encoder", drivetrain.backLeft.getCurrentPosition());
            telemetry.addData("Front Right Encoder", drivetrain.frontRight.getCurrentPosition());
            telemetry.addData("Back Right Encoder", drivetrain.backRight.getCurrentPosition());
            telemetry.update();

            telemetry.addData("Viper current", mech.viperSlide.getCurrentPosition());
            /*
            if(gamepad2.dpad_up) {
                mech.extendViperSlide("up");
            }
            if (gamepad2.dpad_down) {
                mech.extendViperSlide("down");
            }
            */

            //viperslide speed adjust
            double viperSlide = gamepad2.right_stick_y;
            if(viperSlide > 0) {
                //mech.viperSlide.setDirection(DcMotorSimple.Direction.FORWARD);
                //mech.viperSlide.setPower(viperSlide/ 1.5);
                mech.extendViperSlide("up", viperSlide);
            }
            else if(viperSlide<0) {
                //mech.viperSlide.setDirection(DcMotorSimple.Direction.REVERSE);
                //mech.viperSlide.setPower(viperSlide/ 1.5);
                mech.extendViperSlide("down", viperSlide);
            }
            else {
                mech.viperSlide.setDirection(DcMotorSimple.Direction.FORWARD);
                mech.viperSlide.setPower(0);

            }

            double viperPivot = gamepad2.left_stick_y;
            telemetry.addData("armMotorPosition", mech.viperPivot.getCurrentPosition());
            if(viperPivot > 0) {
                mech.viperPivot.setDirection(DcMotorSimple.Direction.REVERSE);
                mech.viperPivot.setPower(viperPivot/ 0.5);
            }
            else if(viperPivot<0) {
                mech.viperPivot.setDirection(DcMotorSimple.Direction.FORWARD);
                mech.viperPivot.setPower(viperPivot/ 0.5);
            }
            else {
                mech.viperPivot.setDirection(DcMotorSimple.Direction.REVERSE);
                mech.viperPivot.setPower(0);

            }
            telemetry.update();

            if(gamepad2.a) {
                mech.setClawOpen();
            }

            if(gamepad2.b) {
                mech.setClawClose();
            }

            if (gamepad2.x) {
                mech.BasketScorePosition();
            }

            if (gamepad2.y) {
                mech.BlockPickupPosition();
            }

            double intakeServoIn = gamepad2.right_trigger;
            double intakeServoOut = gamepad2.left_trigger;
            telemetry.addData("PivotPosition",mech.pivot.getPosition());
            if(intakeServoIn > 0.1){
                mech.setPivot("forward",intakeServoIn);
            }
            if(intakeServoOut > 0.1) {
                mech.setPivot("forward", intakeServoOut);
            }

            //intakeServos
            /*
            if(intakeServoIn > 0.1){
                //mech.intakeServo.setDirection(DcMotorSimple.Direction.FORWARD);
                //mech.intakeServo.setPower(intakeServoIn + 0.5);

                mech.adjustServo("forward", intakeServoIn);
            }
            else if(intakeServoOut > 0.1) {
                //mech.intakeServo.setDirection(CRServo.Direction.REVERSE);
                //mech.intakeServo.setPower(intakeServoOut + 0.5);

                mech.adjustServo("reverse", intakeServoOut);
            }
            else {
                mech.intakeServo.setPower(0);
                mech.intakeServo.setDirection(DcMotorSimple.Direction.FORWARD);
            }

             */

            /*
            if(gamepad2.a) {
                mech.toggleServoDirection2("forward");
            }
            if (gamepad2.b) {
                mech.toggleServoDirection2("backward");
            }
            if(gamepad2.x) {
                mech.toggleServoDirection2("stop");
            }

            if(gamepad2.dpad_right){
                mech.extendMessumiSlides("up");
            } else if(gamepad2.dpad_left) {
                mech.extendMessumiSlides("down");
            }
 */

            /*
            if(gamepad2.dpad_right) {
                mech.moveArmMotor("up");
            }
            if(gamepad2.dpad_left) {
                mech.moveArmMotor("down");
            }
             */

            telemetry.update();

            sleep(100);
        }
    }
}
