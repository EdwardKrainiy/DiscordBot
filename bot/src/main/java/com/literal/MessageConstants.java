package com.literal;

public class MessageConstants {
  private MessageConstants(){}

  public static final String GIVEAWAY_NOT_STARTED = "Giveaway not started yet.";
  public static final String YOU_ALREADY_PARTICIPATES_IN_GIVEAWAY = "You are already participates in this giveaway!";
  public static final String MENU_TEXT = "Commands: "
      + "\n !start - Starts the giveaway. "
      + "\n !stop - Stops the giveaway and randomly chooses the winner. "
      + "\n !time - Shows remaining time."
      + "\n !play - Join to the giveaway.";
  public static final String GIVEAWAY_STARTED = "Giveaway has started for %d minutes.";
  public static final String TIME_IS_OVER = "Time is over! Winner is %s! Congratulations!" + new String(Character.toChars(0x1F349));
  public static final String TIME_IS_OVER_NO_MEMBERS = "Time is over! There are no members on giveaway.";
  public static final String GIVEAWAY_STOPPED = "Giveaway was stopped. Time have passed: %d minutes, %d seconds. Winner is %s! Congratulations!" + new String(Character.toChars(0x1F349));
  public static final String GIVEAWAY_STOPPED_NO_MEMBERS = "Giveaway was stopped. Time have passed: %d minutes, %d seconds. There are no members on giveaway.";
  public static final String PASSED_TIME = "Time have passed: %d minutes, %d seconds";
  public static final String YOU_PARTICIPATE_IN_GIVEAWAY = "You are now participate in giveaway!";
  public static final String MEMBERS_OF_GIVEAWAY = "Members of giveaway: %s";
  public static final String UNKNOWN_COMMAND = "Unknown command. Type '!help' to show all commands.";
  public static final String YOU_HAVE_NO_PERMISSIONS = "You have no permissions to do this.";
}
