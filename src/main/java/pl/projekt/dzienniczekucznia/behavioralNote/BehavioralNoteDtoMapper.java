package pl.projekt.dzienniczekucznia.behavioralNote;

import pl.projekt.dzienniczekucznia.behavioralNote.dto.BehavioralNoteDto;

public class BehavioralNoteDtoMapper {
    static public BehavioralNoteDto map(BehavioralNote behavioralNote){
        return new BehavioralNoteDto(behavioralNote.getId(),
                behavioralNote.getTeacher(),
                behavioralNote.getStudent(),
                behavioralNote.getDescription());
    }
}
