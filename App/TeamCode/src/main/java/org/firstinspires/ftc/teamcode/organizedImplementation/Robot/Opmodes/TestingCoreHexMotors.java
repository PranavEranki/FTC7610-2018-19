package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Core Hex Motor", group = "Linear Opmode")
public class TestingCoreHexMotors extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor turningMotor;

    private double power = 0;

    @Override
    public void runOpMode() {
        turningMotor = hardwareMap.dcMotor.get("mech");
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up && power < 100) {
                power++;
            } else if (gamepad1.dpad_down && power > -100) {
                power--;
            }
            turningMotor.setPower(power * 0.0075);
        }
    }

}