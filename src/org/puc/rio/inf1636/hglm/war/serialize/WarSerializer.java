package org.puc.rio.inf1636.hglm.war.serialize;

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
	public JsonElement serialize(final WarState warState, final Type type,
			final JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("conquestsThisTurn",
				new JsonPrimitive(warState.getConquestsThisTurn()));
		result.add("cardExchange",
				new JsonPrimitive(warState.getCardExchangeArmyCount()));
		result.add("turnState", new JsonPrimitive(warState
				.getCurrentTurnState().name()));
		result.addProperty("currentPlayer", warState.getCurrentPlayer()
				.getName());
		try {
			result.add("canStealCardsFrom", this.serializePlayer(
					warState.getCanStealCardsFrom(), type, context));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JsonObject listOfPlayers = new JsonObject();
		for (Player p : warState.getPlayers()) {
			listOfPlayers.add(p.getName(),
					this.serializePlayer(p, type, context));
		}
		result.add("players", listOfPlayers);
		result.add("map", this.serializeMap(warState.getMap(), type, context));
		// result.add("deck", this.serializeDeck(warState.getDeck()));
		return result;
	}

	public JsonElement serializePlayer(final Player p, final Type type,
			final JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.addProperty("name", p.getName());
		result.add("numberOfTerritories",
				new JsonPrimitive(p.getNumberOfTerritories()));
		result.add("unplacedArmies", new JsonPrimitive(p.getUnplacedArmies()));
		result.add("color", new JsonPrimitive(p.getColor().toString()));
		result.add("objective", new JsonPrimitive(p.getObjective()
				.getDescription()));
		JsonObject listOfCards = (JsonObject) this.serializeCards(p.getCards());

		result.add("cards", listOfCards);
		result.add("objective",
				this.serializeObjective(p.getObjective(), type, context));

		return result;
	}

	public JsonElement serializeObjective(final WarObjective o,
			final Type type, final JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("class", new JsonPrimitive(o.getClass().toString()));
		if (o instanceof ConquerContinentsObjective) {
			ConquerContinentsObjective cco = (ConquerContinentsObjective) o;
			result.addProperty("targetContinent1", cco.getTargetContinent1().getId());
			result.addProperty("targetContinent2", cco.getTargetContinent2().getId());
			result.addProperty("hasToConquerAThirdContinent", cco.hasToConquerAThirdContinent());
		} else if (o instanceof ConquerTerritoriesObjective) {
			ConquerTerritoriesObjective cto = (ConquerTerritoriesObjective) o;
			result.addProperty("numberOfArmiesInEach", cto.getNumberOfArmiesInEach());
			result.addProperty("numberOfTerritoriesToConquer", cto.getNumberOfTerritoriesToConquer());
		} else if (o instanceof DestroyPlayerObjective) {
			DestroyPlayerObjective dpo = (DestroyPlayerObjective) o;
			result.addProperty("targetPlayer", dpo.getTargetPlayer().getName());
		}
		return result;
	}

	public JsonElement serializeCards(List<Card> l) {
		JsonObject listOfCards = new JsonObject();
		for (Card c : l) {
			listOfCards.add(c.toString(),
					new JsonPrimitive(c.getType().getId()));
		}
		return listOfCards;
	}

	public JsonElement serializeMap(final Map m, final Type type,
			final JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		for (Territory t : m.getTerritories()) {
			result.add(t.getName(), this.serializeTerritory(t, type, context));
		}
		return result;
	}

	public JsonElement serializeTerritory(final Territory t, final Type type,
			final JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.addProperty("name", t.getName());
		result.addProperty("armyCount", t.getArmyCount());
		result.addProperty("continent", t.getContinent().getId());
		result.addProperty("owner", t.getOwner().getName());
		result.addProperty("moveableArmyCount", t.getMoveableArmyCount());
		return result;
	}

}
