package org.firstinspires.ftc.teamcode.botImplementation;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

//Disabled
public class Bot {

    final static int ENCODER_TICKS_PER_REV = 1120;
    final static int WHEEL_DIAMETER = 4; //Inches

    final static double INCHES_PER_TICK = (WHEEL_DIAMETER * Math.PI) / ENCODER_TICKS_PER_REV;
    final static int DRIVE_THRESHOLD = (int) (0.1 / INCHES_PER_TICK);


    int _leftOffset;
    int _rightOffset;

    private final static double HEADING_THRESHOLD = 1; // As tight as we can make it with an integer gyro
    private final static double PITCH_THRESHOLD = 1; // As tight as we can make it with an integer gyro

    private final static double P_TURN_COEFF = 0.0517;   // Larger is more responsive, but also less stable
    private final static double P_DRIVE_COEFF = 0.16;  // Larger is more responsive, but also less stable


    private final static double FLAT_PITCH = -1;    // Pitch when robot is flat on the balance stone
    private final static double BALANCE_PITCH = -8; // Pitch when robot is leaving the balance stone

    private final static double AUTO_DRIVE_SPEED = 0.6;
    private final static double AUTO_TURN_SPEED = 0.6;
    private final static double POWER_DAMPEN = .1;

    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;

    private LinearOpMode opMode = null;

    private HardwareMap hwMap = null;

    private BNO055IMU imu = null;
    private Orientation angles = null;
    private Acceleration gravity = null;

    private ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    public Bot(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap;
        leftFrontDrive = hwMap.get(DcMotor.class, "left_motor");
        rightFrontDrive = hwMap.get(DcMotor.class, "right_motor");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = false;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        setLeftDirection(DcMotor.Direction.REVERSE);
        setBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        _leftOffset = leftFrontDrive.getCurrentPosition();
        _rightOffset = rightFrontDrive.getCurrentPosition();
    }

    //Sets the power of both sides of the bot
    public void setPower(double leftPower, double rightPower) {
        leftFrontDrive.setPower(leftPower);
        rightFrontDrive.setPower(rightPower);
    }

    public void setBehavior(DcMotor.ZeroPowerBehavior behavior){
        leftFrontDrive.setZeroPowerBehavior(behavior);
        rightFrontDrive.setZeroPowerBehavior(behavior);
    }

    public void stopDrive(){
        setPower(0,0);

    }

    public void setLeftDirection(DcMotor.Direction direction) {
        leftFrontDrive.setDirection(direction);
    }

    public void setRightDirection(DcMotor.Direction direction) {
        rightFrontDrive.setDirection(direction);
    }

    public int getRightPosition()
    {
        return rightFrontDrive.getCurrentPosition();
    }

    public int getLeftPosition()
    {
        return leftFrontDrive.getCurrentPosition();
    }


    public void driveStraight(double inches)
    {
        driveStraight(opMode, inches, 1);
    }

    /**
     * Method for driving straight
     *
     * @param inches Inches
     * @param maxSpeed  Should be greater than 0. Sets the maximum possible speed value.
     */
    public void driveStraight(LinearOpMode opmode, double inches, double maxSpeed) {
        double speed;
        int error;
        //sets the target encoder value
        int target = rightFrontDrive.getCurrentPosition() + (int) (inches / INCHES_PER_TICK);

        // While the absolute value of the error is greater than the error threshold
        while (opmode.opModeIsActive() && Math.abs(rightFrontDrive.getCurrentPosition() - target) >= DRIVE_THRESHOLD) {
            error = target - rightFrontDrive.getCurrentPosition();
            speed = Range.clip(error * P_DRIVE_COEFF, -maxSpeed , maxSpeed);

            /*if(getGyroHeading() > 5)
            {
                setPower(speed-POWER_DAMPEN, speed);
            }
            else if(getGyroHeading() < -5)
            {
                setPower(speed, speed-POWER_DAMPEN);
            }
            else
            {
                setPower(speed, speed);
            }*/
            setPower(speed, speed-POWER_DAMPEN);

            opmode.telemetry.addData("Left Position", this.getLeftPosition() * INCHES_PER_TICK);
            opmode.telemetry.addData("Right Position", this.getRightPosition() * INCHES_PER_TICK);
            opMode.telemetry.update();
        }

        this.stopDrive();
    }


    /**
     * Checks if the gyro is calibrating
     *
     * @return isCalibrating
     */
    public boolean isGyroCalibrating() {
        boolean isCalibrating = !imu.isGyroCalibrated();

        return isCalibrating;
    }

    /**
     * Gets the heading of the gyro in degrees
     *
     * @return heading
     */
    public double getGyroHeading() {
        // Update gyro
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        gravity = imu.getGravity();

        double heading = AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.firstAngle));
        return heading;
    }

    public void gyroTurn(double angle)
    {
        gyroTurn(AUTO_TURN_SPEED, angle);
    }

    public void gyroTurn(double speed, double angle)
    {
        while(opMode.opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF))
        {
            opMode.telemetry.update();
        }
    }

    /**
     * Gets the pitch of the gyro in degrees
     *
     * @return pitch
     */
    public double getGyroPitch() {
        // Update gyro
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        gravity = imu.getGravity();

        double pitch = AngleUnit.DEGREES.normalize(AngleUnit.DEGREES.fromUnit(angles.angleUnit, angles.thirdAngle));
        return pitch;
    }


    public void gyroHold ( double angle, double holdTime){
        gyroHold(AUTO_TURN_SPEED, angle, holdTime);
    }


    /**
     * Method to obtain & hold a heading for a finite amount of time
     * Move will stop once the requested time has elapsed or the error has reached a threshold
     *
     * @param speed    Desired speed of turn.
     * @param angle    Absolute Angle (in Degrees) relative to last gyro reset.
     *                 0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *                 If a relative angle is required, add/subtract from current heading.
     * @param holdTime Length of time (in seconds) to hold the specified heading.
     */
    public void gyroHold ( double speed, double angle, double holdTime){

        ElapsedTime holdTimer = new ElapsedTime();

        // keep looping while we have time remaining.
        holdTimer.reset();
        while (opMode.opModeIsActive() && (holdTimer.time() < holdTime) && !onHeading(speed, angle, P_TURN_COEFF)) {
            // Update telemetry & Allow time for other processes to run.

            opMode.telemetry.addData("timer", holdTimer.time());
            opMode.telemetry.update();
        }

        // Stop all motion;
        setPower(0, 0);
    }

    /**
     * Perform one cycle of closed loop heading control.
     *
     * @param speed  Desired speed of turn.
     * @param angle  Absolute Angle (in Degrees) relative to last gyro reset.
     *               0 = fwd. +ve is CCW from fwd. -ve is CW from forward.
     *               If a relative angle is required, add/subtract from current heading.
     * @param PCoeff Proportional Gain coefficient
     * @return onTarget
     */
    boolean onHeading ( double speed, double angle, double PCoeff){
        double error;
        double steer;
        boolean onTarget = false;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        } else {
            steer = getSteer(error, PCoeff);
            leftSpeed = speed * steer;
            rightSpeed = -leftSpeed;
        }

        // Send desired speeds to motors
        setPower(leftSpeed, rightSpeed);

        // Display it for the driver
        opMode.telemetry.addData("Target", "%5.2f", angle);
        opMode.telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        opMode.telemetry.addData("Speed", "%5.2f:%5.2f", leftSpeed, rightSpeed);

        return onTarget;
    }

    /**
     * getError determines the error between the target angle and the robot's current heading
     *
     * @param targetAngle Desired angle (relative to global reference established at last Gyro Reset).
     * @return error angle: Degrees in the range +/- 180. Centered on the robot's frame of reference
     * Positive error means the robot should turn LEFT (CCW) to reduce error.
     */
    public double getError ( double targetAngle){

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - getGyroHeading();
        while (robotError > 180) robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    /**
     * returns desired steering force.  +/- 1 range.  positive = steer left
     *
     * @param error  Error angle in robot relative degrees
     * @param PCoeff Proportional Gain Coefficient
     * @return steer
     */
    public double getSteer ( double error, double PCoeff){
        return Range.clip(error * PCoeff, -1, 1);
    }

    public void resetTimer () {
        time.reset();
    }

    public ElapsedTime getTime () {
        return time;
    }
    public void encoderDrive(double inches, double maxSpeed) {

        double speed;
        int error;
        //sets the target encoder value
        int target = rightFrontDrive.getCurrentPosition() + (int) (inches / INCHES_PER_TICK);

        // While the absolute value of the error is greater than the error threshold
        while (opMode.opModeIsActive() && Math.abs(rightFrontDrive.getCurrentPosition() - target) >= DRIVE_THRESHOLD) {
            error = target - rightFrontDrive.getCurrentPosition();
            speed = Range.clip(error * P_DRIVE_COEFF, -maxSpeed , maxSpeed);

            setPower(speed, speed);
            opMode.telemetry.addData("speed: ", speed);
            opMode.telemetry.update();
        }
        stopDrive();
    }

}