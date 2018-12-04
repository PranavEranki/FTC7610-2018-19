package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Depot Autonomous", group = "Autonomous")
//@Disabled
public class DepotAutonomous extends LinearOpMode{

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private DcMotor turningMotor;
    private DcMotor extendingMotor;

    private Servo leftServo;
    private Servo rightServo;

    private final double leftSpeed = 1.0;
    private final double rightSpeed = 0.5;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode(){
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        turningMotor = hardwareMap.dcMotor.get("turning_motor");
        extendingMotor = hardwareMap.dcMotor.get("extending_motor");

        leftServo = hardwareMap.servo.get("left_servo");
        rightServo = hardwareMap.servo.get("right_servo");
        waitForStart();

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        runtime.reset();

        // go straight from start to depot
        leftMotor.setPower(leftSpeed);
        rightMotor.setPower(rightSpeed);
        while(opModeIsActive() && runtime.seconds() < 0.85){
            telemetry.addData("To Depot", "true", runtime.seconds());
            telemetry.update();
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);




        runtime.reset();
        // bring out marker and put down
        // still need to code for motor for linear slide
        turningMotor.setPower(1);
        extendingMotor.setPower(1);
        while(opModeIsActive() && runtime.seconds() < 3.0){
            telemetry.addData("Move Motor", "true", runtime.seconds());
            telemetry.update();
        }
        turningMotor.setPower(0);
        extendingMotor.setPower(0);

        leftServo.setPosition(0);
        rightServo.setPosition(1);
        telemetry.addData("Dropped", "true", runtime.seconds());
        telemetry.update();

        runtime.reset();
        turningMotor.setPower(-1);
        extendingMotor.setPower(-1);
        while(opModeIsActive() && runtime.seconds() < 3.0){
            telemetry.addData("Put the motor back at its original position", "true", runtime.seconds());
            telemetry.update();
        }
        turningMotor.setPower(0);
        extendingMotor.setPower(0);



        runtime.reset();
        // turn from current position to crater
        leftMotor.setPower(-leftSpeed);
        rightMotor.setPower(rightSpeed);
        while(opModeIsActive() && runtime.seconds() < 0.8){
            telemetry.addData("Turn Left Towards Crater", "true", runtime.seconds());
            telemetry.update();
        }

        // move to crater
        leftMotor.setPower(leftSpeed);
        rightMotor.setPower(rightSpeed);
        while(opModeIsActive() && runtime.seconds() < 2.0){
            telemetry.addData("To Crater", "true", runtime.seconds());
            telemetry.update();
        }

        // stop all movement
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        telemetry.addData("Autonomous Finished", "true", runtime.seconds());
        telemetry.update();
    }
}
