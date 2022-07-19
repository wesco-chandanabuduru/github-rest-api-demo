package com.wesco.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wesco.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
