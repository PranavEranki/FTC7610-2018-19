package org.firstinspires.ftc.teamcode.botImplementation;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Robotics on 8/29/2018.
 */

/*

10/1/18 - Issue: robot is moving when we are not moving the stick on the gamepad,
most likely a problem with bot class

 */

@TeleOp(name= "Bot - Teleop", group="Bot")
//@Disabled
public class Teleop extends LinearOpMode {
    private Bot robot = new Bot(this);


    public void runOpMode(){
        robot.init(hardwareMap);
        double power = 0;
        waitForStart();
        while(opModeIsActive()) {
            robot.setPower(Range.clip(gamepad1.left_stick_y + power, -0.75 , 0.75), Range.clip(gamepad1.right_stick_y + power, -0.75 , 0.75));

            if(gamepad1.a){
                power += 0.01;
            }

            if (gamepad1.b){
                power -= 0.01;
            }

            if (gamepad1.a && gamepad1.b){
                power = 0;
            }
            telemetry.addData("Left Position", robot.getLeftPosition());
            telemetry.addData("Right Position", robot.getRightPosition());
            telemetry.addData("Gyro", robot.getGyroHeading());
            telemetry.addData("Pwr", power);
            telemetry.addData("right stick", gamepad1.right_stick_y);
            telemetry.update();
        }
    }
}