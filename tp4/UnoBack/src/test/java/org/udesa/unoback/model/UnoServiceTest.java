package org.udesa.unoback.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.udesa.unoback.service.Dealer;
import org.udesa.unoback.service.UnoService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UnoServiceTest {
    static Card RedOn( int n ) { return new NumberCard( "Red", n ); }
    static Card BlueOn( int n ) { return new NumberCard( "Blue", n ); }
    static Card YellowOn( int n ) { return new NumberCard( "Yellow", n ); }
    static Card GreenOn( int n ) { return new NumberCard( "Green", n ); }
    static Card red1 = RedOn( 1 );
    static Card red2 = RedOn( 2 );
    static Card red3 = RedOn( 3 );
    static Card red4 = RedOn( 4 );
    static Card red5 = RedOn( 5 );
    static Card blue1 = BlueOn( 1 );
    static Card blue2 = BlueOn( 2 );
    static Card blue3 = BlueOn( 3 );
    static Card green1 = GreenOn( 1 );
    static Card green3 = GreenOn( 3 );
    static Card green5 = GreenOn( 5 );
    static Card yellow3 = YellowOn( 3 );
    static Card yellow5 = YellowOn( 5 );


    @Autowired private UnoService unoService;
    @MockBean private Dealer dealer;

    @BeforeEach
    public void setUp() {
        List<Card> deck = deck();
        when(dealer.fullDeck()).thenReturn(deck);
    }

    @Test public void newMatchTest() {
        UUID id = unoService.newMatch(List.of("A", "B"));
        assertNotNull(id);
    }

    @Test public void playerHandTest() {
        UUID id = unoService.newMatch(List.of("A", "B"));
        assertNotNull(unoService.getPlayerHand(id));
    }

    @Test public void playerHandIdError() {
        UUID nonExistantId = UUID.randomUUID();
        assertThrows(RuntimeException.class, () -> unoService.getPlayerHand(nonExistantId));
    }

    @Test public void activeCardTest() {
        UUID id = unoService.newMatch(List.of("A", "B"));
        assertNotNull(unoService.getActiveCard(id));
    }

    @Test public void activeCardIdError() {
        UUID nonExistantId = UUID.randomUUID();
        assertThrows(RuntimeException.class, () -> unoService.getActiveCard(nonExistantId));
    }

    @Test public void drawCardTest() {
        UUID id = unoService.newMatch(List.of("A", "B"));
        unoService.drawCard(id, "A");
    }

    @Test public void drawCardIdError() {
        UUID nonExistantId = UUID.randomUUID();
        assertThrows(RuntimeException.class, () -> unoService.drawCard(nonExistantId, "A"));
    }

    @Test public void drawCardIncorrectPlayer() {
        UUID id = unoService.newMatch(List.of("A", "B"));
        assertThrows(IllegalArgumentException.class, () -> unoService.drawCard(id, "B"));
        assertThrows(IllegalArgumentException.class, () -> unoService.drawCard(id, "C"));
    }

    @Test public void playCardTest() {
        UUID id = unoService.newMatch(List.of("A", "B"));
        unoService.play(id, "A", blue1);
    }

    @Test public void playCardIdError() {
        UUID nonExistantId = UUID.randomUUID();
        assertThrows(RuntimeException.class, () -> unoService.play(nonExistantId, "A", blue1));
    }

    @Test public void playCardIncorrectPlayer() {
        UUID id = unoService.newMatch(List.of("A", "B"));
        assertThrows(IllegalArgumentException.class, () -> unoService.play(id, "B", blue1));
    }

    @Test public void playCardPlayerNotCard() {
        UUID id = unoService.newMatch(List.of("A", "B"));
        assertThrows(IllegalArgumentException.class, () -> unoService.play(id, "A", yellow5));
    }

    @Test public void playCardNonPlayableCard() {
        UUID id = unoService.newMatch(List.of("A", "B"));
        assertThrows(IllegalArgumentException.class, () -> unoService.play(id, "A", blue2));
    }

    @Test public void GameEnded() {
        UUID id = unoService.newMatch(List.of("A", "B"));

        unoService.play(id, "A", blue1 );
        unoService.play(id, "B", blue1 );
        unoService.play(id, "A", blue2 );
        unoService.play(id, "B", blue2 );
        unoService.play(id, "A", blue3 );
        unoService.play(id, "B", blue3 );
        unoService.play(id, "A", green3 );
        unoService.play(id, "B", green3 );
        unoService.play(id, "A", yellow3  );
        unoService.play(id, "B", yellow3 );
        unoService.play(id, "A", red3.uno()  );
        unoService.play(id, "B", red3.uno() );
        unoService.play(id, "A", red4  );

        assertThrowsLike( GameOver.GameOver, () -> unoService.drawCard(id, "B" ) );
        assertThrowsLike( GameOver.GameOver, () -> unoService.play(id, "B", red3 ) );
    }

    @Test public void handleVariousMatches() {
        UUID id = unoService.newMatch(List.of("A", "B"));
        UUID id2 = unoService.newMatch(List.of("C", "D"));

        assertNotNull(unoService.getActiveCard(id));
        assertNotNull(unoService.getActiveCard(id2));

        unoService.play(id, "A", blue1);
        unoService.play(id2, "C", red3);
    }

    private static List<Card> deck() {
        return List.of( red1,
                blue1, blue2, green3, blue3, yellow3, red3, red4,
                blue1, blue2, green3, blue3, yellow3, red3, red4,
                green1, green5, red2, red4, red5);
    }

    private void assertThrowsLike( String expectedMessage, Executable executable ) {
        assertEquals( expectedMessage,
                assertThrows( IllegalArgumentException.class, executable ).getMessage() );
    }
}
