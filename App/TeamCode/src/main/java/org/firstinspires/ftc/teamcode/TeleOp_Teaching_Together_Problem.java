 /* Copyright (c) 2017 FIRST. All rights reserved.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="LinearOpMode Problem", group="Linear Opmode")
@Disabled
public class TeleOp_Teaching_Together_Problem extends LinearOpMode {

    /*
    Create global motor variables, but do not initialize!
    Create an ElapsedTime variable, and initialize this.

    Declare below
     */
    int  LeftDrive, RightDrive;


    /*
    So, here we need to declare some variables for working with encoders
initialize a variable
    Variable 1 - Counts per motor revolution - hint - in the 1400s
    Variable 2 - Driver Gear Reduction - hint - sin(90)
    Variable 3 - Wheel Diameter Inches - hint - 3.6
    Variable 4 - Counts per inch - hint - this is the one that requires math! Explained below vv
    For this, we need to take the counts per motor revolution and multiply it by the drive gear reduction.
    Then, we need to divide that by the circumference of the wheel, which is the diameter times counts
    per inch.

    Declare all these values below
     */
    int CountsRevo = 1440;
    double WheelDiameter = 3.6;



    @Override
    public void runOpMode() {
        //Heres some hints on how to use telemetry!
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        /*
        Initialize your global motors using the motors with the names:
        - left_motor
        - right_motor
        */

        // Wait for the game to start (driver presses PLAY)
        // Then reset the time


        //Necessary reversal of one of the motors - which one? - hint - defaut rotation of a motor is clockwise


        /*
        We need to now reset both encoders for the two motors
        We need to then make them both run using encoder
        */

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
        //Use telemetry to send the time elapsed to the console and update it

            // What is our target position? It is the integer typecasting(ask me for help) of 12 x the counts per inch
            // We save this target position to a variable for later use


            // set both the motors to the encoder mode (Run to position)

            //Now, set the motor target position to the target position specfied above

            //Set the power of the motor to 0.5

            //Now, the way that encoders work when used in run to position mode is that they will go to the
            //position specified. So, we need to have a loop to account for what to do while they are
            // going to that position specified

            //until you declare your motors, the while loop statement below will give you an error
            //once you declare your motors, uncomment the while loop then do the exercise inside
            /*
            while (leftMotor.isBusy() && rightMotor.isBusy()) {
                //Now, for telemetry, telemetry both the target positions of the motors, then update the telemetry
                // hint - use motor_name.getTargetPosition();
                // do the telemetry in this while loop
            }
            */

            //reset the power of both motors by setting their powers to 0

            //set both the motors to the Run using encoder mode.



        }
    }
}