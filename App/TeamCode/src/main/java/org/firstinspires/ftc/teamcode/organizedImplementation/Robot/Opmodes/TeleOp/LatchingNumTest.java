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

@TeleOp(name = "LatchSpeedTest", group = "TeleOp")
//@Disabled
public class LatchingNumTest extends LinearOpMode {

    private DcMotor latchMotor;
    private ElapsedTime runtime = new ElapsedTime();

    private final double maxSpeed = 1;
    private final double minSpeed = -1;
    private final double stop = 0;
    private double speed = 0;
    private double inc = 0.00001;

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
            if(gamepad2.a){
                speed += inc;
            }
            if(gamepad2.b){
                speed -= inc;
            }
            if(gamepad2.right_bumper) {
                latchMotor.setPower(speed);
            }
            telemetry.addData("Speed: " , speed);
            telemetry.update();
        }
        //test out first
    }
}