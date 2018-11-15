package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.Autonomous;

/**
 * Ryan's fun code
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Fake Gyro", group =  "Autonomous")
//@Disabled
public class FakeGyro extends LinearOpMode {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private DcMotor turningMotor;
    private DcMotor extendingMotor;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode(){
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");
        turningMotor = hardwareMap.dcMotor.get("turning_motor");
        extendingMotor = hardwareMap.dcMotor.get("extending_motor");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        telemetry.addData("plz", "true");
        telemetry.update();

        waitForStart();

        if(opModeIsActive()){
            //at least goes to the correct position
            //i know, its not gyro , i tried ;(
            gyro(0.75, 0.75, 875);
            gyro(-0.25, 0.25, 775);
            gyro(0.8, 0.8, 850);
            gyro(1, 1, 150);
        }
    }

    public void gyro(double left, double right, int time){
        leftMotor.setPower(left);
        rightMotor.setPower(right);
        sleep(time);
    }

    public void teamMarker(double power, int time){

    }
}
