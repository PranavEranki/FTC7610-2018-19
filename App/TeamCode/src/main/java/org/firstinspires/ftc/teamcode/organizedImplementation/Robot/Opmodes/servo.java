package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name = "servo", group = "TeleOp")
//@Disabled
public class servo extends LinearOpMode {
    private Servo leftServo;
    private Servo rightServo;

    private final int MIN = 0;
    private final int MAX = 1;
    private final double INCREMENT = 0.05;
    private double left_Servo_Placement = (MIN + MAX) / 2;
    private double right_Servo_Placement = (MIN + MAX) / 2;
    private final double SERVO_MARGIN = 0.05;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {
        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");

        waitForStart();
        runtime.reset();

        telemetry.addData("Starting OpMode", "true");
        telemetry.update();

        while(opModeIsActive()) {
            if (gamepad2.x) {
                // Open
            }
            if (gamepad2.b) {
                // Close
            }
            telemetry.update();
        }
    }
}
