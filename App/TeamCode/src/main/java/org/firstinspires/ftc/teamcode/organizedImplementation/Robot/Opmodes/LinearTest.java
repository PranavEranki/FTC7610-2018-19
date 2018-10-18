package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Sensors.IMU;
import org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Sensors.OpenCV;
import org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Sensors.RangeSensor;
import org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Sensors.Vuforia;

/*

Note: this code is not working properly, acceleration already implemented in main teleop

 */
@TeleOp(name = "Organized - OpMode", group = "Linear Opmode")
@Disabled
public class LinearTest extends LinearOpMode {


    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    private double leftPower = 0;
    private double rightPower = 0;
    private int leftCoefficient = 1;
    private int rightCoefficient = 1;

    private final double accelerationSpeed = 0.01;
    private final double decelerationSpeed = 0.05;

    private final double driveSpeed = 0.5;
    public void runOpMode() {
        // NORMAL OPMODE STUFF
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotor  = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        /*
        //SENSOR STUFF
        IMU imu = new IMU("imu");
        telemetry.addLine("IMU initialized.");

        Vuforia vuforia = new Vuforia(hardwareMap);
        telemetry.addLine("Vuforia initialized.");

        OpenCV cv = new OpenCV();
        telemetry.addLine("OpenCV initialized.");

        RangeSensor range = new RangeSensor("range");
        telemetry.addLine("Range Sensor initialized.");

        vuforia.activate();
        telemetry.update();
        */
        //ONCE STARTED
        waitForStart();
        telemetry.clear();
        runtime.reset();

        while (opModeIsActive()) {
            //vuforia.getVuMark();
            //telemetry.addData("Name", vuforia.getVuMarkName()); //Working
            //vuforia.findRobot(telemetry);
            //telemetry.addData("X", vuforia.getDistanceX());
            //telemetry.addData("Y", vuforia.getDistanceY());
            //telemetry.addData("Z", vuforia.getDistanceZ());
            //telemetry.addData("VUFORIA Head", vuforia.getHeading());
            //telemetry.addData("IMU head", imu.getHeading());

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();


            // Driving method 1 - goes left or right based on stick, have to hold both up straight to go straight

            if (gamepad1.right_stick_y < 0) {
                if (rightPower < 0) rightPower = limitIncrement(rightPower, decelerationSpeed, 0);
                else                rightPower = limitIncrement(rightPower, accelerationSpeed, driveSpeed);
            } else if (gamepad1.right_stick_y > 0) {
                if (rightPower < 0) rightPower = limitDecrement(rightPower, accelerationSpeed, -driveSpeed);
                else                rightPower = limitDecrement(rightPower, decelerationSpeed, 0);
            } else {
                if (rightPower < 0) rightPower = limitIncrement(rightPower, decelerationSpeed, 0);
                else                rightPower = limitDecrement(rightPower, decelerationSpeed, 0);
            }

            if (gamepad1.left_stick_y < 0) {
                if (leftPower < 0) leftPower = limitIncrement(leftPower, decelerationSpeed, 0);
                else               leftPower = limitIncrement(leftPower, accelerationSpeed, driveSpeed);
            } else if (gamepad1.left_stick_y > 0) {
                if (leftPower < 0) leftPower = limitDecrement(leftPower, accelerationSpeed, -driveSpeed);
                else               leftPower = limitDecrement(leftPower, decelerationSpeed, 0);
            } else {
                if (leftPower < 0) leftPower = limitIncrement(leftPower, decelerationSpeed, 0);
                else               leftPower = limitDecrement(leftPower, decelerationSpeed, 0);
            }



            /*
            leftMotor.setPower(-0.5 * gamepad1.left_stick_y);
            rightMotor.setPower(-0.5 * gamepad1.right_stick_y); */
            leftMotor.setPower(-0.5 * leftPower);
            rightMotor.setPower(-0.5 * rightPower);

        }

    }

    private double limitIncrement(double initial, double inc, double limit) {
        initial += inc;
        if (initial > limit){
            return limit;
        }
        else {
            return initial;
        }
    }

    private double limitDecrement(double initial, double dec, double limit) {
        initial -= dec;
        if (initial < limit) {
            return limit;
        }
        else {
            return initial;
        }
    }

}