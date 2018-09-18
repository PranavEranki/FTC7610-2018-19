package org.firstinspires.ftc.teamcode.Opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Sensors.IMU;

public class AutoMain_Test extends OpMode {

    //List of system states. MOVE TO OWN CLASS?
     private enum State {
        INITIAL,
        LAND,
        LEVEL_SLIDE,
        FINDMIN,
        SAMPLE,
        LOGNAV,
        DEPOT_MARKER,
        DEPOSITMIN,
        PICKUPMIN,
        PARK,
    }

        private State mState; //Current state of the state machine.
        private ElapsedTime StateTime;

        //Reset our state run timer and set a new state.
        private void setState(State nS) {
            StateTime.reset();
            mState = nS;
        }

    // Time Variables
    public ElapsedTime mRunTime = new ElapsedTime(); //Time of running.
    private ElapsedTime mInitTime = new ElapsedTime(); //Time it takes to initialize.

//--------------------------------------------------------------------------------------------------
    public AutoMain_Test() {} //Default Constructor
//--------------------------------------------------------------------------------------------------

    //Initialization: Runs once  driver presses init.
    @Override
    public void init() {
        mInitTime.reset(); //Starting our initialization timer.

        //Instantiating our robot objects.

        //Indicates that initialization is complete.
        telemetry.addData("Initialized", "in " + mInitTime.milliseconds() + "ms");
    }

     //Initialization Loop: Loops when driver presses init after init() runs.
    @Override
    public void init_loop() {
    }


    //Start: Runs once driver hits play.
    @Override
    public void start() {
        telemetry.clear(); //Clearing our telemetry dashboard.
        mRunTime.reset();

    }

    //Loop: Loops once driver hits play after start() runs.
    @Override
    public void loop() {

        //Sending our current state and state run time to our driver station.
        telemetry.addData(mState.toString(), StateTime.seconds());

        switch (mState) {
            case INITIAL: /*
               if (enter  condition - i.e encoders@0){
//start
                setState(nextState);
            }
            else { telemetry.addData(mState.toString() , StateTime.seconds() ); } */
                break;

            case LAND:

        }
    }

    //Stop: Runs once driver hits stop.
    @Override
    public void stop() {
    }

}
