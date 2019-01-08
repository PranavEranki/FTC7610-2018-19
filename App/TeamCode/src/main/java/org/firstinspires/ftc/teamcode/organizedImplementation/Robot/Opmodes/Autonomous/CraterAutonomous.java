package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Crater Autonomous", group = "Autonomous")
//@Disabled
public class CraterAutonomous extends LinearOpMode{

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

        latch_motor.setPower(-0.8);
        while(opModeIsActive() && runtime.seconds() < 2){
            telemetry.addData("Unlatching...", "true", runtime.seconds());
            telemetry.update();
        }
        latch_motor.setPower(0);

        runtime.reset();

        // go straight from start to crater
        leftMotor.setPower(leftSpeed);
        rightMotor.setPower(rightSpeed);
        //full speed ahead
        //go go go go go
        while(opModeIsActive() && runtime.seconds() < 2){
            telemetry.addData("To Crater", "true", runtime.seconds());
            telemetry.update();
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        telemetry.addData("Autonomous Finished", "true", runtime.seconds());
        telemetry.update();
    }
}
