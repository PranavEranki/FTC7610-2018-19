package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous (name = "Team Marker Autonomous", group = "Autonomous")
//@Disabled
public class TeamMarkerAutonomous extends LinearOpMode {
    private Servo push_servo;
    private double left = 0.0;
    private double right = 1.0;
    private double stop = 0.5;
    private ElapsedTime runtime = new ElapsedTime();
    public void runOpMode(){
        push_servo = hardwareMap.servo.get("push_servo");

        waitForStart();
        telemetry.addData("hardware map finished","true");
        telemetry.update();
        runtime.reset();
        push_servo.setPosition(1);
        while(opModeIsActive() && runtime.milliseconds() < 500){
            telemetry.addData("push out","true");
            telemetry.update();
        }
        push_servo.setPosition(0.5);
        runtime.reset();

        push_servo.setPosition(0);
        while(opModeIsActive() && runtime.milliseconds() < 100){
            telemetry.addData("push out","true");
            telemetry.update();
        }
        push_servo.setPosition(0.5);
        runtime.reset();

        push_servo.setPosition(1);
        while(opModeIsActive() && runtime.milliseconds() < 100){
            telemetry.addData("push out","true");
            telemetry.update();
        }
        push_servo.setPosition(0.5);
        runtime.reset();

        push_servo.setPosition(0);
        while(opModeIsActive() && runtime.milliseconds() < 100){
            telemetry.addData("push out","true");
            telemetry.update();
        }
        push_servo.setPosition(0.5);
        runtime.reset();

        push_servo.setPosition(1);
        while(opModeIsActive() && runtime.milliseconds() < 100){
            telemetry.addData("push out","true");
            telemetry.update();
        }
        push_servo.setPosition(0.5);
        runtime.reset();

        push_servo.setPosition(0);
        while(opModeIsActive() && runtime.milliseconds() < 100){
            telemetry.addData("push out","true");
            telemetry.update();
        }
        push_servo.setPosition(0.5);
        runtime.reset();

        push_servo.setPosition(1);
        while(opModeIsActive() && runtime.milliseconds() < 100){
            telemetry.addData("push out","true");
            telemetry.update();
        }
        push_servo.setPosition(0.5);
        runtime.reset();

        push_servo.setPosition(0);
        while(opModeIsActive() && runtime.milliseconds() < 100){
            telemetry.addData("push out","true");
            telemetry.update();
        }
        push_servo.setPosition(0.5);
        runtime.reset();

        push_servo.setPosition(1);
        while(opModeIsActive() && runtime.milliseconds() < 100){
            telemetry.addData("push out","true");
            telemetry.update();
        }
        push_servo.setPosition(0.5);
        runtime.reset();

        push_servo.setPosition(0);
        while(opModeIsActive() && runtime.milliseconds() < 600){
            telemetry.addData("push out","true");
            telemetry.update();
        }
        push_servo.setPosition(0.5);
        runtime.reset();
        telemetry.addData("done","true");
        telemetry.update();


    }
}
