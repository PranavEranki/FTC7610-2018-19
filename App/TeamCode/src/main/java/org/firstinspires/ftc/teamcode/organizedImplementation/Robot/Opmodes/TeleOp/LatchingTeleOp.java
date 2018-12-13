package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Latch", group = "TeleOp")
//@Disabled
public class LatchingTeleOp extends LinearOpMode {

    private DcMotor latchMotor;
    private ElapsedTime runtime = new ElapsedTime();

    private final double maxSpeed = 1;
    private final double minSpeed = -1;
    private double stop = 0;

    @Override
    public void runOpMode(){
        latchMotor = hardwareMap.dcMotor.get("latch_motor");
        latchMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        latchMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Init", "true");
        telemetry.update();

        latchMotor.setPower(stop);

        waitForStart();
        runtime.reset();

        while(opModeIsActive()){
            if(gamepad2.right_bumper){
                latchMotor.setPower(maxSpeed * 0.8);
            }else if (gamepad2.right_trigger != 0){
                latchMotor.setPower(minSpeed * 0.8);
            }
        }
        //test out
    }
}