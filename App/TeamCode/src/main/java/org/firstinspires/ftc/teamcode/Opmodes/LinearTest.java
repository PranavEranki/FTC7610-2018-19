package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Sensors.IMU;
import org.firstinspires.ftc.teamcode.Sensors.OpenCV;
import org.firstinspires.ftc.teamcode.Sensors.RangeSensor;
import org.firstinspires.ftc.teamcode.Sensors.Vuforia;
import org.opencv.core.Mat;

@TeleOp(name = "Linear Test", group = "Linear Opmode")
public class LinearTest extends LinearOpMode {
    @Override
    public void runOpMode() {
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
        waitForStart();
        telemetry.clear();

        //Testing Methods - 1
        while (opModeIsActive()) {
            vuforia.getVuMark();
            telemetry.addData("Name", vuforia.getVuMarkName()); //Working
            vuforia.findRobot(telemetry);
            telemetry.addData("X", vuforia.getDistanceX());
            telemetry.addData("Y", vuforia.getDistanceY());
            telemetry.addData("Z", vuforia.getDistanceZ());
            telemetry.addData("VUFORIA Head", vuforia.getHeading());
            telemetry.addData("IMU head", imu.getHeading());
            telemetry.update();

        }
    }


}