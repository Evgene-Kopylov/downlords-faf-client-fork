package com.faforever.client.teammatchmaking;

import com.faforever.client.avatar.AvatarService;
import com.faforever.client.builders.AvatarBeanBuilder;
import com.faforever.client.builders.PlayerBeanBuilder;
import com.faforever.client.domain.GameBean;
import com.faforever.client.domain.PlayerBean;
import com.faforever.client.game.JoinGameHelper;
import com.faforever.client.i18n.I18n;
import com.faforever.client.moderator.ModeratorService;
import com.faforever.client.notification.NotificationService;
import com.faforever.client.player.PlayerService;
import com.faforever.client.replay.ReplayService;
import com.faforever.client.test.UITest;
import com.faforever.client.theme.UiService;
import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testfx.util.WaitForAsyncUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static com.faforever.client.player.SocialStatus.OTHER;
import static com.faforever.client.player.SocialStatus.SELF;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class PartyMemberContextMenuControllerTest extends UITest {
  private static final String TEST_USER_NAME = "junit";

  @Mock
  private AvatarService avatarService;
  @Mock
  private EventBus eventBus;
  @Mock
  private I18n i18n;
  @Mock
  private JoinGameHelper joinGameHelper;
  @Mock
  private ModeratorService moderatorService;
  @Mock
  private NotificationService notificationService;
  @Mock
  private PlayerService playerService;
  @Mock
  private ReplayService replayService;
  @Mock
  private UiService uiService;
  
  private PartyMemberContextMenuController instance;
  private PlayerBean player;

  @BeforeEach
  public void setUp() throws Exception {
    when(avatarService.getAvailableAvatars()).thenReturn(CompletableFuture.completedFuture(Arrays.asList(
        AvatarBeanBuilder.create().defaultValues().url(new URL("http://www.example.com/avatar1.png")).description("Avatar Number #1").get(),
        AvatarBeanBuilder.create().defaultValues().url(new URL("http://www.example.com/avatar2.png")).description("Avatar Number #2").get(),
        AvatarBeanBuilder.create().defaultValues().url(new URL("http://www.example.com/avatar3.png")).description("Avatar Number #3").get()
    )));
    instance = new PartyMemberContextMenuController(avatarService, eventBus, i18n, joinGameHelper, moderatorService, notificationService, playerService, replayService, uiService);
    loadFxml("theme/player_context_menu.fxml", clazz -> instance, instance);

    player = PlayerBeanBuilder.create().defaultValues().username(TEST_USER_NAME).socialStatus(OTHER).avatar(null).game(new GameBean()).get();
    instance.setPlayer(player);
    WaitForAsyncUtils.waitForFxEvents();
  }

  @Test
  public void testShowCorrectItems() {
    assertThat(instance.showUserInfo.isVisible(), is(true));
    assertThat(instance.sendPrivateMessageItem.isVisible(), is(true));
    assertThat(instance.copyUsernameItem.isVisible(), is(true));
    assertThat(instance.colorPickerMenuItem.isVisible(), is(false));
    assertThat(instance.inviteItem.isVisible(), is(false));
    assertThat(instance.addFriendItem.isVisible(), is(true));
    assertThat(instance.removeFriendItem.isVisible(), is(false));
    assertThat(instance.addFoeItem.isVisible(), is(true));
    assertThat(instance.removeFoeItem.isVisible(), is(false));
    assertThat(instance.reportItem.isVisible(), is(true));
    assertThat(instance.joinGameItem.isVisible(), is(false));
    assertThat(instance.watchGameItem.isVisible(), is(false));
    assertThat(instance.viewReplaysItem.isVisible(), is(true));
    assertThat(instance.kickGameItem.isVisible(), is(false));
    assertThat(instance.kickLobbyItem.isVisible(), is(false));
    assertThat(instance.broadcastMessage.isVisible(), is(false));
    assertThat(instance.avatarPickerMenuItem.isVisible(), is(false));
  }

  @Test
  public void testShowCorrectItemsForSelf() {
    player.setSocialStatus(SELF);
    WaitForAsyncUtils.waitForFxEvents();

    assertThat(instance.showUserInfo.isVisible(), is(true));
    assertThat(instance.sendPrivateMessageItem.isVisible(), is(false));
    assertThat(instance.copyUsernameItem.isVisible(), is(true));
    assertThat(instance.colorPickerMenuItem.isVisible(), is(false));
    assertThat(instance.inviteItem.isVisible(), is(false));
    assertThat(instance.addFriendItem.isVisible(), is(false));
    assertThat(instance.removeFriendItem.isVisible(), is(false));
    assertThat(instance.addFoeItem.isVisible(), is(false));
    assertThat(instance.removeFoeItem.isVisible(), is(false));
    assertThat(instance.reportItem.isVisible(), is(false));
    assertThat(instance.joinGameItem.isVisible(), is(false));
    assertThat(instance.watchGameItem.isVisible(), is(false));
    assertThat(instance.viewReplaysItem.isVisible(), is(true));
    assertThat(instance.kickGameItem.isVisible(), is(false));
    assertThat(instance.kickLobbyItem.isVisible(), is(false));
    assertThat(instance.broadcastMessage.isVisible(), is(false));
    assertThat(instance.avatarPickerMenuItem.isVisible(), is(true));
  }
}
