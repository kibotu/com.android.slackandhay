package com.android.slackandhay;

public class GOComponentGraphics extends GOComponent{

	@Override
	public void update(int dt, GOGameObject go) {
		// kleines beispiel für jan, damit der weiß was ich mir so dachte...
		// Angenommen deine Graphikklasse heißt GRA und ist ein singleton:
		
		/*
		
		GRA.drawScaryMonster(go.stateManager.getActiveState(), go.getPosition());
		bzw:
		GRA.drawFigure(GRA.ScaryMonster, go.stateManager.getActiveState(), go.getPosition());
				
		// Im active State, also dem GOState steht dann drin, welcher state (zB IDLE, WALK usw)
		// gerade aktiv ist, und wie lange er aktiv ist ( GOState.getCurrentTime() ) und wie lang
		// der State insgesamt ist: GOState.getDuration()
		// Daraus kannst du dann bestimmen, welches Animationsbild gemalt werden soll, sobald das
		// nächste Frame gerendert wird.
		
				
		
		*/
		
	}

}
