package de.fuberlin.kundenprojekt.friedrich.storage;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import de.fuberlin.kundenprojekt.friedrich.models.User;

import java.lang.reflect.Type;

/**
 * @author Team Friedrich
 */
public class UserTypeAdapter implements JsonSerializer<User> {
    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", user.getId());
        jsonObject.addProperty("username", user.getUsername());
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("phone", user.getPhone());
        jsonObject.addProperty("full_name", user.getFullName());

        return jsonObject;
    }
}
