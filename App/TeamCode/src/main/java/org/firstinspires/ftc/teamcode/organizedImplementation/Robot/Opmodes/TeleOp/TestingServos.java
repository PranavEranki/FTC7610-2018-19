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

@TeleOp(name = "Testing Servos", group = "TeleOp")
//@Disabled
public class TestingServos extends LinearOpMode {
    private Servo leftServo;
    private Servo rightServo;

    private final int MIN = 0;
    private final int MAX = 1;
    private final double INCREMENT = 0.005;
    private double servo_Placement = 0.05;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {
        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");

        waitForStart();
        runtime.reset();

        leftServo.setPosition(0.05);
        rightServo.setPosition(0.05);
        telemetry.addData("Starting OpMode", "true");
        telemetry.update();

        while(opModeIsActive()) {
            if (gamepad1.x) {
                servo_Placement = Range.clip(servo_Placement + INCREMENT, 0.05, 0.95);
            }
            if (gamepad1.b) {
                servo_Placement = Range.clip(servo_Placement - INCREMENT, 0.05, 0.95);
            }
            leftServo.setPosition(servo_Placement);
            rightServo.setPosition(servo_Placement);
            telemetry.addData("Servo", servo_Placement);
            telemetry.update();
        }
    }
}
