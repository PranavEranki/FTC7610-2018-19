package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "All Servos", group = "TeleOp")
//@Disabled
public class AllServos extends LinearOpMode {
    private Servo servoZero;
    private Servo servoOne;
    private Servo servoTwo;
    private Servo servoThree;
    private Servo servoFour;
    private Servo servoFive;

    private final int MIN = 0;
    private final int MAX = 1;
    private final double INCREMENT = 0.005;
    private double servo_Placement = 0.05;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {
        servoZero = hardwareMap.servo.get("servo_zero");
        servoOne = hardwareMap.servo.get("servo_one");
        servoTwo = hardwareMap.servo.get("servo_two");
        servoThree = hardwareMap.servo.get("servo_three");
        servoFour = hardwareMap.servo.get("servo_four");
        servoFive = hardwareMap.servo.get("servo_five");

        waitForStart();
        runtime.reset();

        servoZero.setPosition(0.5);
        servoOne.setPosition(0.5);
        servoTwo.setPosition(0.5);
        servoThree.setPosition(0.5);
        servoFour.setPosition(0.5);
        servoFive.setPosition(0.5);
        telemetry.addData("Starting OpMode", "true");
        telemetry.update();

        while(opModeIsActive()) {
            if (gamepad1.x) {
                servo_Placement = Range.clip(servo_Placement + INCREMENT, 0.05, 0.95);
            }
            if (gamepad1.b) {
                servo_Placement = Range.clip(servo_Placement - INCREMENT, 0.05, 0.95);
            }
            servoZero.setPosition(servo_Placement);
            servoOne.setPosition(servo_Placement);
            servoTwo.setPosition(servo_Placement);
            servoThree.setPosition(servo_Placement);
            servoFour.setPosition(servo_Placement);
            servoFive.setPosition(servo_Placement);
            telemetry.addData("Servo", servo_Placement);
            telemetry.update();
        }
    }
}
