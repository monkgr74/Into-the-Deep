package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Mechanisms {
    public DcMotor viperSlide;
    public DcMotor linearSlide;
    public DcMotor MessumiSlideLeft;
    public DcMotor MessumiSlideRight;
    public DcMotor armMotor;
    //public Servo clawMesh;
    //public Servo claw;
    //public Servo clawPivot;
    public CRServo intakeServo;
    private boolean isForwardActive = false;
    private boolean isBackwardActive = false;

    private static final double servo_min_pos = 0;
    private static final double servo_max_pos = 1;
    public double currentPivotServoPosition = 0.5;
    public double clawOpen = 0.83;
    public double clawClose = 0.5;


    int maxPosition = 4291;
    int MessumiMaxPosition = 0; // Change it
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

    // int maxPosition = 4395;
    double slideSpeed = 1.0;

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
/*
    public void initMessumiSlides(HardwareMap hardwareMap){
        MessumiSlideLeft = hardwareMap.get(DcMotor.class, "MessumiSlideLeft");
        MessumiSlideRight = hardwareMap.get(DcMotor.class, "MessumiSlideRight");

        MessumiSlideLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        MessumiSlideRight.setDirection(DcMotorSimple.Direction.FORWARD);

        MessumiSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MessumiSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        MessumiSlideLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MessumiSlideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MessumiSlideLeft.setPower(0);

        MessumiSlideRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MessumiSlideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        MessumiSlideRight.setPower(0);
    }
*/
    public void initArmMotor(HardwareMap hardwareMap) {
        armMotor = hardwareMap.get(DcMotor.class, "ArmMotor");
        armMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        armMotor.setPower(0);
    }

    public void moveArmMotorAuto(int desiredPosition) {
        armMotor.getCurrentPosition();
        armMotor.setTargetPosition(desiredPosition);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveArmMotor(String direction) {
        int pos1 = armMotor.getCurrentPosition();

        if(direction.equals("up") && pos1 < 185) {
            pos1 += 20;
        } else if(direction.equals("down") && pos1 > 0) {
            pos1 -= 20;
        }

        armMotor.setTargetPosition(pos1);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(1.1);

    }

    public void extendViperSlide(String direction) {
        int pos1 = viperSlide.getCurrentPosition();

        if(direction.equals("up") && pos1 < maxPosition){
            pos1 += 50;
        }
        else if(direction.equals("down") && pos1 > 0) {
            pos1 -= 50;
        }

        viperSlide.setTargetPosition(pos1);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlide.setPower(1.7);
    }
/*
    public void extendViperSlideAuto(int position) {
        viperSlide.setTargetPosition(position);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

 */

    public void extendMessumiSlides(String direction){
        int posLeft = MessumiSlideLeft.getCurrentPosition();
        int posRight = MessumiSlideRight.getCurrentPosition();

        if(direction.equals("up") && posLeft < MessumiMaxPosition && posRight < MessumiMaxPosition) {
            posLeft += 50;
            posRight += 50;
        } else if (direction.equals("down") && posLeft > 0 && posRight > 0) {
            posLeft -= 50;
            posRight -= 50;
        }
    }

    public void initClaw(HardwareMap hardwareMap) {
       // clawPivot = hardwareMap.get(Servo.class,"clawPivot");
        //claw = hardwareMap.get(Servo.class, "claw");
        intakeServo = hardwareMap.get(CRServo.class, "inTakeServo");
        intakeServo.setDirection(CRServo.Direction.FORWARD);
        //clawPivot.setPosition(Enter value after claw is built);
    }



   // public void openClaw() {
    //    claw.setPosition(clawOpen);
    //}

    //public void closeClaw() {
     //   claw.setPosition(clawClose);
    //}
/*
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
*/
    public void toggleServoDirection(String direction) {
        switch(direction) {
            case "forward":
                if(isBackwardActive) {
                    isBackwardActive = false;
                }
                isForwardActive = !isBackwardActive;
                intakeServo.setPower(isForwardActive ? 1.0 : 0);
                break;

            case "backward":
                if(isForwardActive) {
                    isForwardActive = false;
                }
                isBackwardActive = !isBackwardActive;
                intakeServo.setPower(isBackwardActive ? -1.0:0);
                break;

            case "stop":
                isForwardActive = false;
                isBackwardActive = false;
                intakeServo.setPower(0);
                break;
        }
    }

    public void toggleServoDirection2 (String direction) {
        if(direction.equals("forward")) {
            intakeServo.setDirection(CRServo.Direction.FORWARD);
            intakeServo.setPower(1);

        }
        else if(direction.equals("backward")) {
            intakeServo.setDirection(CRServo.Direction.REVERSE);
            intakeServo.setPower(1);
        }

        else if(direction.equals("stop")) {
            intakeServo.setPower(0);
        }
    }

    public void inTakeServoAuto(String spinDirection, double power) {

        if(spinDirection.equals("forward")) {
            intakeServo.setDirection(CRServo.Direction.FORWARD);
        }
        else if(spinDirection.equals("backward")) {
            intakeServo.setDirection((CRServo.Direction.REVERSE));
        }
        intakeServo.setPower(power);
    }

    public void armPreset() {
        armMotor.setPower(1.7);
        armMotor.setTargetPosition();
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /*
    public void setLimitTo22() {
        int pos1 = viperSlide.getCurrentPosition();
        double TPR = 2994.6;
        double ticks = 22*1993.6;
        extendViperSlide();
        pos1 <= ticks;
    }
    public void setLimit() {
        if(viperSlide.getCurrentPosition() < 710) {
            extendViperSlide(String "up");

        }
            setLimitTo22();

    }


//at 90 degrees it can extend fully
    public void scorePosition() {
        double angleToTick = 90*(1993.6/360);
//find ticks using experimental data through driver hub
        //pos2 is basically the angle that the viperslide gear is at
       if(pos2 != angleToTick) {
          double limit = 710;
               extendViperSlideMeetTwo();
       }
       else {
           extendViperSlide()
       }
    }

     */

public void extendViperSlideMeetTwo(String direction) {
    int pos1 = viperSlide.getCurrentPosition();

    if(direction.equals("up") && pos1 < 710){
        pos1 += 50;
    }
    else if(direction.equals("down") && pos1 > 0) {
        pos1 -= 50;
    }








    // Method to extend or retract the claw mesh incrementally
  /*  public void adjustClawMesh(String direction) {

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

    public void BasketScorePosition() {
        //viperSlide.setTargetPosition(enter position);
        viperSlide.setPower(0.6);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //adjustingClawUP();
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void zeroPosition() {
        viperSlide.setTargetPosition(0);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //openClaw();
    }



/*
dont remove

    public void BasketScorePosition() {
        viperSlide.setTargetPosition(enter position);
        viperSlide.setPower(0.5);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }



     */

}
