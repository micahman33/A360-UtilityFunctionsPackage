package com.automationanywhere.botcommand.samples.commands;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.annotations.*;
import com.automationanywhere.commandsdk.annotations.rules.Equals;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.AttributeType;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.NUMBER;
import static com.automationanywhere.commandsdk.model.DataType.STRING;

//BotCommand makes a class eligible for being considered as an action.
@BotCommand

//CommandPks adds required information to be dispalable on GUI.
@CommandPkg(
        //Unique name inside a package and label to display.
        name = "numberRounding", label = "[[NumberRounding.label]]",
        node_label = "[[NumberRounding.node_label]]", description = "[[NumberRounding.description]]", icon = "pkg.svg",

        //Return type information. return_type ensures only the right kind of variable is provided on the UI.
        return_label = "[[NumberRounding.return_label]]", return_type = NUMBER, return_required = true)
public class NumberRounding {

    //Messages read from full qualified property file name and provide i18n capability.
    private static final Messages MESSAGES = MessagesFactory
            .getMessages("com.automationanywhere.botcommand.samples.messages");

    //Identify the entry point for the action. Returns a Value<String> because the return type is String.
    @Execute
    public NumberValue action(
            //Idx 1 would be displayed first, with a text box for entering the value.
            @Idx(index = "1", type = AttributeType.NUMBER)
            //UI labels.
            @Pkg(label = "[[NumberRounding.inputNumber.label]]")
            //Ensure that a validation error is thrown when the value is null.
            @NotEmpty
                    Double inputNumber,

            @Idx(index = "2", type = AttributeType.NUMBER)
            @Pkg(label = "[[NumberRounding.digitsAfter.label]]", description = "[[NumberRounding.digitsAfter.description]]")
            @NotEmpty
                    Double digitsAfter) {

        //validate input
        if(digitsAfter < 0 || digitsAfter > 16){
            throw new BotCommandException("Rounding digits must be 0 or a positive number between 1-16.");
        }
        Double outputNumber =  0.00;
        int digitsAfterInt = digitsAfter.intValue();

        //Business logic
        try{
            BigDecimal bd = new BigDecimal((Double.toString(inputNumber)));
            bd = bd.setScale(digitsAfterInt, RoundingMode.HALF_UP);
            outputNumber = bd.doubleValue();

        } catch (Exception e) {
            throw new BotCommandException("Error occured while rounding " + String.valueOf(inputNumber) + " with " + String.valueOf(digitsAfter) + ". Error code: " + e.toString());
        }

        //Return StringValue.
        System.out.println(outputNumber);
        return new NumberValue(outputNumber);
    }
}
