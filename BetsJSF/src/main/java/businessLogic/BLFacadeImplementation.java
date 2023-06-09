package businessLogic;
//hola
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import dataAccess.DataAccess;
import dataAccess.DataAccessInterface;
import domain.Question;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
//@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	DataAccessInterface dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		//ConfigXML c=ConfigXML.getInstance();
		
		/*if (c.getDataBaseOpenMode().equals("initialize")) {
			
		    dbManager=new DataAccessInterface(new ObjectDbDAOManager());
			dbManager.initializeDB();
			dbManager.close();
			}
		*/
	}
	
    public BLFacadeImplementation(DataAccessInterface da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		///ConfigXML c=ConfigXML.getInstance();
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		da.initializeDB();
	
		dbManager=da;		
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   //@WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
		
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dbManager.createQuestion(event,question,betMinimum);		

		
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    //@WebMethod	
	public List<Event> getEvents(Date date)  {
		
		List<Event>  events=dbManager.getEvents(date);
		
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	//@WebMethod 
	public List<Date> getEventsMonth(Date date) {
		
		List<Date>  dates=dbManager.getEventsMonth(date);
		
		return dates;
	}
	
	/*
	public void close() {
		//DataAccess dB4oManager=new DataAccess(false);

		//dB4oManager.close();
		


	}
	*/

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
   // @WebMethod	
	 public void initializeBD(){
    	
		dbManager.initializeDB();
		
	}
	/* 
	 public List<Question> getQuestionsByEvent(Event event) {
		 // Verificar  el evento valido
		    if (event == null) {
		        return null;
		    }

		    // Obtener las preguntas del evento
		    List<Question> questions = event.getQuestions();

		    // devuelve la lista de preguntas
		    return questions;
	}
*/
}

