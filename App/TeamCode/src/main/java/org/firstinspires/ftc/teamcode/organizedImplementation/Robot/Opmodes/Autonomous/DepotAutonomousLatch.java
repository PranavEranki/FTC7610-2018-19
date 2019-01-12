package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;

@Autonomous (name = "Depot Autonomous w/ Latch", group = "Autonomous")
//@Disabled
public class DepotAutonomousLatch extends LinearOpMode {




    // latching variables
    private Servo servo_motor_lift;
    private ServoControllerEx latchMotor;
    private int servo_port_lift;
    private PwmControl.PwmRange theRange_lift;
    private final double upValue = 0.5;





    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private final double leftSpeed = 1.0;
    private final double rightSpeed = 0.5;

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode(){
        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");






        // latching motor setup
        servo_motor_lift = hardwareMap.get(Servo.class, "latch_motor");
        // Set the rotation servo for extended PWM range
        if (servo_motor_lift.getController() instanceof ServoControllerEx) {
            // Confirm its an extended range servo controller before we try to set to avoid crash
            latchMotor = (ServoControllerEx) servo_motor_lift.getController();
            servo_port_lift = servo_motor_lift.getPortNumber();
            telemetry.addData("ServoPort#", servo_port_lift);
            telemetry.update();
            theRange_lift = new PwmControl.PwmRange(500, 2500);
            latchMotor.setServoPwmRange(servo_port_lift, theRange_lift);
        }
        latchMotor.setServoPwmEnable(servo_port_lift);





        waitForStart();

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        runtime.reset();





        while(opModeIsActive() && runtime.seconds() < 15){
            telemetry.addData("Waiting for space", "true", runtime.seconds());
            telemetry.update();
        }
        runtime.reset();



        // start
        set_latch_motor(-0.5);
        while(opModeIsActive() && runtime.seconds() < 8.0){
            telemetry.addData("Unlatching...", "true", runtime.seconds());
            telemetry.update();
        }
        set_latch_motor(0);

        runtime.reset();

        /*

        rightMotor.setPower(rightSpeed);
        leftMotor.setPower(leftSpeed);
        while(opModeIsActive() && runtime.milliseconds() < 200){
            telemetry.addData("forward unlatching movement", "true", runtime.milliseconds());
            telemetry.update();
        }
        rightMotor.setPower(0);
        leftMotor.setPower(0);
        sleep(500);

        runtime.reset();

        rightMotor.setPower(-rightSpeed);
        leftMotor.setPower(leftSpeed);
        while(opModeIsActive() && runtime.milliseconds() < 500){
            telemetry.addData("turning to depot", "true", runtime.milliseconds());
            telemetry.update();
        }
        rightMotor.setPower(0);
        leftMotor.setPower(0);

        sleep(500);

        runtime.reset();

        rightMotor.setPower(-rightSpeed * 0.5);
        leftMotor.setPower(leftSpeed * 0.5);
        while(opModeIsActive() && runtime.milliseconds() < 100){
            telemetry.addData("spaz up", "true", runtime.milliseconds());
            telemetry.update();
        }
        rightMotor.setPower(0);
        leftMotor.setPower(0);

        sleep(250);

        runtime.reset();

        // go straight from start to depot
        leftMotor.setPower(leftSpeed);
        rightMotor.setPower(rightSpeed);
        //full speed ahead
        //go go go go go
        while(opModeIsActive() && runtime.seconds() < 1.5){
            telemetry.addData("To Depot", "true", runtime.seconds());
            telemetry.update();
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);


        // go straight from depot to lander
        leftMotor.setPower(-leftSpeed);
        rightMotor.setPower(-rightSpeed);
        //full speed back
        //go go go go go
        while(opModeIsActive() && runtime.seconds() < 1.5){
            telemetry.addData("Back To Lander", "true", runtime.seconds());
            telemetry.update();
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        */


        telemetry.addData("Autonomous Finished", "true", runtime.seconds());
        telemetry.update();
    }

    public void set_latch_motor(double speed){
        speed = Range.clip(speed, -1.0, 1.0) ;
        latchMotor.setServoPosition(servo_port_lift,(speed+1)/2);
    }
}
