package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name = "Servos and Motors", group = "TeleOp")
//@Disabled
/*


9/24/18 - During meeting, worked on & updated
code for the servos

10/1/18 - Got robot working and running, found some bugs with code
1. motors going too fast - fixed
2. motors going backwards - fixed
3. left servo continuously running, right servo not moving at all - trying to fix


 */
public class TestingServosAndMotors extends LinearOpMode {

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private Servo leftServo;
    private Servo rightServo;

    //servo variables
    private final int MIN = 0;
    private final int MAX = 1;
    private final double INCREMENT = 0.05;
    private double left_Servo_Placement = (MIN + MAX) / 2;
    private double right_Servo_Placement = (MIN + MAX) / 2;
    private final double SERVO_MARGIN = 0.05;

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
            leftMotor.setPower(gamepad1.left_stick_y * 0.33);
            rightMotor.setPower(gamepad1.right_stick_y * 0.33);

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


}
