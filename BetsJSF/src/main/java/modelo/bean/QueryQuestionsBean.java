package modelo.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class QueryQuestionsBean {
	
	private BLFacade blfacade;
	private Date fecha;
	
	private Event event;
	private List<Event> events = new ArrayList<Event>();
	
	public Question question;
	private List<Question> questions = new ArrayList<Question>();
	public String quest;
	
	public float min;

	
	public QueryQuestionsBean() {
		this.blfacade = new BLFacadeImplementation(new DataAccess());
	}
	
	////fecha

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	///evento

	public Event getEvento() {
		return event;
	}

	public void setEvento(Event evento) {
		this.event = evento;
	}

	public List<Event> getEventos() {
		return events;
	}

	public void setEventos(List<Event> eventos) {
		this.events = eventos;
	}
	
	public List<Event> getEvents(Date date) {
		return blfacade.getEvents(date);
	}
	
	//pregunta

	public Question getPregunta() {
		return question;
	}

	public void setPregunta(Question question) {
		this.question = question;
	}

	public List<Question> getPreguntas() {
		return questions;
	}

	public void setPreguntas(List<Question> questions) {
		this.questions = questions;
	}

	public String getQuest() {
		return quest;
	}

	public void setQuest(String quest) {
		this.quest = quest;
	}
	
	public List<Question> getQuestions(Event event) {
		return event.getQuestions();
	}

	public Question createQuestion(Event evento, String desc, float min) throws EventFinished, QuestionAlreadyExist {
		return blfacade.createQuestion(evento, desc, min);
	}

	///minimos
	
	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}
	
	///////

	public void onDateSelect(SelectEvent event) {
		this.event = null;
		this.fecha = (Date) event.getObject();
		setPreguntas(null);
		setEventos(getEvents(fecha));

	}

	public void onEventSelect(SelectEvent evento) {
		this.questions = null;
		this.event = (Event) evento.getObject();
		setPreguntas(getQuestions(event));

	}

	/////crear pregunta

	public void crearPregunta() {
		try {
			if (event != null && !quest.equals("") && min != 0.0) {
				question = createQuestion(event, quest, min);
				reiniciar();
				if (question != null) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("PREGUNTA CREADA CORRECTAMENTE"));
				} else
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("ERROR AL CREAR LA PREGUNTA"));
			} else
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("ELIGE UN EVENTO Y RELLENA TODOS LOS CAMPOS"));
		} catch (EventFinished e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("EVENTO FINALIZADO"));
		} catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("LA PREGUNTA YA EXISTE"));
		}

	}

	public void reiniciar() {
		this.event = null;
		this.events = null;
		this.questions = null;
		this.fecha = null;
		this.min = (float) 0.0;
		this.quest = "";
	}
	
	

}
