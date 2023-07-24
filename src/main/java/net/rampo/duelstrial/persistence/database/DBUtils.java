package net.rampo.duelstrial.persistence.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.UUID;

public class DBUtils {

    public static MongoDatabase database;
    public static MongoCollection<Document> players;
    public static boolean connect(){
        try {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("duelsTrial");
            boolean collectionExists = database.listCollectionNames()
                    .into(new ArrayList<>()).contains("players");
            if (!collectionExists) {
                database.createCollection("players");
            }
            players = database.getCollection("players");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean savePlayer(PlayerData playerData){
        try {
            Document document = new Document("uuid", playerData.getUuid().toString())
                    .append("wins", playerData.getWins())
                    .append("losses", playerData.getLosses())
                    .append("kills", playerData.getKills())
                    .append("deaths", playerData.getDeaths())
                    .append("winStreak", playerData.getWinStreak())
                    .append("currentWinStreak", playerData.getCurrentWinStreak());
            players.insertOne(document);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean updatePlayer(PlayerData playerData){
        try {
            Document document = new Document("uuid", playerData.getUuid().toString())
                    .append("wins", playerData.getWins())
                    .append("losses", playerData.getLosses())
                    .append("kills", playerData.getKills())
                    .append("deaths", playerData.getDeaths())
                    .append("winStreak", playerData.getWinStreak())
                    .append("currentWinStreak", playerData.getCurrentWinStreak());
            players.updateOne(new Document("uuid", playerData.getUuid().toString()), document);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static PlayerData getPlayer(UUID uuid){
        try {
            Document document = players.find(new Document("uuid", uuid.toString())).first();
            if (document == null) {
                return null;
            }
            return PlayerData.builder()
                .uuid(uuid)
                .wins(document.getInteger("wins"))
                .losses(document.getInteger("losses"))
                .kills(document.getInteger("kills"))
                .deaths(document.getInteger("deaths"))
                .winStreak(document.getInteger("winStreak"))
                .currentWinStreak(document.getInteger("currentWinStreak"))
                .build();
        } catch (Exception e) {
            return null;
        }
    }
}
