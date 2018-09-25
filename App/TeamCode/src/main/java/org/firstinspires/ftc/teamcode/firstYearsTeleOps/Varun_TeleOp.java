package org.firstinspires.ftc.teamcode.firstYearsTeleOps;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp (name = "Varun TeleOp", group = "TeleOp")

public class Varun_TeleOp extends LinearOpMode {

    private DcMotor RightMotor;
    private DcMotor LeftMotor;

    public ElapsedTime RunTime = new ElapsedTime();

    @Override
    public void runOpMode(){
        telemetry.addData("Status", "Initialize : ");
        telemetry.update();

        LeftMotor = hardwareMap.dcMotor.get("Left motor");
        RightMotor = hardwareMap.dcMotor.get("Right motor");

        LeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        RightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        while(opModeIsActive()) {
            if (gamepad1.left_stick_y < 0) {
                LeftMotor.setPower(-1);
            } else if (gamepad1.left_stick_y > 0) {
                LeftMotor.setPower(1);
            } else {
                LeftMotor.setPower(0);
            }
            if (gamepad1.right_stick_y < 0) {
                RightMotor.setPower(-1);
            } else if (gamepad1.right_stick_y > 0) {
                RightMotor.setPower(1);
            } else {
                RightMotor.setPower(0);
            }
        }


    }

}

