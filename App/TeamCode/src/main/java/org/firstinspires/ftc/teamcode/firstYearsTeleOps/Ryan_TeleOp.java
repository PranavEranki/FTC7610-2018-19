package org.firstinspires.ftc.teamcode.firstYearsTeleOps;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name = "Ryan TeleOp", group = "TeleOp")

public class Ryan_TeleOp extends LinearOpMode {

    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode(){
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        rightMotor = hardwareMap.dcMotor.get("rightMotor");

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        /*
        leftMotor.setPower(gamepad1.left_stick_y);
        rightMotor.setPower(gamepad1.right_stick_y);
        */

        //this is hard code, switch to other method that is not hard code
        while(opModeIsActive()){
            if(gamepad1.left_stick_y < 0){
                leftMotor.setPower(-1);
                //rightMotor.setPower(-1);
            }else if (gamepad1.left_stick_y > 0){
                leftMotor.setPower(1);
                //rightMotor.setPower(1);
            }else{
                leftMotor.setPower(0); //idle at 0
            }
            if(gamepad1.right_stick_y < 0){
                //leftMotor.setPower(-1);
                rightMotor.setPower(-1);
            }else if (gamepad1.right_stick_y > 0){
                //leftMotor.setPower(1);
                rightMotor.setPower(1);
            }else{
                rightMotor.setPower(0); //idle at 0
            }
        }
        //what's next
    }
}
