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

    int maxPosition = 4291;
    // Claw positions (ALL OF THESE MUST BE ADJUSTED!!!!1!!1!!)
    private final double CLAW_LEFT_OPEN = 0.8;
    private final double CLAW_LEFT_CLOSED = 0.5;
    private final double CLAW_RIGHT_OPEN = 0.2;
    private final double CLAW_RIGHT_CLOSED = 0.5;
    private final double CLAW_MESH_EXTENDED = 1.0;
    private final double CLAW_MESH_RETRACTED = 0.0;
    private final double CLAW_INCREMENT = 0.01;  // Adjust to control speed of adjustment
    private final double CLAW_MESH_INCREMENT = 0.05;  // Increment value for each step in mesh extension


    // Current positions for the claw and mesh
    private double currentLeftPosition;
    private double currentRightPosition;
    private double currentMeshPosition;

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

        // Start with the claw closed
        currentLeftPosition = CLAW_LEFT_CLOSED;
        currentRightPosition = CLAW_RIGHT_CLOSED;
        clawLeft.setPosition(currentLeftPosition);
        clawRight.setPosition(currentRightPosition);
    }

    // Method to gradually open the claw
    public void openClaw() {
        // Check if current position is less than the max open position
        if (currentLeftPosition < CLAW_LEFT_OPEN) {
            currentLeftPosition = Math.min(currentLeftPosition + CLAW_INCREMENT, CLAW_LEFT_OPEN);
            clawLeft.setPosition(currentLeftPosition);
        }
        if (currentRightPosition < CLAW_RIGHT_OPEN) {
            currentRightPosition = Math.min(currentRightPosition + CLAW_INCREMENT, CLAW_RIGHT_OPEN);
            clawRight.setPosition(currentRightPosition);
        }
    }

    // Method to gradually close the claw
    public void closeClaw() {
        // Check if current position is greater than the max closed position
        if (currentLeftPosition > CLAW_LEFT_CLOSED) {
            currentLeftPosition = Math.max(currentLeftPosition - CLAW_INCREMENT, CLAW_LEFT_CLOSED);
            clawLeft.setPosition(currentLeftPosition);
        }
        if (currentRightPosition > CLAW_RIGHT_CLOSED) {
            currentRightPosition = Math.max(currentRightPosition - CLAW_INCREMENT, CLAW_RIGHT_CLOSED);
            clawRight.setPosition(currentRightPosition);
        }
    }


    // Method to extend or retract the claw mesh incrementally
    public void adjustClawMesh(String direction) {
        // Extend the mesh if the direction is "extend" and the mesh is not fully extended
        if (direction.equals("extend") && currentMeshPosition < CLAW_MESH_EXTENDED) {
            currentMeshPosition = Math.min(currentMeshPosition + CLAW_MESH_INCREMENT, CLAW_MESH_EXTENDED);
        }
        // Retract the mesh if the direction is "retract" and the mesh is not fully retracted
        else if (direction.equals("retract") && currentMeshPosition > CLAW_MESH_RETRACTED) {
            currentMeshPosition = Math.max(currentMeshPosition - CLAW_MESH_INCREMENT, CLAW_MESH_RETRACTED);
        }

        // Set the servo to the new position
        clawMesh.setPosition(currentMeshPosition);
    }
/*
    public void BasketScorePosition() {
        viperSlide.setTargetPosition();
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
