package e201.skeleton ;

import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas ;
import fr.lri.swingstates.canvas.CShape ;
import fr.lri.swingstates.canvas.CText ;
import fr.lri.swingstates.canvas.animations.transitions.CElementEvent;
import fr.lri.swingstates.canvas.transitions.EnterOnShape;
import fr.lri.swingstates.canvas.transitions.LeaveOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.canvas.transitions.ReleaseOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import javax.swing.JFrame ;
import javax.swing.border.StrokeBorder;

import java.awt.BasicStroke;
import java.awt.Font ;
import java.awt.Stroke;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class SimpleButton {

    private CText label;

    SimpleButton(Canvas canvas, String text) {
	   label = canvas.newText(0, 0, text, new Font("verdana", Font.PLAIN, 12)) ;
	   final CRectangle rect = canvas.newRectangle(label.getScaleX(), label.getScaleY(), label.getWidth(), label.getHeight());
	   label.addChild(rect);
	   label.above(rect);
	   CStateMachine sm = new CStateMachine(canvas) {
		    public State idle = new State(){
		    	Transition Enter = new EnterOnShape(">> hover");
		    };
		    
		    public State hover = new State(){
		    	Transition Leave = new LeaveOnShape(">> idle");
		    	Transition Press = new PressOnShape(">> pressed"){
		    		public void action(){
		    			System.out.println("bords du bouton");
		    			rect.setStroke((Stroke) new StrokeBorder(new BasicStroke(5))) ;
		    		}
		    	};
		    };
		    
		    public State pressed = new State() {
		    	Transition Leave = new LeaveOnShape(">> desactivated");
		    	Transition Rekease = new ReleaseOnShape(">> hover"){
		    		public void action(){
		    			System.out.println("IHM TU FAIS DES BOUTONS");
		    		}
		    	};
			};
			public State desactivated = new State(){
				Transition Enter = new EnterOnShape(">> pressed");
				Transition Release = new ReleaseOnShape(">> idle");
			};
	   
	   };
    }
    
    
    
    public void action() {
	   System.out.println("ACTION!") ;
    }

    public CShape getShape() {
	   return label ;
    }

    static public void main(String[] args) {
	   JFrame frame = new JFrame() ;
	   Canvas canvas = new Canvas(400,400) ;
	   frame.getContentPane().add(canvas) ;
	   frame.pack() ;
	   frame.setVisible(true) ;

	   SimpleButton simple = new SimpleButton(canvas, "simple") ;
	   simple.getShape().translateBy(100,100) ;
    }

}
