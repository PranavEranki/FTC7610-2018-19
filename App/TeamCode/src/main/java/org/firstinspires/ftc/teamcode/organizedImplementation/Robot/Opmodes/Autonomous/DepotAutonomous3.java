package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Depot Autonomous 1.0", group = "Autonomous")
//@Disabled
public class DepotAutonomous3 extends LinearOpMode{

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private DcMotor turningMotor;
    private DcMotor extendingMotor;

    private double speed = 0.5;
    private double error = 0.0001;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode(){
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        //turningMotor = hardwareMap.dcMotor.get("turning_motor");
        //extendingMotor = hardwareMap.dcMotor.get("extending_motor");

        waitForStart();

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        runtime.reset();

        // go straight from start to depot
        leftMotor.setPower(speed + error);
        rightMotor.setPower(speed);
        while(opModeIsActive() && runtime.seconds() < 0.5){
            telemetry.addData("To Depot", "true", runtime.seconds());
            telemetry.update();
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        /*
        // bring out marker and put down
        // still need to code for motor for linear slide
        turningMotor.setPower(1);
        while(opModeIsActive() && runtime.seconds() < 2.0){
            telemetry.addData("Drop the Team Marker", "true", runtime.seconds());
            telemetry.update();
        }
        turningMotor.setPower(-1);
        while(opModeIsActive() && runtime.seconds() < 2.0){
            telemetry.addData("Put the motor back at its original position", "true", runtime.seconds());
            telemetry.update();
        }
        turningMotor.setPower(0);
        */

        // turn from current position to crater
        leftMotor.setPower(-speed - error);
        rightMotor.setPower(speed);
        while(opModeIsActive() && runtime.seconds() < 1.0){
            telemetry.addData("Turn Towards To Crater", "true", runtime.seconds());
            telemetry.update();
        }

        // move to crater
        leftMotor.setPower(speed + error);
        rightMotor.setPower(speed);
        while(opModeIsActive() && runtime.seconds() < 1.1){
            telemetry.addData("To Crater", "true", runtime.seconds());
            telemetry.update();
        }

        leftMotor.setPower(speed * 2);
        rightMotor.setPower(speed * 2);
        while(opModeIsActive() && runtime.seconds() < 1.5){
            telemetry.addData("Half Over The Crater", "true", runtime.seconds());
            telemetry.update();
        }

        // stop all movement
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }
}
