package com.example.archivos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private static final String FILE_PATH = "personas.json";
    private List<Persona> personas;
    private Gson gson;

    public PersonaDAO() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        personas = loadPersonas();
    }

    private List<Persona> loadPersonas() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Persona>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>(); // Si el archivo no existe, devolver una lista vacía
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void savePersonas() { 
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(personas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createPersona(Persona persona) {
        personas.add(persona);
        savePersonas();
    }

    public List<Persona> readPersonas() {
        return new ArrayList<>(personas);
    }

    public void updatePersona(Persona updatedPersona) {
        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).getId() == updatedPersona.getId()) {
                personas.set(i, updatedPersona);
                savePersonas();
                return;
            }
        }
    }

    public void deletePersona(int id) {
        personas.removeIf(persona -> persona.getId() == id);
        savePersonas();
    }
}
