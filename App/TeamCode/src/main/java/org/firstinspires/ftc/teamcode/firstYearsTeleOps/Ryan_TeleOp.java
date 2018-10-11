package org.firstinspires.ftc.teamcode.firstYearsTeleOps;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name = "Ryan TeleOp", group = "TeleOp")
//@Disabled
/*


9/24/18 - During meeting, worked on & updated
code for the servos

 */
public class Ryan_TeleOp extends LinearOpMode {

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private Servo leftServo;
    private Servo rightServo;

    private final double accel_move = 0.002;

    //servo variables
    private final int MIN = 0;
    private final int MAX = 1;
    private final double INCREMENT = 0.05;
    private double left_Servo_Placement = (MIN + MAX) / 2;
    private double right_Servo_Placement = (MIN + MAX) / 2;
    private final double SERVO_MARGIN = 0.05;

    private int leftTime = 0;
    private int rightTime = 0;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.addData("Servo: ", "X = leftClose, Y = leftOpen, A = rightClose, B = rightOpen");
        telemetry.update();

        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");
        telemetry.addData("Mapped", "mapping complete");
        telemetry.update();
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        runtime.reset();
        telemetry.addData("starting op mode", "true");
        telemetry.update();
        while(opModeIsActive()) {
            if (gamepad1.left_bumper && gamepad1.right_bumper) {
                leftTime = 0;
                rightTime = 0;
            }
            if(gamepad1.left_stick_y > 0) {
                if (leftTime < 500) {
                    leftTime++;
                }
            }
            if(gamepad1.left_stick_y < 0) {
                if (leftTime > -500) {
                    leftTime--;
                }
            }
            if (gamepad1.right_stick_y > 0) {
                if (rightTime < 500) {
                    rightTime++;
                }
            }
            if(gamepad1.right_stick_y < 0){
                if (rightTime > -500) {
                    rightTime--;
                }
            }

            leftMotor.setPower((leftTime * accel_move) * 0.75);
            rightMotor.setPower((rightTime * accel_move) * 0.75);

            //bumper(top), trigger(bottom)

            if(gamepad1.y) {
                leftOpen();
                servoWait();
            }
            if (gamepad1.x) {
                leftClose();
                servoWait();
            }

            if(gamepad1.b){
                rightOpen();
                servoWait();
            }
            if (gamepad1.a){
                rightClose();
                servoWait();
            }


        }

        //subject to change
        //X: left, Close
        //Y: left, Open
        //A: right, Close
        //B: right, open
    }

    //if servos are flipped, change one of the servo directions (names)

    private void leftOpen(){
        if (left_Servo_Placement >= (MAX - SERVO_MARGIN)) {
            left_Servo_Placement = MAX - SERVO_MARGIN;
        } else {
            left_Servo_Placement += INCREMENT;
        }
    }

    private void leftClose(){
        if (left_Servo_Placement <= (MIN + SERVO_MARGIN)) {
            left_Servo_Placement = MIN + SERVO_MARGIN;
        } else {
            left_Servo_Placement -= INCREMENT;
        }
    }

    private void rightOpen(){
        if (right_Servo_Placement >= (MAX - SERVO_MARGIN)) {
            right_Servo_Placement = MAX - SERVO_MARGIN;
        } else {
            right_Servo_Placement += INCREMENT;
        }
    }

    private void rightClose(){
        if (right_Servo_Placement <= (MIN + SERVO_MARGIN)) {
            right_Servo_Placement = MIN + SERVO_MARGIN;
        } else {
            right_Servo_Placement -= INCREMENT;
        }
    }

    private void servoWait() {
        leftServo.setPosition(left_Servo_Placement);
        rightServo.setPosition(right_Servo_Placement);
    }

    /* something to consider, wrong code for our robot
        if(gamepad1.dpad_down && left_Servo_Placement <= (MAX - SERVO_MARGIN)){
            left_Servo_Placement += INCREMENT;
            right_Servo_Placement -= INCREMENT;

            leftServo.setPosition(left_Servo_Placement);
            rightServo.setPosition(right_Servo_Placement);
        }else if(gamepad1.dpad_up && right_Servo_Placement <= (MAX - SERVO_MARGIN)){
            left_Servo_Placement -= INCREn nMENT;
            right_Servo_Placement += INCREMENT;

            leftServo.setPosition(left_Servo_Placement);
            rightServo.setPosition(right_Servo_Placement);
        }
        */


        /*this is hard code, switch to other method that is not hard code
        while(opModeIsActive()){
            if(gamepad1.left_stick_y < 0){
                leftMotor.setPower(-1);
                //rightMotor.setPower(-1);
            }else if (gamepad1.left_stick_y > 0){
                leftMotor.setPower(1);
                //rightMotor.setPower(1);
            }else{
                leftMotor.setPower(0); //idle at 0
            }
            if(gamepad1.right_stick_y < 0){
                //leftMotor.setPower(-1);
                rightMotor.setPower(-1);
            }else if (gamepad1.right_stick_y > 0){
                //leftMotor.setPower(1);
                rightMotor.setPower(1);
            }else{
                rightMotor.setPower(0); //idle at 0
            }
        }
        //what's next
        */

}
