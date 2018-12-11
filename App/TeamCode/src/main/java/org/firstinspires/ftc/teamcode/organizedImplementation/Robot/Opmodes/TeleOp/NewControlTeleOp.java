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

@TeleOp(name = "New Control TeleOp", group = "TeleOp")
//@Disabled
public class NewControlTeleOp extends LinearOpMode {

    private final double upValue = 0.5;
    private DcMotor latchMotor;
    private ElapsedTime runtime = new ElapsedTime();
    private final double servoMax = 1;
    private final double servoMin = 0;
    private double leftServoPlacement = 0;
    private double rightServoPlacement = 1;
    private Servo leftServo;
    private Servo rightServo;

    @Override
    public void runOpMode(){
        latchMotor = hardwareMap.dcMotor.get("latch_motor");

        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");

        telemetry.addData("hardware maoping complete", "true");
        telemetry.update();

        leftServo.setPosition(leftServoPlacement);
        rightServo.setPosition(rightServoPlacement);

        waitForStart();
        runtime.reset();

        while(opModeIsActive()){
            //down
            if(gamepad2.right_trigger != 0){
                latchMotor.setPower(-gamepad2.right_trigger);
            }
            //up
            if(gamepad2.right_bumper){
                latchMotor.setPower(upValue);
            }
            leftServo.setPosition(gamepad2.left_stick_x);
            rightServo.setPosition(gamepad2.right_stick_x);
        }
    }
}
