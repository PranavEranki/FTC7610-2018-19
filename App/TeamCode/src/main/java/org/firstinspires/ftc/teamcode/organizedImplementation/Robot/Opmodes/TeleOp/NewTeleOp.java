package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "New TeleOp", group = "TeleOp")
//@Disabled
public class NewTeleOp extends LinearOpMode {


    // latching variables
    /*
    private Servo servo_motor_lift;
    private ServoControllerEx latchMotor;
    private int servo_port_lift;
    private PwmControl.PwmRange theRange_lift;
    private final double upValue = 0.5;
    */







    // motors
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private ElapsedTime runtime = new ElapsedTime();

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

    private double servo_Placement = 0;

    private boolean accelMode = false;

    private final double ACCEL = 0.003;
    private final double DECEL = 0.01;











    @Override
    public void runOpMode(){


        // motors
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        extendingMotor = hardwareMap.dcMotor.get("extending_motor");
        turningMotor = hardwareMap.dcMotor.get("turning_motor");





        /*
        // latching motor setup
        servo_motor_lift = hardwareMap.get(Servo.class, "latch_motor");
        // Set the rotation servo for extended PWM range
        if (servo_motor_lift.getController() instanceof ServoControllerEx) {
            // Confirm its an extended range servo controller before we try to set to avoid crash
            latchMotor = (ServoControllerEx) servo_motor_lift.getController();
            servo_port_lift = servo_motor_lift.getPortNumber();
            telemetry.addData("ServoPort#", servo_port_lift);
            telemetry.update();
            theRange_lift = new PwmControl.PwmRange(500, 2500);
            latchMotor.setServoPwmRange(servo_port_lift, theRange_lift);
        }
        latchMotor.setServoPwmEnable(servo_port_lift);
        */








        // hardware mapping and setup for other servos and motors
        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");

        extendingMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        turningMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        turningMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("hardware mapping complete", "true");
        telemetry.update();

        leftServo.setPosition(leftServoPlacement);
        rightServo.setPosition(rightServoPlacement);



        waitForStart();
        runtime.reset();




        while(opModeIsActive()){


            // switch between modes
            if (gamepad1.right_bumper) {
                accelMode = !accelMode;
            }




            // motor code
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

                leftMotor.setPower(gamepad1.left_stick_y * 1.0);
                rightMotor.setPower(gamepad1.right_stick_y * 0.5);
            }







            /*
            // latch code
            if(gamepad2.x){
                set_latch_motor(upValue); // up
            } else if(gamepad2.b){
                set_latch_motor(-upValue); // down
            } else{
                set_latch_motor(0);
            }
            */








            // servo code, works, do not question!!
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








            // linear slide extending
            if(gamepad2.left_bumper){
                extendingMotor.setPower(-extendPower);
            }else if(gamepad2.right_bumper){
                extendingMotor.setPower(extendPower);
            }else{
                extendingMotor.setPower(0);
            }





            // rotating linear slide
            if(gamepad2.left_trigger > 0){
                turningMotor.setPower(gamepad2.left_trigger);
                telemetry.addData("Up, turnPower: ", gamepad2.left_trigger);
                telemetry.update();
            } else if(gamepad2.right_trigger > 0){
                turningMotor.setPower(-gamepad2.right_trigger);
                telemetry.addData("Down, turnPower: ", -gamepad2.right_trigger);
                telemetry.update();
            } else{
                turningMotor.setPower(0);
            }





            addMessages();
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
        telemetry.addData("Motor Speed", "Left - " + leftPower + ", Right - " + rightPower);
        telemetry.addData("Servos (gamepad 2)", "leftStick and rightStick, imitate servos");
        telemetry.addData("Left Servo Position: ", leftServo.getPosition());
        telemetry.addData("Right Servo Position: ", rightServo.getPosition());
        telemetry.addData("Motors (gamepad 1)", "use left and right sticks");
        telemetry.addData("Core Hex Motors (gamepad 2)", "use left and right triggers (turning) and bumpers (extending/bringing back)");
        telemetry.addData("To switch between modes (gamepad 1)", "press right bumper");
        telemetry.addData("To stop all movement (gamepad 1)", "press left bumper");
    }

    /*
    public void set_latch_motor(double speed){
        speed = Range.clip(speed, -1.0, 1.0) ;
        latchMotor.setServoPosition(servo_port_lift,(speed+1)/2);
    }
    */
}