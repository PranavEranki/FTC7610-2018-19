package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static com.sun.tools.javac.util.Constants.format;

/**
 * PLEASE WORK PLEASE I BEG PLSSS
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "VuforiaTestForImageNumber ", group = "concept")
@com.qualcomm.robotcore.eventloop.opmode.Disabled
public class VuforiaImageFinder extends LinearOpMode {
    VuforiaLocalizer vuforia; //an image-processing library that allows us to analyze pictures
    RelicRecoveryVuMark vuMark;

    @Override
    public void runOpMode() throws InterruptedException {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "INSERT KEY HERE YEET";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; //CHANGE DEPENDING ON THE CAMERA DETECTION
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables roverTrackables = this.vuforia.loadTrackablesFromAsset("ROVERVUMARKASSET INSERT");
        VuforiaTrackable roverTemplate = roverTrackables.get(0);
        roverTemplate.setName("roverVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        roverTrackables.activate();

        telemetry.addData(">", roverTrackables.size());
        telemetry.update();

        while (opModeIsActive()) {
            roverTemplate = roverTrackables.get(0);

            vuMark = RelicRecoveryVuMark.from(roverTemplate);

            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", vuMark);

                break;

            } else {
                telemetry.addData("VuMark", "not visible");
            }
        }

    }
}