package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "NewControlTeleOp", group = "TeleOp")
//@Disabled
public class NewControlTeleOp extends LinearOpMode {

    private final double upValue = 0.5;

    //private DcMotor latchMotor;

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private ElapsedTime runtime = new ElapsedTime();

    private final double servoMax = 1;
    private final double servoMin = 0;

    private double leftServoPlacement = 0;
    private double rightServoPlacement = 1;

    private Servo leftServo;
    private Servo rightServo;

    private DcMotor extendingMotor;
    private DcMotor turningMotor;

    private final double turnPower = 1.0;
    private final double extendPower = 0.6;

    private double leftPower = 0;
    private double rightPower = 0;

    private final double INCREMENT = 0.005;
    private double servo_Placement = 0;

    private boolean accelMode = false;

    private final double ACCEL = 0.003;
    private final double DECEL = 0.01;

    @Override
    public void runOpMode(){
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        extendingMotor = hardwareMap.dcMotor.get("extending_motor");
        turningMotor = hardwareMap.dcMotor.get("turning_motor");

        //latchMotor = hardwareMap.dcMotor.get("latch_motor");
        //latchMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        //latchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");

        extendingMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        turningMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        turningMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("hardware maoping complete", "true");
        telemetry.update();

        leftServo.setPosition(leftServoPlacement);
        rightServo.setPosition(rightServoPlacement);

        waitForStart();
        runtime.reset();

        while(opModeIsActive()){
            if (gamepad1.right_bumper) {
                accelMode = !accelMode;
            }

            if (accelMode) {
                telemetry.addData("Mode", "Acceleration");
                addMessages();

                // Everything stops moving when left bumper is pressed
                if (gamepad1.left_bumper) {
                    leftPower = 0;
                    rightPower = 0;
                }

                if (gamepad1.left_stick_y > 0) {
                    if (leftPower < 0) leftPower = limitIncrement(leftPower, DECEL, 0);
                    else leftPower = limitIncrement(leftPower, ACCEL, 1);
                } else if (gamepad1.left_stick_y < 0) {
                    if (leftPower > 0) leftPower = limitDecrement(leftPower, DECEL, 0);
                    else leftPower = limitDecrement(leftPower, ACCEL, -1);
                }

                if (gamepad1.right_stick_y > 0) {
                    if (rightPower < 0) rightPower = limitIncrement(rightPower, DECEL, 0);
                    else rightPower = limitIncrement(rightPower, ACCEL, 1);
                } else if (gamepad1.right_stick_y < 0) {
                    if (rightPower > 0) rightPower = limitDecrement(rightPower, DECEL, 0);
                    else rightPower = limitDecrement(rightPower, ACCEL, -1);
                }

                leftMotor.setPower(leftPower * 0.75);
                rightMotor.setPower(rightPower * 0.75);
            } else {
                telemetry.addData("Mode", "No Acceleration");
                addMessages();

                leftMotor.setPower(gamepad1.left_stick_y * 1.0);
                rightMotor.setPower(gamepad1.right_stick_y * 0.5);
            }
            //up
            //if(gamepad2.x){
                //latchMotor.setPower(upValue);
            //}
            //if(gamepad2.b){
                //latchMotor.setPower(-upValue);
            //}
            //down

            if(gamepad2.left_stick_x < 0) {
                leftServo.setPosition((gamepad2.left_stick_x + 1) * 0.5);
            }
            if(gamepad2.left_stick_x > 0){
                leftServo.setPosition(gamepad2.left_stick_x);
            }
            if(gamepad2.right_stick_x > 0){
                rightServo.setPosition(gamepad2.right_stick_x);
            }
            if(gamepad2.right_stick_x < 0){
                rightServo.setPosition((-gamepad2.right_stick_x - 1) * 0.5);
            }
            /*if(gamepad2.dpad_up){
                turningMotor.setPower(turnPower); // turnPower
                telemetry.addData("Up, turnPower: ", turnPower);
                telemetry.update();
            }else{
                turningMotor.setPower(0);
            }
            if (gamepad2.dpad_down){
                turningMotor.setPower(-turnPower); //-turnPower
                telemetry.addData("Down, turnPower: ", turnPower);
                telemetry.update();
            }else{
                turningMotor.setPower(0);
            }*/
            if(gamepad2.left_bumper){
                extendingMotor.setPower(-extendPower);
            }else{
                extendingMotor.setPower(0);
            }
            if(gamepad2.left_trigger > 0){
                turningMotor.setPower(gamepad2.left_trigger);
                telemetry.addData("Up, turnPower: ", gamepad2.left_trigger);
                telemetry.update();
            }else{
                turningMotor.setPower(0);
            }
            if(gamepad2.right_bumper){
                extendingMotor.setPower(extendPower);
            }else{
                extendingMotor.setPower(0);
            }
            if(gamepad2.right_trigger > 0){
                turningMotor.setPower(-gamepad2.right_trigger);
                telemetry.addData("Down, turnPower: ", -turnPower);
                telemetry.update();
            }else{
                turningMotor.setPower(0);
            }
            telemetry.addData("Left Servo Position: ", leftServoPlacement);
            telemetry.addData("Right Servo Position: ", rightServoPlacement);
            telemetry.update();
        }
    }

    private double limitIncrement(double initial, double inc, double limit) {
        initial += inc;
        if (initial > limit){
            return limit;
        }
        else {
            return initial;
        }
    }

    private double limitDecrement(double initial, double dec, double limit) {
        initial -= dec;
        if (initial < limit) {
            return limit;
        }
        else {
            return initial;
        }
    }

    private void addMessages() {
        telemetry.addData("Servo Placements", "Left - " + servo_Placement + ", Right - " + (1 - servo_Placement));
        telemetry.addData("Motor Speed", "Left - " + leftPower + ", Right - " + rightPower);
        telemetry.addData("Motors (gamepad 1)", "use left and right sticks");
        telemetry.addData("Core Hex Motors (gamepad 2)", "use left (turning) and right (extending)");
        telemetry.addData("Servos (gamepad 1)", "X = Open, B = Close");
        telemetry.addData("To switch between modes (gamepad 1)", "press right bumper");
        telemetry.addData("To stop all movement (gamepad 1)", "press left bumper");
    }
}
//0,
// -1, 0, 1 // 0, 1, 2
// 0, 1
