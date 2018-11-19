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

/*

11/19/18 - Testing positions for servos

 */
@TeleOp (name = "Testing Servo V2", group = "TeleOp")
//@Disabled
public class TestingServoV2 extends LinearOpMode {

    // Servos
    private Servo leftServo;
    private Servo rightServo;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");

        telemetry.addData("Mapped", "mapping complete");
        telemetry.update();

        waitForStart();
        runtime.reset();

        telemetry.addData("Starting OpMode", "true");
        telemetry.update();

        while(opModeIsActive()) {

            // Servos
            // Switch if open/close are opposite
            telemetry.addData("Left Servo Direction: ", leftServo.getDirection());
            telemetry.addData("Left Servo Position", leftServo.getPosition());
            telemetry.addData("Right Servo Direction: ", rightServo.getDirection());
            telemetry.addData("Right Servo Position", rightServo.getPosition());
            telemetry.update();

        }
    }

}
