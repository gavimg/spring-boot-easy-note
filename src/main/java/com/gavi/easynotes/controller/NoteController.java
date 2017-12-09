package com.gavi.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gavi.easynotes.model.Note;
import com.gavi.easynotes.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {
	
	@Autowired
	NoteRepository noteRepository;
	
	
	
	//Get All Notes
	@GetMapping("/notes")
	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}
	
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) {
		return noteRepository.save(note);
	}
	
	@GetMapping("/notes/{id}")
	public ResponseEntity<Note> getNoteById(@PathVariable(value="id") Long nodeId) {
		
		Note note = noteRepository.findOne(nodeId);
		if(note == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(note);
	}
	
	@PutMapping("/notes/{id}")
	public ResponseEntity<Note> updateNote(@PathVariable(value="id") Long noteId, @Valid @RequestBody Note noteDetails) {
		
		Note note = noteRepository.findOne(noteId);
		
		if(note == null) {
			return ResponseEntity.notFound().build();
		}
		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());
		
		Note updatedNote = noteRepository.save(note);
		
		return ResponseEntity.ok().body(updatedNote);
	}
	
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<Note> deleteNote(@PathVariable(value="id") Long noteId) {
		Note note = noteRepository.findOne(noteId);
		
		if(note == null) {
			return ResponseEntity.notFound().build();
		}
		noteRepository.delete(note);
		
		return ResponseEntity.ok().build();
	}

}
