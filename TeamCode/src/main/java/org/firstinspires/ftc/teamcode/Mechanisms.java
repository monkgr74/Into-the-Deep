package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Mechanisms {
    public DcMotor viperSlide;
    public DcMotor linearSlide;
    public Servo claw;

   // public double open = 0.83;
   // public double close = 0.5;

    int maxPosition = 4291;
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
