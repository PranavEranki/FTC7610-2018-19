package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

@TeleOp(name = "Testing", group = "TeleOp")
//@Disabled
public class PatrickTeleOp extends LinearOpMode {

    private DcMotor leftMotor;
    private DcMotor rightMotor;


    @Override
    public void runOpMode() {

        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()) {
            leftMotor.setPower(gamepad1.left_stick_y * 0.5);
            rightMotor.setPower(gamepad1.right_stick_y * 0.5 );

            telemetry.addData("Left Position:", leftMotor.getPower());
            telemetry.addData("Roght Position:", rightMotor.getPower());
            telemetry.update();
        }
    }
}
