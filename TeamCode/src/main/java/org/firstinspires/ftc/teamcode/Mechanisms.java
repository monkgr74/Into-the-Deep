package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Mechanisms {
    public DcMotor viperSlide;
    public DcMotor linearSlide;
    public Servo clawLeft;
    public Servo clawRight;
    public Servo clawMesh;
    // public Servo claw;

   // public double open = 0.83;
   // public double close = 0.5;

    // Claw positions
    private final double CLAW_LEFT_OPEN = 0.8;
    private final double CLAW_LEFT_CLOSED = 0.5;
    private final double CLAW_RIGHT_OPEN = 0.2;
    private final double CLAW_RIGHT_CLOSED = 0.5;
    private final double CLAW_MESH_EXTENDED = 1.0;
    private final double CLAW_MESH_RETRACTED = 0.0;

    int maxPosition = 4395;
    double slideSpeed = 0.6;

    LinearOpMode opMode;

    public Mechanisms (LinearOpMode op){
        opMode = op;
    }

    public void initViperSlide(HardwareMap hardwareMap){
        viperSlide = hardwareMap.get(DcMotor.class, "viperSlide");
        viperSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        viperSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        viperSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperSlide.setPower(0);
    }

    public void extendSlide(String direction) {
        int pos1 = viperSlide.getCurrentPosition();

        if(direction.equals("up") && pos1 >= 4300) {
            viperSlide.setPower(0);
        }
        if (direction.equals("down") && pos1 == 0) {
            viperSlide.setPower(0);
        }

        if(direction.equals("up") && pos1 <= maxPosition){
            pos1 += 100;
        }
        else if(direction.equals("down") && pos1 <= maxPosition) {
            pos1 -= 100;
        }

        viperSlide.setTargetPosition(pos1);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlide.setPower(slideSpeed);
    }

    // Initialize claw servos
    public void initClaw(HardwareMap hardwareMap) {
        clawLeft = hardwareMap.get(Servo.class, "clawLeft");
        clawRight = hardwareMap.get(Servo.class, "clawRight");
        clawMesh = hardwareMap.get(Servo.class, "clawMesh");

        // Set the initial position of the servos
        setClawPosition(CLAW_LEFT_CLOSED, CLAW_RIGHT_CLOSED);  // Start closed
        setClawMeshPosition(CLAW_MESH_RETRACTED);  // Start retracted
    }

    // Set claw open/close position based on input values
    public void setClawPosition(double leftPosition, double rightPosition) {
        clawLeft.setPosition(Math.max(CLAW_LEFT_CLOSED, Math.min(leftPosition, CLAW_LEFT_OPEN)));
        clawRight.setPosition(Math.max(CLAW_RIGHT_CLOSED, Math.min(rightPosition, CLAW_RIGHT_OPEN)));
    }

    // Set claw extend/retract position based on input value
    public void setClawMeshPosition(double meshPosition) {
        clawMesh.setPosition(Math.max(CLAW_MESH_RETRACTED, Math.min(meshPosition, CLAW_MESH_EXTENDED)));
    }

    // Helper methods for incrementing/decrementing positions for finer control
    public void incrementClawPosition(double increment) {
        double newLeftPosition = clawLeft.getPosition() + increment;
        double newRightPosition = clawRight.getPosition() + increment;
        setClawPosition(newLeftPosition, newRightPosition);
    }

    public void incrementClawMeshPosition(double increment) {
        double newMeshPosition = clawMesh.getPosition() + increment;
        setClawMeshPosition(newMeshPosition);
    }
/*
    public void BasketScorePosition() {
        viperSlide .setTargetPosition();
        viperSlide.setPower(0.5);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }


    public void initClaw(HardwareMap hardwareMap){
        claw = hardwareMap.get(Servo.class, "Claw");
    }


    public void openClaw() {
        claw.setPosition(open);
    }
    public void closeClaw() {
        claw.setPosition(close);
    }

     */

}
