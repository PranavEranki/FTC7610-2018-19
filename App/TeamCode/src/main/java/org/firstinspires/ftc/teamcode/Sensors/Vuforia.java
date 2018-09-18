package org.firstinspires.ftc.teamcode.Sensors;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

/**
 * This 2018-2019 OpMode illustrates the basics of using the Vuforia localizer to determine
 * positioning and orientation of robot on the FTC field.
 * The code is structured as a LinearOpMode
 * <p>
 * Vuforia uses the phone's camera to inspect it's surroundings, and attempt to locate target images.
 * <p>
 * When images are located, Vuforia is able to determine the position and orientation of the
 * image relative to the camera.  This sample code than combines that information with a
 * knowledge of where the target images are on the field, to determine the location of the camera.
 * <p>
 * This example assumes a "square" field configuration where the red and blue alliance stations
 * are on opposite walls of each other.
 * <p>
 * From the Audience perspective, the Red Alliance station is on the right and the
 * Blue Alliance Station is on the left.
 * <p>
 * The four vision targets are located in the center of each of the perimeter walls with
 * the images facing inwards towards the robots:
 * - BlueRover is the Mars Rover image target on the wall closest to the blue alliance
 * - RedFootprint is the Lunar Footprint target on the wall closest to the red alliance
 * - FrontCraters is the Lunar Craters image target on the wall closest to the audience
 * - BackSpace is the Deep Space image target on the wall farthest from the audience
 * <p>
 * A final calculation then uses the location of the camera on the robot to determine the
 * robot's location and orientation on the field.
 */

//Get Methods need to be fixed. getVuMarkName() works.

public class Vuforia {
    private static final String VUFORIA_KEY = "AZN6LX7/////AAABmbg6nR27kkt3k51B4sS0SN1X0YTVkeE2krX3iLv5vh13mmWhegXY0TBkNA2mwtchs8g317OarcIF98ujECp35m/e3tAfohaTv9biiKvrcw3z+cb1RSatzL2l57sOU/dyvQX+waQ8TJ6uWiaO67P2zAOa5KCI2jsgmyILciFeC8wUqKUprOgk6F6rucdf/B+dEt4C2ZEycufoPz2XEQrHlhpPfmBymFNu93Kja2qrisBazRc8QwP2ZMwSLvoibe3b6ss06rh81AiIYIulJEkWhenKxdQh7nNUH+RQ3jvFRoJBASXEyhzGKItWaAEDOABm9zfis4sx+eNijNfChh8mdVUKSvztrjNUBcRU8On0z8kJ";

    //Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    private static final float mmPerInch = 25.4f;
    private static final float mmFTCFieldWidth = (12 * 6) * mmPerInch;       // the width of the FTC field (from the center point to the outer panels)
    private static final float mmTargetHeight = (6) * mmPerInch;          // the height of the center of the target image above the floor

    //Variable we use to store our instance of the Vuforia localization engine.
    private VuforiaLocalizer vuforia;
    private VuforiaLocalizer.Parameters parameters;

    //Our trackables, and trackable objects.
    private VuforiaTrackables targetsRoverRuckus;
    private VuforiaTrackable blueRover;
    private VuforiaTrackable redFootprint;
    private VuforiaTrackable frontCraters;
    private VuforiaTrackable backSpace;
    private List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();

    private VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;

    private OpenGLMatrix lastLocation = null;
    private boolean targetVisible = false;

    private float[] trans;
    private Orientation rot;

    //Initializes our vuforia object without a camera monitor.
    public Vuforia() {
        parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CAMERA_CHOICE;
        parameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        initTrack();
    }

    public Vuforia(HardwareMap hardwareMap) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CAMERA_CHOICE;
        parameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        initTrack();
    }

    public void initTrack() {
        targetsRoverRuckus = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        // Load the data sets that for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        blueRover = targetsRoverRuckus.get(0);
        blueRover.setName("Blue-Rover");
        redFootprint = targetsRoverRuckus.get(1);
        redFootprint.setName("Red-Footprint");
        frontCraters = targetsRoverRuckus.get(2);
        frontCraters.setName("Front-Craters");
        backSpace = targetsRoverRuckus.get(3);
        backSpace.setName("Back-Space");

        navMatrix();
        phoneMatrix();
        lastLocation = OpenGLMatrix.translation(0, 0, 0);

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables.addAll(targetsRoverRuckus);
    }

    //Start tracking the data sets we care about.
    public void activate() {
        targetsRoverRuckus.activate();
    }

    //Finding our vuMark and all its information.
    public void getVuMark() {
        //check all the trackable target to see which one (if any) is visible.
        targetVisible = false;
        for (VuforiaTrackable vuMark : allTrackables) {
            //If we locate a vumark, log it, and the robot location.
            if (((VuforiaTrackableDefaultListener) vuMark.getListener()).isVisible()) {
                targetVisible = true; //Logging the target as visible.

                //Log the robot location.
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) vuMark.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;

                    trans = lastLocation.getTranslation().getData();
                    rot = Orientation.getOrientation(lastLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                }

            }
            break;
        }
    }

    //Finding our vuMark and returning only the name. Returns null if not found.
    public String getVuMarkName() {
        for (VuforiaTrackable vuMark : allTrackables) {
            if (((VuforiaTrackableDefaultListener) vuMark.getListener()).isVisible()) {
                return vuMark.getName(); //Logging the vumark name.
            }
        }
        return null;
    }

    // Extract the X, Y, and Z components of the target robot relative to the target.
    //Returning the x (forwards) value of the robot relative to the target.
    public float getDistanceX() {
        if (targetVisible) return trans[0];
        return 0;
    }

    //Returning the y (left/right) value of the robot relative to the target.
    public float getDistanceY() {
        if (targetVisible) return trans[1];
        return 0;
    }

    //Returning the z (upwards) value of the robot relative to the target.
    public float getDistanceZ() {
        if (targetVisible) return trans[2];
        return 0;
    }

    //Extract the rotational components of the robot relative to the target.
    //Returning the heading? value of the robot relative to the target.
    public double getHeading() {
        if (targetVisible) return rot.firstAngle;
        return 0;
    }

    //Returning the roll? value of the robot relative to the target.
    public double getRoll() {
        if (targetVisible) return rot.secondAngle;
        return 0;
    }

    //Returning the pitch? value of the robot relative to the target.
    public double getPitch() {
        if (targetVisible) return rot.thirdAngle;
        return 0;
    }


    //Locates our robot based upon the field Matrix.
    public void findRobot(Telemetry telemetry) {
        //Provide feedback as to where the robot is located (if we know).
        if (targetVisible) {
            // express position (translation) of robot in inches.
            VectorF translation = lastLocation.getTranslation();
            telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

            // express the rotation of the robot in degrees.
            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
        } else {
            telemetry.addData("Visible Target", "none");
        }
    }

    //Returning a bitmap for use in OpenCV.
    public Bitmap getBitmap(){
        vuforia.setFrameQueueCapacity(1);
        VuforiaLocalizer.CloseableFrame frame = null; //takes the frame at the head of the queue
        try {
            frame = vuforia.getFrameQueue().take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long numImages = frame.getNumImages();
        Image rgb = null;

        for (int i = 0; i < numImages; i++) {
            if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB888) {
                rgb = frame.getImage(i);
                break;
            }
        }

            Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
            bm.copyPixelsFromBuffer(rgb.getPixels());

        frame.close();

        return bm;
    }

    //Creating our matrix which places our object on the field, so we can move based off the object's position.
    private void navMatrix() {
        /**
         * To place the BlueRover target in the middle of the blue perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Then, we translate it along the Y axis to the blue perimeter wall.
         */
        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        /**
         * To place the RedFootprint target in the middle of the red perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 180 around the field's Z axis so the image is flat against the red perimeter wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative Y axis to the red perimeter wall.
         */
        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocation(redFootprintLocationOnField);

        /**
         * To place the FrontCraters target in the middle of the front perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 90 around the field's Z axis so the image is flat against the front wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative X axis to the front perimeter wall.
         */
        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        /**
         * To place the BackSpace target in the middle of the back perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it -90 around the field's Z axis so the image is flat against the back wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the X axis to the back perimeter wall.
         */
        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);
    }

    /**
     * Create a transformation matrix describing where the phone is on the robot.
     * <p>
     * The coordinate frame for the robot looks the same as the field.
     * The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
     * Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
     * <p>
     * The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
     * pointing to the LEFT side of the Robot.  It's very important when you test this code that the top of the
     * camera is pointing to the left side of the  robot.  The rotation angles don't work if you flip the phone.
     * <p>
     * If using the rear (High Res) camera:
     * We need to rotate the camera around it's long axis to bring the rear camera forward.
     * This requires a negative 90 degree rotation on the Y axis
     * <p>
     * Next, translate the camera lens to where it is on the robot.
     * In this example, it is centered (left to right), but 110 mm forward of the middle of the robot, and 200 mm above ground level.
     */
    private void phoneMatrix() {
        //Placing our camera on a matrix which allows us to move the robot based on the distance
        //between
        final int CAMERA_FORWARD_DISPLACEMENT = 110;   // eg: Camera is 110 mm in front of robot center
        final int CAMERA_VERTICAL_DISPLACEMENT = 200;   // eg: Camera is 200 mm above ground
        final int CAMERA_LEFT_DISPLACEMENT = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix phoneMatrix = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        CAMERA_CHOICE == FRONT ? 90 : -90, 0, 0));

        //Let all the trackable listeners know where the phone is.
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneMatrix, parameters.cameraDirection);
        }
    }
}
