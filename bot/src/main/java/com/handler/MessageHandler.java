package com.handler;

import static com.util.RandomIndex.getRandomElem;

import com.literal.MessageConstants;
import com.literal.RegexStrings;
import com.literal.TimeConstants;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageHandler extends ListenerAdapter {
  private final JDA currentJdaObject;
  private LocalDateTime startTime;
  private Timer timer;
  private final Set<User> giveawayMembers = new HashSet<>();
  private User winner = null;
  private boolean isGiveawayStarted = false;

  public MessageHandler(JDA currentJdaObject){
    this.currentJdaObject = currentJdaObject;
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event)
  {
    Message msg = event.getMessage();
    MessageChannel messageChannel = event.getChannel();

    if(msg.getContentRaw().matches(RegexStrings.COMMAND_REGEX)){
      long minutesPassed;
      long secondsPassed;
      switch (msg.getContentRaw().toLowerCase()) {
        case "!help":
          messageChannel.sendMessage(MessageConstants.MENU_TEXT)
              .complete();
          break;

        case "!start":
          TimerTask timerTask;
          if (Objects.requireNonNull(event.getMember()).getPermissions()
              .contains(Permission.ADMINISTRATOR)) {
            timer = new Timer();
            isGiveawayStarted = true;

            timerTask = new TimerTask() {
              public void run() {
                if (winner != null) {
                  messageChannel
                      .sendMessage(
                          String.format(
                              MessageConstants.TIME_IS_OVER,
                              Objects.requireNonNull(getRandomElem(giveawayMembers)).getName()))
                      .complete();
                } else {
                  messageChannel
                      .sendMessage(MessageConstants.TIME_IS_OVER_NO_MEMBERS)
                      .complete();
                }

                giveawayMembers.clear();
              }
            };
          } else {
            messageChannel.sendMessage(MessageConstants.YOU_HAVE_NO_PERMISSIONS).complete();
            break;
          }

          timer.schedule(timerTask, TimeConstants.AMOUNT_OF_MINUTES_FOR_GIVEAWAY * 60 * 1000
              + TimeConstants.AMOUNT_OF_SECONDS_FOR_GIVEAWAY);
          messageChannel.sendMessage(String.format(MessageConstants.GIVEAWAY_STARTED,
              TimeConstants.AMOUNT_OF_MINUTES_FOR_GIVEAWAY)).complete();
          startTime = LocalDateTime.now();
          break;

        case "!stop":
          if (Objects.requireNonNull(event.getMember()).getPermissions()
              .contains(Permission.ADMINISTRATOR)) {
            if (isGiveawayStarted) {
              timer.cancel();
              isGiveawayStarted = false;
              minutesPassed = Duration.between(startTime, LocalDateTime.now()).getSeconds() / 60;
              secondsPassed =
                  Duration.between(startTime, LocalDateTime.now()).getSeconds() - minutesPassed
                      * 60;

              winner = getRandomElem(giveawayMembers);

              if (winner != null) {
                messageChannel.sendMessage(String.format(MessageConstants.GIVEAWAY_STOPPED,
                    minutesPassed, secondsPassed, winner)).complete();
              } else {
                messageChannel.sendMessage(String.format(MessageConstants.GIVEAWAY_STOPPED_NO_MEMBERS,
                    minutesPassed, secondsPassed)).complete();
              }

              giveawayMembers.clear();
            } else {
              messageChannel.sendMessage(MessageConstants.GIVEAWAY_NOT_STARTED).complete();
            }
            break;
            } else {
            messageChannel.sendMessage(MessageConstants.YOU_HAVE_NO_PERMISSIONS).complete();
            break;
          }

        case "!time":
          if (isGiveawayStarted) {
            minutesPassed = Duration.between(startTime, LocalDateTime.now()).getSeconds() / 60;
            secondsPassed =
                Duration.between(startTime, LocalDateTime.now()).getSeconds() - minutesPassed
                    * 60;
            messageChannel.sendMessage(String.format(MessageConstants.PASSED_TIME,
                minutesPassed, secondsPassed)).complete();
          } else {
            messageChannel.sendMessage(MessageConstants.GIVEAWAY_NOT_STARTED).complete();
          }
          break;

        case "!play":
          if (isGiveawayStarted) {
            if (giveawayMembers.contains(Objects.requireNonNull(event.getMember()).getUser())) {
              messageChannel.sendMessage(MessageConstants.YOU_ALREADY_PARTICIPATES_IN_GIVEAWAY)
                  .complete();
              break;
            }

            giveawayMembers.add(currentJdaObject.getUserById(
                Objects.requireNonNull(event.getMember()).getId()));
            messageChannel.sendMessage(MessageConstants.YOU_PARTICIPATE_IN_GIVEAWAY).complete();
          } else {
            messageChannel.sendMessage(MessageConstants.GIVEAWAY_NOT_STARTED).complete();
          }
          break;

        case "!members":
          if (isGiveawayStarted) {
            String giveawayMembersString = giveawayMembers.stream().map(User::getName)
                .collect(Collectors.joining(", "));

            messageChannel.sendMessage(
                    String.format(MessageConstants.MEMBERS_OF_GIVEAWAY, giveawayMembersString))
                .complete();
          } else {
            messageChannel.sendMessage(MessageConstants.GIVEAWAY_NOT_STARTED).complete();
          }
          break;

        default:
          messageChannel.sendMessage(MessageConstants.UNKNOWN_COMMAND).complete();
      }
    }
  }
}
