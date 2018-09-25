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

/*


9/24/18 - During meeting, worked on & updated
code for the servos


 */
public class Ryan_TeleOp extends LinearOpMode {

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private Servo leftServo;
    private Servo rightServo;

    //servo variables
    private final int MIN = 0;
    private final int MAX = 1;
    private final double INCREMENT = 0.01;
    private double left_Servo_Placement = (MIN + MAX) / 2;
    private double right_Servo_Placement = (MIN + MAX) / 2;
    private final double SERVO_MARGIN = 0.05;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing...");
        telemetry.addData("Servo: ", "UP = OPEN, DOWN = CLOSE");
        telemetry.update();

        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        leftServo.setPosition(left_Servo_Placement);
        rightServo.setPosition(right_Servo_Placement);


        while(opModeIsActive()) {
            leftMotor.setPower(gamepad1.left_stick_y);
            rightMotor.setPower(gamepad1.right_stick_y);

            if(gamepad1.y) {
                leftOpen();
            } else if (gamepad1.x) {
                leftClose();
            }

            if(gamepad1.b){
                rightOpen();
            } else if(gamepad1.a){
                rightClose();
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

    /* something to consider, wrong code for our robot
        if(gamepad1.dpad_down && left_Servo_Placement <= (MAX - SERVO_MARGIN)){
            left_Servo_Placement += INCREMENT;
            right_Servo_Placement -= INCREMENT;

            leftServo.setPosition(left_Servo_Placement);
            rightServo.setPosition(right_Servo_Placement);
        }else if(gamepad1.dpad_up && right_Servo_Placement <= (MAX - SERVO_MARGIN)){
            left_Servo_Placement -= INCREMENT;
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
