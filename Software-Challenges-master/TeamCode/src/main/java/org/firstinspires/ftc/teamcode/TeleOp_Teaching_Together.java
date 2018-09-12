/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Basic: Linear OpMode", group="Linear Opmode")
@Disabled
public class BasicOpMode_Linear extends LinearOpMode {

    /*
    Create global motor variables, but do not initialize -- hardwareMap!
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

            while (leftMotor.isBusy() && rightMotor.isBusy()) {
                //Now, for telemetry, telemtry both the target positions of the motors, then update the telemetry
                // hint - use motor_name.getTargetPosition();
                // do the telemetry in this while loop
            }

            //reset the power of both motors by setting their powers to 0

            //set both the motors to the Run using encoder mode.



        }
    }
}