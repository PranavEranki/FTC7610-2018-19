package org.firstinspires.ftc.teamcode.organizedImplementation.Robot.Opmodes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/*

10/3/18 - Unable to initialize

10/4/18 - Fixed the issue

 */
@Autonomous(name="Organized - Autonomous w/ Encoders", group ="red")

public class AutoMain_Test extends LinearOpMode {

    public DcMotor leftMotor;
    public DcMotor rightMotor;

    public ElapsedTime runtime = new ElapsedTime();
    int i;
    int ralph; // distance variable for vuforia
    private double waitTime;
    private int gameState;
    BNO055IMU imu;
    public Orientation angles;

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() {
        gameState = 0;
        waitTime = 0;

        leftMotor = hardwareMap.dcMotor.get("left_motor");
        rightMotor = hardwareMap.dcMotor.get("right_motor");

        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        time = getRuntime();

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                leftMotor.getCurrentPosition(),
                rightMotor.getCurrentPosition());

        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        //driveForward(0.3, 1000*1440);
        encoderDrive(0.3, 60, 60, 5);
        telemetry.addData("Forward", "Done");
        telemetry.update();
        //driveBackward(0.3, 1000*1440);
        telemetry.addData("Backward", "Done");
        telemetry.update();
        //driveToDepot();
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            leftMotor.setTargetPosition(newLeftTarget);
            rightMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftMotor.setPower(Math.abs(speed));
            rightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftMotor.isBusy() && rightMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        leftMotor.getCurrentPosition(),
                        rightMotor.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            leftMotor.setPower(0);
            rightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }


//    public void driveToDepot(){
//        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way
//
//        // Step 1:  Drive forward for 3 seconds
//        motorFrontLeft.setPower(.5);
//        motorFrontRight.setPower(.5);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 1)) {
//            telemetry.addData("Path", "Forward", runtime.seconds());
//            telemetry.update();
//        }
//        motorFrontRight.setPower(0);
//        motorFrontLeft.setPower(0.0);
//
//        motorFrontRight.setPower(-.25);
//        motorFrontLeft.setPower(-.25);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 1)) {
//            telemetry.addData("Path", "Backward", runtime.seconds());
//            telemetry.update();
//        }
//        motorFrontRight.setPower(0);
//        motorFrontLeft.setPower(0);
//        driveForward(0.5, 30*1440);
//        motorFrontRight.setPower(.5);
//        motorFrontLeft.setPower(-.5);
//        runtime.reset();
//        /*
//        while (opModeIsActive() && (runtime.seconds() < 1.5)) {
//            telemetry.addData("Path", "Turn", runtime.seconds());
//            telemetry.update();
//        }
//        motorFrontRight.setPower(0);[0
//        motorFrontLeft.setPower(0.0);
//
//        motorFrontRight.setPower(.75);
//        motorFrontLeft.setPower(.75);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 5.0)) {
//            telemetry.addData("Path", "Forward", runtime.seconds());
//            telemetry.update();
//        }
//        motorFrontRight.setPower(0);
//        motorFrontLeft.setPower(0);
//
//        telemetry.addData("Path", "Complete");
//        telemetry.update();
//        sleep(1000); */
//    }
//
//    public void driveForward(double power, int distance) {
//
//
//        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//
//        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        motorFrontLeft.setPower(0);
//        motorFrontRight.setPower(0);
//
//        motorFrontRight.setTargetPosition(distance);
//        motorFrontLeft.setTargetPosition(distance);
//
//        sleep(500);
//    }
//
//    public void driveBackward(double power, int distance) {
//
//        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//
//        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        motorFrontLeft.setPower(0);
//        motorFrontRight.setPower(0);
//
//        motorFrontRight.setTargetPosition(-distance);
//        motorFrontLeft.setTargetPosition(-distance);
//
//        sleep(500);
//    }
//
//    public void pointTurnRight(double power, int distance) {
//
//
//        motorFrontRight.setTargetPosition(-distance);
//        motorFrontLeft.setTargetPosition(distance);
//
//
//        motorFrontLeft.setPower(power);
//        motorFrontRight.setPower(-power);
//
//        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        motorFrontLeft.setPower(0);
//        motorFrontRight.setPower(0);
//
//        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//
//        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        sleep(5000);
//    }
//
//    public void pointTurnLeft(double power, int distance) {
//
//        motorFrontRight.setTargetPosition(distance);
//        motorFrontLeft.setTargetPosition(distance);
//
//        motorFrontLeft.setPower(-power);
//        motorFrontRight.setPower(power);
//
//        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        motorFrontLeft.setPower(0);
//        motorFrontRight.setPower(0);
//
//        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        sleep(500);
//        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        sleep(500);
//    }
//
//
//    public void strafeLeft(double power, int distance) {
//
//        motorFrontRight.setTargetPosition(distance);
//        motorFrontLeft.setTargetPosition(distance);
//
//        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        motorFrontLeft.setPower(-power);
//        motorFrontRight.setPower(power);
//
//        motorFrontLeft.setPower(0);
//        motorFrontRight.setPower(0);
//
//        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        sleep(500);
//        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        sleep(500);
//    }
//
//
//    public void strafeRight(double power, int distance) {
//        motorFrontRight.setTargetPosition(distance);
//        motorFrontLeft.setTargetPosition(-distance);
//
//        motorFrontLeft.setPower(power);
//        motorFrontRight.setPower(-power);
//
//        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        motorFrontLeft.setPower(0);
//        motorFrontRight.setPower(0);
//
//        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        sleep(500);
//        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        sleep(500);
//    }


}

