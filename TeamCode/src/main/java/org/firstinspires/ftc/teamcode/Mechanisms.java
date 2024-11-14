package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Mechanisms {
    public DcMotor viperSlide;
    public DcMotor linearSlide;
    public Servo clawMesh;
    public Servo claw;
    public Servo clawPivot;
    public Servo activeIntake;
    private static final double servo_min_pos = 0;
    private static final double servo_max_pos = 1;
    public double currentPivotServoPosition = 0.5;
    public double clawOpen = 0.83;
    public double clawClose = 0.5;

    int maxPosition = 4291;
    // Claw positions (ALL OF THESE MUST BE ADJUSTED!!!!1!!1!!)
    //private final double CLAW_LEFT_OPEN = 0.8;
    //private final double CLAW_LEFT_CLOSED = 0.5;
    //private final double CLAW_RIGHT_OPEN = 0.2;
    //private final double CLAW_RIGHT_CLOSED = 0.5;
    private final double CLAW_MESH_EXTENDED = 1.0;
    private final double CLAW_MESH_RETRACTED = 0.0;
    private final double CLAW_INCREMENT = 0.01;  // Adjust to control speed of adjustment
    private final double CLAW_MESH_INCREMENT = 0.05;  // Increment value for each step in mesh extension


    // Current positions for the claw and mesh
    private double currentLeftPosition;
    private double currentRightPosition;
    private double currentMeshPosition;

    private boolean isIntakeRunning = false;

    // int maxPosition = 4395;
    double slideSpeed = 0.8;

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

        if(direction.equals("up") && pos1 < maxPosition){
            pos1 += 50;
        }
        else if(direction.equals("down") && pos1 > 0) {
            pos1 -= 50;
        }

        viperSlide.setTargetPosition(pos1);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlide.setPower(slideSpeed);
    }

    // Initialize claw servos
    public void initClaw(HardwareMap hardwareMap) {
        clawPivot = hardwareMap.get(Servo.class,"clawPivot");
        claw = hardwareMap.get(Servo.class, "claw");
        activeIntake = hardwareMap.get(Servo.class,"activeIntake");
        //clawPivot.setPosition(Enter value after claw is built);
    }

    public void activeIntakeIn() {
        if (isIntakeRunning){
            activeIntake.setPosition(0.5);
            isIntakeRunning = false;
            opMode.telemetry.addLine("Active intake inwards stopped.");
            opMode.telemetry.update();
        } else {
            activeIntake.setPosition(1.0);
            isIntakeRunning = true;
            opMode.telemetry.addLine("Active intake inwards started.");
            opMode.telemetry.update();
        }
    }

    public void activeIntakeOut() {
        if (isIntakeRunning){
            activeIntake.setPosition(0.5);
            isIntakeRunning = false;
            opMode.telemetry.addLine("Active intake outwards stopped.");
            opMode.telemetry.update();
        } else {
            activeIntake.setPosition(0.0);
            isIntakeRunning = true;
            opMode.telemetry.addLine("Active intake outwards started.");
            opMode.telemetry.update();
        }
    }

    public void openClaw() {
        claw.setPosition(clawOpen);
    }

    public void closeClaw() {
        claw.setPosition(clawClose);
    }

    public void adjustingClawUP() {
        if(currentPivotServoPosition < 1) {
            currentPivotServoPosition += 0.25;
            claw.setPosition(currentPivotServoPosition);
            opMode.telemetry.addData("Claw Pivot Position", currentPivotServoPosition);
            opMode.telemetry.update();
        }

    }

    public void adjustingClawDOWN() {
        if(currentPivotServoPosition > 0){
            currentPivotServoPosition -= 25;
            claw.setPosition(currentPivotServoPosition);
            opMode.telemetry.addData("Claw Pivot Position", currentPivotServoPosition);
            opMode.telemetry.update();
        }
    }


    // Method to extend or retract the claw mesh incrementally
    /*
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
    */

    public void basketScorePosition() {
        //viperSlide.setTargetPosition(enter position);
        viperSlide.setPower(0.6);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        adjustingClawUP();
    }

    public void zeroPosition() {
        viperSlide.setTargetPosition(0);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
