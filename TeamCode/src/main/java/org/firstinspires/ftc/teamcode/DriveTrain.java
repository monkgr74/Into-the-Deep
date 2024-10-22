package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DriveTrain {

    public DcMotor frontLeft;
    public DcMotor backLeft;
    public DcMotor frontRight;
    public DcMotor backRight;

    private ElapsedTime runtime = new ElapsedTime();

    LinearOpMode opMode;

    public DriveTrain(LinearOpMode op) {
        opMode = op;
    }

    public void initDriveTrain(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeftMotor");
        backLeft= hardwareMap.get(DcMotor.class, "backLeftMotor");
        frontRight = hardwareMap.get(DcMotor.class, "frontRightMotor");
        backRight = hardwareMap.get(DcMotor.class, "backRightMotor");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);


        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        frontLeft.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        frontRight.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        backLeft.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        backRight.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

    }

    public void stopMotors(){
        opMode.telemetry.addData("Status", "Stopped");
        opMode.telemetry.update();

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }


    public void moveForward(double power, long targetInMilis) {
        runtime.reset();

        opMode.telemetry.addData("Status", "Moving Forward");
        opMode.telemetry.update();

        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);

        opMode.sleep(targetInMilis);
    }

    public void moveBackwards(double power, long targetInMillis) {
        opMode.telemetry.addData("Status", "Moving Backward");
        opMode.telemetry.update();

        frontLeft.setPower(-power);
        frontRight.setPower(-power);
        backLeft.setPower(-power);
        backRight.setPower(-power);

        while(runtime.milliseconds() < targetInMillis) {
            //Keeps on looping until target is reached
        }
        stopMotors();
    }

    public void strafeLeft(double power, long targetTimeMillis) {
        runtime.reset();

        opMode.telemetry.addData("Status", "Moving Left");
        opMode.telemetry.update();

        frontLeft.setPower(-power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(-power);

        while(runtime.milliseconds() < targetTimeMillis) {
            //keeps on looping until target is reached
        }
        stopMotors();

    }

    public void strafeRight(double power, long targetTimeMillis){
        runtime.reset();

        opMode.telemetry.addData("Status", "Moving Right");
        opMode.telemetry.update();

        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(power);
        backRight.setPower(-power);

        while(runtime.milliseconds() < targetTimeMillis) {

        }
        stopMotors();
    }


}
