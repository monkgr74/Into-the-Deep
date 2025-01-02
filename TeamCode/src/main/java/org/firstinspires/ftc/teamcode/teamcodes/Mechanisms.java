package org.firstinspires.ftc.teamcode.teamcodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Mechanisms {
    public DcMotor viperSlide;
    public DcMotor viperPivot;
    //public DcMotor BWTSlide1;
    //public DcMotor BWTSlide2;
    public Servo claw;
    public Servo pivot;
    public ColorSensor colorSensor;
    double clawOpen = 0.38; //change value
    int clawClose = 0; //Change Value
    private boolean isForwardActive = false;
    private boolean isBackwardActive = false;

    private static final double servo_min_pos = 0;
    private static final double servo_max_pos = 1;
    public double currentPivotServoPosition = 0.5;

    private static final double TICKS_PER_REV = 1538.0; // Encoder ticks per revolution
    private static final double DISTANCE_PER_REV_MM = 120.0; // Distance moved per revolution in mm
    private static final double MM_TO_INCHES = 25.4; // Conversion factor for mm to inches

    private static final double DISTANCE_PER_TICK_INCHES = (DISTANCE_PER_REV_MM / TICKS_PER_REV) / MM_TO_INCHES;

    boolean limitPlaced = false;


    private static final int viperPivotScoringPosition = 900;
    private static final int viperExtendScoringPosition = 2000;
    private static final int viperPivotBlockPickupPosition = 900;
    private static final int viperExtendBlockPickupPosition = 2000;

    private boolean allowFloat = false; // Flag to track when to enable float
    private long delayStartTime = 0;

    boolean allowExtension = false;
    boolean allowPivot = false;

    int MaximumArmLimit = 100;
    int maxPosition = 2609;
    int maximumLimit = 43991; //change value
    double slideSpeed = 1.0;

    boolean isBlue = false;
    LinearOpMode opMode;
    public Mechanisms (LinearOpMode op){
        opMode = op;
    }

    /**
     * Converts inches to encoder ticks.
     *
     * @param inches The distance in inches to convert.
     * @return The equivalent number of encoder ticks as an integer.
     */
    public static int inchesToTicks(double inches) {
        return (int) Math.round(inches / DISTANCE_PER_TICK_INCHES);
    }

    /**
     * Converts encoder ticks to inches.
     *
     * @param ticks The number of encoder ticks to convert.
     * @return The equivalent distance in inches as a double.
     */
    public static double ticksToInches(int ticks) {
        return ticks * DISTANCE_PER_TICK_INCHES;
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
        viperPivot = hardwareMap.get(DcMotor.class, "ArmMotor");
        viperPivot.setDirection(DcMotor.Direction.FORWARD);
        viperPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        viperPivot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        viperPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        viperPivot.setPower(0);

    }

    public void initClaw(HardwareMap hardwareMap) {
        // clawPivot = hardwareMap.get(Servo.class,"clawPivot");
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(0);

        pivot = hardwareMap.get(Servo.class, "pivotClaw");
        //pivot.setDirection();
        //pivot.setPosition(0);
    }

    public void initColorSensor(HardwareMap hardwareMap){
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    }

    public void checkIfBlue(boolean teamColor){
        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();



        if(teamColor == true){
            boolean isBluish = blue > red && blue > green;
            opMode.telemetry.addData("Blue Detected", isBluish);
            setClawClose();
        }


    }

    public void checkIfRed(boolean teamColor){
        int red = colorSensor.red();
        int green = colorSensor.green();
        int blue = colorSensor.blue();

        if(teamColor == true){
            boolean isRedish = red > blue && red > green;
            opMode.telemetry.addData("RED DETECTED", isRedish);
            setClawClose();
        }


    }

    public void setClawOpen() {
        claw.setPosition(clawOpen);
    }

    public void openClaw(double input){
        if(input >0.1){
            setClawOpen();
        }
    }
    public void closeClaw(double input){
        if(input >0.1){
            setClawClose();
        }
    }

    public void setClawClose() {
        claw.setPosition(clawClose);
    }

    public void setClawPivot(String direction) {
        double pos1 = pivot.getPosition();
        if(direction.equals("up")) {
            pivot.setDirection(Servo.Direction.REVERSE);
            pos1 += 0.10;
            pivot.setPosition(pos1);

        }
        else if(direction.equals("down")) {
            pivot.setDirection(Servo.Direction.REVERSE);
            pos1 -= 0.10;
            pivot.setPosition(pos1);
        }
    }


    public void moveArmMotorAuto(int desiredPosition) {
        viperPivot.getCurrentPosition();
        viperPivot.setTargetPosition(desiredPosition);
        viperPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void moveArmMotor(String direction, double input) {
        int pos1 = viperPivot.getCurrentPosition();

        if(direction.equals("up") && pos1 < MaximumArmLimit && input >0) {
            viperPivot.setDirection(DcMotorSimple.Direction.FORWARD);
            viperPivot.setPower(input/0.5);

        } else if(direction.equals("down") && pos1 > 0 && input < 0) {
            viperPivot.setDirection(DcMotorSimple.Direction.REVERSE);
            viperPivot.setPower(input/0.5);
        }

        viperPivot.setTargetPosition(pos1);
        viperPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //armMotor.setPower(1.5);

    }

    public void armMotorPivot(String direction){
        int pos1 = viperPivot.getCurrentPosition();

        if(direction.equals("up")){
            pos1+=500;
        }
        if(direction.equals("down")){
            pos1-=500;
        }
        viperPivot.setTargetPosition(pos1);
        viperPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperPivot.setPower(1.7);
    }

    public void extendViperSlide(String direction) {
        int pos1 = viperSlide.getCurrentPosition();

        if(direction.equals("forward") && pos1 < maxPosition){
            pos1 += 150;
        }
        else if(direction.equals("backward") && pos1 > 0) {
            pos1 -= 150;
        }

        viperSlide.setTargetPosition(pos1);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperSlide.setPower(0.7);
    }

    //extends viperslide using sticks
    public void extendViperSlide(String direction, double input) {
        int pos1 = viperSlide.getCurrentPosition();
        if(direction.equals("up") && pos1 < maxPosition){
            viperSlide.setDirection(DcMotorSimple.Direction.FORWARD);
            viperSlide.setPower(input/1.5);
        }
        else if(direction.equals("down") && pos1 > 0) {
            viperSlide.setDirection(DcMotorSimple.Direction.FORWARD);
            viperSlide.setPower(input/1.5);
        }

        //viperSlide.setTargetPosition(pos1);
        //viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //viperSlide.setPower(1.7);
    }




    /*
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


     */

    /*
    distance per tick = (lead screw pith)/(encoder ticks per revolution)
     */


    public void pivotLimit1(){

        //int ticksPerRev = 1538;
        int armPosition = viperPivot.getCurrentPosition();

        double degrees = ((double) viperPivot.getCurrentPosition() /TICKS_PER_REV) * 360;

        if(degrees > 90 && degrees < 180){
            //degrees-=180;
            Math.abs(degrees-=180);

            opMode.telemetry.addData("Degree", Math.abs(degrees-=180));

            //double angleRadians = Math.toRadians(degrees);
        }

        if(degrees > 180 && degrees < 270){
            degrees-=270;
            Math.abs(degrees-=270);

            opMode.telemetry.addData("Degree", Math.abs(degrees-270));
        }

        double angleRadians = Math.toRadians(degrees);
        double base = inchesToTicks(25);
        double tangent = base * Math.tan(angleRadians); // height
        double hypotenuse = Math.sqrt((base * base) + (tangent * tangent));
        double distance = ticksToInches(armPosition);

        /*
        if(checkHorizontal()) {
            allowExtension = true;
            if(viperSlide.getCurrentPosition() < base){
                allowExtension = true;
            }
            else {
                allowExtension = false;
            }
        }
        if(checkVerticall()){
            allowExtension = true;
            allowPivot = true;
        }



        if(distance == hypotenuse){
            allowPivot = true;
            allowExtension = true;
        }
        if(distance < hypotenuse){
            allowPivot = true;
            allowExtension = true;
        }
        if(distance > hypotenuse){
            allowPivot = false;
            allowExtension = true;
        }
        */

    }

    public double getAngle() {
        int tickersPerRev = 312;
        double degrees = (viperPivot.getCurrentPosition() / tickersPerRev) * 360;
        opMode.telemetry.addData("Angle", degrees);

        return degrees;
    }

    public boolean checkHorizontal() {
        int pos = viperPivot.getCurrentPosition();

        if(pos > 0 && pos < 9) {//change the values
            limitPlaced = true;
        }
        if(pos > 0){
            limitPlaced = false;
        }


        return limitPlaced;
    }

    public boolean checkVerticall() {
        int pos = viperPivot.getCurrentPosition();

        if(pos == 90){
            limitPlaced = true;
        }
        if(pos> 0){
            limitPlaced = false;
        }
        return limitPlaced;
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
    /*
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


     */

    //intake servo auto
    /*
    public void inTakeServoAuto(String spinDirection, double power) {

        if(spinDirection.equals("forward")) {
            intakeServo.setDirection(CRServo.Direction.FORWARD);
        }
        else if(spinDirection.equals("backward")) {
            intakeServo.setDirection((CRServo.Direction.REVERSE));
        }
        intakeServo.setPower(power);
    }

     */

    //adjustservo for intake servo
    /*
    public void adjustServo(String direction, double input) {
        if(direction.equals("forward")) {
            intakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
            intakeServo.setPower(input);
        } else if(direction.equals("backward")) {
            intakeServo.setDirection(DcMotorSimple.Direction.FORWARD);
            intakeServo.setPower(input);
        } else {
            intakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
            intakeServo.setPower(0);
        }
    }

     */



/*
    public void armPreset() {
        armMotor.setPower(1.7);
        armMotor.setTargetPosition();
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

 */


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
        viperPivot.setTargetPosition(4700);
        viperPivot.setPower(1.7);
        //viperSlide.setTargetPosition(viperExtendScoringPosition);
        //viperSlide.setPower(1.7);
        pivot.setPosition(0.4);
    }

    public void SpecimenPickupPosition() {
        viperPivot.setTargetPosition(0);
        viperPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperPivot.setPower(1.7);
        pivot.setDirection(Servo.Direction.FORWARD);
        pivot.setPosition(0.6);
        setClawOpen();
    }

    public void SpecimenScoringPosition() {
        viperPivot.setTargetPosition(7960);
        viperPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperPivot.setPower(1.7);
        pivot.setDirection(Servo.Direction.FORWARD);
        pivot.setPosition(0.9);
    }
    public void BlockPickupPosition() {
        viperPivot.setTargetPosition(12850);
        viperPivot.setPower(1.7);
        //viperSlide.setTargetPosition(56);
        //viperSlide.setPower(1.7);
        pivot.setPosition(0.9);
    }

    public void zeroPosition() {
        viperSlide.setTargetPosition(0);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        viperPivot.setTargetPosition(0);
        viperPivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Ensure brakes are initially applied
        viperPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        allowFloat = false; // Reset flag

        //openClaw();
    }

    public void updateZeroPosition() {
        if (!viperPivot.isBusy() && !allowFloat) {
            viperPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            allowFloat = true;
        }

        if (allowFloat) {
            // Use non-blocking delay
            if (nonBlockingSleep(100)) {
                viperPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                allowFloat = false; // Reset flag
            }
        }

        opMode.telemetry.addData("Pivot Position", viperPivot.getCurrentPosition());
        opMode.telemetry.update();
    }

    public boolean nonBlockingSleep(long milliseconds) {
        if (delayStartTime == 0) {
            // Initialize the delay
            delayStartTime = System.currentTimeMillis();
            return false;
        }

        if (System.currentTimeMillis() - delayStartTime >= milliseconds) {
            // Delay is complete, reset timer
            delayStartTime = 0;
            return true; // Indicates the delay is finished
        }

        // Delay is still ongoing
        return false;
    }

    public void initMechanisms(HardwareMap hardwareMap) {
        initViperSlide(hardwareMap);
        initClaw(hardwareMap);
        initArmMotor(hardwareMap);
        initColorSensor(hardwareMap);
    }



/*
dont remove. i shall remove it - anuj

    public void BasketScorePosition() {
        viperSlide.setTargetPosition(enter position);
        viperSlide.setPower(0.5);
        viperSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    }


     */

}
