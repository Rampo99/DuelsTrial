package net.rampo.duelstrial.persistence.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.java.Log;
import org.bson.Document;

import java.util.ArrayList;
import java.util.UUID;

@Log
public class DBUtils {

    public static MongoDatabase database;
    public static MongoCollection<Document> players;

    public static MongoClient mongoClient;
    public static boolean connect(){
        String connectionString = System.getenv("MONGO_URI");
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try {
            mongoClient = MongoClients.create(settings);
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

    public static void savePlayer(PlayerData playerData){
        try {
            Document document = new Document("_id", playerData.getUuid().toString())
                    .append("wins", playerData.getWins())
                    .append("losses", playerData.getLosses())
                    .append("kills", playerData.getKills())
                    .append("deaths", playerData.getDeaths())
                    .append("winStreak", playerData.getWinStreak())
                    .append("currentWinStreak", playerData.getCurrentWinStreak());
            players.insertOne(document);
        } catch (Exception e) {
            log.severe("Failed to save player data for " + playerData.getUuid().toString());
            e.printStackTrace();
        }
    }

    public static boolean updatePlayer(PlayerData playerData){
        try {
            Document document = new Document("_id", playerData.getUuid().toString())
                    .append("wins", playerData.getWins())
                    .append("losses", playerData.getLosses())
                    .append("kills", playerData.getKills())
                    .append("deaths", playerData.getDeaths())
                    .append("winStreak", playerData.getWinStreak())
                    .append("currentWinStreak", playerData.getCurrentWinStreak());
            players.updateOne(new Document("_id", playerData.getUuid().toString()), document);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static PlayerData getPlayer(UUID uuid){
        try {
            Document document = players.find(new Document("_id", uuid.toString())).first();
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
