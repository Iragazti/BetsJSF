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
	private Date date;
	
	private Event evento;
	private List<Event> eventos = new ArrayList<Event>();
	
	public Question question;
	private List<Question> questions = new ArrayList<Question>();
	public String quest;
	
	public float min;

	
	public QueryQuestionsBean() {
		this.blfacade = new BLFacadeImplementation(new DataAccess());
		//this.eventos = new ArrayList<Event>();
		//this.questions = new ArrayList<Question>();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public List<Event> getEvents(Date date) {
		return blfacade.getEvents(date);
	}


	public Event getEvento() {
		return evento;
	}

	public void setEvento(Event evento) {
		this.evento = evento;
	}

	public List<Event> getEventos() {
		return eventos;
	}

	public void setEventos(List<Event> eventos) {
		this.eventos = eventos;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getQuest() {
		return quest;
	}

	public void setQuest(String quest) {
		this.quest = quest;
	}

	////minimo
	
	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	///////
	
	public Question createQuestion(Event evento, String desc, float min) throws EventFinished, QuestionAlreadyExist {
		return blfacade.createQuestion(evento, desc, min);
	}

	public void onDateSelect(SelectEvent event) {
		this.evento=null;
		this.date = (Date) event.getObject();
		this.questions.clear();
		this.eventos = getEvents(date);
		//setQuestions(null);
		//setEventos(getEventos());

		printEventsandQuestions();

	}
	
	

	public void onEventSelect(SelectEvent event) {
		//this.questions=null;
		this.evento = (Event) event.getObject();
		//setQuestions(getQuestions());
		this.questions.clear();
		printEventsandQuestions();

	}

	/////crear pregunta --> problemas: exite pregunta y evento
	
	private boolean esEventoValido() {
		return evento != null;
	}
	
	private boolean esDescripcionValida() {
		return !quest.equals("");
	}
	private boolean esMinValido() {
		return min != 0.0;
	}
	private boolean esPreguntaVacia() {
		return question !=null;
	}

	public String crearPregunta() throws EventFinished, QuestionAlreadyExist {
		String ok="";
		try {
			///evento pregunta y min valido --> crear pregunta
			if (esEventoValido()&& esDescripcionValida()&& esMinValido()) {
				question = createQuestion (evento, quest, min);
				if (esPreguntaVacia()) {
					ok="ok";
					FacesContext.getCurrentInstance().addMessage(null,
							 new FacesMessage("pregunta creada OK")); 
				}
				else {
					ok="error";
					FacesContext.getCurrentInstance().addMessage(null,
							 new FacesMessage("error: pregunta NO creada")); 
				}
			} 
			else {
				ok="error";
				FacesContext.getCurrentInstance().addMessage(null,
						 new FacesMessage("elige correctamente un evento")); 
			}

			
		} catch (EventFinished e) {
			ok="error";
			FacesContext.getCurrentInstance().addMessage(null,
					 new FacesMessage("el evento ha finalizado")); 
		} catch (QuestionAlreadyExist q) {
			ok="error";
			FacesContext.getCurrentInstance().addMessage(null,
					 new FacesMessage("la pregunta existe con anterioridad")); 
		}
		return ok;
	}

	public void printEventsandQuestions(){
		System.out.println("Eventos: ");
		for (Event event: eventos) {
			System.out.println("Event number: " + event.getEventNumber());
			System.out.println("Event desc: " + event.getDescription());
			
		}
		System.out.println("Preguntas:");
		for(Question question:questions) {
			System.out.println("Question number: "+ question.getQuestionNumber());
			System.out.println("Question: "+question.getQuestion());
		}
	}

}
