package com.example.androidthings.myproject;

import com.google.android.things.pio.Gpio;

public class HandDevice extends SimplePicoPro {

    public MorseCodeDecoder decoder;
    public String curMorse;

    public HandDevice() {
        decoder = new MorseCodeDecoder();
        curMorse = "";
    }

    @Override
    public void setup() {
        //set four GPIOs to input

        //BackSpace Button
        pinMode(GPIO_128,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_128,Gpio.EDGE_BOTH);

        //Space Button
        pinMode(GPIO_39,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_39,Gpio.EDGE_BOTH);

        //Dot Button
        pinMode(GPIO_37,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_37,Gpio.EDGE_BOTH);

        //Dash Button
        pinMode(GPIO_35,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_35,Gpio.EDGE_BOTH);

        //Enter Button
        pinMode(GPIO_34,Gpio.DIRECTION_IN);
        setEdgeTrigger(GPIO_34,Gpio.EDGE_BOTH);
    }

    @Override
    public void loop() {
        //nothing in here

    }

    @Override
    void digitalEdgeEvent(Gpio pin, boolean value) {
        println("digitalEdgeEvent"+pin+", "+value);

        if(pin==GPIO_37 && value==HIGH) {
            inputDot();
        }
        //when 39 goes from HIGH to HIGH
        else if (pin==GPIO_35 && value==HIGH) {
            inputDash();
        }

        //when 39 goes from HIGH to HIGH
        else if (pin==GPIO_128 && value==HIGH) {
            backspaceACharacter();
        }

        else if (pin==GPIO_39 && value==HIGH) {
            printCharacterToScreen(' ');
        }

        //When enter is pressed, output to screen
        if (pin==GPIO_34 && value==HIGH) {
            char outputChar;
            if(!curMorse.isEmpty()) {

                try {
                    outputChar = decoder.decodeInput(curMorse);
                    printCharacterToScreen(outputChar);
                }
                catch (IllegalValueException i) {

                }
                curMorse = "";
            }

        }
    }

    private void inputDot() {
        curMorse = curMorse + ".";
    }

    private void inputDash() {
        curMorse = curMorse + "-";
    }
    }
