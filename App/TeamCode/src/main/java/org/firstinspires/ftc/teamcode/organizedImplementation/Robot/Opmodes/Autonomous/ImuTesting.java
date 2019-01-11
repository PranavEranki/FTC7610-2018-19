package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes.Autonomous;
import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.util.Range;
//import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
//import org.opencv.core.Mat;

/*import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;*/


//declaring that it is an autonomous opmode
@Autonomous(name="IMU Testing")
@Disabled
//don't show "variable unused" or "access could be different" warnings: not always needed
@SuppressWarnings({"unused", "WeakerAccess"})

public class ImuTesting extends OpMode {

    //declaring imu and motor variables
    BNO055IMU imu;
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor latchMech;
    double TOLERANCE = 3;


    //various motor and drive related variables and numbers needed for drive calculations

    //"ticks" per motor turn
    private static final double COUNTS_PER_MOTOR_REV = 56;

    //drive gear ratio
    private static final double DRIVE_GEAR_REDUCTION = 40.0;

    //total ticks
    private static final double TOTAL_TICKS = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;

    //core hex total ticks
    private static final double TOTAL_TICKS_HEX = 288 * 30;

    //wheel diameter
    private static final double WHEEL_DIAMETER_INCHES = 4.0 ;

    //"ticks" per inch
    private static final double COUNTS_PER_INCH = TOTAL_TICKS / (WHEEL_DIAMETER_INCHES * Math.PI);

    //"ticks" per 360 degrees
    private static final double COUNTS_PER_360 = COUNTS_PER_INCH * (14.5 * Math.PI);

    //"ticks" per degree
    private static final double COUNTS_PER_DEGREE = COUNTS_PER_360 / 360;


    @Override
    public void init() {


        telemetry.addData("Status", "CV");

        //getting motor, imu, and color sensor hardware
        leftDrive  = hardwareMap.get(DcMotor.class, "left_motor");
        rightDrive = hardwareMap.get(DcMotor.class, "right_motor");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //setting up gyro axes
        imu.getAngularOrientation().toAxesOrder(AxesOrder.ZYX);
        imu.getAngularOrientation().toAxesReference(AxesReference.EXTRINSIC);
        imu.getAngularOrientation().firstAngle = 0;

        //reversing right motor drive direction (mounted reverse on bot)
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        //resetting encoder and preparing for run`
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //confirming heading is 0
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                leftDrive.getCurrentPosition(),
                rightDrive.getCurrentPosition());
        telemetry.update();


        //letting driver know robot is ready and initialized
        telemetry.addData(">", "Robot Ready.");
        telemetry.update();
    }



    @Override
    public void start(){

        imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle = 0;

        if(imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle != 0){
            reset:
            while(imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle != 0){
                leftDrive.setPower(0.3);
                rightDrive.setPower(-0.3);
                if(Math.abs(imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle) <= 2){
                    break reset;
                }
            }

        }



        //drive backward and park in crater
        driveStraight(-0.6, 76);

    }


    @Override
    public void loop() {

    }


    @Override
    public void stop() {
    }

    private void turn(double speed, double angle) {

        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //setting overall target angle to current angle + how much robot needs to turn
        double targetAngle = angle + imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;

        telemetry.addData("target", targetAngle);
        telemetry.update();



        //if turning counterclockwise (left)
        if (targetAngle > 0) {

            //set motor power to specified speed for 100 ms
            leftDrive.setPower(speed);
            rightDrive.setPower(-speed);

            turn1:
            while (Math.abs(imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - targetAngle) > TOLERANCE) {

                leftDrive.setPower(speed);
                rightDrive.setPower(-speed);

                if(Math.abs(imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - targetAngle) < TOLERANCE){
                    break turn1;
                }
            }

            leftDrive.setPower(0.0);
            rightDrive.setPower(0.0);


        }

        //same logic, except in the case the robot turns clockwise (right): flip statements from first case
        else {
            leftDrive.setPower(-speed);
            rightDrive.setPower(speed);

            turn2:
            while (Math.abs(imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - targetAngle) > TOLERANCE) {

                leftDrive.setPower(-speed);
                rightDrive.setPower(speed);

                if(Math.abs(imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle - targetAngle) < TOLERANCE){
                    break turn2;
                }
            }

            leftDrive.setPower(0.0);
            rightDrive.setPower(0.0);

        }
    }




    //drive function - for driving in a straight line - takes in speed and distance needed to travel
    private void driveStraight(double speed, double distance){

        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //converting distance from inches to "ticks"
        int newDistance = (int)(Math.ceil(COUNTS_PER_INCH * distance));



        //tell encoders distance to run to
        leftDrive.setTargetPosition(newDistance);
        rightDrive.setTargetPosition(newDistance);

        //setting speed of motor as per specified input
        leftDrive.setPower(speed);
        rightDrive.setPower(speed);
    }

}