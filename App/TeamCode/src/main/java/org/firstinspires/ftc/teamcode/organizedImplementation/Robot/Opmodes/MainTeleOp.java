package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*


9/24/18 - During meeting, worked on & updated
code for the servos

10/1/18 - Got robot working and running, found some bugs with code
1. motors going too fast - fixed
2. motors going backwards - fixed
3. left servo continuously running, right servo not moving at all - trying to fix

10/3/18 - Realized that the problem wasn't in the code but it was with the servos,
so we replaced them & now we need to come up with a good number to increment
its position by so that it does not open or close too quickly

10/10/18 - Changed to add acceleration

10/17/18 - Added Core Hex Motors, tweaked some stuff (Shannon)


 */
@TeleOp (name = "Main TeleOp", group = "TeleOp")
//@Disabled
public class MainTeleOp extends LinearOpMode {

    // REV Robotics 40:1 Motors
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    // Core Hex Motors
    private DcMotor turningMotor;
    private DcMotor extendingMotor; // will map when hardware finishes

    // Servos
    private Servo leftServo;
    private Servo rightServo;

    private final double ACCEL = 0.003;
    private final double DECEL = 0.01;

    private double leftPower = 0;
    private double rightPower = 0;

    // Servo variables
    private final int MIN = 0;
    private final int MAX = 1;
    private final double INCREMENT = 0.05;
    private double left_Servo_Placement = (MIN + MAX) / 2;
    private double right_Servo_Placement = (MIN + MAX) / 2;
    private final double SERVO_MARGIN = 0.05;

    private boolean accelMode = true;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        turningMotor = hardwareMap.dcMotor.get("mech");
        // extendingMotor = hardwareMap.dcMotor.get("extending_motor");

        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");

        telemetry.addData("Mapped", "mapping complete");
        telemetry.update();

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        waitForStart();
        runtime.reset();

        telemetry.addData("Starting OpMode", "true");
        telemetry.update();

        while(opModeIsActive()) {

            // Notes
            telemetry.addData("Motors (gamepad 1)", "use left and right sticks");
            telemetry.addData("Core Hex Motors (gamepad 2)", "use left stick");
            telemetry.addData("Servos (gamepad 1)", "X = leftClose, Y = leftOpen, A = rightClose, B = rightOpen");
            telemetry.addData("To switch between modes (gamepad 1)", "press right bumper");
            telemetry.addData("To stop all movement (gamepad 1)", "press left bumper");
            telemetry.addData("Servo Placements", "Left - " + left_Servo_Placement + ", Right - " + right_Servo_Placement);
            telemetry.addData("Motor Speed", "Left - " + leftPower + ", Right - " + rightPower);

            // Motors

            // Changing between modes
            if (gamepad1.right_bumper) {
                accelMode = !accelMode;
            }

            if (accelMode) {
                telemetry.addData("Mode", "Acceleration");
                telemetry.update();

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
                telemetry.update();

                leftMotor.setPower(gamepad1.left_stick_y * 0.75);
                rightMotor.setPower(gamepad1.right_stick_y * 0.75);
            }

            // Core Hex Motors
            turningMotor.setPower(gamepad2.left_stick_y * 0.5);

            // Servos
            if(gamepad1.y) {
                leftOpen();
                servoPos();
            }
            if (gamepad1.x) {
                leftClose();
                servoPos();
            }

            if(gamepad1.b){
                rightOpen();
                servoPos();
            }
            if (gamepad1.a){
                rightClose();
                servoPos();
            }
        }
    }

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

    private void servoPos() {
        leftServo.setPosition(left_Servo_Placement);
        rightServo.setPosition(right_Servo_Placement);
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

}