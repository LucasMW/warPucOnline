package org.puc.rio.inf1636.hglm.war.model.serialize;
import java.lang.reflect.Type;
import java.util.List;

import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.model.*;
import org.puc.rio.inf1636.hglm.war.objective.ConquerContinentsObjective;
import org.puc.rio.inf1636.hglm.war.objective.ConquerTerritoriesObjective;
import org.puc.rio.inf1636.hglm.war.objective.DestroyPlayerObjective;
import org.puc.rio.inf1636.hglm.war.objective.WarObjective;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class WarSerializer implements JsonSerializer<WarState> {

	@Override
	public JsonElement serialize(final WarState warState, final Type type, final JsonSerializationContext context) {
		// TODO Auto-generated method stub
		
		JsonObject result = new JsonObject();
        result.add("conquestsThisTurn", new JsonPrimitive(warState.getConquestsThisTurn()));
        result.add("cardExchange", new JsonPrimitive(warState.getCardExchangeArmyCount()));
        result.add("turnState" , new JsonPrimitive(warState.getCurrentTurnState().name()));
        result.add("currentPlayer",this.serialize(warState.getCurrentPlayer(), type, context));
        try {
        result.add("canStealCardsFrom", this.serialize(warState.getCanStealCardsFrom(),type, context));
        }
        catch(Exception e){
        	System.out.println(e.getMessage());
        }
        JsonObject listOfPlayers = new JsonObject();
        for(Player p : warState.getPlayers()) {
        	listOfPlayers.add(p.getName(), this.serialize(p, type, context));
        }
        result.add("Players" , listOfPlayers );
        //result.add("Deck", this.serializeDeck(warState.getDeck()));
        
        return result;
    }
	public JsonElement serialize(final Player p, final Type type, final JsonSerializationContext context) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		result.addProperty("name", p.getName());
		result.add("numberOfTerritories", new JsonPrimitive(p.getNumberOfTerritories()));
		result.add("UnplacedArmies", new JsonPrimitive(p.getUnplacedArmies()));
		result.add("Color", new JsonPrimitive(p.getColor().toString()));
		result.add("objective", new JsonPrimitive(p.getObjective().getDescription()));
		JsonObject listOfCards = (JsonObject) this.serializeCards(p.getCards());
        
		result.add("cards", listOfCards);
		result.add("objective", this.serialize(p.getObjective(), type, context));
		
		return result;
    }
	public JsonElement serialize(final WarObjective o, final Type type, final JsonSerializationContext context) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		result.add("class", new JsonPrimitive(o.getClass().toString()));
		if (o instanceof ConquerContinentsObjective) {
			ConquerContinentsObjective os = (ConquerContinentsObjective)o;	
			
		}
		else if(o instanceof ConquerTerritoriesObjective) {
			
		}
		else if(o instanceof DestroyPlayerObjective) {
			
		}
		return result;
	}
	public JsonElement serializeCards(List<Card> l) {
		JsonObject listOfCards = new JsonObject();
        for(Card c : l) {
        	listOfCards.add(c.toString(),new JsonPrimitive(c.getType().getId()));
        }
        return listOfCards;
	}
	public JsonElement serializeDeck(Deck deck) {
		JsonObject listOfCards = new JsonObject();
		Card c = deck.takeCard();
        while( c != null) {
        	listOfCards.add(c.toString(),new JsonPrimitive(c.getType().getId()));
        	c = deck.takeCard();
        }
        	
        
        return listOfCards;
	}
	public JsonElement serialize(final Map m, final Type type, final JsonSerializationContext context) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		for(Territory t : m.getTerritories())
		result.add(t.getName(), this.serialize(t, type, context));
		
		return result;
    }
	public JsonElement serialize(final Territory t, final Type type, final JsonSerializationContext context) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		result.add("name",new JsonPrimitive(t.getName()));
		result.add("armyCount", new JsonPrimitive(t.getArmyCount()));
		result.add("continentId", new JsonPrimitive(t.getContinent().getId()));
		result.add("owner", this.serialize(t.getOwner(), type, context));	
		result.add("moveableArmyCount", new JsonPrimitive( t.getMoveableArmyCount()));	 //watch out	
		return result;
    }
	
	
     
}
