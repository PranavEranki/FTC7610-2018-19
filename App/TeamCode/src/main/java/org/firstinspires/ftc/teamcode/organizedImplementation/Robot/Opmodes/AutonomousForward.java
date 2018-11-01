package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/*

10/17/18 - Only goes forward for 6 seconds

 */

@Autonomous(name= "Autonomous Forward", group="Autonomous")
public class AutonomousForward extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    @Override
    public void runOpMode() {

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Forward for 10 seconds
        runtime.reset();

        leftMotor.setPower(0.5001);
        rightMotor.setPower(0.5);
        while (opModeIsActive() && (runtime.seconds() < 2.0)) {
            telemetry.addData("Path 1 - Forward", runtime.seconds());
            telemetry.update();
        }


        //Stop
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}
