package org.firstinspires.ftc.teamcode.firstYearsTeleOps;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/*

10/1/18 - not sure what the issue is as of now? will probably test in the future

 */
@TeleOp(name="Swapnil Tele-Op", group="Linear Opmode")
@Disabled
public class Swapnil_TeleOp extends LinearOpMode {

    ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    double power = 0.5;


    // move forward at power of 0.5 for 5 seconds
    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");

        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        waitForStart();
        runtime.reset();

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor.setPower(gamepad1.left_stick_y);
        rightMotor.setPower(gamepad1.right_stick_y);

        // wait 5 seconds before executing next line of code
        // (robot continues moving)
        sleep(5000);

        leftMotor.setPower(0);
        rightMotor.setPower(0);

    }


    /*
    // moves based on the gamepad
    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");

        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        waitForStart();
        runtime.reset();

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        while (opModeIsActive()) {

            telemetry.addData("Status", "Runtime: " + runtime.toString());
            telemetry.update();

            leftMotor.setPower(-gamepad1.left_stick_y);
            rightMotor.setPower(-gamepad1.right_stick_y);

        }

    }
    */

}

