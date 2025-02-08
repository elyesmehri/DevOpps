package tn.esprit.eventsproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.IEventServices;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventRestControllerTest {

    @InjectMocks
    private EventRestController eventRestController;  // Le contrôleur à tester

    @Mock
    private IEventServices eventServices;  // Le service mocké

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialise les mocks
    }

    @Test
    public void testAddParticipant() {
        // Arrange
        Participant participant = new Participant();
        participant.setId(1);
        participant.setName("John Doe");
        when(eventServices.addParticipant(any(Participant.class))).thenReturn(participant);  // Mock du service

        // Act
        Participant result = eventRestController.addParticipant(participant);  // Appel du contrôleur

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());  // Vérification que le nom est correct
        verify(eventServices, times(1)).addParticipant(participant);  // Vérifie que la méthode du service a été appelée
    }

    @Test
    public void testAddEvent() {
        // Arrange
        Event event = new Event();
        event.setId(1);
        event.setName("Sample Event");
        when(eventServices.addAffectEvenParticipant(any(Event.class))).thenReturn(event);  // Mock du service

        // Act
        Event result = eventRestController.addEvent(event);  // Appel du contrôleur

        // Assert
        assertNotNull(result);
        assertEquals("Sample Event", result.getName());  // Vérification que le nom de l'événement est correct
        verify(eventServices, times(1)).addAffectEvenParticipant(event);  // Vérification de l'appel du service
    }

    @Test
    public void testAddEventWithParticipant() {
        // Arrange
        Event event = new Event();
        event.setId(1);
        event.setName("Sample Event with Participant");
        int participantId = 1;
        when(eventServices.addAffectEvenParticipant(any(Event.class), eq(participantId))).thenReturn(event);  // Mock du service

        // Act
        Event result = eventRestController.addEventPart(event, participantId);  // Appel du contrôleur avec un participant

        // Assert
        assertNotNull(result);
        assertEquals("Sample Event with Participant", result.getName());
        verify(eventServices, times(1)).addAffectEvenParticipant(event, participantId);  // Vérifie que la méthode du service a bien été appelée avec le bon ID
    }

    @Test
    public void testGetLogistiquesDates() {
        // Arrange
        Logistics logistics1 = new Logistics();
        logistics1.setDescription("Logistics 1");
        Logistics logistics2 = new Logistics();
        logistics2.setDescription("Logistics 2");
        when(eventServices.getLogisticsDates(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Arrays.asList(logistics1, logistics2));  // Mock du service

        // Act
        List<Logistics> result = eventRestController.getLogistiquesDates(LocalDate.now(), LocalDate.now().plusDays(1));  // Appel du contrôleur

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());  // Vérification que deux logistiques sont retournées
        assertEquals("Logistics 1", result.get(0).getDescription());  // Vérifie la première logistique
        assertEquals("Logistics 2", result.get(1).getDescription());  // Vérifie la deuxième logistique
        verify(eventServices, times(1)).getLogisticsDates(any(LocalDate.class), any(LocalDate.class));  // Vérifie l'appel du service
    }

    @Test
    public void testAddAffectLog() {
        // Arrange
        Logistics logistics = new Logistics();
        logistics.setDescription("Logistics Sample");
        String eventDescription = "Sample Event";
        when(eventServices.addAffectLog(any(Logistics.class), eq(eventDescription))).thenReturn(logistics);  // Mock du service

        // Act
        Logistics result = eventRestController.addAffectLog(logistics, eventDescription);  // Appel du contrôleur

        // Assert
        assertNotNull(result);
        assertEquals("Logistics Sample", result.getDescription());  // Vérifie que la description est correcte
        verify(eventServices, times(1)).addAffectLog(logistics, eventDescription);  // Vérifie l'appel du service
    }

    @Test
    public void testGetParticipantsLogis() {
        // Arrange
        Participant participant1 = new Participant();
        participant1.setId(1);
        participant1.setName("John Doe");
        Participant participant2 = new Participant();
        participant2.setId(2);
        participant2.setName("Jane Doe");
        when(eventServices.getParReservLogis()).thenReturn(Arrays.asList(participant1, participant2));  // Mock du service

        // Act
        List<Participant> result = eventRestController.getParReservLogis();  // Appel du contrôleur

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());  // Vérification que deux participants sont retournés
        assertEquals("John Doe", result.get(0).getName());  // Vérifie le nom du premier participant
        assertEquals("Jane Doe", result.get(1).getName());  // Vérifie le nom du deuxième participant
        verify(eventServices, times(1)).getParReservLogis();  // Vérifie l'appel du service
    }
}
