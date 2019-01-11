package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Sleeping Crater Auto", group = "Autonomous")
//@Disabled
public class SleepingCrater extends LinearOpMode{

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private DcMotor turningMotor;
    private DcMotor extendingMotor;

    private DcMotor latch_motor;

    private final double leftSpeed = 1.0;
    private final double rightSpeed = 0.5;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode(){
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        turningMotor = hardwareMap.dcMotor.get("turning_motor");
        extendingMotor = hardwareMap.dcMotor.get("extending_motor");

        latch_motor = hardwareMap.dcMotor.get("latch_motor");
        latch_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        runtime.reset();
        sleep(15000);
        runtime.reset();

        latch_motor.setPower(-1);
        while(opModeIsActive() && runtime.seconds() < 3){
            telemetry.addData("Unlatching...", "true", runtime.seconds());
            telemetry.update();
        }
        latch_motor.setPower(0);

        runtime.reset();

        rightMotor.setPower(0.5);
        leftMotor.setPower(1);
        while(opModeIsActive() && runtime.milliseconds() < 200){
            telemetry.addData("forward unlatching movement", "true", runtime.milliseconds());
            telemetry.update();
        }
        rightMotor.setPower(0);
        leftMotor.setPower(0);
        sleep(500);

        runtime.reset();

        rightMotor.setPower(-rightSpeed);
        leftMotor.setPower(leftSpeed);
        while(opModeIsActive() && runtime.milliseconds() < 500){
            telemetry.addData("turning to crater", "true", runtime.milliseconds());
            telemetry.update();
        }
        rightMotor.setPower(0);
        leftMotor.setPower(0);
        sleep(500);
        runtime.reset();
        //
        rightMotor.setPower(-rightSpeed * 0.5);
        leftMotor.setPower(leftSpeed * 0.5);
        while(opModeIsActive() && runtime.milliseconds() < 100){
            telemetry.addData("spaz up", "true", runtime.milliseconds());
            telemetry.update();
        }
        rightMotor.setPower(0);
        leftMotor.setPower(0);
        sleep(250);
        runtime.reset();
        //
        // go straight from start to crater
        leftMotor.setPower(leftSpeed);
        rightMotor.setPower(rightSpeed);
        //full speed ahead
        //go go go go go
        while(opModeIsActive() && runtime.seconds() < 2.0){
            telemetry.addData("To Crater", "true", runtime.seconds());
            telemetry.update();
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        telemetry.addData("Autonomous Finished", "true", runtime.seconds());
        telemetry.update();
    }
}

