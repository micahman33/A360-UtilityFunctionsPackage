package com.automationanywhere.botcommand.samples.commands;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Double;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NumberRoundingCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(NumberRoundingCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.entrySet().stream().filter(en -> !Arrays.asList( new String[] {}).contains(en.getKey()) && en.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    NumberRounding command = new NumberRounding();
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("inputNumber") && parameters.get("inputNumber") != null && parameters.get("inputNumber").get() != null) {
      convertedParameters.put("inputNumber", parameters.get("inputNumber").get());
      if(convertedParameters.get("inputNumber") !=null && !(convertedParameters.get("inputNumber") instanceof Double)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","inputNumber", "Double", parameters.get("inputNumber").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("inputNumber") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","inputNumber"));
    }

    if(parameters.containsKey("digitsAfter") && parameters.get("digitsAfter") != null && parameters.get("digitsAfter").get() != null) {
      convertedParameters.put("digitsAfter", parameters.get("digitsAfter").get());
      if(convertedParameters.get("digitsAfter") !=null && !(convertedParameters.get("digitsAfter") instanceof Double)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","digitsAfter", "Double", parameters.get("digitsAfter").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("digitsAfter") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","digitsAfter"));
    }

    try {
      Optional<Value> result =  Optional.ofNullable(command.action((Double)convertedParameters.get("inputNumber"),(Double)convertedParameters.get("digitsAfter")));
      return logger.traceExit(result);
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","action"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }

  public Map<String, Value> executeAndReturnMany(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return null;
  }
}
