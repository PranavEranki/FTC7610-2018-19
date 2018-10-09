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

    public DcMotor motorFrontLeft;
    public DcMotor motorFrontRight;

    public ElapsedTime runtime = new ElapsedTime();
    int i;
    int ralph; // distance variable for vuforia
    private double waitTime;
    private int gameState;
    BNO055IMU imu;
    public Orientation angles;

    @Override
    public void runOpMode() {
        gameState = 0;
        waitTime = 0;

        motorFrontLeft = hardwareMap.dcMotor.get("left_motor");
        motorFrontRight = hardwareMap.dcMotor.get("right_motor");

        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        time = getRuntime();

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                motorFrontLeft.getCurrentPosition(),
                motorFrontRight.getCurrentPosition());

        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        while (opModeIsActive()) {

            //MOVE
            // Send telemetry message to signify robot waiting;
            telemetry.addData("Status", "Ready to run");    //
            telemetry.update();

            driveForward(1, 1000*1440);
            telemetry.addData("Forward", "Done");
            telemetry.update();
            driveBackward(1, 1000*1440);
            telemetry.addData("Backward", "Done");
            telemetry.update();
            //driveToDepot();
        }
    }

    public void driveToDepot(){
        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1:  Drive forward for 3 seconds
        motorFrontLeft.setPower(.5);
        motorFrontRight.setPower(.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1)) {
            telemetry.addData("Path", "Forward", runtime.seconds());
            telemetry.update();
        }
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0.0);

        motorFrontRight.setPower(-.25);
        motorFrontLeft.setPower(-.25);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1)) {
            telemetry.addData("Path", "Backward", runtime.seconds());
            telemetry.update();
        }
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        driveForward(0.5, 30*1440);
        motorFrontRight.setPower(.5);
        motorFrontLeft.setPower(-.5);
        runtime.reset();
        /*
        while (opModeIsActive() && (runtime.seconds() < 1.5)) {
            telemetry.addData("Path", "Turn", runtime.seconds());
            telemetry.update();
        }
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0.0);

        motorFrontRight.setPower(.75);
        motorFrontLeft.setPower(.75);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 5.0)) {
            telemetry.addData("Path", "Forward", runtime.seconds());
            telemetry.update();
        }
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000); */
    }

    public void driveForward(double power, int distance) {

        motorFrontRight.setTargetPosition(distance);
        motorFrontLeft.setTargetPosition(distance);


        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(500);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(500);
    }

    public void driveBackward(double power, int distance) {

        motorFrontRight.setTargetPosition(-distance);
        motorFrontLeft.setTargetPosition(-distance);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(-power*.65);
        motorFrontRight.setPower(-power *1.35);

        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(500);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(500);
    }

    public void pointTurnRight(double power, int distance) {


        motorFrontRight.setTargetPosition(-distance);
        motorFrontLeft.setTargetPosition(distance);


        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(-power);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(500);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(500);
    }

    public void pointTurnLeft(double power, int distance) {

        motorFrontRight.setTargetPosition(distance);
        motorFrontLeft.setTargetPosition(distance);

        motorFrontLeft.setPower(-power);
        motorFrontRight.setPower(power);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(500);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(500);
    }


    public void strafeLeft(double power, int distance) {

        motorFrontRight.setTargetPosition(distance);
        motorFrontLeft.setTargetPosition(distance);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(-power);
        motorFrontRight.setPower(power);

        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(500);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(500);
    }


    public void strafeRight(double power, int distance) {
        motorFrontRight.setTargetPosition(distance);
        motorFrontLeft.setTargetPosition(-distance);

        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(-power);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(500);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(500);
    }

}

