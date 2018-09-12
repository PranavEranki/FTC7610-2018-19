package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.ViewParent;

public class Chassis_SimpleLinearOpMode_Test extends OpMode {

    DcMotor rightMotor; // Right side wheels
    DcMotor leftMotor; // Left Side Wheels
    private ElapsedTime runtime = new ElapsedTime();
   @Override
    public void init() {
        // HERE WE ACCESS ALL FOUR MOTORS ON THE CHASSIS, ASSUMING WE HAVE 4 MOTORS FOR 4 WHEELS
        rightMotor = hardwareMap.dcMotor.get("right_motor");
        leftMotor = hardwareMap.dcMotor.get("left_motor");


        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        //reverses right side, so collector is front


    }

    @Override
    public void loop() {
        // Gamepad 1


        //start test

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        if(gamepad1.dpad_up) {
            rightMotor.setPower(1);
            leftMotor.setPower(1);
        } else if(gamepad1.dpad_down) {
            rightMotor.setPower(-0.5);
            leftMotor.setPower(-0.5);
        } else if(gamepad1.dpad_left) {
            leftMotor.setPower(-1);
            rightMotor.setPower(1);
        } else if(gamepad1.dpad_right) {
            leftMotor.setPower(1);
            rightMotor.setPower(-1);
        } else {
            leftMotor.setPower(0);
            rightMotor.setPower(0);
        }
   }


    @Override
    public void stop() {
        // IMPLEMENT STOP FUNCTION LATER
    }
}