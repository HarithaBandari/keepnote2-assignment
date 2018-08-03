package com.stackroute.keepnote.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.model.Note;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus
 *                  clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database
 *                     transaction. The database transaction happens inside the scope of a persistence
 *                     context. 
 * */
@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {
	private SessionFactory sessionFactory;
	List<Note> list = null;
	Note noteObj = new Note();
	boolean bool = false;

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */
	@Autowired
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Save the note in the database(note) table.
	 */

	public boolean saveNote(Note note) {
		Session session = sessionFactory.getCurrentSession();
		session.save(note);
		session.flush();
		return true;

	}

	/*
	 * Remove the note from the database(note) table.
	 */

	public boolean deleteNote(int noteId) {
		Session session = sessionFactory.getCurrentSession();
//        Query query = session.createQuery("from Note where noteId = :noteId");
//        query.setInteger("noteId", noteId);
//        Note note=(Note)query.uniqueResult();
		session.delete(getNoteById(noteId));
		session.flush();
		return true;
	}

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	public List<Note> getAllNotes() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("from Note order by createdAt DESC");
		@SuppressWarnings("unchecked")
		List<Note> resultList = query.getResultList();
		return resultList;

	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	@SuppressWarnings("deprecation")
	public Note getNoteById(int noteId) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("from Note where noteId = :noteId");
		query.setInteger("noteId", noteId);
		Note note = (Note) query.uniqueResult();
		return note;
	}

	/* Update existing note */

	public boolean UpdateNote(Note note) {
		System.out.println("Update note is getting called");
		sessionFactory.getCurrentSession().update(note);
		return true;
	}

}