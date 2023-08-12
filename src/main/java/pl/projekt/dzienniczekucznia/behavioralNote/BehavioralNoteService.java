package pl.projekt.dzienniczekucznia.behavioralNote;

import org.springframework.stereotype.Service;

@Service
public class BehavioralNoteService {
    private final BehavioralNoteRepository behavioralNoteRepository;

    public BehavioralNoteService(BehavioralNoteRepository behavioralNoteRepository) {
        this.behavioralNoteRepository = behavioralNoteRepository;
    }

    public void addBehavioralNote(BehavioralNote behavioralNote) {
        behavioralNoteRepository.save(behavioralNote);
    }
}
