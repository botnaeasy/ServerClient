/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myproject.Model.Message.Client2ServerMessages;

import java.awt.Rectangle;

/**
 *
 * @author BotNaEasyEnv
 */
public class FillDimensionDataMessage extends AbstractC2SMessage{

    public FillDimensionDataMessage(Object[][] args) {
        super("FillDimensionDataMessage", args);
    }

    @Override
    public String[] setMethod() {
        return new String[]{
            "transferDimensionData"
        };
    }

    @Override
    public Class[][] setClassArguments() {
        return new Class[][]{
            {Rectangle.class}  
        };
    }
}
