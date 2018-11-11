package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "DepotAutonomous", group = "Autonomous")
//@Disabled
public class DepotAutonomous extends LinearOpMode{

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private DcMotor turningMotor;

    private double speed = 0.5;
    private double error = 0.0001;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode(){
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        turningMotor = hardwareMap.dcMotor.get("mech");

        waitForStart();

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        runtime.reset();

        leftMotor.setPower(speed + error);
        rightMotor.setPower(speed);
        while(opModeIsActive() && runtime.seconds() < 0.75){
            telemetry.addData("To Depot", "true", runtime.seconds());
            telemetry.update();
        }
        turningMotor.setPower(1);
        //or whatever servo or motor we'll use to drop the marker
        while(opModeIsActive() && runtime.seconds() < 2.0){
            telemetry.addData("Drop the Team Marker", "true", runtime.seconds());
            telemetry.update();
        }
        //do we have to turn the motor back?
        /*
        turningMotor.setPower(-1);
        while(opModeIsActive() && runtime.seconds() < 2.0){
            telemetry.addData("Put the motor back at its original position", "true", runtime.seconds());
            telemetry.update();
        }
         */
        leftMotor.setPower(-speed - error);
        rightMotor.setPower(speed);
        while(opModeIsActive() && runtime.seconds() < 0.2){
            telemetry.addData("Turn Towards To Crater", "true", runtime.seconds());
            telemetry.update();
        }
        leftMotor.setPower(speed + error);
        rightMotor.setPower(speed);
        while(opModeIsActive() && runtime.seconds() < 1.5){
            telemetry.addData("To Crater", "true", runtime.seconds());
            telemetry.update();
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        //stop
    }
}
